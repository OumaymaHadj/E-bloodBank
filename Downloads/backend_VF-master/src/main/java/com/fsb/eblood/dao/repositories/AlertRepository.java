package com.fsb.eblood.dao.repositories;

import com.fsb.eblood.dao.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Integer> {

    List<Alert> findAlertById(int id);
    List<Alert> findAllByOrderByIdDesc();

    long countAlertByCreatedAt(String date);
}
