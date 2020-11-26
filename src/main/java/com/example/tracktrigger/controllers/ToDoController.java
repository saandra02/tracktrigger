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
import com.example.tracktrigger.models.ToDoItem;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.ToDoItemRepository;

import java.util.ArrayList;

import org.json.JSONObject;

@RestController
@RequestMapping("/demo/todo")
public class ToDoController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	@Autowired 
	private ToDoItemRepository toDoItemRepository;
	
    @PostMapping(path="/create")
    public ResponseEntity <String> addTask(Authentication authentication, 
    		@RequestParam String task_name, @RequestParam String task_priority) {
    	
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	Long user_id = user.getId();
    	ToDoItem todo = new ToDoItem();
    	todo.setUserId(user_id);
    	todo.setTaskName(task_name);
    	todo.setTaskPriority(task_priority);
    	todo.setTaskStatus(false);
    	
    	toDoItemRepository.save(todo);
    	
        return new ResponseEntity <String>("Added Successfully", HttpStatus.OK);
    }
    
    @GetMapping(path="/read")
    public ArrayList <ToDoItem> viewTasks(Authentication authentication){
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	Long user_id = user.getId();
    	return toDoItemRepository.findByUserId(user_id);
    }  
    
    @PostMapping(path="/update")
    public ResponseEntity <String> updateCategory(@RequestParam Long id, @RequestParam boolean task_status){
    	ToDoItem todo = toDoItemRepository.findByToDoItemId(id);
    	todo.setTaskStatus(task_status);
    	return new ResponseEntity <String>("Updated successfully", HttpStatus.OK);
    }
    
    @DeleteMapping(path="/delete")
    public ResponseEntity <String> deleteCategory(@RequestParam Long id){
    	toDoItemRepository.deleteById(id);
    	return new ResponseEntity <String> ("Deleted successfully", HttpStatus.OK);
    }
    
}
