package com.fsb.eblood.dao.repositories;

import com.fsb.eblood.dao.entities.ConfirmationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationEmail,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationEmail c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.email = ?1")
    int updateConfirmedAt(String email,
                          LocalDateTime confirmedAt);

    Optional<ConfirmationEmail> findByEmail(String email);
}
