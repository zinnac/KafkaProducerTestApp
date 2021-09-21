package com.zinac.demo;

import java.util.Properties;
import com.google.gson.Gson;  

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


@RestController
public class ProducerController {

	@GetMapping("/")
	@ResponseBody
	public String index(@RequestParam String userId, @RequestParam String firstName, @RequestParam String lastName) {
		final String topic = "WebUsers";
		Producer<String,String> producer = createProducer();
		user tempUser = new user(userId,firstName,lastName);
		producer.send(new ProducerRecord<String,String>(topic,new Gson().toJson(tempUser)));
		producer.flush();
		producer.close();
		return "Success: "+tempUser.toString();
	}
	

	
	private static Producer<String, String> createProducer(){
		Properties config = new Properties();
		config.put("bootstrap.servers", "10.0.0.200:9092");
		config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return new KafkaProducer<>(config);
	}

}