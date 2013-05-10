package com.jeff.demo.tag.po;

public class UserInfo {
	private String userName;
	private int age;
	private String email;
	
	public UserInfo(String userName, int age, String email) {
	    super();
	    this.userName = userName;
	    this.age = age;
	    this.email = email;
    }
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
