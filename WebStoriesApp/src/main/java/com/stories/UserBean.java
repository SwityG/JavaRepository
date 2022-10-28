package com.stories;

/**
 * 
 * @author Swity Java bean to store user login data
 */
public class UserBean {

	private int userId;
	private String userName;
	private String password;
	private String email;

	public UserBean() {
	}

	public UserBean(int userId, String userName, String password, String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public UserBean(String userName2, String password2, String email2) {
		super();
		this.userName = userName2;
		this.password = password2;
		this.email = email2;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
