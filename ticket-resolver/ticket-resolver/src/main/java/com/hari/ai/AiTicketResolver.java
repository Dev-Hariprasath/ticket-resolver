package com.hari.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hari.dto.TicketAnalysisResponse;
import com.hari.model.Ticket;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiTicketResolver {

    private final ChatClient chatClient;
    private final AiPromptService promptService;
    private final ObjectMapper objectMapper;

    public AiTicketResolver(
            ChatClient.Builder chatClientBuilder,
            AiPromptService promptService,
            ObjectMapper objectMapper
    ) {

        this.chatClient =
                chatClientBuilder.build();

        this.promptService =
                promptService;

        this.objectMapper =
                objectMapper;
    }

    public TicketAnalysisResponse analyze(
            Ticket ticket,
            List<String> knowledgeContext
    ) {

        String prompt =
                promptService.buildPrompt(
                        ticket,
                        knowledgeContext
                );

        String response =
                chatClient
                        .prompt()
                        .user(prompt)
                        .call()
                        .content();

        if (response == null ||
                response.isBlank()) {

            throw new IllegalStateException(
                    "Ollama returned an empty response."
            );
        }

        /*
         * Some local models may wrap JSON
         * inside markdown code fences.
         */
        response =
                cleanJsonResponse(response);

        try {

            return objectMapper.readValue(
                    response,
                    TicketAnalysisResponse.class
            );

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Unable to parse Ollama response.\n"
                            + "Raw response:\n"
                            + response,
                    exception
            );
        }
    }

    private String cleanJsonResponse(
            String response
    ) {

        String cleaned =
                response.trim();

        if (cleaned.startsWith("```json")) {

            cleaned =
                    cleaned.substring(7);

        } else if (
                cleaned.startsWith("```")
        ) {

            cleaned =
                    cleaned.substring(3);
        }

        if (cleaned.endsWith("```")) {

            cleaned =
                    cleaned.substring(
                            0,
                            cleaned.length() - 3
                    );
        }

        return cleaned.trim();
    }
}