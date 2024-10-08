package com.thieuduong.account_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thieuduong.account_service.model.MessageDTO;
import com.thieuduong.account_service.model.StatisticDTO;
import com.thieuduong.account_service.repository.MessageRepo;
import com.thieuduong.account_service.repository.StatisticRepo;

import java.util.List;

@Component
public class PollingService {
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	MessageRepo messageRepo;

	@Autowired
	StatisticRepo statisticRepo;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(fixedDelay = 1000)
	public void producer() {

		List<MessageDTO> messageDTOs = messageRepo.findByStatus(false);
		for (MessageDTO messageDTO : messageDTOs) {
			kafkaTemplate.send("notification", messageDTO)
				.whenComplete(
					(result, ex) -> {
						if (ex == null) {
							logger.info("Sent message=[" + messageDTO.getId() +
								"] with offset=["
								+ result.getRecordMetadata().offset() + "]");
							messageDTO.setStatus(true);// success
							messageRepo.save(messageDTO);
						} else {
							logger.error("Unable to send message=[" +
								messageDTO.getId() + "] due to : "
								+ ex.getMessage());
						}
					});

		}

		List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(false);
		for (StatisticDTO statisticDTO : statisticDTOs) {
			kafkaTemplate.send("statistic", statisticDTO).whenComplete(
				(result, ex) -> {
					if (ex == null) {
						logger.info("Sent message=[" + statisticDTO.getId() +
							"] with offset=["
							+ result.getRecordMetadata().offset() + "]");
						statisticDTO.setStatus(true);// success
						statisticRepo.save(statisticDTO);
					} else {
						logger.error("Unable to send message=[" +
							statisticDTO.getId() + "] due to : "
							+ ex.getMessage());
					}
				});
		}
	}

	@Scheduled(fixedDelay = 60000)
	public void delete() {
		List<MessageDTO> messageDTOs = messageRepo.findByStatus(true);
		messageRepo.deleteAllInBatch(messageDTOs);

		List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(true);
		statisticRepo.deleteAllInBatch(statisticDTOs);
	}
}