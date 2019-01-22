package com.cts.springboot.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ParentTask {
	
	public ParentTask() {}
	
	public ParentTask(Long parentTaskID, String parentTaskName) {
		super();
		this.parentTaskID = parentTaskID;
		this.parentTaskName = parentTaskName;
	}
	
	@Id
	@GeneratedValue
	private Long parentTaskID;
	private String parentTaskName;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="parentTask")
	private Set<Task> tasks=new HashSet<Task>();
	
	/**
	 * @return the parentTaskID
	 */
	public Long getParentTaskID() {
		return parentTaskID;
	}

	/**
	 * @param parentTaskID the parentTaskID to set
	 */
	public void setParentTaskID(Long parentTaskID) {
		this.parentTaskID = parentTaskID;
	}

	/**
	 * @return the parentTaskName
	 */
	public String getParentTaskName() {
		return parentTaskName;
	}

	/**
	 * @param parentTaskName the parentTaskName to set
	 */
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}

	/**
	 * @return the tasks
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}	
	
}
