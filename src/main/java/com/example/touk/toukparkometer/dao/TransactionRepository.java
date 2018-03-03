package com.example.touk.toukparkometer.dao;


import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<ParkEvent, Long> {
    List<ParkEvent> findAllByEndDateIsBetweenAndPriceIsNotNull(LocalDateTime startOfDay, LocalDateTime endOfDay);
    ParkEvent findFirstByEndDateIsNull();
}
