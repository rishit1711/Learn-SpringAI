package com.Spring.Ai.Learning.Learn.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AItests {

    @Autowired
    private AIService aiService;
    @Test
    public  void testGetJoke(){
        var joke = aiService.getJoke("Java");
        System.out.println(joke);
    }
    @Test
    public  void testEmbed() {
        var embed = aiService.getEmbed("The weather is quite romantic");
        System.out.println(embed.length);
        for (float ele : embed) {
            System.out.println(ele + " ");
        }
    }
        @Test
                public void toStore(){
            aiService.ingest();
        }
    @Test
    public void search(){
        var res= aiService.similaritySearch("Spring Boot is a Good Framework");
        System.out.println(res);
    }


}


