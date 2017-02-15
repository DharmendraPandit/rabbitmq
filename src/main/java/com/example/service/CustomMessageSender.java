package com.example.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.SpringBootAmqpMessagingApplication;
import com.example.model.CustomMessage;


@Service
public class CustomMessageSender {

	private static final Logger log = LoggerFactory.getLogger(CustomMessageSender.class);

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public CustomMessageSender(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Scheduled(fixedDelay = 8000L)
	public void sendMessage() {
		
		/*System.out.println("host name: "+ rabbitTemplate.getConnectionFactory().getHost());
		System.out.println("port: "+ rabbitTemplate.getConnectionFactory().getPort());
		System.out.println("user name: "+ rabbitTemplate.getConnectionFactory().getUsername());
		System.out.println("virtual host: "+ rabbitTemplate.getConnectionFactory().getVirtualHost());*/
		
		final CustomMessage message = new CustomMessage("Hello there!", new Random().nextInt(50), false);
		log.info("Sending message...");
		rabbitTemplate.convertAndSend(SpringBootAmqpMessagingApplication.EXCHANGE_NAME,
				SpringBootAmqpMessagingApplication.ROUTING_KEY, message);
	}
}
