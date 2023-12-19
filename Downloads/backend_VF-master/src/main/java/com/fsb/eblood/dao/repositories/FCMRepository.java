package com.fsb.eblood.dao.repositories;


import com.fsb.eblood.dao.entities.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FCMRepository extends JpaRepository<FCMToken,Integer> {


    List<FCMToken> findFCMTokenByUsername(String username);



}
