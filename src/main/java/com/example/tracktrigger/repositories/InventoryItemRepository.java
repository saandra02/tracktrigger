package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tracktrigger.models.InventoryItem;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
public interface InventoryItemRepository extends CrudRepository <InventoryItem, Long>{
	@Query(value="SELECT * FROM inventory_item i WHERE i.user_id=?1", nativeQuery=true)
	ArrayList <InventoryItem> findByUserId(Long user_id);
	
}
