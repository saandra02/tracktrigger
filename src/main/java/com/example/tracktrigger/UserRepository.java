package com.example.tracktrigger;

import org.springframework.data.repository.CrudRepository;
import java.lang.Iterable;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByEmail(String email);
	User findByUsername(String username);
	
	@Query(value = "SELECT * FROM User u WHERE u.id = 1", nativeQuery = true)
			Iterable<User> find();
	
	@Query(value = "SELECT * FROM User u WHERE u.email = ?1", nativeQuery = true)
			ArrayList <User> emailExists(String email);
	
	@Query(value = "SELECT * FROM User u WHERE u.username = ?1", nativeQuery = true)
			ArrayList <User> usernameExists(String username);
	
	
	

}