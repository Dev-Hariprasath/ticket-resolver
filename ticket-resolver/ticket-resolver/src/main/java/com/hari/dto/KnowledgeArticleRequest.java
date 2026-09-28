package com.hari.dto;

import java.util.List;

public class KnowledgeArticleRequest {

    private String title;

    private String category;

    private String subCategory;

    private String problem;

    private List<String> resolutionSteps;


    public KnowledgeArticleRequest() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }


    public List<String> getResolutionSteps() {
        return resolutionSteps;
    }

    public void setResolutionSteps(List<String> resolutionSteps) {
        this.resolutionSteps = resolutionSteps;
    }
}
