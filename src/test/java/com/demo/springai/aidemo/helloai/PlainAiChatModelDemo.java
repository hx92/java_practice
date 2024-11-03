package com.demo.springai.aidemo.helloai;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

public class PlainAiChatModelDemo {
    private static final String AI_OPENAI_BASEURL = System.getenv("AI_OPENAI_BASEURL");
    private static final String AI_OPENAI_KEY = System.getenv("AI_OPENAI_KEY");
    private static final String AI_OPENAI_MODEL = System.getenv("AI_OPENAI_MODEL");

    @Test
    public void test() {
        OpenAiChatModel chatModel = buildChatModel();
        chatModel.call(new Prompt("", ChatOptionsBuilder.builder().withModel("").build()));
        System.out.println(chatModel.call("hello"));
    }

    @NotNull
    private static OpenAiChatModel buildChatModel() {
        OpenAiApi openAiApi = new OpenAiApi(AI_OPENAI_BASEURL, AI_OPENAI_KEY);
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withModel(AI_OPENAI_MODEL).build();
        return new OpenAiChatModel(openAiApi, chatOptions);
    }

}
