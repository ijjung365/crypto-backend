package org.jung.crypto.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void testCreateUser(){
        UserDTO userDTO = UserDTO.builder()
                .username("username")
                .password("password")
                .role("user")
                .build();
        userService.createUser(userDTO);
        try{
            userService.createUser(userDTO);
        } catch (Exception e){
            log.error(e);
            return;
        }
        fail("Should have thrown an exception");
    }
}