package com.hari.model;

import java.util.List;

public class KnowledgeArticle {

    private String id;

    private String title;

    private String category;

    private String subCategory;

    private String problem;

    private List<String> resolutionSteps;


    public KnowledgeArticle() {
    }


    public KnowledgeArticle(
            String id,
            String title,
            String category,
            String subCategory,
            String problem,
            List<String> resolutionSteps
    ) {

        this.id = id;
        this.title = title;
        this.category = category;
        this.subCategory = subCategory;
        this.problem = problem;
        this.resolutionSteps = resolutionSteps;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
