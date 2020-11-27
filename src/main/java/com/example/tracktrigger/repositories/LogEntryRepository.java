package com.example.tracktrigger.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.tracktrigger.models.LogEntry;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;

public interface LogEntryRepository extends CrudRepository <LogEntry, Long>{
	
	@Query(value="SELECT * FROM log_entry i WHERE i.user_id=?1", nativeQuery=true)
	ArrayList <LogEntry> findByUserId(Long user_id);
	
	@Query(value = "SELECT * FROM log_entry i WHERE i.id=?1", nativeQuery = true)
	LogEntry findByLogEntryId(Long id);
}
