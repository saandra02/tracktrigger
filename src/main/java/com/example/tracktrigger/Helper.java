package com.example.tracktrigger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Helper {
	
	public static String SHA_512_Encrypt(String password, String salt){
	    String generatedPassword = null;
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(salt.getBytes(StandardCharsets.UTF_8));
	        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return generatedPassword;
	}
	
	public static User CreateUser(String name, String username, String password, 
			String email, String phno, String profession) {
		
			User n = new User();
		   	n.setName(name);
		    n.setUsername(username);
		    SecureRandom random = new SecureRandom();
			byte[] s = new byte[16];
			random.nextBytes(s);
			String salt = s.toString();
			String p = SHA_512_Encrypt(password, salt);
			n.setPassword(p);
			n.setSalt(salt);
			
		    n.setEmail(email);
		    n.setPhno(phno);
		    n.setProfession(profession);
		    
		    n.setEmail_Verified(false);
		    n.setPh_Verified(false);
		    
		    return n;
		
	}
	



}
