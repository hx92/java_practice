package com.demo.springai.aidemo;

import com.demo.springai.aidemo.entity.Response;
import com.demo.springai.aidemo.functioncall.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@SpringBootApplication
public class AidemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AidemoApplication.class, args);
	}
	@Bean
	@Description("根据用户编号和订单编号查询订单信息")
	public Function<OrderService.Request, Response> getOrderFunction(OrderService orderService) {
		return orderService::getOrder;
	}

}
