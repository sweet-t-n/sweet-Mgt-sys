package sweet;

import java.time.LocalDateTime;

public class Post {
    private String title;
    private String content;
    private LocalDateTime timestamp;
    private String author;
    private String imagePath;

    // Constructor
    public Post(String title, String content, String author, String imagePath) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
