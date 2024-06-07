package com.fontolan.springsqsexample.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SQSService {
    private final SqsClient sqsClient;
    private final String queueUrl = "http://localhost:4566/000000000000/sqs-queue"; // Substitua pelo URL da sua fila

    public SQSService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @PostConstruct
    public void start() {
        new Thread(() -> {
            while (true) {
                receiveMessages();
                try {
                    Thread.sleep(5000); // Aguardar 5 segundos antes de buscar novamente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AttributeOne", MessageAttributeValue.builder()
                .dataType("String")
                .stringValue("ValueOne")
                .build());
        messageAttributes.put("AttributeTwo", MessageAttributeValue.builder()
                .dataType("Number")
                .stringValue("123")
                .build());

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .messageAttributes(messageAttributes)
                .build();

        SendMessageResponse response = sqsClient.sendMessage(request);
        System.out.println("Message sent with ID: " + response.messageId());
    }

    public void receiveMessages() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(20)
                .messageAttributeNames("All")
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();
        for (Message message : messages) {
            for (Map.Entry<String, MessageAttributeValue> attributes : message.messageAttributes().entrySet()) {
                System.out.println("Key: " + attributes.getKey() + ", Value: " + attributes.getValue());
            }
            System.out.println("Message received: " + message.body());

            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();

            sqsClient.deleteMessage(deleteMessageRequest);
        }
    }
}
