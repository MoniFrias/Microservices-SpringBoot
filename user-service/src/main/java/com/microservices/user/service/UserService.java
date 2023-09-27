package com.microservices.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.user.entity.User;
import com.microservices.user.model.Rating;
import com.microservices.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public User saveUser(User user){
		userRepository.save(user);
		return user;
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public List<Rating> getRatings(Long userId){
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			List<Rating> ratings = restTemplate.getForObject("http://localhost:8082/rating/getRatingByUserId/" + userId, List.class);
			return ratings;
		}
		return new ArrayList<>();
	}
}
