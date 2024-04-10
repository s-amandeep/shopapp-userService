package com.zionique.userserviceapplication.repositories;

import com.zionique.userserviceapplication.models.Session;
import com.zionique.userserviceapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByTokenAndUser_Id(String token, Long id);

}
