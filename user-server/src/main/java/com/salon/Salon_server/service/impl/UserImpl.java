package com.salon.Salon_server.service.impl;

import com.salon.Salon_server.dto.UserDto;
import com.salon.Salon_server.entity.User;
import com.salon.Salon_server.exceptionHandling.CustomException;
import com.salon.Salon_server.payloadResponse.ApiResponse;
import com.salon.Salon_server.repository.UserRepository;
import com.salon.Salon_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserProfile() {
        return null;
    }

    @Override
    public User createUserProfile(UserDto newUser) {
        User user = modelMapper.map(newUser, User.class);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUserList() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User updateUserProfile(Long userId, UserDto updateProfileData) {
        User userExistWithId = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Profile is not found with this " + userId));
        modelMapper.map(updateProfileData, userExistWithId);
        return userRepository.save(userExistWithId);
    }

    @Override
    public User getUserProfileById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new CustomException("Profile is not found.");
        }

        return user.get();
    }

    @Override
    public ApiResponse<String> deletUserProfile(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Profile is not found with this " + userId));
        userRepository.deleteById(userId);
        return new ApiResponse<>(200, true, "User deleted successfully", null);
    }


}
