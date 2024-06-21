package com.example.chat.domain.repository;

import java.util.List;

import com.example.chat.domain.entity.Chat;

public interface ChatMessageRepository {
	
	void put(Chat message);
	
	List<Chat> query(String id);
}
