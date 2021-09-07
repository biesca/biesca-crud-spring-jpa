package com.biesca.application.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="task_status")
public class TaskStatusData implements Serializable  {

	private static final long serialVersionUID = -1106912956891178722L;
	
	@Id
	@Column(name="code_status", nullable=false, updatable = false, length=20)	
	private String codeStatus;
	
	@Column(name="done", nullable=false)
	private Boolean done;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "taskStatusData")
	private List<TaskData> tasks;
	
}
