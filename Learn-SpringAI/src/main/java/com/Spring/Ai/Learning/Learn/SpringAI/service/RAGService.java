package com.Spring.Ai.Learning.Learn.SpringAI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;

    @Value("classpath:xyz.pdf")
     Resource pdfFile;


    public String askAIwithAdvisors(String prompt){
        return chatClient
                .prompt()
                .system("")
                .user(prompt)
                .advisors(
                        VectorStoreChatMemoryAdvisor.builder(vectorStore)
                                .build()
                )
                .call()
                .content();
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

    public void ingestPdfToVectorStore(){
        PagePdfDocumentReader reader =new PagePdfDocumentReader(pdfFile);
        List<Document> pages = reader.get();
        TokenTextSplitter splitter= TokenTextSplitter.builder()
                .withChunkSize(200)

                .build();

        List<Document> chunks = splitter.apply(pages);
        vectorStore.add(chunks);


    }



}
