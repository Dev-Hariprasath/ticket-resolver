package com.hari.service;


import com.hari.ai.AiTicketResolver;
import com.hari.dto.ResolutionStep;
import com.hari.dto.TicketAnalysisResponse;
import com.hari.enums.AiDecision;
import com.hari.enums.TicketStatus;
import com.hari.model.Ticket;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketAnalysisService {

    private final TicketService ticketService;
    private final RagService ragService;
    private final AiTicketResolver aiTicketResolver;

    public TicketAnalysisService(
            TicketService ticketService,
            RagService ragService,
            AiTicketResolver aiTicketResolver
    ) {
        this.ticketService = ticketService;
        this.ragService = ragService;
        this.aiTicketResolver = aiTicketResolver;
    }

    public TicketAnalysisResponse analyzeTicket(
            String ticketId
    ) {

        Ticket ticket =
                ticketService.getTicket(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket not found: " + ticketId
            );
        }

        /*
         * Mark ticket as being analyzed.
         */
        ticket.setStatus(
                TicketStatus.AI_ANALYZING
        );

        ticketService.updateTicket(ticket);

        /*
         * Build semantic search query.
         */
        String query =
                buildSearchQuery(ticket);

        /*
         * RAG retrieval.
         */
        List<Document> documents =
                ragService.search(query);

        /*
         * IMPORTANT:
         *
         * If no relevant knowledge article exists,
         * do NOT ask Ollama to invent a solution.
         *
         * Directly create/assign a support ticket.
         */
        if (documents.isEmpty()) {

            TicketAnalysisResponse response =
                    createTicketDecision();

            applyDecision(
                    ticket,
                    response
            );

            ticketService.updateTicket(ticket);

            return response;
        }

        /*
         * We have relevant KB context.
         * Now Ollama is allowed to reason over
         * the retrieved knowledge.
         */
        List<String> knowledgeContext =
                documents.stream()
                        .map(Document::getText)
                        .toList();

        TicketAnalysisResponse aiResponse =
                aiTicketResolver.analyze(
                        ticket,
                        knowledgeContext
                );

        applyDecision(
                ticket,
                aiResponse
        );

        ticketService.updateTicket(ticket);

        return aiResponse;
    }

    private void applyDecision(
            Ticket ticket,
            TicketAnalysisResponse response
    ) {
        if (response.decision() == AiDecision.RESOLVE) {

            ticket.setStatus(TicketStatus.AI_RESOLVED);
            ticket.setAiDecision(AiDecision.RESOLVE);
            ticket.setKnowledgeArticleId(response.knowledgeArticleId());

            String resolution = buildResolution(response);

            ticket.setResolution(resolution);

        } else {

            ticket.setStatus(TicketStatus.ASSIGNED);
            ticket.setAiDecision(AiDecision.CREATE_TICKET);
            ticket.setKnowledgeArticleId(null);

            ticket.setResolution(
                    "No verified knowledge-base resolution was found. " +
                            "Ticket requires support engineer intervention."
            );
        }
    }

    private String buildResolution(
            TicketAnalysisResponse response
    ) {
        StringBuilder resolution = new StringBuilder();

        resolution.append("Troubleshooting Steps:\n\n");

        for (ResolutionStep step : response.resolutionSteps()) {

            resolution.append(step.stepNumber())
                    .append(". ")
                    .append(step.action())
                    .append("\n");
        }

        resolution.append("\nAI Support Response:\n\n");

        resolution.append(response.suggestedResponse());

        return resolution.toString();
    }

    private TicketAnalysisResponse createTicketDecision() {

        return new TicketAnalysisResponse(

                AiDecision.CREATE_TICKET,

                1.0,

                "No sufficiently relevant knowledge " +
                        "base article was found for this ticket.",

                null,

                List.of(),

                "This issue requires support engineer " +
                        "investigation."
        );
    }

    private String buildSearchQuery(
            Ticket ticket
    ) {

        return """
                Category: %s
                Sub Category: %s
                Priority: %s
                Ticket Description: %s
                """
                .formatted(
                        ticket.getCategory(),
                        ticket.getSubCategory(),
                        ticket.getPriority(),
                        ticket.getDescription()
                );
    }
}