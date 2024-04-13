package com.zionique.userserviceapplication.services;


import com.zionique.userserviceapplication.dtos.SignupRequestDto;
import com.zionique.userserviceapplication.dtos.UserDto;
import com.zionique.userserviceapplication.exceptions.NotFoundException;
import com.zionique.userserviceapplication.exceptions.UserExistsException;
import com.zionique.userserviceapplication.exceptions.WrongPasswordException;
import com.zionique.userserviceapplication.models.Session;
import com.zionique.userserviceapplication.models.SessionStatus;
import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.repositories.SessionRepository;
import com.zionique.userserviceapplication.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDto signup(SignupRequestDto requestDto) throws UserExistsException {
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());

        if (userOptional.isPresent()){
            throw new UserExistsException("User with email: " + requestDto.getEmail() + " already exists.");
        }
        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }

    public ResponseEntity<UserDto> login(String email, String password) throws NotFoundException, WrongPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            throw new NotFoundException("User with email: " + email + " not found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new WrongPasswordException("Wrong Password, Please try again.");
        }

        SecretKey key = Jwts.SIG.HS256.key().build();
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("email", user.getEmail());
        payload.put("roles", user.getRoles());
        payload.put("userId", user.getId());

        String token = Jwts.builder()
//                .claim("payload", payload)
                .claims(payload)
                .signWith(key)
                .compact();

//        String token = RandomStringUtils.randomAscii(20); import this library from Apache Common Lang
//        String token = "thisIsYourToken";
        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", token);

        return new ResponseEntity<>(userDto,
                headers,
                HttpStatus.OK);
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()){
            return null;
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);

        return ResponseEntity.ok().build();

    }

    public Optional<UserDto> validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()){
//            return SessionStatus.INVALID;
            return Optional.empty();
        }

        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
//            return SessionStatus.EXPIRED;
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findById(userId);

//        if (session.getExpiringAt().toInstant().isBefore(new Date().toInstant())){
//            return SessionStatus.EXPIRED;
//        }

//        return SessionStatus.ACTIVE;
        return Optional.of(UserDto.from(userOptional.get()));
    }
}
