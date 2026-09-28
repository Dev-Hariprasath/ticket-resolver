package com.hari.repository;

import com.hari.model.KnowledgeArticle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class KnowledgeBaseRepository {

    private final Map<String, KnowledgeArticle> articles =
            new ConcurrentHashMap<>();


    public KnowledgeArticle save(
            KnowledgeArticle article
    ) {

        articles.put(
                article.getId(),
                article
        );

        return article;
    }


    public KnowledgeArticle findById(String id) {

        return articles.get(id);
    }


    public List<KnowledgeArticle> findAll() {

        return new ArrayList<>(
                articles.values()
        );
    }
}
