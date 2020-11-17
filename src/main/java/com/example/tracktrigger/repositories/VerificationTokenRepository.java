package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.tracktrigger.models.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository <VerificationToken, Long> {
	VerificationToken findByToken(String token);

}
