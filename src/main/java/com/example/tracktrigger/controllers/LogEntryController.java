package com.example.tracktrigger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.tracktrigger.models.ApplicationUser;
import com.example.tracktrigger.models.LogEntry;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.LogEntryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONObject;

@RestController
@RequestMapping("/demo/log")
public class LogEntryController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	@Autowired 
	private LogEntryRepository logEntryRepository;
	
    @PostMapping(path="/create")
    public ResponseEntity <String> addEntry(Authentication authentication, 
    		@RequestParam String entry_content) {
    	
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	Long user_id = user.getId();
    	LogEntry entry = new LogEntry();
    	entry.setUserId(user_id);
    	entry.setEntryDate(LocalDateTime.now());
    	entry.setEntryContent(entry_content);
    	logEntryRepository.save(entry);
    	
        return new ResponseEntity <String>("Added Successfully", HttpStatus.OK);
    }
    
    @GetMapping(path="/read")
    public ArrayList <LogEntry> viewEntries(Authentication authentication){
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	Long user_id = user.getId();
    	return logEntryRepository.findByUserId(user_id);
    }
    
    @GetMapping(path = "/readone")
    public LogEntry readOneEntry(@RequestParam Long id){
    	LogEntry entry = logEntryRepository.findByLogEntryId(id);
    	return entry;
    }
    
    @DeleteMapping(path="/delete")
    public ResponseEntity <String> deleteEntry(@RequestParam Long id){
    	logEntryRepository.deleteById(id);
    	return new ResponseEntity <String> ("Deleted successfully", HttpStatus.OK);
    }
    

}
