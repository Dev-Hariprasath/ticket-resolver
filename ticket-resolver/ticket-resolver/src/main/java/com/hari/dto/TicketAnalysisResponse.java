package com.hari.dto;


import com.hari.enums.AiDecision;

import java.util.List;

public record TicketAnalysisResponse(

        AiDecision decision,

        double confidence,

        String reason,

        String knowledgeArticleId,

        List<ResolutionStep> resolutionSteps,

        String suggestedResponse

) {
}
