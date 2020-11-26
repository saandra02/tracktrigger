package com.example.tracktrigger.controllers;

import com.example.tracktrigger.models.ApplicationUser;
import com.example.tracktrigger.models.VerificationToken;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController // This means that this class is a Rest Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class UserController {
  @Autowired 
  private ApplicationUserRepository applicationUserRepository;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;
  
  @Autowired
  private JavaMailSender mailSender;
  
  
  @PostMapping(path="/signup") // Map ONLY POST Requests
  public ResponseEntity<String> addNewUser (HttpServletRequest request, 
		  @RequestParam String name, 
		  @RequestParam String username, @RequestParam String password,
		  @RequestParam String email, @RequestParam String phno,
		  @RequestParam String profession) {
	  
		if (applicationUserRepository.findByEmail(email) != null) {
			return ResponseEntity.badRequest().body("An account with this email exists!");
		}
		if (applicationUserRepository.findByUsername(username) != null) {
			return ResponseEntity.badRequest().body("Username already exists.");
		}
		ApplicationUser n = new ApplicationUser();
		n.setName(name);
		n.setUsername(username);
		n.setPassword(bCryptPasswordEncoder.encode(password));
		n.setEmail(email);
		n.setPhno(phno);
		n.setProfession(profession);
		n.setEmail_Verified(false);
		n.setPh_Verified(false);
		applicationUserRepository.save(n);
		//Send verification email
		VerificationToken v_token = new VerificationToken(n);
		verificationTokenRepository.save(v_token);
		String appUrl = request.getContextPath();
		String confirmationUrl = appUrl + "/verify-registration?token="+v_token.getToken();
		
		SimpleMailMessage v_email = new SimpleMailMessage();
		v_email.setTo(n.getEmail());
		v_email.setSubject("Account Verification");
		v_email.setText("To verify your account please click the below link\n"
				+ "http://localhost:8080" + confirmationUrl);
		mailSender.send(v_email);
    
		return ResponseEntity.ok("Saved");
    
  }
  

 

  @GetMapping(path="/all")
  public Iterable<ApplicationUser> getAllUsers() {
    return applicationUserRepository.findAll();
  }
  
  @PostMapping(path = "/test")
  public ResponseEntity<String> Test(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		ApplicationUser creds = new ApplicationUser();
		creds.setUsername(username);
		creds.setPassword(password);
		String message = creds.getUsername() + creds.getPassword();
		
	  return ResponseEntity.ok(message);
  }
  
}