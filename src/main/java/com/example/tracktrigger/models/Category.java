package com.example.tracktrigger.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Category {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private long user_id;
  private String category_name;
  private String category_desc;
  private int category_default;
  
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
  public String getCategoryName() {
	    return category_name;
	  }
  public void setCategoryName(String category_name) {
	    this.category_name = category_name;
	  }
  public String getCategoryDesc() {
	    return category_desc;
	  }
  public void setCategoryDesc(String category_desc) {
	    this.category_desc = category_desc;
	  }
  public int getCategoryDefault() {
	  return category_default;
  }
  public void setCategoryDefault(int category_default) {
	  this.category_default = category_default;
  }
	
}
