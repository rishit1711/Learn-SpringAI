package com.Spring.Ai.Learning.Learn.SpringAI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {
    private final ChatClient chatClient;

    String getJoke(String topic){
        return chatClient.prompt().user("Give me a Joke on the Topic : "+topic)
                .call()
                .content();
    }
}
