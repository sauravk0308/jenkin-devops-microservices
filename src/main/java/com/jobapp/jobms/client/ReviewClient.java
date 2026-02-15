package com.jobapp.jobms.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobapp.jobms.external.Review;

@FeignClient(name = "REVIEW-SERVICE", url = "${review-service.url}")
public interface ReviewClient {

	@GetMapping("/reviews")
	List<Review> getReviews(@RequestParam("companyId") Long compaynId);
}
