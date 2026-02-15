package com.jobapp.jobms.dto;

import java.util.List;

import com.jobapp.jobms.external.Company;
import com.jobapp.jobms.external.Review;
import com.jobapp.jobms.job.Job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDTO {
	private Job job;
	private Company company;
	private List<Review> review;
}
