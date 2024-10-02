package com.shah.userservice.UserService.Service.Impl;

import com.shah.userservice.UserService.Dto.UserDto;
import com.shah.userservice.UserService.Entity.Hotel;
import com.shah.userservice.UserService.Entity.Rating;
import com.shah.userservice.UserService.Entity.User;
import com.shah.userservice.UserService.Exception.ResourceNotFoundException;
import com.shah.userservice.UserService.Repository.UserRepository;
import com.shah.userservice.UserService.Service.UserService;
import com.shah.userservice.UserService.external.services.HotelService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;


   private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUsers(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User user1 = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(user1, UserDto.class);
        return userDto1;
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        //fetch rating of the above user from RATING SERVICE
        //localhost:8083/ratings/users/9eb50d60-a8a2-496b-b35c-0e881ea09c70
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class );
        logger.info("{} ",ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();


        List<Rating> ratingList = ratings.stream().map(rating -> {
           // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            //logger.info("response status code: {} ", forEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(c -> modelMapper.map(c, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateUserById(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setAbout(userDto.getAbout());
        User user1 = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(user1, UserDto.class);
        return userDto1;
    }


}
