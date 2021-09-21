package com.zinac.demo;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.kafka.clients.consumer.*;


@RestController
public class ConsumerController {

	@GetMapping("/test")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@GetMapping("/consume")
	public void consumeMessages() {
		/*try {
			while (true) {
				ConsumerRecords<String,String> records = createConsumer().poll(Duration.ofMillis(100));
			}
		} catch(Exception e) {
			return String.valueOf(e);
		}*/
		
	}
	
	private static Consumer<String, String> createConsumer(){

		Properties config = new Properties();
		final String topic = "javatestapplication";
		//config.put("client.id", InetAddress.getLocalHost().getHostName());
		config.put("bootstrap.servers", "10.0.0.200:9092");
		config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Consumer<String,String> consumer = new KafkaConsumer<>(config);
		consumer.subscribe(Collections.singletonList(topic));
		return consumer;
	}

}