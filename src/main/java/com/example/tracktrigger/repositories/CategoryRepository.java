package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tracktrigger.models.Category;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends CrudRepository <Category, Long>{
	
	@Query(value="SELECT * FROM category i WHERE i.user_id=?1", nativeQuery=true)
	ArrayList <Category> findByUserId(Long user_id);
	
	@Query(value = "SELECT * FROM category i WHERE i.id=?1", nativeQuery = true)
	Category findByCategoryId(Long id);
}
