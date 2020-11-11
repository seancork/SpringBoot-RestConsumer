package ie.son.controllers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ie.son.domain.Bid;
import ie.son.formobjects.BidsByUserIdForm;

@Controller
public class BidController {
	

	@GetMapping("/bidsbyuserid")
	public String addNewBidForm(Model model)
	{
	  
	model.addAttribute("bidsByUserIdForm", new BidsByUserIdForm());
	return "bidsbyuserid";
}
	
	@PostMapping("/bidsbyuserid")
	public String findAllBidsByUserId(@Valid BidsByUserIdForm bidsByUserIdForm, BindingResult binding, RedirectAttributes redirectAttributes)
	{
		if (binding.hasErrors())
			return "bidsbyuserid";

		int userId = bidsByUserIdForm.getUserId();
		return "redirect:/bidsbyuserid/"+userId;
		}
		
		
	@GetMapping("/bidsbyuserid/{id}")
	public String showAllBidsByUserId(@PathVariable(name="id") int id, Model model)
	{
		try {
		RestTemplate restTemplate = new RestTemplate();
		String URL = "http://localhost:8080/api/bidsbyuserid/"+id;
		String username = "test@mycit.ie";
		String password = "password";
		HttpHeaders headers = createHeaders(username, password);
		
		ResponseEntity<List<Bid>> responseEntity = restTemplate.exchange(
				URL,
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<List<Bid>>() {}
				);
		List<Bid> bid = responseEntity.getBody();
		 if (!bid.isEmpty()){
			 System.out.println(model.addAttribute("bids",bid));
		model.addAttribute("bids",bid);
		return "bids";
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
