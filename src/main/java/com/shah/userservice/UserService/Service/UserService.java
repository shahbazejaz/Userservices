package com.shah.userservice.UserService.Service;

import com.shah.userservice.UserService.Dto.UserDto;
import com.shah.userservice.UserService.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public UserDto createUsers(UserDto userDto);

    User getUserById(String userId);

    List<UserDto> getAllUsers();

    void deleteUserById(String userId);

    UserDto updateUserById(UserDto userDto, String userId);
}
