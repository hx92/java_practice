package com.demo.springai.aidemo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AidemoHelloWorld {

	@Autowired
	ChatModel chatModel;
	@Test
	void helloWorld() {
		String hello = chatModel.call("hello");
		System.out.println(hello);
	}

}
