package org.jung.crypto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.domain.User;
import org.jung.crypto.dto.CustomUserDetails;
import org.jung.crypto.dto.UserDTO;
import org.jung.crypto.dto.UserInfoDTO;
import org.jung.crypto.exception.DuplicateUserException;
import org.jung.crypto.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void createUser(UserDTO userDTO) {
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateUserException(userDTO.getUsername() + " already exists");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setBalance(10000D);
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }

    @Override
    public UserInfoDTO fetchUser() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername()).orElseThrow();
        return modelMapper.map(user, UserInfoDTO.class);
    }

    @Override
    public void updatePassword(String password){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername()).orElseThrow();
        user.changePassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
