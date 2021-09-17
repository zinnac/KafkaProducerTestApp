package com.zinac.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;

import java.time.Duration;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaProducerTestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerTestAppApplication.class, args);
		
		new Thread(new Runnable(){
			public void run() {
				produceMessages();
			}
		}).start();
		
		new Thread(new Runnable(){
			public void run() {
				consumeMessages();
			}
		}).start();
	}

	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}
	
	public static void produceMessages() {
		Properties config = new Properties();
		final String topic = "javatestapplication";
		//config.put("client.id", InetAddress.getLocalHost().getHostName());
		config.put("bootstrap.servers", "10.0.0.200:9092");
		config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> producer = new KafkaProducer<>(config);
		int i = 0;

		while(true){
			try{
				Thread.sleep(500);
				final String output = String.valueOf(i);
				producer.send(
				new ProducerRecord<String,String>(topic, "Test: "+i),
				(event, ex) ->{
					if(ex != null) ex.printStackTrace(); 
					else System.out.printf("\nProduced event to topic %s: Test: %s",topic,output);
				});
			i++;
			}
			catch(Exception e){
				producer.flush();
				producer.close();
				System.out.println(e);
				System.out.println("Program has ended."+"\n");
			}
		}
	}
	
	public static void consumeMessages() {
		Consumer<String,String> consumer = createConsumer();
		while(true) {
			try {
			Thread.sleep(10000);
			consumer.poll(Duration.ofMillis(1000)).forEach(record -> processRecord(record));	
			consumer.commitSync();	
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
		
		
	public static void processRecord(ConsumerRecord<String,String> record) {
		System.out.printf("\nConsumed event from topic: key:%s value:%s",record.key(),record.value());
	}
	
	private static Consumer<String, String> createConsumer(){

		Properties config = new Properties();
		final String topic = "javatestapplication";
		//config.put("client.id", InetAddress.getLocalHost().getHostName());
		config.put("group.id", "java-apps");
		config.put("bootstrap.servers", "10.0.0.200:9092");
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put("max.poll.records", "5");
		config.put("session.timeout.ms", "30000");
		
		Consumer<String,String> consumer = new KafkaConsumer<>(config);
		consumer.subscribe(Collections.singletonList(topic));
		return consumer;
	}

}
