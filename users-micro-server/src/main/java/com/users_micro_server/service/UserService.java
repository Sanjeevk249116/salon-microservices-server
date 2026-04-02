package com.users_micro_server.service;


import com.users_micro_server.dto.UserDto;
import com.users_micro_server.entity.User;
import com.users_micro_server.payloadResponse.ApiResponse;

import java.util.List;


public interface UserService {
    User getUserProfile();

    User createUserProfile(UserDto newUser);

    List<User> getAllUserList();

    User updateUserProfile(Long userId, UserDto updateProfileData);

    User getUserProfileById(Long userId);


    ApiResponse<String> deletUserProfile(Long userId);
}
