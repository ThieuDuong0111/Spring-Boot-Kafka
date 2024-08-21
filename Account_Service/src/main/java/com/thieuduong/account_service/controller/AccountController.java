package com.thieuduong.account_service.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.account_service.model.AccountDTO;
import com.thieuduong.account_service.model.MessageDTO;
import com.thieuduong.account_service.model.StatisticDTO;
import com.thieuduong.account_service.repository.AccountRepo;
import com.thieuduong.account_service.repository.MessageRepo;
import com.thieuduong.account_service.repository.StatisticRepo;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	MessageRepo messageRepo;

	@Autowired
	StatisticRepo statisticRepo;

	@PostMapping("/new")
	public AccountDTO create(@RequestBody AccountDTO account) {

		StatisticDTO stat = new StatisticDTO(
			"Account " + account.getEmail() + " is created", new Date());
		stat.setStatus(false);

		// send notificaiton
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setTo(account.getEmail());
		messageDTO.setToName(account.getName());
		messageDTO.setSubject("Welcome to JMaster.io");
		messageDTO.setContent("JMaster is online learning platform.");
		messageDTO.setStatus(false);

		accountRepo.save(account);
		messageRepo.save(messageDTO);
		statisticRepo.save(stat);
		
		kafkaTemplate.send("statistic", stat);

//		for (int i = 0; i < 100; i++)
//			kafkaTemplate.send("notification", messageDTO).whenComplete(
//				(result, ex) -> {
//					if (ex == null) {
//						System.out
//							.println("Sent message=[" + messageDTO.getId() +
//								"] with offset=["
//								+ result.getRecordMetadata().offset() + "]");
//						messageDTO.setStatus(true);// success
////						messageRepo.save(messageDTO);
//					} else {
//						System.out.println("Unable to send message=[" +
//							messageDTO.getId() + "] due to : "
//							+ ex.getMessage());
//					}
//				});

		return account;
	}
}
