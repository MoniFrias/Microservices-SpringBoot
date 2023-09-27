package com.microservices.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.hotel.entity.Hotel;
import com.microservices.hotel.repository.HotelRepository;

@Service
public class HotelService {
	
	private final HotelRepository repository;
	
	@Autowired
	public HotelService(HotelRepository hotelRepository) {
		this.repository = hotelRepository;
	}

	public Hotel saveHotel(Hotel hotel) {
		repository.save(hotel);		
		return hotel;
	}

	public Hotel getHotel(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<Hotel> getAll() {
		return repository.findAll();
	}

}
