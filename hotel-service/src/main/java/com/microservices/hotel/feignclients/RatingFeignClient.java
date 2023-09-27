package com.microservices.hotel.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.hotel.model.Rating;


@FeignClient(name = "rating-service", url = "http://localhost:8082/rating")
public interface RatingFeignClient {

	@GetMapping(path = "/getRatingByIdHotel/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByIdHotel(@PathVariable final Long hotelId);
}