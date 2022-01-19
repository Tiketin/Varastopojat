package model;

import javax.persistence.*;


/**
 * The Class Users.
 *
 * @author Nikke Tikka
 */
@Entity
@Table(name = "Users")
public class Users {
	
	/** The user id. */
	@Id
	@Column
	private int userId;
	
	/** The username. */
	@Column
	private String username;
	
	/** The password. */
	@Column
	private String password;
	
	/** The login status. */
	@Column 
	private String loginStatus;
	
	

	/**
	 * Instantiates a new users.
	 */
	public Users() {}
	
	/**
	 * Instantiates a new users.
	 *
	 * @param userId the user id
	 * @param username the username
	 * @param password the password
	 */
	public Users(int userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	
	/*public boolean logIn() {
		//tänne kirjautumisehdot?
	}*/
	
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the login status.
	 *
	 * @return the login status
	 */
	public String getLoginStatus() {
		return loginStatus;
	}

	/**
	 * Sets the login status.
	 *
	 * @param loginStatus the new login status
	 */
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
}
