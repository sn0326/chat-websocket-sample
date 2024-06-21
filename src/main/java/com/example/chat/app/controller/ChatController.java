package com.example.chat.app.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.domain.entity.Chat;
import com.example.chat.domain.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    
    private final ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(Chat message) {
    	chatService.send(message);
    }
    
    @GetMapping("/api/chat/{from}/{to}")
    public List<Chat> listChats(@PathVariable("from")String from, @PathVariable("to")String to){
    	from = URLEncoder.encode(from, StandardCharsets.UTF_8);
    	to =  URLEncoder.encode(to, StandardCharsets.UTF_8);
    	
		return Stream.concat(chatService.listChats(from + "/" + to).stream(), chatService.listChats(to + "/" + from).stream())
				.sorted(Comparator.comparing(Chat::getTimestamp))
                .collect(Collectors.toList());
    	
    }
    
}
