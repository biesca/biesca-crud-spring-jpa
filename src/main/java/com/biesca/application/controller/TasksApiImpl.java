package com.biesca.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.biesca.application.exceptions.AlreadyExistsException;
import com.biesca.application.exceptions.NotFoundException;
import com.biesca.application.service.ITaskService;
import com.biesca.application.service.ITaskStatusService;
import com.biesca.generated.api.TasksApi;
import com.biesca.generated.model.NewTaskDto;
import com.biesca.generated.model.TaskDto;
import com.biesca.generated.model.TaskList;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"tasks"})
@Controller
@RestController
@Slf4j
public class TasksApiImpl implements TasksApi {
	
	@Autowired
	ITaskService iTaskService;
	
	@Autowired
	ITaskStatusService iTaskStatusService;	
	
	@Override
	public ResponseEntity<TaskList> getDoneTasks() {
		
		log.info("getDoneTasks");	
		
		return new ResponseEntity<>(iTaskService.findByByDoneStatus(true),HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<TaskList> getUnfinishedTasks() {
		
		log.info("getUnfinishedTasks");		
		
		return new ResponseEntity<>(iTaskService.findByByDoneStatus(false),HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<TaskDto> addNewTask(NewTaskDto newTaskDto) {

		log.info("addNewTask = {} ", newTaskDto);

		try {
			return new ResponseEntity<>(iTaskService.saveTask(newTaskDto), HttpStatus.CREATED);
		} catch (AlreadyExistsException e) {
			log.warn("addNewTask | code task exists");
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
	}

	
	@Override
	public ResponseEntity<TaskList> getAllTasks() {

		log.info("getAllTasks");
		
		return new ResponseEntity<>(iTaskService.findAll(),HttpStatus.OK);
	}	
	
	
	@Override
	public ResponseEntity<Void> updateTaskStatus(String taskCode, String taskStatus) {

		log.info("updateTaskStatus");

		try {

			iTaskService.updateTaskStatus(taskCode, taskStatus);			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (NotFoundException e) {			
			log.warn("addNewTask | code task not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
		}
	}
	
	
	@Override
	public ResponseEntity<Void> deleteTask(String taskCode) {

		log.info("deleteTask");	
		
		try {

			iTaskService.deleteTask(taskCode);	
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (NotFoundException e) {			
			log.warn("addNewTask | code task not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
		}
	}
	
}
