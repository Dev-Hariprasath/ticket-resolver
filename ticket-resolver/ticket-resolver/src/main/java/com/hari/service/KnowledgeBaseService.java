package com.hari.service;

import com.hari.dto.KnowledgeArticleRequest;
import com.hari.model.KnowledgeArticle;
import com.hari.repository.KnowledgeBaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KnowledgeBaseService {

    private final KnowledgeBaseRepository repository;

    private final RagService ragService;


    public KnowledgeBaseService(
            KnowledgeBaseRepository repository,
            RagService ragService
    ) {

        this.repository = repository;
        this.ragService = ragService;
    }


    public KnowledgeArticle createArticle(
            KnowledgeArticleRequest request
    ) {

        String id =
                "KB-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();


        KnowledgeArticle article =
                new KnowledgeArticle(

                        id,

                        request.getTitle(),

                        request.getCategory(),

                        request.getSubCategory(),

                        request.getProblem(),

                        request.getResolutionSteps()
                );


        repository.save(article);

        ragService.indexArticle(article);

        return article;
    }


    public List<KnowledgeArticle> getAllArticles() {

        return repository.findAll();
    }


    public KnowledgeArticle getArticle(String id) {

        return repository.findById(id);
    }
}
