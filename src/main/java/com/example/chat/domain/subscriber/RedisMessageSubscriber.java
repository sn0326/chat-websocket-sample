package com.example.chat.domain.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.chat.domain.entity.Chat;
import com.example.chat.domain.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        
        Chat chatMessage;
		try {
			chatMessage = objectMapper.readValue(msg, Chat.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("json error", e);
		}
        log.info("Received message: {}", msg);
       
        // メッセージを永続化
        //chatService.save(chatMessage);
        
        // オンラインの場合、配信
        if (isUserOnline(chatMessage.getTo())) {
        	String to = chatMessage.getTo();
        	String from = chatMessage.getFrom();
        	String uri = UriComponentsBuilder.fromUriString("/topic/messages/").pathSegment(to,from).encode().build().toUriString();
            messagingTemplate.convertAndSend(uri, chatMessage);
        }
    }

    private boolean isUserOnline(String userId) {
        // Implement the logic to check if the user is online
        return true; // Placeholder
    }
}
