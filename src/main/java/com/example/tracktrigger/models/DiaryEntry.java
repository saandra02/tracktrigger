package com.example.tracktrigger.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class DiaryEntry {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private Date date;
  private String title;
  private String content;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Date getDate() {
	    return date;
	  }
  public void setDate(Date date) {
	    this.date = date;
	  }
  public String getTitle() {
	    return title;
	  }
  public void setTitle(String title) {
	    this.title = title;
	  }
  public String getContent() {
	    return content;
	  }
  public void setContent(String content) {
	    this.content = content;
	  }
	
}
