package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tracktrigger.models.ToDoItem;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;

public interface ToDoItemRepository extends CrudRepository <ToDoItem, Long>{
	
	@Query(value="SELECT * FROM to_do_item i WHERE i.user_id=?1", nativeQuery=true)
	ArrayList <ToDoItem> findByUserId(Long user_id);
	
	@Query(value = "SELECT * FROM to_do_item i WHERE i.id=?1", nativeQuery = true)
	ToDoItem findByToDoItemId(Long id);
}
