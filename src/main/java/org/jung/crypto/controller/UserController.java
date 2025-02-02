package org.jung.crypto.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jung.crypto.dto.UserDTO;
import org.jung.crypto.dto.UserInfoDTO;
import org.jung.crypto.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody UserDTO user) {
        log.info("User: " + user);
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> info() {
        log.info("User info");
        UserInfoDTO userInfoDTO = userService.fetchUser();
        return ResponseEntity.ok(userInfoDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody String password) {
        log.info("User password: " + password);
        userService.updatePassword(password);
        return ResponseEntity.ok().build();
    }

}
