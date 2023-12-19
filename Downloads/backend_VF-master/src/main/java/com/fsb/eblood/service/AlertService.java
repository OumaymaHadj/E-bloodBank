package com.fsb.eblood.service;

import com.fsb.eblood.dao.entities.Alert;
import com.fsb.eblood.dao.entities.Publication;
import com.fsb.eblood.dao.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public Alert saveAlert(Alert alert){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        alert.setCreatedAt(dtf.format(LocalDateTime.now()));
        alert.setCreatedAt(dtf1.format(LocalDateTime.now()));
        return alertRepository.save(alert);
    }

    public Alert updateAlert(Alert alert) {
        Alert alert1 = alertRepository.getById(alert.getId());
        if(!alert.isAccepted()) alert1.setAccepted(true);
        return alertRepository.save(alert1);
    }

    public List<Alert> getAlert(int id) {
        return alertRepository.findAlertById(id);
    }

    public void deleteAlert(int id){
        alertRepository.deleteById(id);
    }

    public List<Alert> getAll() {
       return alertRepository.findAllByOrderByIdDesc();
    }

    public  long countAlert(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date = dtf.format(LocalDateTime.now());
        return alertRepository.countAlertByCreatedAt(date);
    }
}
