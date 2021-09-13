package com.biesca.application.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biesca.application.entity.TaskStatusData;
import com.biesca.application.repository.TaskStatusRepository;
import com.biesca.application.service.ITaskStatusService;
import com.biesca.application.service.transform.TransformUtils;
import com.biesca.generated.model.TaskStatusDto;
import com.biesca.generated.model.TaskStatusList;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskStatusService implements ITaskStatusService {

	@Autowired
	TransformUtils transformUtils;
	
	@Autowired
	TaskStatusRepository taskStatusRepository;

	/**
	 * Method for finding a task status in system
	 * @param statusCode task status code to find {@link java.lang.String}
	 * @return TaskStatusDto if exists, null otherwise {@link com.biesca.generated.model.TaskStatusDto}
	 */
	@Override
	public TaskStatusDto findByTaskStatusCode(String statusCode) {
		
		log.info("findByTaskStatusCode. statusCode = {} ", statusCode);
		
		Optional<TaskStatusData> status = taskStatusRepository.findById(statusCode);
				
		return status.isPresent()
				? transformUtils.convertToTaskStatusDto(status.get())
				: null;		
	}

	/**
	 * Method for finding all task status in system
	 * @return TaskStatusList task status list {@link com.biesca.generated.model.TaskStatusList}
	 */
	@Override
	public TaskStatusList findAll() {
		
		log.info("findAll");

		TaskStatusList lTaskStatus = new TaskStatusList();
		
		taskStatusRepository.findAll().stream()
		  .map(task ->transformUtils.convertToTaskStatusDto(task))
		  .forEach(taskTransf -> lTaskStatus.add(taskTransf));			
		
		return lTaskStatus;
	}
}