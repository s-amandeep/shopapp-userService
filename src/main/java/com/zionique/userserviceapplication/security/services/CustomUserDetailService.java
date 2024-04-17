package com.zionique.userserviceapplication.security.services;

import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.repositories.UserRepository;
import com.zionique.userserviceapplication.security.models.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        return new CustomUserDetails(userOptional.get());
    }
}
