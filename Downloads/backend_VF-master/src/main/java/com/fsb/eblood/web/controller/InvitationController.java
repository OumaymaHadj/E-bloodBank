package com.fsb.eblood.web.controller;


import com.fsb.eblood.dao.entities.Alert;
import com.fsb.eblood.dao.entities.Invitation;
import com.fsb.eblood.dao.entities.Event;
import com.fsb.eblood.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/invitation")
@CrossOrigin(origins = "http://localhost:4200/")
public class InvitationController {

    @Autowired
    InvitationService invitationService;


    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Invitation saveInvitation(@RequestBody Invitation invitation){
        return invitationService.saveInvitation(invitation);
    }
    @PostMapping("/update")
    public Invitation updateInvitation(@RequestBody Invitation invitation)
    { return invitationService.updateInvit(invitation);}

    @DeleteMapping("/delete")
    public void deleteInvitation(@RequestBody Event event){

        invitationService.deleteInvitation(event);
    }

    @GetMapping("/getAll")
    public List<Invitation> getAll(){
        return  invitationService.getAll();
    }
}
