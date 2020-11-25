package com.example.tracktrigger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tracktrigger.models.ApplicationUser;
import com.example.tracktrigger.models.VerificationToken;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.VerificationTokenRepository;

@Controller
public class ViewController {
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	
	@RequestMapping("/")
	public String home() {
	        return "index";
	 }
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}
	@RequestMapping("/userlogin")
	public String login() {
		return "Login";
	}
	@RequestMapping("/verify")
	public String verify() {
		return "verification";
	}
	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}
	
	@RequestMapping(path="/verify-registration", method= {RequestMethod.GET, RequestMethod.POST})
	public String CompleteVerify(@RequestParam("token") String vtoken){
		VerificationToken v_token = verificationTokenRepository.findByToken(vtoken);
		if(v_token != null){
			ApplicationUser user = applicationUserRepository.findByEmail(v_token.getUser().getEmail());
			user.setEmail_Verified(true);
			applicationUserRepository.save(user);
			return "verification";
			}
		else {
			return "signup";
		}
		  
		  
	  } 
	
	@RequestMapping(path = "/categories")
	public String categories() {
		return "categories";
	}
	  
}


