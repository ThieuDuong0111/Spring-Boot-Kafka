package com.thieuduong.account_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.thieuduong.account_service.model.StatisticDTO;

public interface StatisticRepo extends JpaRepository<StatisticDTO, Integer> {
	List<StatisticDTO> findByStatus(boolean status);
}
