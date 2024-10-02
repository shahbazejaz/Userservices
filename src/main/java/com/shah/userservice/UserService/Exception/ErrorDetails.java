package com.shah.userservice.UserService.Exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ErrorDetails {

    private String message;
    private boolean status;

}
