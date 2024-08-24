package najah.edu.acceptance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sweet.login;

public class LoginTest {

    private login loginManager;

    @Before
    public void setUp() {
        loginManager = new login();
    }

    @Test
    public void testInitialLoggedInState() {
        // تحقق من الحالة الافتراضية عند إنشاء كائن جديد
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testSetCredentials() {
        // إعداد بيانات الاعتماد
        loginManager.setCredentials("user1", "password1");
        
        // التحقق من تعيين القيم بشكل صحيح
        assertTrue(loginManager.login("user1", "password1"));
    }

    @Test
    public void testValidLogin() {
        // إعداد بيانات الاعتماد
        loginManager.setCredentials("user1", "password1");
        
        // محاولة تسجيل الدخول باستخدام بيانات الاعتماد الصحيحة
        assertTrue(loginManager.login("user1", "password1"));
        assertTrue(loginManager.isLoggedIn());
    }

    @Test
    public void testInvalidLogin() {
        // إعداد بيانات الاعتماد
        loginManager.setCredentials("user1", "password1");
        
        // محاولة تسجيل الدخول باستخدام بيانات الاعتماد غير الصحيحة
        assertFalse(loginManager.login("user1", "wrongpassword"));
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testLoginWithDifferentUsername() {
        // إعداد بيانات الاعتماد
        loginManager.setCredentials("user1", "password1");
        
        // محاولة تسجيل الدخول باستخدام اسم مستخدم مختلف
        assertFalse(loginManager.login("user2", "password1"));
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testLoginWithDifferentPassword() {
        // إعداد بيانات الاعتماد
        loginManager.setCredentials("user1", "password1");
        
        // محاولة تسجيل الدخول باستخدام كلمة مرور مختلفة
        assertFalse(loginManager.login("user1", "differentpassword"));
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testLogout() {
        // إعداد بيانات الاعتماد
        loginManager.setCredentials("user1", "password1");
        
        // تسجيل الدخول أولاً
        loginManager.login("user1", "password1");
        assertTrue(loginManager.isLoggedIn());
        
        // تسجيل الخروج
        loginManager.logout();
        assertFalse(loginManager.isLoggedIn());
    }
}
