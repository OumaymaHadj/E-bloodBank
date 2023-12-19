package com.fsb.eblood.dao.entities;

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
public class FCMToken {
    @Id
    @SequenceGenerator(name = "fcm_sequence", sequenceName = "fcm_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fcm_sequence")
    private int token_id;
    private String username;
    private String token;
}
