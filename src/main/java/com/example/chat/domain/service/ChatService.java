package com.example.chat.domain.service;

import java.util.List;

import com.example.chat.domain.entity.Chat;

public interface ChatService {
	
	void send(Chat message);
	
	List<Chat> listChats(String id);

}
