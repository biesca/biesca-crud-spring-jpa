package com.biesca.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.biesca.application.service.ITaskStatusService;
import com.biesca.generated.api.TaskStatusApi;
import com.biesca.generated.model.TaskStatusDto;
import com.biesca.generated.model.TaskStatusList;

import io.swagger.annotations.Api;

@Api(tags = {"taskStatus"})
@Controller
@RestController
public class TasksStatusApiImpl implements TaskStatusApi {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(TasksStatusApiImpl.class);	

	@Autowired
	ITaskStatusService iTaskStatusService;
	
	@Override
	public ResponseEntity<TaskStatusList> getAllTasksStatus() {

		LOGGER.info("getAllTasksStatus");
		return new ResponseEntity<>(iTaskStatusService.findAll(),HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<TaskStatusDto> findTaskStatus(String statusCode) {

		LOGGER.info("findTaskStatus");
		return new ResponseEntity<>(iTaskStatusService.findByTaskStatusCode(statusCode),HttpStatus.OK);
	}
}
