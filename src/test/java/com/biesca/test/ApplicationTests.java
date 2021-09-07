package com.biesca.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.biesca.application.Application;
import com.biesca.application.constants.TaskStatusEnum;
import com.biesca.application.controller.TasksApiImpl;
import com.biesca.application.controller.TasksStatusApiImpl;
import com.biesca.application.entity.TaskData;
import com.biesca.application.service.transform.TransformUtils;
import com.biesca.generated.model.NewTaskDto;
import com.biesca.generated.model.TaskDto;
import com.biesca.generated.model.TaskStatusDto;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = Application.class)
class ApplicationTests  extends CommonTestUtils {
	
	protected static final String TEST_JSON_TASK= "test-json/TASK001_biesca.json";

	@InjectMocks
	@Autowired
	private TasksApiImpl tasksApiImpl;
	
	@InjectMocks
	@Autowired
	private TasksStatusApiImpl tasksStatusApiImpl;
	
	@Autowired
	TransformUtils transformUtils;
	
	private ResponseEntity<TaskDto> responseEntityTaskDto;
	
	private ResponseEntity<TaskStatusDto> responseEntityTaskStatusDto;
	
	private ResponseEntity<Void> responseEntity;
	
	@BeforeEach
    public void setUp() {
    }
	
	
	@Test
	void test00() {		
		assertEquals(5,tasksStatusApiImpl.getAllTasksStatus().getBody().size()); //Get all status types
		responseEntityTaskStatusDto = tasksStatusApiImpl.findTaskStatus("DOING");
		assertEquals(HttpStatus.OK, responseEntityTaskStatusDto.getStatusCode()); //Check HttpStatus 200		
	}
	
	
	@Test
	void test01() {
		assertEquals(1,tasksApiImpl.getAllTasks().getBody().size()); //Get all task in system
	}
	
    @Test
    void test02() {    	
    	
    	TaskData newTaskData = parseJsonDto(TEST_JSON_TASK, TaskData.class);    	   	
    	
    	NewTaskDto newTaskDto = new NewTaskDto();
    	newTaskDto.setTaskCode(newTaskData.getTaskCode());
    	newTaskDto.setTaskDescription(newTaskData.getTaskDescription());
    	
    	responseEntityTaskDto = tasksApiImpl.addNewTask(newTaskDto); //add new task to system
    	    	
    	assertEquals(HttpStatus.CREATED, responseEntityTaskDto.getStatusCode()); //Check HttpStatus 201
    	
    	assertEquals(2, tasksApiImpl.getAllTasks().getBody().size()); //Check Task list size
    	    	
    	responseEntityTaskDto = tasksApiImpl.addNewTask(newTaskDto); //Add same task code
    	
    	assertEquals(HttpStatus.ALREADY_REPORTED, responseEntityTaskDto.getStatusCode()); //Check HttpStatus 208
    	
    	assertEquals(2, tasksApiImpl.getAllTasks().getBody().size()); //Check Task list size is the same
    }
    
	@Test
	void test03() {	
		
		responseEntity = tasksApiImpl.updateTaskStatus("NO_TASK_EXISTS", TaskStatusEnum.DOING.getType());		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode()); //Check HttpStatus 404
		
		Assert.assertTrue(tasksApiImpl.getDoneTasks().getBody().isEmpty());
		
		responseEntity = tasksApiImpl.updateTaskStatus(parseJsonDto(TEST_JSON_TASK, TaskData.class).getTaskCode(), TaskStatusEnum.DOING.getType());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); //Check HttpStatus 200		
		
		Assert.assertNotNull(tasksApiImpl.getAllTasks().getBody().get(1).getUpdatedAt());		
		
		tasksApiImpl.updateTaskStatus(parseJsonDto(TEST_JSON_TASK, TaskData.class).getTaskCode(), TaskStatusEnum.STOPPED.getType());
		
		tasksApiImpl.updateTaskStatus(parseJsonDto(TEST_JSON_TASK, TaskData.class).getTaskCode(), TaskStatusEnum.DONE.getType());
		
		Assert.assertTrue(!tasksApiImpl.getDoneTasks().getBody().isEmpty());
		
		assertEquals(1, tasksApiImpl.getUnfinishedTasks().getBody().size()); //Check unfinished tasks is one
		
		tasksApiImpl.deleteTask(parseJsonDto(TEST_JSON_TASK, TaskData.class).getTaskCode()); //Delete task
		
		assertEquals(2, tasksApiImpl.getUnfinishedTasks().getBody().size()); //Check unfinished tasks is twho	
				
	}
	
	

}
