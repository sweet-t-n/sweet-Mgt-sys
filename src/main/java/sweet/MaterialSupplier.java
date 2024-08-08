package sweet;

public class MaterialSupplier {
    private String username;
    private String password;
    private String country;
    private String email;

    
    public MaterialSupplier(String username, String password, String email, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
