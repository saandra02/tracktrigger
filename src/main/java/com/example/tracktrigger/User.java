package com.example.tracktrigger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

	private String name;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String phno;
	private String profession;
	private boolean email_verified;
	private boolean ph_verified;
  

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public String getUsername() {
	    return username;
	  }
  public void setUsername(String username) {
	    this.username = username;
	    }
  
  public String getPassword() {
	    return password;
	  }
  public void setPassword(String password) {
	    this.password = password;
	  }
  
  public String getSalt() {
	    return salt;
	  }
  public void setSalt(String salt) {
	    this.salt = salt;
	  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPhno() {
	    return phno;
	  }
  public void setPhno(String phno) {
	    this.phno = phno;
	    }
  
  public String getProfession() {
	    return profession;
	  }
  public void setProfession(String profession) {
	    this.profession = profession;
	  }
  public boolean getEmail_Verified() {
	    return email_verified;
	  }
  public void setEmail_Verified(boolean email_verified) {
	    this.email_verified = email_verified;
	  }
  public boolean getPh_Verified() {
	    return ph_verified;
	  }
public void setPh_Verified(boolean ph_verified) {
	    this.ph_verified = ph_verified;
	  }
	
}
