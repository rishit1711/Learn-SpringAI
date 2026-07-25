package com.Spring.Ai.Learning.Learn.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AItests {

    @Autowired
    private AIService aiService;
    @Test
    public  void testGetJoke(){
        var joke = aiService.getJoke("Java");
        System.out.println(joke);
    }
}
