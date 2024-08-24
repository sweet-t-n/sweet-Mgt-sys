package najah.edu.acceptance;

import static org.junit.Assert.*;

import org.junit.Test;

import sweet.Post;

import java.time.LocalDateTime;

public class PostTest {

    @Test
    public void testPostCreation() {
        Post post = new Post("Title", "Content", "Author", "path/to/image.jpg");
        assertEquals("Title", post.getTitle());
        assertEquals("Content", post.getContent());
        assertEquals("Author", post.getAuthor());
        assertNotNull(post.getTimestamp());
        assertEquals("path/to/image.jpg", post.getImagePath());
    }

    @Test
    public void testSetters() {
        Post post = new Post("Title", "Content", "Author", "path/to/image.jpg");
        post.setTitle("New Title");
        post.setContent("New Content");
        post.setAuthor("New Author");
        post.setImagePath("new/path/to/image.jpg");

        assertEquals("New Title", post.getTitle());
        assertEquals("New Content", post.getContent());
        assertEquals("New Author", post.getAuthor());
        assertEquals("new/path/to/image.jpg", post.getImagePath());
    }

    @Test
    public void testDefaultValues() {
        Post post = new Post("", "", "", "");
        assertEquals("", post.getTitle());
        assertEquals("", post.getContent());
        assertEquals("", post.getAuthor());
        assertNotNull(post.getTimestamp());
        assertEquals("", post.getImagePath());
    }

    @Test
    public void testEmptyValues() {
        try {
            Post post = new Post("", "", "", "");
            assertEquals("", post.getTitle());
            assertEquals("", post.getContent());
            assertEquals("", post.getAuthor());
            assertEquals("", post.getImagePath());
            // Ensure no exceptions are thrown
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testTimestamp() {
        Post post = new Post("Title", "Content", "Author", "path/to/image.jpg");
        assertNotNull(post.getTimestamp());
    }

    @Test
    public void testImagePath() {
        Post post = new Post("Title", "Content", "Author", "path/to/image.jpg");
        assertEquals("path/to/image.jpg", post.getImagePath());

        post.setImagePath("new/path/to/image.jpg");
        assertEquals("new/path/to/image.jpg", post.getImagePath());
    }

  

    @Test
    public void testNullValues() {
        Post post = new Post("Title", "Content", "Author", "path/to/image.jpg");
        post.setTitle(null);
        post.setContent(null);
        post.setAuthor(null);
        post.setImagePath(null);

        assertNull(post.getTitle());
        assertNull(post.getContent());
        assertNull(post.getAuthor());
        assertNull(post.getImagePath());
    }
 

}

