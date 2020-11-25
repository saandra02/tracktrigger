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
import com.example.tracktrigger.models.Category;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.CategoryRepository;

import java.util.ArrayList;

import org.json.JSONObject;

@RestController
@RequestMapping("/demo/category")
public class CategoryController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	@Autowired 
	private CategoryRepository categoryRepository;
	
    @PostMapping(path="/create")
    public ResponseEntity <String> addCategory(Authentication authentication, 
    		@RequestParam String category_name, @RequestParam String category_desc) {
    	
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	Long user_id = user.getId();
    	Category cat = new Category();
    	cat.setUserId(user_id);
    	cat.setCategoryName(category_name);
    	cat.setCategoryDesc(category_desc);
    	cat.setCategoryDefault(0);
    	
    	categoryRepository.save(cat);
    	
        return new ResponseEntity <String>("Added Successfully", HttpStatus.OK);
    }
    
    @GetMapping(path="/read")
    public ArrayList <Category> viewCategories(Authentication authentication){
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	Long user_id = user.getId();
    	return categoryRepository.findByUserId(user_id);
    }
    
    @GetMapping(path = "/readone")
    public Category readOneCategory(@RequestParam Long id){
    	Category cat = categoryRepository.findByCategoryId(id);
    	return cat;
    }
    
    @PostMapping(path="/update")
    public ResponseEntity <String> updateCategory(@RequestParam Long id, 
    		@RequestParam String category_name, @RequestParam String category_desc){
    	Category cat = categoryRepository.findByCategoryId(id);
    	cat.setCategoryName(category_name);
    	cat.setCategoryDesc(category_desc);
    	categoryRepository.save(cat);
    	return new ResponseEntity <String>("Updated successfully", HttpStatus.OK);
    }
    
    @DeleteMapping(path="/delete")
    public ResponseEntity <String> deleteCategory(@RequestParam Long id){
    	categoryRepository.deleteById(id);
    	return new ResponseEntity <String> ("Deleted successfully", HttpStatus.OK);
    }
    
    @PostMapping(path="/default")
    public ResponseEntity <String> changeDefault(@RequestParam Long old_default_id, 
    		@RequestParam Long new_default_id){
    	
    	Category old = categoryRepository.findByCategoryId(old_default_id);
    	Category n = categoryRepository.findByCategoryId(new_default_id);
    	old.setCategoryDefault(0);
    	n.setCategoryDefault(1);
    	categoryRepository.save(old);
    	categoryRepository.save(n);
    	return new ResponseEntity <String>("Default changed successfully", HttpStatus.OK);
    }
}
