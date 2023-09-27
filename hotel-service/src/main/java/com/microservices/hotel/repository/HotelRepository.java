package com.microservices.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.hotel.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
