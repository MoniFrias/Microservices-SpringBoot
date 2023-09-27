package com.microservices.rating.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.rating.entity.Rating;
import com.microservices.rating.repository.RatingRepository;

@Service
public class RatingService {

	private final RatingRepository repository;
	
	@Autowired
	public RatingService(RatingRepository ratingRepository) {
		this.repository = ratingRepository;
	}
	
	public Rating saveRating(Rating rating) {
		repository.save(rating);
		return rating;
	}
	
	public Rating getRating(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Rating> getAll(){
		return repository.findAll();
	}
}
