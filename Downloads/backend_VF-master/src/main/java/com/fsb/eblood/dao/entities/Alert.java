package com.fsb.eblood.dao.entities;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Alert {
    @Id
    @SequenceGenerator(name = "alert_sequence", sequenceName = "alert_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_sequence")
    private int id;
    private String createdOn;
    private String createdAt;
    private String description;
    private String receiver;
    private String sender;
    private boolean isAccepted = false;
}
