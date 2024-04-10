package com.zionique.userserviceapplication.controllers;

import com.zionique.userserviceapplication.exceptions.NotFoundException;
import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws NotFoundException {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()){
            throw new NotFoundException("User with id: " + id + " not found.");
        }
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

}
