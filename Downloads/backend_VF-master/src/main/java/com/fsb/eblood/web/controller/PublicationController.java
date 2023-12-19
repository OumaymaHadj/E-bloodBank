package com.fsb.eblood.web.controller;


import com.fsb.eblood.dao.entities.User;
import com.fsb.eblood.service.PublicationService;
import com.fsb.eblood.dao.entities.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pub")
public class PublicationController {
    @Autowired
    private PublicationService pubserv;

    public PublicationController(){ }

    @GetMapping("/getPublications")
    public List<Publication> findAllPubs() {
        return pubserv.getAllPublications();
    }

    @GetMapping("/getUserPub")
    public List<Publication> findUserPubs(@RequestBody User user) {
        return pubserv.getUserPublication(user);
    }
    @PostMapping("/addPublication")
    public void createPub(@RequestBody Publication pub) {
        pubserv.createPublication(pub);
    }

    @DeleteMapping("/deletePub/{id}")
    public void deletePubById(@PathVariable("id") int id){
        pubserv.delete(id);
    }
    @PostMapping("/updatePub")
    public ResponseEntity<Publication> updatePost(@RequestBody Publication post) {

        return pubserv.updatePost(post);
    }





}
