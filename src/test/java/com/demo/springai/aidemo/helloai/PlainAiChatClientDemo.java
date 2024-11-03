package com.demo.springai.aidemo.helloai;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

public class PlainAiChatClientDemo {
    private static final String AI_OPENAI_BASEURL = System.getenv("AI_OPENAI_BASEURL");
    private static final String AI_OPENAI_KEY = System.getenv("AI_OPENAI_KEY");
    private static final String AI_OPENAI_MODEL = System.getenv("AI_OPENAI_MODEL");

    @Test
    public void testClientHello() {
        ChatClient chatClient = ChatClient.builder(buildChatModel()).build();
        System.out.println(chatClient.prompt().system("你是一个办公助手").user("你好，你是谁？").call().content());
    }


    @Test
    public void testClientWithSystem() {
        ChatClient chatClient = ChatClient.builder(buildChatModel()).build();
        System.out.println(chatClient.prompt().system("你是一个办公助手").user("帮我定间会议室，时间是今天9点半，预计1小时，定在A001号会议室，需要提供白板").call().content());
    }


    @NotNull
    private static OpenAiChatModel buildChatModel() {
        OpenAiApi openAiApi = new OpenAiApi(AI_OPENAI_BASEURL, AI_OPENAI_KEY);
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withModel(AI_OPENAI_MODEL).build();
        return new OpenAiChatModel(openAiApi, chatOptions);
    }

}
