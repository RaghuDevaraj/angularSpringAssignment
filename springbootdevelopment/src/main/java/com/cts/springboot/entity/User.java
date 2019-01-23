package com.cts.springboot.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	public User(){}

	public User(Long userID, String firstName, String lastName, String employeeID) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
	}
	
	@Id
	@GeneratedValue
	private Long userID;
	private String firstName;
	private String lastName;
	private String employeeID;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user", orphanRemoval = true)
	private Set<Project> projects=new HashSet<Project>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user", orphanRemoval = true)
	private Set<Task> tasks=new HashSet<Task>();

	/**
	 * @return the userID
	 */
	public Long getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the employeeID
	 */
	public String getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID the employeeID to set
	 */
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	
}
