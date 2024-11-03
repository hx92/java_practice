package com.demo.springai.aidemo;

import com.demo.springai.aidemo.entity.Response;
import com.demo.springai.aidemo.functioncall.service.OrderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import java.util.function.Function;

public class Demo {
    public static void main(String[] args) {
        ChatClient chatClient = ChatClient.builder(buildChatModel()).build();
        System.out.println(
                chatClient.prompt().function("OrderTool", "根据用户编号和订单编号查询订单信息", getGetOrder())
                        .user(String.format("帮我查询一下订单, 用户编号为%s, 订单编号为%s", "22222", "23333"))
                        .call().content());

    }


    public void test() {
        OpenAiChatModel chatModel = buildChatModel();
        System.out.println(chatModel.call("hello"));
    }

    @NotNull
    private static OpenAiChatModel buildChatModel() {
        String aiOpenaiBaseurl = System.getenv("AI_OPENAI_BASEURL");
        String aiOpenaiKey = System.getenv("AI_OPENAI_KEY");
        String aiOpenaiModel = System.getenv("AI_OPENAI_MODEL");
//        aiOpenaiModel = "Qwen/Qwen2.5-72B-Instruct-128K";
        OpenAiApi openAiApi = new OpenAiApi(aiOpenaiBaseurl, aiOpenaiKey);
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withModel(aiOpenaiModel).build();
        return new OpenAiChatModel(openAiApi, chatOptions);
    }

    public void testClientHello() {
        ChatClient chatClient = ChatClient.builder(buildChatModel()).build();
        System.out.println(chatClient.prompt("hello").call().content());
    }

    public void testClientWithFunc() {
        ChatClient chatClient = ChatClient.builder(buildChatModel()).build();
        System.out.println(
                chatClient.prompt().function("OrderTool", "根据用户编号和订单编号查询订单信息", getGetOrder())
                        .user(String.format("帮我查询一下订单, 用户编号为%s, 订单编号为%s", "22222", "23333"))
                        .call().content());

    }

    @NotNull
    private static Function<OrderService.Request, Response> getGetOrder() {
        return new OrderService()::getOrder;
    }
}
