package com.example.touk.toukparkometer.dao;


import com.example.touk.toukparkometer.dao.model.ParkEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface ParkEventRepository extends JpaRepository<ParkEvent, Long> {
    ParkEvent findByCustomerIdentity(String customerIdentity);

    List<ParkEvent> findByEndDateIsBetween(LocalDateTime startDay, LocalDateTime endDay);

    default List<ParkEvent> findParkEventsFinishedAt(LocalDate date){
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        return findByEndDateIsBetween(dateTime, dateTime.plus(1, ChronoUnit.DAYS));
    }
}
