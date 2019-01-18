package com.cts.springboot.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Task {
	
	public Task() {}
	
	public Task(Long taskID, String taskName, int priority, Date startDate, Date endDate, String status) {
		super();
		this.taskID = taskID;
		this.taskName = taskName;
		this.priority = priority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}
	
	@Id
	@GeneratedValue
	private Long taskID;
	private String taskName;
	private int priority;
	private Date startDate;
	private Date endDate;
	private String status;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userID")
	private User userID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectID")
	private Project projectID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parentTaskID")
	private ParentTask parentTaskID;	
	
	/**
	 * @return the userID
	 */
	public User getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(User userID) {
		this.userID = userID;
	}
	/**
	 * @return the projectID
	 */
	public Project getProjectID() {
		return projectID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(Project projectID) {
		this.projectID = projectID;
	}
	/**
	 * @return the parentTaskID
	 */
	public ParentTask getParentTaskID() {
		return parentTaskID;
	}
	/**
	 * @param parentTaskID the parentTaskID to set
	 */
	public void setParentTaskID(ParentTask parentTaskID) {
		this.parentTaskID = parentTaskID;
	}
	/**
	 * @return the taskID
	 */
	public Long getTaskID() {
		return taskID;
	}
	/**
	 * @param taskID the taskID to set
	 */
	public void setTaskID(Long taskID) {
		this.taskID = taskID;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
