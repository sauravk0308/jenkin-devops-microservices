package com.jobapp.jobms.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobapp.jobms.client.CompanyClient;
import com.jobapp.jobms.client.ReviewClient;
import com.jobapp.jobms.dto.JobDTO;
import com.jobapp.jobms.external.Company;
import com.jobapp.jobms.external.Review;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
//	private List<Job> jobs = new ArrayList<>();
//	private Long nextId = 1L;
	
	private final JobRepository jobRepository;
	//private final RestTemplate restTemplate;
	private final CompanyClient companyClient;
	private final ReviewClient reviewClient;
	private int attempt = 0;
	
	@Override
	//@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> findAll() {
		System.out.println("Attempt: "+ ++attempt);
		List<Job> jobs = jobRepository.findAll();
		return jobs.stream().map(this::convertToDto).collect(Collectors.toList());

	}
	
	public List<String> companyBreakerFallback(Exception ex){
		List<String> list = new ArrayList<>();
		list.add("Dummy");
		return list;
	}

	private JobDTO convertToDto(Job job) {
		JobDTO dto = new JobDTO();
			//Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
			Company company = companyClient.getCompany(job.getCompanyId());
			/*ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<Review>>(){
				
			}); */
			//List<Review> reviews = reviewResponse.getBody();
			List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
			
			dto.setJob(job);
			dto.setCompany(company);
			dto.setReview(reviews);
			
			return dto;
	}

	@Override
	public Job createJob(Job job) {
		jobRepository.save(job);
		return job;
	}

	@Override
	public JobDTO getJobById(Long id) {
//	    return jobs.stream()
//	            .filter(job -> job.getId().equals(id))
//	            .findFirst();
		Job job =  jobRepository.findById(id).orElse(null);
		return convertToDto(job);
	}

	@Override
	public boolean deleteJobById(Long id) {
		//return jobs.removeIf(job -> job.getId().equals(id));
		try {
			jobRepository.deleteById(id);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
/*	@Override
	public boolean updateJob(Long id, Job updatedJob) {
	    for (Job job : jobs) {
	        if (job.getId().equals(id)) {
	            job.setTitle(updatedJob.getTitle());
	            job.setDescription(updatedJob.getDescription());
	            job.setMinSalary(updatedJob.getMinSalary());
	            job.setMaxSalary(updatedJob.getMaxSalary());
	            job.setLocation(updatedJob.getLocation());
	            return true;
	        }
	    }
	    return false;
	}
	*/

	@Override
	public boolean updatedJob(Long id, Job updatedJob) {
		/*return jobs.stream().filter(job -> job.getId().equals(id)).findFirst().map(job -> {
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			return true;
		}).orElse(false); */
		Optional<Job> jobOptional = jobRepository.findById(id);
		if(jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			return true;
		}
		return false;
	}
}
