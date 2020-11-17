package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tracktrigger.models.ApplicationUser;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
	ApplicationUser findByEmail(String email);
	ApplicationUser findByUsername(String username);
}