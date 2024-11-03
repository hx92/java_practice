package com.demo.springai.aidemo.controller;

import com.demo.springai.aidemo.entity.Response;
import com.demo.springai.aidemo.functioncall.service.DateService;
import com.demo.springai.aidemo.functioncall.service.WeatherService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@RestController
@RequestMapping("/ai/fc")
public class FunctionCallController {
    private final ChatClient chatClient;

    public FunctionCallController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/weather-service")
    public String weatherService(String subject) {
        String content = chatClient.prompt()
                .function("getWeather", "根据城市查询天气", new WeatherService())
                .function("getDate", "获取今天的日期", new DateService())
                .user(subject)
                .call()
                .content();
        return content;
    }

    @GetMapping("/order-detail")
    public String orderDetail(String userId,String orderId) {
        return chatClient.prompt()
                .functions("getOrderFunction")
                .user(String.format("帮我查询一下订单, 用户编号为%s, 订单编号为%s",userId,orderId))
                .call()
                .content();
    }

}
