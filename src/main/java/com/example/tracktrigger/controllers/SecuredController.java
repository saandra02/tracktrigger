package com.example.tracktrigger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tracktrigger.models.ApplicationUser;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import org.json.JSONObject;

@RestController
@RequestMapping("/demo")
public class SecuredController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	
    @GetMapping(path="/profile")
    public ResponseEntity <ApplicationUser> viewProfile(Authentication authentication) {
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
        return new ResponseEntity <ApplicationUser>(user, HttpStatus.OK);
    }
    
}
