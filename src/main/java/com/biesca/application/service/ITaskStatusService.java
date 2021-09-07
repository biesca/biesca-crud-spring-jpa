package com.biesca.application.service;

import com.biesca.generated.model.TaskStatusDto;
import com.biesca.generated.model.TaskStatusList;

public interface ITaskStatusService {
	
	public TaskStatusDto findByTaskStatusCode(String statusCode);
	
	public TaskStatusList findAll();
	
}