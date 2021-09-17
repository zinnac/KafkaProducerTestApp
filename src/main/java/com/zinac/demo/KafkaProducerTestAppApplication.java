package com.zinac.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.clients.producer.*;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaProducerTestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerTestAppApplication.class, args);
		produceMessages();
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
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String, String> producer = new KafkaProducer<>(config);
		int i = 0;

		while(true){
			try{
				Thread.sleep(5000);
				
				producer.send(
				new ProducerRecord<String,String>(topic, "Test: "+i),
				(event, ex) ->{
					if(ex != null) ex.printStackTrace(); 
					else System.out.printf("Produced event to topic %s",topic);
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

}
