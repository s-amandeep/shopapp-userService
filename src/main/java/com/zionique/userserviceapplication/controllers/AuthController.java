package com.zionique.userserviceapplication.controllers;

import com.zionique.userserviceapplication.dtos.*;
import com.zionique.userserviceapplication.exceptions.NotFoundException;
import com.zionique.userserviceapplication.exceptions.UserExistsException;
import com.zionique.userserviceapplication.exceptions.WrongPasswordException;
import com.zionique.userserviceapplication.models.SessionStatus;
import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto request) throws UserExistsException {

        UserDto userDto = authService.signup(request);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws NotFoundException, WrongPasswordException {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){
        return authService.logout(request.getToken(), request.getUserId());
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestDto request){
        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());

        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
