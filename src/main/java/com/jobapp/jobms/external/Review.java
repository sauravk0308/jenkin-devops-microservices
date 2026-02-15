package com.jobapp.jobms.external;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Review {

	private Long id;
	private String title;
	private String description;
	private double rating;
}
