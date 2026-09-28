package com.hari.dto;

import com.hari.enums.AiDecision;
import com.hari.enums.TicketPriority;
import com.hari.enums.TicketStatus;

import java.time.LocalDateTime;

public record TicketResponse(

        String id,

        String name,

        String category,

        String subCategory,

        TicketPriority priority,

        String description,

        TicketStatus status,

        AiDecision aiDecision,

        String resolution,

        String knowledgeArticleId,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}
