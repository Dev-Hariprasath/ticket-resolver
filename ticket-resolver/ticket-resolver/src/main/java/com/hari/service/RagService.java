package com.hari.service;

import com.hari.model.KnowledgeArticle;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RagService {

    private final VectorStore vectorStore;

    public RagService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void indexArticle(
            KnowledgeArticle article
    ) {

        String content =
                buildDocumentContent(article);

        Document document =
                new Document(
                        content,
                        Map.of(
                                "knowledgeArticleId",
                                article.getId(),

                                "title",
                                article.getTitle(),

                                "category",
                                article.getCategory(),

                                "subCategory",
                                article.getSubCategory()
                        )
                );

        vectorStore.add(
                List.of(document)
        );
    }

    public List<Document> search(
            String query
    ) {

        SearchRequest request =
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .similarityThreshold(0.50)
                        .build();

        return vectorStore.similaritySearch(
                request
        );
    }

    private String buildDocumentContent(
            KnowledgeArticle article
    ) {

        StringBuilder content =
                new StringBuilder();

        content.append(
                "Knowledge Article ID: "
        ).append(
                article.getId()
        ).append("\n");

        content.append(
                "Title: "
        ).append(
                article.getTitle()
        ).append("\n");

        content.append(
                "Category: "
        ).append(
                article.getCategory()
        ).append("\n");

        content.append(
                "Sub Category: "
        ).append(
                article.getSubCategory()
        ).append("\n");

        content.append(
                "Problem: "
        ).append(
                article.getProblem()
        ).append("\n");

        content.append(
                "Resolution Steps:\n"
        );

        int stepNumber = 1;

        for (
                String step
                : article.getResolutionSteps()
        ) {

            content.append(
                            stepNumber
                    )
                    .append(". ")
                    .append(step)
                    .append("\n");

            stepNumber++;
        }

        return content.toString();
    }
}