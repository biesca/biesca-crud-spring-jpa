package com.biesca.application.service;

import com.biesca.generated.model.NewTaskDto;
import com.biesca.generated.model.TaskDto;
import com.biesca.generated.model.TaskList;

public interface ITaskService {
	
	public TaskList findAll();
	
	public TaskDto saveTask(NewTaskDto newTaskDto);
	
	public TaskDto findByTaskCode(String taskCode);
	
	public void updateTaskStatus(String taskCode, String status);
	
	public TaskList findByByDoneStatus(Boolean done);
	
	public void deleteTask(String taskCode);
	
}