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
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.json.JSONObject;

@RestController
@RequestMapping("/demo/inventory")
public class InventoryController {
	@Autowired 
	private ApplicationUserRepository applicationUserRepository;
	@Autowired 
	private InventoryItemRepository inventoryItemRepository;
	
    @PostMapping(path="/create")
    public ResponseEntity <String> addItem (Authentication authentication, 
    		@RequestParam String itemname, @RequestParam String itemdesc, @RequestParam Long category_id,
    		@RequestParam int qty, @RequestParam double price, 
    		@RequestParam(value = "imageFile", required = false) MultipartFile file) throws IOException {
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
    	item.setCategoryId(category_id);
    	item.setQty(qty);
    	item.setPrice(price);
    	if(file != null) {
    		item.setPicByte(compressBytes(file.getBytes()));
    	}
    	inventoryItemRepository.save(item);
        return new ResponseEntity <String>("Added Successfully", HttpStatus.OK);
    }
    
    @GetMapping(path="/read")
    public Iterable <InventoryItem> viewTasks(Authentication authentication) throws IOException{
    	String json = authentication.getName();
    	json = json.replaceAll("=", ":");
    	JSONObject obj = new JSONObject(json);
    	String username = obj.getString("sub");
    	ApplicationUser user = applicationUserRepository.findByUsername(username);
    	Long user_id = user.getId();
    	
    	ArrayList <InventoryItem> item_list = inventoryItemRepository.findByUserId(user_id);
    	for(int i=0; i<item_list.size(); i++) {
    		InventoryItem item = (InventoryItem) item_list.get(i);
    		if(item.getPicByte() != null) {
    			item.setPicByte(decompressBytes(item.getPicByte()));
    		}
    		
    	}
    	return item_list;
    }
   
    
    @DeleteMapping(path="/delete")
    public ResponseEntity<String> deleteItem(@RequestParam Long itemid){
    	inventoryItemRepository.deleteById(itemid);
    	return new ResponseEntity <String>("Deleted successfully!", HttpStatus.OK);
    }
    
    
	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	// uncompress the image bytes before returning it to the application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

}    
