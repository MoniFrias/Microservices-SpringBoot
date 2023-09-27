package com.microservices.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.user.entity.User;
import com.microservices.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
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
}
