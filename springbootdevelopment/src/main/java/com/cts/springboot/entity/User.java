package com.cts.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	public User(){}

	public User(Long id, String firstName, String lastName, String employeeID) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String employeeID;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
}
