package com.thieuduong.account_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thieuduong.account_service.model.MessageDTO;

public interface MessageRepo extends JpaRepository<MessageDTO, Integer> {
	List<MessageDTO> findByStatus(boolean status);
}
