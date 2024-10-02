package com.shah.userservice.UserService.Repository;

import com.shah.userservice.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
