package ie.son.controllers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import ie.son.domain.Jobs;

@Controller
public class JobsController {
	
	@GetMapping("/allactivejobs")
	public String showAllActiveJobs(Model model)
	{
		try {
		RestTemplate restTemplate = new RestTemplate();
		String URL = "http://localhost:8080/api/allactivejobs";
		String username = "test@mycit.ie";
		String password = "password";
		HttpHeaders headers = createHeaders(username, password);
		
		ResponseEntity<List<Jobs>> responseEntity = restTemplate.exchange(
				URL,
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<List<Jobs>>() {}
				);
		
		List<Jobs> jobs = responseEntity.getBody();
		 if (jobs != null){
			 System.out.println(model.addAttribute("jobs",jobs));
		model.addAttribute("jobs",jobs);
		return "jobs";
		 }else {
			 return "notfound";
		}
		
		}catch(Exception e) {
			return "notfound";
		}
	
	}

HttpHeaders createHeaders(String username, String password) {
	return new HttpHeaders() {
		{
		String auth = username + ":" + password;
		byte[] encodeStringIntoBytes = auth.getBytes(StandardCharsets.UTF_8);
		byte[] encodedAuth = Base64.encodeBase64(encodeStringIntoBytes);
		String authHeader = "Basic " + new String( encodedAuth );
		set(HttpHeaders.AUTHORIZATION, authHeader);
	}};
  }
}
