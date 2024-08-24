package sweet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PostManagerTest {

    private PostManager postManager;

    @Before
    public void setUp() {
        postManager = new PostManager();
    }

    @Test
    public void testCreatePostSuccess() {
        // اختبار إنشاء منشور بنجاح
        boolean result = postManager.createPost("user1", "Post Description", "imagePath");
        assertTrue(result);
        assertEquals("Post created successfully", postManager.getFeedbackMessage());
        
        // تحقق من أن المنشور تم إنشاؤه بنجاح
        Post post = postManager.getPost("user1", "Post Description");
        assertNotNull(post);
        assertEquals("Post Description", post.getTitle());
        assertEquals("imagePath", post.getImagePath());
        assertEquals("user1", post.getAuthor());
    }

    @Test
    public void testCreatePostWithEmptyDescription() {
        // اختبار محاولة إنشاء منشور مع وصف فارغ
        boolean result = postManager.createPost("user1", "", "imagePath");
        assertFalse(result);
        assertEquals("Description cannot be empty", postManager.getFeedbackMessage());
    }

    @Test
    public void testCreatePostWithEmptyImage() {
        // اختبار محاولة إنشاء منشور مع صورة فارغة
        boolean result = postManager.createPost("user1", "Post Description", "");
        assertFalse(result);
        assertEquals("Image cannot be empty", postManager.getFeedbackMessage());
    }

    @Test
    public void testCreatePostWithNullDescription() {
        // اختبار محاولة إنشاء منشور مع وصف null
        boolean result = postManager.createPost("user1", null, "imagePath");
        assertFalse(result);
        assertEquals("Description cannot be empty", postManager.getFeedbackMessage());
    }

    @Test
    public void testCreatePostWithNullImage() {
        // اختبار محاولة إنشاء منشور مع صورة null
        boolean result = postManager.createPost("user1", "Post Description", null);
        assertFalse(result);
        assertEquals("Image cannot be empty", postManager.getFeedbackMessage());
    }

    @Test
    public void testGetPostSuccess() {
        // إنشاء منشور ثم محاولة الحصول عليه
        postManager.createPost("user1", "Post Description", "imagePath");
        Post post = postManager.getPost("user1", "Post Description");
        assertNotNull(post);
        assertEquals("Post Description", post.getTitle());
        assertEquals("imagePath", post.getImagePath());
        assertEquals("user1", post.getAuthor());
    }

    @Test
    public void testGetPostNotFound() {
        // محاولة الحصول على منشور غير موجود
        Post post = postManager.getPost("user1", "Non-existent Post");
        assertNull(post);
    }
}
