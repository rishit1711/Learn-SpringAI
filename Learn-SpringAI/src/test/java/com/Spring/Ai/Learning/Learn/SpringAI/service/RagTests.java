package com.Spring.Ai.Learning.Learn.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RagTests {

    @Autowired
    RAGService ragService;

    @Test
    public void testIngest(){
         ragService.ingestPdfToVectorStore();

    }

}
