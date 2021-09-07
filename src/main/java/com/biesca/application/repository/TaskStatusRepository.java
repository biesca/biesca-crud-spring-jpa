package com.biesca.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biesca.application.entity.TaskStatusData;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusData, String> {

	@Query("FROM TaskStatusData WHERE codeStatus=?1")
	Optional<TaskStatusData> findByTaskStatusCode(@Param("taskStatusCode") String taskStatusCode);

}
