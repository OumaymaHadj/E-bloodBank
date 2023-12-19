package com.fsb.eblood.web.controller;


import com.fsb.eblood.dao.entities.Event;
import com.fsb.eblood.service.EventService;
import com.fsb.eblood.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:4200/")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    InvitationService invitationService;

    @PostMapping("/save")
    public Event saveEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int id){
//notificationService.deleteNotif(id);
        eventService.deleteEvent(id);
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable("id") int eventId){
        return eventService.getEventById(eventId);
    }


    @PostMapping("/update")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }


   @GetMapping("/countByDate")
    public long countByDate(){
        return eventService.countByDate(2020-12-01+'T'+10+':'+10+':'+41.808+'Z',

                                        2022-12-19+'T'+10+':'+10+':'+41.808+'Z');
    }

  /*  @GetMapping("/countByDate/{sDate}/{eDate}")
    public long countByDate(@PathVariable("sDate") String sDate,
                            @PathVariable("eDate") String eDate) {
        return eventService.countByDate(sDate, eDate);
    }*/
}
