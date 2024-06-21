package com.example.chat.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chat.domain.entity.Chat;
import com.example.chat.domain.publisher.MessagePublisher;
import com.example.chat.domain.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final MessagePublisher messagePublisher;

	private final ChatMessageRepository chatMessageRepository;

	@Override
	public void send(Chat message) {
		// 時刻付与
		long timestamp = System.currentTimeMillis();
		message.setTimestamp(timestamp);
		
		chatMessageRepository.put(message);
		messagePublisher.publish(message);

	}

	@Override
	public List<Chat> listChats(String id) {
		return chatMessageRepository.query(id);
	}


}
