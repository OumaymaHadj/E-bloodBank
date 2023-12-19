package com.fsb.eblood.service;


import com.fsb.eblood.dao.entities.Event;
import com.fsb.eblood.dao.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event){
       return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    public void deleteEvent(int id) {eventRepository.deleteById(id);}

    public Optional<Event> getEventById(int eventId) {
        return eventRepository.findById(eventId);
    }

    public ResponseEntity<Event> updateEvent(Event event ) {

        Event existingEvent = eventRepository.findById(event.getId()).orElseThrow(
                () -> new UsernameNotFoundException("Could not find Event with id = " + event.getId())
        );

        if (event.getTitle()!=null) existingEvent.setTitle(event.getTitle());
        if (event.getDate()!=null) existingEvent.setDate(event.getDate());
        if (event.getDescription()!=null) existingEvent.setDescription(event.getDescription());
        if (event.getStart_time()!=null) existingEvent.setStart_time(event.getStart_time());
        if (event.getEnd_time()!=null) existingEvent.setEnd_time(event.getEnd_time());
        if (event.getCity()!=null) existingEvent.setCity(event.getCity());
        if (event.getNbPartcipant()!=0) existingEvent.setNbPartcipant(event.getNbPartcipant());
        if (event.getMax_nb_participants()!=0) existingEvent.setMax_nb_participants(event.getMax_nb_participants());

        eventRepository.save(existingEvent);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public long countByDate(double sDate, double eDate) {

        return eventRepository.countEventsByDateBetween(sDate, eDate);
    }

}
