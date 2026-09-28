package com.hari.model;


import com.hari.enums.AiDecision;
import com.hari.enums.TicketPriority;
import com.hari.enums.TicketStatus;

import java.time.LocalDateTime;

public class Ticket {

    private String id;

    private String name;

    private String category;

    private String subCategory;

    private TicketPriority priority;

    private String description;

    private TicketStatus status;

    private AiDecision aiDecision;

    private String resolution;

    private String knowledgeArticleId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public Ticket() {
    }


    public Ticket(
            String id,
            String name,
            String category,
            String subCategory,
            TicketPriority priority,
            String description
    ) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.priority = priority;
        this.description = description;

        this.status = TicketStatus.OPEN;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }


    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }


    public AiDecision getAiDecision() {
        return aiDecision;
    }

    public void setAiDecision(AiDecision aiDecision) {
        this.aiDecision = aiDecision;
    }


    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }


    public String getKnowledgeArticleId() {
        return knowledgeArticleId;
    }

    public void setKnowledgeArticleId(String knowledgeArticleId) {
        this.knowledgeArticleId = knowledgeArticleId;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
