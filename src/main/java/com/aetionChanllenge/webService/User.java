package com.aetionChanllenge.webService;

public class User {

	private String email, firstName, lastName;
	private int age;
	
	public User(String email, String firstName, String lastName, int age)
	{
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public int getAge()
	{
		return age;
	}
	
}
