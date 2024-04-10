package com.zionique.userserviceapplication.services;

import com.zionique.userserviceapplication.models.Session;
import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
@AllArgsConstructor
public class RoleService {

    private SessionRepository sessionRepository;
//
//    public Session createNewSession(User user) {
//        Session session = new Session();
//        session.setUser(user);
//        String token = generateNewToken();
//        session.setToken(token);
//        return sessionRepository.save(session);
//    }
//
//    public String generateNewToken(){
//        byte[] array = new byte[7]; // length is bounded by 7
//        new Random().nextBytes(array);
//        String generatedString = new String(array, Charset.forName("UTF-8"));
//        return generatedString;
//    }
}
