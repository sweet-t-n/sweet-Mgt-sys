package sweet;

import java.time.LocalDateTime;

public class post {
    private static String title;
    private static String content;
    private static LocalDateTime timestamp;
    private static String author;

    // Constructor
    public post(String title, String content, String author) {
        post.title = title;
        post.content = content;
        post.author = author;
        post.timestamp = LocalDateTime.now(); // تعيين التاريخ والوقت الحالي عند إنشاء المنشور
    }

    // Getter and Setter for title
    public static String getTitle() {
      
		return title;
    }

    public  void setTitle(String title) {
        post.title = title;
    }

    // Getter and Setter for content
    public static String getContent() {
        return content;
    }

    public void setContent(String content) {
        post.content = content;
    }

  
    public static LocalDateTime getTimestamp() {
       
		return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        post.timestamp = timestamp;
    }

    // Getter and Setter for author
    public static String getAuthor() {
        
		return author;
    }

    public void setAuthor(String author) {
        post.author = author;
    }
}
