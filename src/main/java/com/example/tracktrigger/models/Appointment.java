package com.example.tracktrigger.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Appointment {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private long user_id;
  private String appt_name;
  private String appt_desc;
  private String appt_reqs;
  private String appt_location;
  private LocalDateTime dateTime;
  private String appt_trigger_name;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public long getUserId() {
	    return user_id;
	  }
  public void setUserId(Long user_id) {
	    this.user_id = user_id;
	  }
  public String getApptName() {
	  return this.appt_name;
  }
  public void setApptName(String appt_name) {
	  this.appt_name = appt_name;
  }
  public String getApptDesc() {
	  return this.appt_desc;
  }
  public void setApptDesc(String appt_desc) {
	  this.appt_desc = appt_desc;
  }
  public String getApptReqs() {
	  return this.appt_reqs;
  }
  public void setApptReqs(String appt_reqs) {
	  this.appt_reqs = appt_reqs;
  }
  public String getAppLoc() {
	  return this.appt_location;
  }
  public void setApptLoc(String appt_location) {
	  this.appt_location = appt_location;
  }
  public LocalDateTime getDateTime() {
	  return this.dateTime;
  }
  public void setDateTime(LocalDateTime dateTime) {
	  this.dateTime = dateTime;
  }
  public String getApptTriggerName() {
	  return this.appt_trigger_name;
  }
  public void setApptTriggerName(String appt_trigger_name) {
	  this.appt_trigger_name = appt_trigger_name;
  }


	
}
