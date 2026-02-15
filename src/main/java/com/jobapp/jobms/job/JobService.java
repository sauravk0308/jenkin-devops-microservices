package com.jobapp.jobms.job;

import java.util.List;

import com.jobapp.jobms.dto.JobDTO;

public interface JobService {
	List<JobDTO> findAll();
	Job createJob(Job job);
	JobDTO getJobById(Long id);
	boolean deleteJobById(Long id);
	boolean updatedJob(Long id, Job updatedJob);
}
