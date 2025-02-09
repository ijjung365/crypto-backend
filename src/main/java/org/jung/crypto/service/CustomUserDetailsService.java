package org.jung.crypto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jung.crypto.domain.User;
import org.jung.crypto.dto.CustomUserDetails;
import org.jung.crypto.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow();

        if (user != null) {

            return new CustomUserDetails(user);
        }


        return null;
    }
}
