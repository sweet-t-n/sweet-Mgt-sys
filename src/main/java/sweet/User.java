package sweet;

import java.util.Objects;

public class User {
	String username;
	String password;
	
	
	public User(String name, String pass) {
		username=name;
		password=pass;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	

}
