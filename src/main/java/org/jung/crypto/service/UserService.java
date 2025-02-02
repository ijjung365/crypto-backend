package org.jung.crypto.service;

import org.jung.crypto.dto.UserDTO;
import org.jung.crypto.dto.UserInfoDTO;


public interface UserService {
    public void createUser(UserDTO userDTO);
    public UserInfoDTO fetchUser();
    void updatePassword(String password);
}
