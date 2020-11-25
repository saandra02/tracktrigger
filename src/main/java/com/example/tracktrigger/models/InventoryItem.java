package com.example.tracktrigger.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class InventoryItem {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private long user_id;
  private long category_id;
  private String item_name;
  private String item_desc;
  private int qty;
  private double price;
  
  @Column(name = "picByte", length = 1000, nullable = true)
  private byte[] picByte;
  
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
  public String getItemName() {
	    return item_name;
	  }
  public void setItemName(String item_name) {
	    this.item_name = item_name;
	  }
  public String getItemDesc() {
	    return item_desc;
	  }
  public void setItemDesc(String item_desc) {
	    this.item_desc = item_desc;
	  }
  public Long getCategoryId() {
	    return category_id;
	  }
  public void setCategoryId(Long category_id) {
	    this.category_id = category_id;
	  }
  public int getQty() {
	    return qty;
	  }
  public void setQty(int qty) {
	    this.qty = qty;
	  }
  public double getPrice() {
	    return price;
	  }
  public void setPrice(double price) {
	    this.price = price;
	  }
  
  public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}


	
}
