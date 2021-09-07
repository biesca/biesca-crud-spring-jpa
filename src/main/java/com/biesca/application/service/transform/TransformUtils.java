package com.biesca.application.service.transform;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.biesca.application.entity.TaskData;
import com.biesca.application.entity.TaskStatusData;
import com.biesca.generated.model.TaskDto;
import com.biesca.generated.model.TaskStatusDto;


@Component
public class TransformUtils extends ModelMapper {

	private TransformUtils() {}

	/**
	 * Transform taskData to TaskDto using ModelMapper library
	 * @param taskData {@link com.biesca.application.entity.TaskData}
	 * @return TaskDto  {@link com.biesca.generated.model.TaskDto}
	 */
	public TaskDto convertToTaskDto(TaskData taskData) {
		return map(taskData, TaskDto.class);
	}
	
	/**
	 * Transform TaskStatusData to TaskStatusDto using ModelMapper library
	 * @param taskStatusData {@link com.biesca.application.entity.TaskStatusData}
	 * @return TaskStatusDto  {@link com.biesca.generated.model.TaskStatusDto}
	 */
	public TaskStatusDto convertToTaskStatusDto(TaskStatusData taskStatusData) {
		return map(taskStatusData, TaskStatusDto.class);
	}

}