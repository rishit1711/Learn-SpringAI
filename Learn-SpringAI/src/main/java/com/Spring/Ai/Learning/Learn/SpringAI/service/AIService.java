package com.Spring.Ai.Learning.Learn.SpringAI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {
    private final ChatClient chatClient;

    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;
    public void ingest(){
        List<Document> docs = List.of(
                new Document("Spring Boot is a framework."),
                new Document("Spring AI supports RAG."),
                new Document("PGVector stores embeddings.")
        );
        vectorStore.add(docs);
        vectorStore.add(docs());

    }
    public static List<Document> docs(){
        return  List.of(
                new Document(
                        "Spring AI Introduction",
                        Map.of(
                                "source", "spring-ai.pdf",
                                "page", 1
                        )
                ),
                new Document(
                        "What is RAG?",
                        Map.of(
                                "source", "rag-guide.pdf",
                                "page", 5
                        )
                ),
                new Document(
                        "Vector Databases",
                        Map.of(
                                "source", "pgvector.pdf",
                                "page", 10
                        )
                )
        );
    }
    public String askAI(String prompt){
        return chatClient.prompt().user(prompt)
                .call().content();
    }
    public List<Document> similaritySearch(String text){
        return  vectorStore.similaritySearch(text);
    }

    public float[] getEmbed(String text){
        return embeddingModel.embed(text);
    }


    String getJoke(String topic){
        return chatClient.prompt()
                .system("You are a Sarcastic Joker , Give the joke in 2 lines")
                .user("Give me a Joke on the Topic : "+topic)
                .call()
                .content();
    }
}
