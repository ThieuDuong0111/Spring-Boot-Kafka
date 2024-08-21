package com.thieuduong.statistic_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.statistic_service.entity.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

}
