package com.example.tracktrigger.models;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String token;
  
    @OneToOne(targetEntity = ApplicationUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private ApplicationUser user;
    
    private Date expiryDate;
   
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    
    public VerificationToken() {
    	
    }
    
    public VerificationToken(ApplicationUser u) {
    	this.user = u;
    	this.token = UUID.randomUUID().toString();
    	this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    
    public Long getId() {
    	return this.id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getToken() {
    	return this.token;
    }
    public void setToken(String token) {
    	this.token = token;
    }
    
    public ApplicationUser getUser() {
    	return this.user;
    }
    public void setUser(ApplicationUser user) {
    	this.user = user;
    }
    public Date getExpiryDate() {
    	return this.expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
    	this.expiryDate = expiryDate;
    }
}