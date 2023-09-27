package com.microservices.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rating {

	private Long idUser;
	private Long idHotel;
	private int rating;
	private String feedback;
}