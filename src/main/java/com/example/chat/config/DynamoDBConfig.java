package com.example.chat.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chat.domain.entity.Chat;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

	@Bean
	DynamoDbEnhancedClient dynamoDbEnhancedClient() {
		DynamoDbClient standardClient  = DynamoDbClient.builder()
			    .region(Region.AP_NORTHEAST_1)
			    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("dummy", "dummy")))
			    .endpointOverride(URI.create("http://localhost:8000"))
			    .build();
		DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
			    .dynamoDbClient(standardClient)
			    .build();
		return enhancedClient;
	}
	
	@Bean
	DynamoDbTable<Chat> chatTable(DynamoDbEnhancedClient dynamoDbEnhancedClient){
		return dynamoDbEnhancedClient.table("Chat", TableSchema.fromBean(Chat.class));
	}
	
}
