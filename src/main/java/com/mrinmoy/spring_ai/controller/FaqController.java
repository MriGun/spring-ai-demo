package com.mrinmoy.spring_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FaqController {

    private final OpenAiChatModel chatModel;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/rag-prompt-template.st")
    private Resource ragPromptTemplete;

    public FaqController(OpenAiChatModel chatModel, VectorStore vectorStore, VectorStore vectorStore1) {
        this.vectorStore = vectorStore1;
        this.chatModel = chatModel;
    }

    @GetMapping("/faq")
    public String faq(@RequestParam(value = "message") String message) { //defaultValue = "How many athletes compete in the Olympic Games Paris 2024"
        System.out.println("faq entered : " + message);
        List<Document> similiarDocuments = vectorStore.similaritySearch(SearchRequest.builder().query(message).topK(2).build());
        List<String> contentList = similiarDocuments.stream().map(Document::getText).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplete);

        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", message);
        promptParameters.put("documents", String.join("\n", contentList));

        Prompt prompt = promptTemplate.create(promptParameters);

        return chatModel.call(prompt.getContents());
    }
}
