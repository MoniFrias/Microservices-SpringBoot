package com.microservices.hotel.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservices.hotel.model.Rating;


@FeignClient(name = "rating-service")

public interface RatingFeignClient {

	@GetMapping(path = "/rating/getRatingByIdHotel/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByIdHotel(@PathVariable final Long hotelId);
}