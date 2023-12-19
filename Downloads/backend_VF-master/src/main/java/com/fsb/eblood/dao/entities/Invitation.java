package com.fsb.eblood.dao.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invitation {

    @Id
    @SequenceGenerator(name = "invitation_sequence", sequenceName = "invitation_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invitation_sequence")
    private int id;
    private String createdAt;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    private int nbParticipants;

}
