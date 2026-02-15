package com.jobapp.jobms.job;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobapp.jobms.dto.JobDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {
	private final JobService jobService;
	
	@GetMapping
	public ResponseEntity<List<JobDTO>> findAll() {
		List<JobDTO> jobs = jobService.findAll();
	    return jobs.isEmpty()
	            ? ResponseEntity.noContent().build()
	            : ResponseEntity.ok(jobs);
	}
	
	@PostMapping
	public ResponseEntity<Job> createJob(@RequestBody Job job) {
	    Job createdJob = jobService.createJob(job);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
//	    return jobService.getJobById(id)
//	            .map(dto -> {
//	            	return new ResponseEntity<>(dto, HttpStatus.OK);
//	            })
//	            
//	            .orElse(ResponseEntity.notFound().build());
	    
	    //Without Java 8
		JobDTO job = jobService.getJobById(id);
	      if(job != null)
	      		return new ResponseEntity<>(job, HttpStatus.OK);
	      	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
		boolean deleted = jobService.deleteJobById(id);
		if (deleted)
			return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		/*
		 * return jobService.deleteJobById(id) ? ResponseEntity.noContent().build() :
		 * ResponseEntity.notFound().build();
		 */
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updatedJob(@PathVariable Long id, @RequestBody Job updatedJob){
		boolean updated = jobService.updatedJob(id, updatedJob);
		if(updated) 
			return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		/*
		 return jobService.updateJob(id, updatedJob)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
		 */
	}
}
