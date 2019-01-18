package com.cts.springboot.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project {
	
	public Project() {
	}

	public Project(Long projectID, String projectName, Date startDate, Date endDate, int priority) {
		super();
		this.projectID = projectID;
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}

	@Id
	@GeneratedValue
	private Long projectID;
	private String projectName;
	private Date startDate;
	private Date endDate;
	private int priority;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userID")
	private User userID;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="projectID", fetch=FetchType.EAGER)
	private Set<Task> tasks=new HashSet<Task>();	

	/**
	 * @return the projectID
	 */
	public Long getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID
	 *            the projectID to set
	 */
	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
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
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the userID
	 */
	public User getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(User userID) {
		this.userID = userID;
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
