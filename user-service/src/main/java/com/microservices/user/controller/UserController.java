package com.microservices.user.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user.entity.User;
import com.microservices.user.model.Rating;
import com.microservices.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping(path = "/saveUser")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		userService.saveUser(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getUser/{id}")
	public ResponseEntity<User> getUserById(@PathVariable final Long id){
		User user = userService.getUserById(id);
		if(Objects.isNull(user)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getRatings/{userId}")
	@CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetRatings")
	public ResponseEntity<List<Rating>> getRatings(@PathVariable final Long userId){
		List<Rating> ratings = userService.getRatings(userId);
		if(ratings.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAll")
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.getAll();
		if(users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	private ResponseEntity<List<Rating>> fallBackGetRatings(RuntimeException ex){
		logger.info("Fallback is executed because service is down ", ex.getMessage());
		return new ResponseEntity("Oops! Something went wrong, please try again later!", HttpStatus.OK);
	}
	
}
