package com.example.chat.domain.publisher;

import com.example.chat.domain.entity.Chat;

public interface MessagePublisher {
	
	void publish(Chat message);

}
