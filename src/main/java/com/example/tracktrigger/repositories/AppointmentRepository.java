package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tracktrigger.models.Appointment;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends CrudRepository <Appointment, Long>{
	
	@Query(value="SELECT * FROM category i WHERE i.user_id=?1", nativeQuery=true)
	ArrayList <Appointment> findByUserId(Long user_id);
	
	@Query(value = "SELECT * FROM appointment i WHERE i.id=?1", nativeQuery = true)
	Appointment findByApptId(Long id);
}
