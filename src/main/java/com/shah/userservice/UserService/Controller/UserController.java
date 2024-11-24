package com.shah.userservice.UserService.Controller;

import com.shah.userservice.UserService.Dto.UserDto;
import com.shah.userservice.UserService.Entity.User;
import com.shah.userservice.UserService.Service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto dto = userService.createUsers(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    int retryCount=1;
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallBack")
    @GetMapping("/users/{userId}")
   // @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId){
        logger.info("Retry count: {}",retryCount);
        retryCount++;
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

    //creating fallback method for circuit breaker

    public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex){

      // logger.info("Fallback is executed because service is down :",ex.getMessage());
     User user=  User.builder()
               .email("dummy@gmail.com")
               .name("dummy")
               .about("This user is created dummy because some services is down")
               .userId("122334")
               .build();

       return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
