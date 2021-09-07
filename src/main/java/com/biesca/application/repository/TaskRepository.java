package com.biesca.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biesca.application.entity.TaskData;

@Repository
public interface TaskRepository extends JpaRepository<TaskData, Long> {
	
	@Query("FROM TaskData WHERE taskCode=?1")
	Optional<TaskData> findByTaskCode(@Param("taskCode") String taskCode);

}
