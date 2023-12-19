package com.fsb.eblood.dao.entities;


import com.fsb.eblood.dao.enumerations.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @SequenceGenerator(name = "event_sequence", sequenceName = "event_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    private int id;
    private String title;
    private String date;
    private String description;
    private String start_time;
    private String end_time;
    @Enumerated(EnumType.STRING)
    private Region city;
    private String country;
    private int nbPartcipant=0;
    private int max_nb_participants;

}
