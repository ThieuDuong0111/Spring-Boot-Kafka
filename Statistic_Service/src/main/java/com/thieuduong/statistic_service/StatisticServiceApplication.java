package com.thieuduong.statistic_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@SpringBootApplication
public class StatisticServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticServiceApplication.class, args);
	}

	@Bean
	JsonMessageConverter converter() {
		return new JsonMessageConverter();
	}

//	@Bean
//	DefaultErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
//		return new DefaultErrorHandler(
//			new DeadLetterPublishingRecoverer(template),
//			new FixedBackOff(1000L, 2));
//	}
//
//	@Bean
//	NewTopic dlt() {
//		return new NewTopic("statistic.DLT", 1, (short) 1);
//	}

}
