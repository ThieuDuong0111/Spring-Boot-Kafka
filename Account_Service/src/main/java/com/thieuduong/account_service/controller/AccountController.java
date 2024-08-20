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

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

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

		kafkaTemplate.send("notification", messageDTO);
		kafkaTemplate.send("statistic", stat);

		return account;
	}
}
