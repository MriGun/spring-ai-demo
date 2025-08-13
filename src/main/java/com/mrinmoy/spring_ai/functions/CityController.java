package com.mrinmoy.spring_ai.functions;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    private final OpenAiChatModel chatModel;

    public CityController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/cities")
    public String cities(@RequestParam(value = "messege") String messege) {

        SystemMessage systemMessage = new SystemMessage("You are a helpful AI Assistant answering questions about cities around the world.");
        UserMessage userMessage = new UserMessage(messege);

        ToolCallback[] weatherTools = ToolCallbacks.from(new WeatherServiceTool());

        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                                    .toolCallbacks(weatherTools)
                                    .build();

        ChatResponse chatResponse = chatModel
                .call(Prompt.builder()
                        .messages(List.of(systemMessage, userMessage))
                        .chatOptions(chatOptions)
                        .build());

        return chatResponse.getResult().getOutput().getText();

    }
}
