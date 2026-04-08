package com.users_micro_server.service;


import com.users_micro_server.dto.UserDto;
import com.users_micro_server.dto.UserResponseDto;
import com.users_micro_server.entity.User;
import com.users_micro_server.enums.RoleEnum;

import java.util.List;
import java.util.Set;


public interface UserService {
    UserResponseDto getUserProfile(String Email);

    UserResponseDto createUserProfile(UserDto newUser, Set<RoleEnum> role);

    List<User> getAllUserList();

    User updateUserProfile(Long userId, UserDto updateProfileData);

    User getUserProfileById(Long userId);


    String deletUserProfile(Long userId);
}
