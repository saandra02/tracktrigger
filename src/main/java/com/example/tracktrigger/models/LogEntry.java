package com.example.tracktrigger.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class LogEntry {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private long user_id;
  private LocalDateTime entry_date;
  private String entry_content;
  
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
  public LocalDateTime getEntryDate() {
	    return entry_date;
	  }
  public void setEntryDate(LocalDateTime entry_date) {
	    this.entry_date = entry_date;
	  }
  public String getEntryContent() {
	    return entry_content;
	  }
  public void setEntryContent(String entry_content) {
	    this.entry_content = entry_content;
	  }

	
}
