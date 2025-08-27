package com.mrinmoy.spring_ai.controller;

import com.mrinmoy.spring_ai.dto.BookRecommendation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final ChatClient chatClient;

    public BookController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/book")
    public BookRecommendation home() {
        return chatClient.prompt()
                .user("Generate a book recommendation for a book on AI and coding. Please limit the summary to 100 words.")
                .call()
                .entity(BookRecommendation.class);
    }

}
