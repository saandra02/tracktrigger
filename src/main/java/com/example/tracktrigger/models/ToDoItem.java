package com.example.tracktrigger.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class ToDoItem {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private long user_id;
  private String task_name;
  private boolean task_status;
  private String task_priority;
  
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
  public String getTaskName() {
	    return task_name;
	  }
  public void setTaskName(String task_name) {
	    this.task_name = task_name;
	  }
  public boolean getTaskStatus() {
	    return task_status;
	  }
  public void setTaskStatus(boolean task_status) {
	    this.task_status = task_status;
	  }
  public String getTaskPriority() {
	  return task_priority;
  }
  public void setTaskPriority(String task_priority) {
	  this.task_priority = task_priority;
  }
	
}
