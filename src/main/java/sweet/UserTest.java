package sweet;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        // إعداد المستخدم لاختبار الوظائف
        user = new User("testUser", "password123", "test@example.com", "TestCountry");
    }

    @Test
    public void testUserCreation() {
        // تحقق من القيم الأولية عند إنشاء كائن User
        assertEquals("testUser", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("TestCountry", user.getCountry());
    }

    @Test
    public void testCreatePost() {
        // إضافة منشور جديد والتحقق من إضافته بشكل صحيح
        user.createPost("This is a test post.", "http://example.com/image.jpg");
        List<String> descriptions = user.getPostDescriptions();
        List<String> imageUrls = user.getPostImageUrls();

        assertEquals(1, descriptions.size());
        assertEquals(1, imageUrls.size());
        assertEquals("This is a test post.", descriptions.get(0));
        assertEquals("http://example.com/image.jpg", imageUrls.get(0));
    }

    @Test
    public void testGetName() {
        // تحقق من الحصول على الاسم بشكل صحيح
        assertEquals("testUser", user.getName());
    }

    @Test
    public void testSetName() {
        // تعيين اسم جديد والتحقق من أنه تم تعيينه بشكل صحيح
        user.setName("newUser");
        assertEquals("newUser", user.getName());
    }

    @Test
    public void testGetPassword() {
        // تحقق من الحصول على كلمة المرور بشكل صحيح
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        // تعيين كلمة مرور جديدة والتحقق من أنها تم تعيينها بشكل صحيح
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testGetEmail() {
        // تحقق من الحصول على البريد الإلكتروني بشكل صحيح
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        // تعيين بريد إلكتروني جديد والتحقق من أنه تم تعيينه بشكل صحيح
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    public void testGetCountry() {
        // تحقق من الحصول على الدولة بشكل صحيح
        assertEquals("TestCountry", user.getCountry());
    }

    @Test
    public void testSetCountry() {
        // تعيين دولة جديدة والتحقق من أنها تم تعيينها بشكل صحيح
        user.setCountry("NewCountry");
        assertEquals("NewCountry", user.getCountry());
    }

    @Test
    public void testGetPostDescriptions() {
        // تحقق من الحصول على أوصاف المنشورات بشكل صحيح
        user.createPost("Post description", "http://example.com/image.jpg");
        List<String> descriptions = user.getPostDescriptions();
        assertEquals(1, descriptions.size());
        assertEquals("Post description", descriptions.get(0));
    }

    @Test
    public void testGetPostImageUrls() {
        // تحقق من الحصول على روابط صور المنشورات بشكل صحيح
        user.createPost("Post description", "http://example.com/image.jpg");
        List<String> imageUrls = user.getPostImageUrls();
        assertEquals(1, imageUrls.size());
        assertEquals("http://example.com/image.jpg", imageUrls.get(0));
    }
}
