package com.mrinmoy.spring_ai.controller;


import com.mrinmoy.spring_ai.record.Author;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    private final OpenAiChatModel chatModel;

    @Value("classpath:/prompts/youtube.st")
    private Resource ytResource;

    public ChatController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/jokes")
    public String generate(@RequestParam(value = "messege", defaultValue = "Tell me a dad joke") String messege) {
        return chatModel.call(messege);
    }

    @GetMapping("/jokes2")
    public String generate2(@RequestParam(value = "messege", defaultValue = "Tell me a dad joke") String messege) {
        return chatModel.call(new Prompt("Tell me a joke about cat").getContents());
    }

    @GetMapping("/generete-by-genre")
    public String generatebygenre(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
        String messege = """
                List 10 of the most popular YouTubers in {genre} along with their current subscriber count. If you dont know the answer, just say "I dont know" 
                """;

        PromptTemplate promptTemplate = new PromptTemplate(messege);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        return chatModel.call(prompt.getContents());
    }

    @GetMapping("/generete-by-genre2")
    public String generatebygenre2(@RequestParam(value = "genre", defaultValue = "tech") String genre) {

        PromptTemplate promptTemplate = new PromptTemplate(ytResource);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        return chatModel.call(prompt.getContents());
    }

    @GetMapping("/parsing")
    public String parsing(@RequestParam(value = "genre", defaultValue = "tech") String genre) {

        PromptTemplate promptTemplate = new PromptTemplate(ytResource);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        ChatResponse chatResponse = chatModel.call(prompt);
        return chatResponse.getResult().getOutput().getText();
    }

    @GetMapping("/output-parsing")
    public List<String> outputParsing(@RequestParam(value = "genre", defaultValue = "tech") String genre) {

        String messege = """
                List 10 of the most popular YouTubers in {genre} along with their current subscriber count. If you dont know the answer, just say "I dont know {format}" 
                """;

        ListOutputConverter outputConverter = new ListOutputConverter();
        PromptTemplate promptTemplate = new PromptTemplate(messege);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre, "format", outputConverter.getFormat()));
        ChatResponse chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getText());
    }

    @GetMapping("/author/{author}")
    public Map<String, Object> getAuthorSocialList(@PathVariable String author) {

        String messege = """
                Generate a list of links for the author {author}. Include the authors name as the key and any social network links as object.{format}" 
                """;

        MapOutputConverter outputConverter = new MapOutputConverter();
        PromptTemplate promptTemplate = new PromptTemplate(messege);
        Prompt prompt = promptTemplate.create(Map.of("author", author, "format", outputConverter.getFormat()));
        ChatResponse chatResponse = chatModel.call(prompt);
        return outputConverter.convert(chatResponse.getResult().getOutput().getText());
    }

    @GetMapping("/by-author")
    public Author getAuthorBookList(@RequestParam(value = "author", defaultValue = "Humayun Ahmed")  String author) {

        String messege = """
                Generate a list of books written by the author {author}.{format}" 
                """;

        BeanOutputConverter outputConverter = new BeanOutputConverter<>(Author.class);
        PromptTemplate promptTemplate = new PromptTemplate(messege);
        Prompt prompt = promptTemplate.create(Map.of("author", author, "format", outputConverter.getFormat()));
        ChatResponse chatResponse = chatModel.call(prompt);
        return (Author) outputConverter.convert(chatResponse.getResult().getOutput().getText());
    }
}
