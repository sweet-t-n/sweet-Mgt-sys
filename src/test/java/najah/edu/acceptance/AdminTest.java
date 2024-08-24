package najah.edu.acceptance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sweet.Admin;

public class AdminTest {

    private Admin admin;

    @Before
    public void setUp() {
        // إنشاء كائن Admin لاختبار الوظائف
        admin = new Admin("adminUser", "adminPass", "admin@example.com", "AdminCountry");
    }

    @Test
    public void testGetUsername() {
        // تحقق من أن اسم المستخدم يتم إرجاعه بشكل صحيح
        assertEquals("adminUser", admin.getUsername());
    }

    @Test
    public void testSetUsername() {
        // تعيين اسم مستخدم جديد والتحقق من أنه تم تعيينه بشكل صحيح
        admin.setUsername("newUser");
        assertEquals("newUser", admin.getUsername());
    }

    @Test
    public void testGetPassword() {
        // تحقق من أن كلمة المرور يتم إرجاعها بشكل صحيح
        assertEquals("adminPass", admin.getPassword());
    }

    @Test
    public void testSetPassword() {
        // تعيين كلمة مرور جديدة والتحقق من أنها تم تعيينها بشكل صحيح
        admin.setPassword("newPass");
        assertEquals("newPass", admin.getPassword());
    }

    @Test
    public void testGetCountry() {
        // تحقق من أن البلد يتم إرجاعه بشكل صحيح
        assertEquals("AdminCountry", admin.getCountry());
    }

    @Test
    public void testSetCountry() {
        // تعيين بلد جديد والتحقق من أنه تم تعيينه بشكل صحيح
        admin.setCountry("NewCountry");
        assertEquals("NewCountry", admin.getCountry());
    }

    @Test
    public void testGetEmail() {
        // تحقق من أن البريد الإلكتروني يتم إرجاعه بشكل صحيح
        assertEquals("admin@example.com", admin.getEmail());
    }

    @Test
    public void testSetEmail() {
        // تعيين بريد إلكتروني جديد والتحقق من أنه تم تعيينه بشكل صحيح
        admin.setEmail("newadmin@example.com");
        assertEquals("newadmin@example.com", admin.getEmail());
    }
}
