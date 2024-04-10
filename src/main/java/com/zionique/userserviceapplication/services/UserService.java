package com.zionique.userserviceapplication.services;

import com.zionique.userserviceapplication.dtos.UserDto;
import com.zionique.userserviceapplication.exceptions.UserExistsException;
import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Optional<User> getUserById(Long id){
        return userRepository.getUserById(id);
    }

}
