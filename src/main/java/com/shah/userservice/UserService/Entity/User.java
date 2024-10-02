package com.shah.userservice.UserService.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "micro_user")
@Builder
public class User {

    @Id
    @Column(name = "Id",nullable = false,unique = true)
    private String userId;
    @Column(name = "Name",length = 25)
    private String name;
    @Column(name = "Email",nullable = false)
    private String email;
    @Column(name = "About")
    private String about;
    @Column(name = "address")
    private String address;
    @Transient
    private List<Rating> ratings;

}
