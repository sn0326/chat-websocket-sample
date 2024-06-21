package com.example.chat.domain.entity;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
public class Chat {
	
    @Getter(onMethod_=@DynamoDbPartitionKey)
	private String id;
    
    @Getter(onMethod_=@DynamoDbSortKey)
    private Long timestamp;
    
    private String from;
    
    private String to;
    
    private String content;

}
