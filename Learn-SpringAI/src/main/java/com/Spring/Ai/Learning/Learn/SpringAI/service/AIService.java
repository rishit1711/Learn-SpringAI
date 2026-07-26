package com.Spring.Ai.Learning.Learn.SpringAI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String askAI(String question) {

        String template = """
        You are a helpful AI assistant.

        Use the retrieved context to answer the user's question.

        Rules:
        - Prefer information from the provided context.
        - Never invent facts.
        - If the answer is not present, say you don't have enough information.
        - Be clear and concise.
        - Preserve technical terminology.

        Context:
        {context}
        """;

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(2)
                        .build()
        );

        if (documents.isEmpty()) {
            return "I don't have enough information to answer that question.";
        }

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);

        String systemPrompt = promptTemplate.render(
                Map.of("context", context)
        );

        return chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();
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
