package com.demo.springai.aidemo.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * * @author Christian Tzolov
 */
@Service
public class MeetingRoomBookingAgent {

    private final ChatClient chatClient;

    public MeetingRoomBookingAgent(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory) {
        // @formatter:off
        this.chatClient = modelBuilder
                .defaultSystem("""
                        您是“企业会议室管理系统”的智能助手。请以友好、乐于助人且专业的方式来回复。
                       您正在通过在线聊天系统与用户互动。
                       在提供有关会议室预定或取消预定的信息之前，您必须始终
                       从用户处获取以下信息：预定号、用户名。
                       在询问用户之前，请检查消息历史记录以获取此信息。
                       在更改预定之前，您必须确保条款允许这样做。
                       使用提供的功能获取预定详细信息、更改预定和取消预定。
                       如果需要，可以调用相应函数调用完成辅助动作。
                       请讲中文。
                       今天的日期是 {current_date}.
                    """)  // System Prompt
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()) // RAG
                        )
                .defaultFunctions("getReservationDetails", "changeReservation", "cancelReservation") // FUNCTION CALLING
                .build();
        // @formatter:on
    }

    public Flux<String> chat(String chatId, String userMessageContent) {
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .stream()
                .content();
    }
}