package com.demo.springai.aidemo.functioncall.service;

import com.demo.springai.aidemo.entity.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import javax.annotation.concurrent.Immutable;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    private final Map<Request, String> orderInfo = ImmutableMap.of(new Request("1111", "AAAA"), "尤尼克斯羽毛球拍");

    public Response getOrder(Request request) {
        String productName = orderInfo.getOrDefault(request, "默认商品");
        return new Response(String.format("%s的订单编号为%s, 购买的商品为: %s", request.userId, request.orderId, productName));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Request(
            @JsonProperty(required = true,
                    value = "orderId") @JsonPropertyDescription("订单编号, 比如1001***") String orderId,
            @JsonProperty(required = true,
                    value = "userId") @JsonPropertyDescription("用户编号, 比如2001***") String userId) {
    }

}
