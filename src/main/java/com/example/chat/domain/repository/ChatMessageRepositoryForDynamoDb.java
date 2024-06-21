package com.example.chat.domain.repository;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.chat.domain.entity.Chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatMessageRepositoryForDynamoDb implements ChatMessageRepository {
	
	private final DynamoDbTable<Chat> chatTable;

	@Override
	public void put(Chat message) {
		log.info(message.toString());
		chatTable.putItem(message);
		
	}

	@Override
	public List<Chat> query(String id) {
		QueryConditional keyEqual = QueryConditional.keyEqualTo(b -> b.partitionValue(id));
		 QueryEnhancedRequest tableQuery = QueryEnhancedRequest.builder()
	                .queryConditional(keyEqual)
	                .build();
		 PageIterable<Chat> pagedResults = chatTable.query(tableQuery);
		 log.info("page count: {}", pagedResults.stream().count()); // Log  number of pages.

		 return pagedResults.items().stream()
				 .sorted(Comparator.comparing(Chat::getTimestamp))
         .toList();
	}

}
