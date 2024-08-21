package com.thieuduong.statistic_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.thieuduong.statistic_service.entity.Statistic;
import com.thieuduong.statistic_service.repository.StatisticRepository;

@Service
public class StatisticService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatisticRepository statisticRepository;

	@KafkaListener(id = "statisticGroup", topics = "statistic")
	public void listen(Statistic statistic) {
		logger.info("Received: " + statistic.getMessage());
		statisticRepository.save(statistic);
	}
}
