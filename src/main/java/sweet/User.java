package sweet;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String password;
    private String email;
    private String country;
    private List<String> postDescriptions;
    private List<String> postImageUrls;

    public User(String name, String password, String email, String country) {
        this.setName(name);
        this.setPassword(password);
        this.setEmail(email);
        this.setCountry(country);
        this.postDescriptions = new ArrayList<>();
        this.postImageUrls = new ArrayList<>();
    }

    public void createPost(String description, String imageUrl) {
        postDescriptions.add(description);
        postImageUrls.add(imageUrl);
    }

    public List<String> getPostDescriptions() {
        return postDescriptions;
    }

    public List<String> getPostImageUrls() {
        return postImageUrls;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

  
}
