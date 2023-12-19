package com.fsb.eblood.service;

import com.fsb.eblood.dao.entities.User;
import com.fsb.eblood.dao.repositories.PubRepository;
import com.fsb.eblood.dao.entities.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PublicationService {

    @Autowired
    private PubRepository pubRepo;

    public List<Publication> getAllPublications() {
        return pubRepo.findAllByOrderByPubIdDesc();
    }

    public List<Publication> getUserPublication(User user){
        return pubRepo.findPublicationByAppUserId(user);
    }


    public Publication createPublication(Publication pub) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        pub.setCreatedAt(dtf.format(LocalDateTime.now()));
        return pubRepo.save(pub);
    }

    public void delete(int id) {
        pubRepo.deleteById(id);
    }

    public ResponseEntity<Publication> updatePost(Publication post) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Publication pub = pubRepo.getById(post.getPubId());
        pub.setCreatedAt(dtf.format(LocalDateTime.now()));
        if(post.getContenu()!=null) pub.setContenu(post.getContenu());

        pubRepo.save(pub);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
