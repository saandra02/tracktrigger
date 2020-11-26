package com.example.tracktrigger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tracktrigger.models.ApplicationUser;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import org.json.JSONObject;

import com.example.tracktrigger.jobs.EmailJob;
import com.example.tracktrigger.payloads.ScheduleEmailRequest;
import com.example.tracktrigger.payloads.ScheduleEmailResponse;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/demo")
public class SecuredController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	
	  private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

	  @Autowired
	  private Scheduler scheduler;
	
    @GetMapping(path="/profile")
    public ResponseEntity <ApplicationUser> viewProfile(Authentication authentication) {
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
        return new ResponseEntity <ApplicationUser>(user, HttpStatus.OK);
    }
    
    @PostMapping(path="/schedulereminders")
    public ResponseEntity<String> ScheduleReminders(Authentication authentication){
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	ScheduleEmailRequest scheduleEmailRequest =  new ScheduleEmailRequest();
    	scheduleEmailRequest.setEmail(user.getEmail());
    	scheduleEmailRequest.setSubject("Daily Update Reminder");
    	scheduleEmailRequest.setBody("This is your reminder email to update today's log.");
    	scheduleEmailRequest.setTimeZone(ZoneId.of("Asia/Kolkata"));
    	LocalDateTime localdatetime = LocalDateTime.now().with(LocalTime.of(17, 0));
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Die");
    	
    	
    	
    	
    }
    
}
