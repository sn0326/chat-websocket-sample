package com.example.chat.domain.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.example.chat.domain.entity.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {

	private final RedisTemplate<String, String> redisTemplate;
	private final ChannelTopic topic;
	private final ObjectMapper objectMapper;

	public void publish(Chat message) {
		String msg;
		try {
			msg = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("json error", e);
		}
		redisTemplate.convertAndSend(topic.getTopic(), msg);
	}
}
