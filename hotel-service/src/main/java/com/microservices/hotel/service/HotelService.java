package com.microservices.hotel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.hotel.entity.Hotel;
import com.microservices.hotel.model.Rating;
import com.microservices.hotel.repository.HotelRepository;

@Service
public class HotelService {
	
	private final HotelRepository repository;
	private final RestTemplate restTemplate;
	
	@Autowired
	public HotelService(HotelRepository hotelRepository, RestTemplate restTemplate) {
		this.repository = hotelRepository;
		this.restTemplate = restTemplate;
	}

	public Hotel saveHotel(Hotel hotel) {
		repository.save(hotel);		
		return hotel;
	}
	
	public List<Rating> getRatings(Long hotelId) {
		Optional<Hotel> hotel = repository.findById(hotelId);
		if(hotel.isPresent()) {
			List<Rating> ratings = restTemplate.getForObject("http://localhost:8082/rating/getRatingByIdHotel/" + hotelId, List.class);
			return ratings;
		}
		return new ArrayList<>();
	}

	public Hotel getHotel(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<Hotel> getAll() {
		return repository.findAll();
	}

}
