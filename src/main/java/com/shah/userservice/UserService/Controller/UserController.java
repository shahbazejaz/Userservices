package com.shah.userservice.UserService.Controller;

import com.shah.userservice.UserService.Dto.UserDto;
import com.shah.userservice.UserService.Entity.User;
import com.shah.userservice.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto dto = userService.createUsers(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId){
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId){
    userService.deleteUserById(userId);
    return new ResponseEntity<>("user get deleted",HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto,@PathVariable("userId") String userId){
        UserDto dto = userService.updateUserById(userDto, userId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
