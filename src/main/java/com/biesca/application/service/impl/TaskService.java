package com.biesca.application.service.impl;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biesca.application.constants.TaskStatusEnum;
import com.biesca.application.entity.TaskData;
import com.biesca.application.entity.TaskStatusData;
import com.biesca.application.exceptions.AlreadyExistsException;
import com.biesca.application.exceptions.NotFoundException;
import com.biesca.application.repository.TaskRepository;
import com.biesca.application.service.ITaskService;
import com.biesca.application.service.transform.TransformUtils;
import com.biesca.generated.model.NewTaskDto;
import com.biesca.generated.model.TaskDto;
import com.biesca.generated.model.TaskList;

@Service
public class TaskService implements ITaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskStatusService iTaskStatusService;
	
	@Autowired
	TransformUtils transformUtils;

	
	/**
	 * Method for finding all done tasks in system. 
	 * 
	 * @param taskCode task code to find {@link java.lang.Boolean}
	 * @return TaskList {@link com.biesca.generated.model.TaskList}
	 */
	@Override
	public TaskList findByByDoneStatus(Boolean done) {
		
		LOGGER.info("findByByDoneStatus. done = {} ", done);	

		TaskList lTask = new TaskList();
		
		//This part of code could be more optimal searching by done status in database, but that would not allow me to use some lambda ;)
		taskRepository.findAll().stream()
        .filter(task -> task.getTaskStatusData().getDone().equals(done))
        .forEach(task->
        	lTask.add(transformUtils.convertToTaskDto(task))
        );
		
		return lTask;
	}
	
	/**
	 * Method for obtaining all task in system
	 * @return TaskList {@link com.biesca.generated.model.TaskList}
	 */
	@Override
	public TaskList findAll() {

		LOGGER.info("findAll");

		TaskList lTask = new TaskList();

		taskRepository.findAll().forEach(task -> 			
			lTask.add(transformUtils.convertToTaskDto(task))
		);

		return lTask;
	}
	

	/**
	 * Method for saving a new task in system
	 * @param newTaskDto {@link com.biesca.generated.model.NewTaskDto}
	 * @return taskDto saved {@link com.biesca.generated.model.TaskDto}
	 * @throws AlreadyExistsException if task with same code exists
	 */
	@Override
	public TaskDto saveTask(NewTaskDto newTaskDto) {

		LOGGER.info("saveTask - newTaskDto = {}", newTaskDto);
		
		if (findByTaskCode(newTaskDto.getTaskCode())!=null)
			throw new AlreadyExistsException("Task code exists");	

		return transformUtils.convertToTaskDto(taskRepository.save(TaskData.builder().taskCode(newTaskDto.getTaskCode())
				.taskStatusData(TaskStatusData.builder().codeStatus(TaskStatusEnum.NEW.getType()).build())
				.taskDescription(newTaskDto.getTaskDescription()).build()));
	}
	

	/**
	 * Method for finding a task in system
	 * @param taskCode task code to find {@link java.lang.String}
	 * @return TaskDto if exists, null otherwise {@link com.biesca.generated.model.TaskDto}
	 */
	@Override
	public TaskDto findByTaskCode(String taskCode) {
		
		LOGGER.info("findByTaskCode - taskCode = {}", taskCode);
		
		Optional<TaskData> task = taskRepository.findByTaskCode(taskCode);
		
		return task.isPresent()
				? transformUtils.convertToTaskDto(taskRepository.findByTaskCode(taskCode).get())
				: null;
	}
	

	/**
	 * Method for updating a task in system. Task will be setted with updateAt at now time system.
	 * If new status is REMOVED the task will be setted as removed in database
	 * 
	 * @param taskCode task code to find {@link java.lang.String}
	 * @param status New task status {@link java.lang.String}
	 */
	@Override
	@Transactional
	public void updateTaskStatus(String taskCode, String status) {

		LOGGER.info("updateTaskStatus. taskCode = {}, status = {} ", taskCode, status);
		
		Optional<TaskData> taskData = taskRepository.findByTaskCode(taskCode);
		
		if (!taskData.isPresent() || iTaskStatusService.findByTaskStatusCode(status)==null) 
			throw new NotFoundException("No data found");		

		taskData.get().setTaskStatusData(TaskStatusData.builder().codeStatus(status).build());
		taskData.get().setUpdatedAt(OffsetDateTime.now());
		
		if (!status.equals(TaskStatusEnum.REMOVED.getType()))
			taskData.get().setRemoved(false);
		
		taskRepository.saveAndFlush(taskData.get());
	}
	



	
	/**
	 * Method for deleting (logical) a task in system. 
	 * 
	 * @param taskCode task code to be deleted {@link java.lang.Boolean}
	 * @throws NotFoundException if task not found
	 */
	@Transactional
	@Override
	public void deleteTask(String taskCode) {
		
		LOGGER.info("deleteTask. taskCode = {}", taskCode);
		
		Optional<TaskData> taskData = taskRepository.findByTaskCode(taskCode);
		
		if (!taskData.isPresent())
			throw new NotFoundException("No data found");
		
		taskData.get().setRemoved(true);
		taskData.get().setUpdatedAt(OffsetDateTime.now());
		taskData.get().setTaskStatusData(TaskStatusData.builder().codeStatus(TaskStatusEnum.REMOVED.getType()).build());
		taskRepository.saveAndFlush(taskData.get());
	}	

}