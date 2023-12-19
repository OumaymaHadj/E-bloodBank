package com.fsb.eblood.service;

import com.fsb.eblood.dao.repositories.ConfirmationRepository;
import com.fsb.eblood.dao.entities.ConfirmationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ConfirmationService {

    @Autowired
    private ConfirmationRepository confirmationRepository;

    public void saveConfirmationEmail(ConfirmationEmail confirmationEmail){
        confirmationRepository.save(confirmationEmail);
    }


    public int setConfirmedAt(String email) {
        return confirmationRepository.updateConfirmedAt(
                email, LocalDateTime.now());
    }

    public Optional<ConfirmationEmail> findConfirmationByEmail(String email){
        return confirmationRepository.findByEmail(email);
    }
}

