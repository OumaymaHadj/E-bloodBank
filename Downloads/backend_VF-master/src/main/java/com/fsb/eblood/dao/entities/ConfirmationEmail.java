package com.fsb.eblood.dao.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Entity
public class ConfirmationEmail {

    @Id
    @SequenceGenerator(name = "confirmation_Email_sequence", sequenceName = "confirmation_Email_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_Email_sequence")
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;


    private String email;

    public ConfirmationEmail(LocalDateTime createdAt, LocalDateTime expiresAt, String email) {
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.email = email;
    }
}
