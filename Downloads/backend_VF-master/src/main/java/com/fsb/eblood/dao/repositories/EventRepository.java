package com.fsb.eblood.dao.repositories;



import com.fsb.eblood.dao.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {


    long countEventsByDateBetween(double startDate, double endDate);


}
