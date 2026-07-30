package com.Spring.Ai.Learning.Learn.SpringAI.controller;

import com.Spring.Ai.Learning.Learn.SpringAI.service.RAGService;
import com.Spring.Ai.Learning.Learn.SpringAI.tool.TravelTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChatControler {

        private final ChatClient chatClient;
        private final TravelTool travelTool;
        private final RAGService ragService;
    @PostMapping("/chat")
    public String chat(@RequestParam String message){
        return chatClient.prompt()
                .user(message)
                .tools(travelTool)
                .call().content();

    }
    @PostMapping("/advisor")
    public String getMessage(@RequestParam String message, @RequestParam String conversationId){
        return ragService.testAdvisor(message,conversationId);
    }

}
