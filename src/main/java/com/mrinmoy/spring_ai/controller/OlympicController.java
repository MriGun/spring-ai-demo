package com.mrinmoy.spring_ai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/olympics")
public class OlympicController {

    private final OpenAiChatModel chatModel;

    @Value("classpath:/prompts/olympic-sports.st")
    private Resource olympicResource;

    @Value("classpath:/docs/olympic-sports.txt")
    private Resource docsToStuffResource;

    public OlympicController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/2024")
    public String get2024OlympicSports(
            @RequestParam(value = "message", defaultValue = "What sports are being included in the 2024 Summer Olympics?") String message,
            @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit
    ) throws IOException {

        PromptTemplate promptTemplate = new PromptTemplate(olympicResource);
        Map<String, Object> map = new HashMap<>();

        map.put("question", message);
        if (stuffit) {
            map.put("context", docsToStuffResource);
        }
        else {
            map.put("context", "");
        }

        Prompt prompt = promptTemplate.create(map);
        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }

}
