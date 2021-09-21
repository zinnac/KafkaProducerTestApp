package com.zinac.demo;

public class user {

	private String userId;
	private String firstName;
	private String lastName;
	
	public user(String userId, String firstName, String lastName) {
		this.setUserId(userId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public String toString() {
		return "\"User [UserId "+ userId + ", FirstName = " + firstName + ", LastName = " + lastName + "]";
	}
	
}
