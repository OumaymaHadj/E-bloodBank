package com.fsb.eblood.service;


import com.fsb.eblood.dao.entities.Alert;
import com.fsb.eblood.dao.entities.Event;
import com.fsb.eblood.dao.entities.Invitation;
import com.fsb.eblood.dao.repositories.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    public Invitation saveInvitation(Invitation invitation){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        invitation.setCreatedAt(dtf.format(LocalDateTime.now()));
        return invitationRepository.save(invitation);
    }

    public void deleteInvitation(Event event) {
        invitationRepository.deleteByEvent(event);
    }

    public List<Invitation> getAll(){ return invitationRepository.findAllByOrderByIdDesc();}
    public Invitation updateInvit(Invitation invitation) {
        Invitation invit = invitationRepository.getById(invitation.getId());
        int i = invitation.getNbParticipants();
        invit.setNbParticipants(++i);
        return invitationRepository.save(invit);
    }
}
