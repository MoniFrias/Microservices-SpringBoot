package com.microservices.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.rating.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{

}
