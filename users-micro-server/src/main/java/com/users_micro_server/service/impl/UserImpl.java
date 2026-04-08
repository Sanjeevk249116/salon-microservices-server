package com.users_micro_server.service.impl;


import com.users_micro_server.dto.UserDto;
import com.users_micro_server.dto.UserResponseDto;
import com.users_micro_server.entity.User;
import com.users_micro_server.enums.RoleEnum;
import com.users_micro_server.exceptionHandling.CustomException;
import com.users_micro_server.repository.UserRepository;
import com.users_micro_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new CustomException("Profile is not found", 401);
        }

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto createUserProfile(UserDto newUser, Set<RoleEnum> role) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new CustomException("Email already exists in user profile.", 401);
        }
        User user = modelMapper.map(newUser, User.class);
        user.setRole(role);
        return modelMapper.map(userRepository.save(user), UserResponseDto.class);
    }

    @Override
    public List<User> getAllUserList() {
        System.out.println("getAllUserList");
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
        if (user.isEmpty()) {
            throw new CustomException("Profile is not found.", 400);
        }

        return user.get();
    }

    @Override
    public String deletUserProfile(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Profile is not found with this " + userId));
        userRepository.deleteById(userId);
        return "User deleted successfully";
    }


}
