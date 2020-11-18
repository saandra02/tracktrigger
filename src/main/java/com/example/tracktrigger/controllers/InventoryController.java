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
import com.example.tracktrigger.models.InventoryItem;
import com.example.tracktrigger.repositories.ApplicationUserRepository;
import com.example.tracktrigger.repositories.InventoryItemRepository;

import org.json.JSONObject;

@RestController
@RequestMapping("/demo/inventory")
public class InventoryController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	@Autowired 
	private InventoryItemRepository inventoryItemRepository;
	
    @PostMapping(path="/items")
    public ResponseEntity <String> addTask(Authentication authentication, 
    		@RequestParam String itemname, @RequestParam String itemdesc, @RequestParam String cat,
    		@RequestParam int qty, @RequestParam double price) {
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	
    	Long user_id = user.getId();
    	InventoryItem item = new InventoryItem();
    	item.setUserId(user_id);
    	item.setItemName(itemname);
    	item.setItemDesc(itemdesc);
    	item.setCat(cat);
    	item.setQty(qty);
    	item.setPrice(price);
    	inventoryItemRepository.save(item);
        return new ResponseEntity <String>("Added Successfully", HttpStatus.OK);
    }
    
    @GetMapping(path="/items")
    public Iterable <InventoryItem> viewTasks(Authentication authentication){
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	Long user_id = user.getId();
    	return inventoryItemRepository.findByUserId(user_id);
    }
    
    @DeleteMapping(path="/item")
    public ResponseEntity<String> deleteTask(@RequestParam Long itemid){
    	inventoryItemRepository.deleteById(itemid);
    	return new ResponseEntity <String>("Deleted successfully!", HttpStatus.OK);
    }
    
}
