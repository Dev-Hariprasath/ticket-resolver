package com.hari.ai;

import com.hari.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AiPromptService {

    public String buildPrompt(
            Ticket ticket,
            List<String> knowledgeContext
    ) {

        String context =
                String.join(
                        "\n\n--- KNOWLEDGE ARTICLE ---\n\n",
                        knowledgeContext
                );

        return """
                You are an AI IT Support Ticket Resolution Agent.

                Analyze the ticket using ONLY the provided
                knowledge base context.

                RULES:

                1. Do not invent troubleshooting steps.
                2. Do not use your general knowledge.
                3. Use only the provided knowledge articles.
                4. If the knowledge clearly matches the ticket,
                   choose RESOLVE.
                5. If the knowledge does not provide enough
                   information, choose CREATE_TICKET.
                6. Resolution steps must come from the knowledge
                   base.
                7. Return only valid JSON.
                8. Do not return markdown.
                9. Confidence must be between 0 and 1.

                TICKET

                ID:
                %s

                Name:
                %s

                Category:
                %s

                Sub Category:
                %s

                Priority:
                %s

                Description:
                %s


                RETRIEVED KNOWLEDGE BASE

                %s


                REQUIRED JSON

                {
                  "decision": "RESOLVE",
                  "confidence": 0.0,
                  "reason": "reason",
                  "knowledgeArticleId": "KB-ID",
                  "resolutionSteps": [
                    {
                      "stepNumber": 1,
                      "action": "documented step"
                    }
                  ],
                  "suggestedResponse": "response"
                }

                OR

                {
                  "decision": "CREATE_TICKET",
                  "confidence": 0.0,
                  "reason": "reason",
                  "knowledgeArticleId": null,
                  "resolutionSteps": [],
                  "suggestedResponse": "response"
                }
                """
                .formatted(
                        ticket.getId(),
                        ticket.getName(),
                        ticket.getCategory(),
                        ticket.getSubCategory(),
                        ticket.getPriority(),
                        ticket.getDescription(),
                        context
                );
    }
}