package sweet;

import java.util.HashMap;
import java.util.Map;

public class PostManager {
    private Map<String, Post> posts = new HashMap<>();
    private String feedbackMessage;

    public boolean createPost(String username, String description, String image) {
        if (description == null || description.trim().isEmpty()) {
            feedbackMessage = "Description cannot be empty";
            return false;
        }
        if (image == null || image.trim().isEmpty()) {
            feedbackMessage = "Image cannot be empty";
            return false;
        }

        Post newPost = new Post(description, image, username, image);
        posts.put(username + "_" + description, newPost);
        feedbackMessage = "Post created successfully";
        return true;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public Post getPost(String username, String description) {
        return posts.get(username + "_" + description);
    }
}
