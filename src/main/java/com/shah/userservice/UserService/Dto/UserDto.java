package com.shah.userservice.UserService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;
    private String name;
    private String email;
    private String about;
    private String address;
}
