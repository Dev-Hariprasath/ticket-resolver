package com.hari.controller;

import com.hari.dto.KnowledgeArticleRequest;
import com.hari.model.KnowledgeArticle;
import com.hari.service.KnowledgeBaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;


    public KnowledgeBaseController(
            KnowledgeBaseService knowledgeBaseService
    ) {

        this.knowledgeBaseService =
                knowledgeBaseService;
    }


    @PostMapping
    public KnowledgeArticle createArticle(

            @RequestBody KnowledgeArticleRequest request

    ) {

        return knowledgeBaseService
                .createArticle(request);
    }


    @GetMapping
    public List<KnowledgeArticle> getAllArticles() {

        return knowledgeBaseService
                .getAllArticles();
    }


    @GetMapping("/{id}")
    public KnowledgeArticle getArticle(

            @PathVariable String id

    ) {

        return knowledgeBaseService
                .getArticle(id);
    }
}
