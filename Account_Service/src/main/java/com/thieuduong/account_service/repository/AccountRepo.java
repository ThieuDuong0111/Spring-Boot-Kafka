package com.thieuduong.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thieuduong.account_service.model.AccountDTO;

public interface AccountRepo extends JpaRepository<AccountDTO, Integer> {

}
