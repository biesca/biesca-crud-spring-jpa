package com.biesca.application.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="task")
public class TaskData implements java.io.Serializable {

	private static final long serialVersionUID = -3667577131163275594L;

	@Id
	@GeneratedValue(generator = "task_generator")
	@SequenceGenerator(name = "task_generator", sequenceName = "seq_task", initialValue = 1, allocationSize = 1)
	@Column(name = "task_id", nullable = false, length = 10)
	private Long taskId;
       	
	@Column(name="task_code", nullable=false, length = 20)
	private String taskCode;
	
	@Column(name="task_description", nullable=true,  length = 100)
	private String taskDescription;
	
	@ManyToOne(targetEntity=TaskStatusData.class, fetch=FetchType.EAGER)	
	@JoinColumns(value={ @JoinColumn(name="task_code_status", referencedColumnName="code_status", nullable=false) }, foreignKey=@ForeignKey(name="fk_task_status"))	
	private TaskStatusData taskStatusData;
    
    @Column(name="created_at", nullable=false, length=13)
    private OffsetDateTime createdAt;
    
    @Column(name="updated_at", length=13)
    private OffsetDateTime updatedAt;   
    
    @Column(name="removed", nullable=false)
	private Boolean removed;
    
    @PrePersist
    protected void onCreate() {
    	if (createdAt==null) createdAt = OffsetDateTime.now();
    	if (removed==null) removed = false;
    }
}


