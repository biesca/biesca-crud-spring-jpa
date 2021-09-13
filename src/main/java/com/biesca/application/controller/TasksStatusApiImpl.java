package com.biesca.application.controller;

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
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"taskStatus"})
@Controller
@RestController
@Slf4j
public class TasksStatusApiImpl implements TaskStatusApi {
	
	@Autowired
	ITaskStatusService iTaskStatusService;
	
	@Override
	public ResponseEntity<TaskStatusList> getAllTasksStatus() {

		log.info("getAllTasksStatus");
		return new ResponseEntity<>(iTaskStatusService.findAll(),HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<TaskStatusDto> findTaskStatus(String statusCode) {

		log.info("findTaskStatus");
		return new ResponseEntity<>(iTaskStatusService.findByTaskStatusCode(statusCode),HttpStatus.OK);
	}
}
