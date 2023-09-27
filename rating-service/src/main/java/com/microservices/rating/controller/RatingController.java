package com.microservices.rating.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.rating.entity.Rating;
import com.microservices.rating.service.RatingService;

@RestController
@RequestMapping(path = "/rating")
public class RatingController {

	private final RatingService service;
	
	@Autowired
	public RatingController (RatingService ratingService) {
		this.service = ratingService;
	}
	
	@PostMapping(path = "/saveRating")
	public ResponseEntity<Rating> saveRating(@RequestBody Rating rating){
		service.saveRating(rating);
		return new ResponseEntity<>(rating, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getRating/{id}")
	public ResponseEntity<Rating> getRating(@PathVariable final Long id){
		Rating rating = service.getRating(id);
		if(Objects.isNull(rating)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rating, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAll")
	public ResponseEntity<List<Rating>> getAll(){
		List<Rating> ratings = service.getAll();
		if(ratings.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}
}
