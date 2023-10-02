package com.microservices.hotel.controller;

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

import com.microservices.hotel.entity.Hotel;
import com.microservices.hotel.model.Rating;
import com.microservices.hotel.service.HotelService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(path = "/hotel")
public class HotelController {
	
	private final HotelService hotelService;
	
	 private Logger logger = LoggerFactory.getLogger(HotelController.class);
	
	@Autowired
	public HotelController (HotelService hotelService) {
		this.hotelService = hotelService;
	}
	
	@PostMapping(path = "/saveHotel")
	public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
		hotelService.saveHotel(hotel);
		return new ResponseEntity<>(hotel, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getHotel/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable final Long id){
		Hotel hotel = hotelService.getHotel(id);
		if(Objects.isNull(hotel)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(hotel, HttpStatus.OK);
	}
	
	@CircuitBreaker(name = "ratingCB", fallbackMethod = "fallBackGetRatings")
	@GetMapping(path = "/getRatings/{hotelId}")
	public ResponseEntity<List<Rating>> getRatings(@PathVariable final Long hotelId){
		List<Rating> ratings = hotelService.getRatings(hotelId);
		if(ratings.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getAll")
	public ResponseEntity<List<Hotel>> getAll(){
		List<Hotel> hotels = hotelService.getAll();
		return hotels.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(hotels, HttpStatus.OK);
	}
	
	private ResponseEntity<List<Rating>> fallBackGetRatings(RuntimeException ex){
		logger.info("Fallback is executed because service is down ", ex.getMessage());
		return new ResponseEntity("Oops! Something went wrong, please try again later!", HttpStatus.OK);
	}
	
}
