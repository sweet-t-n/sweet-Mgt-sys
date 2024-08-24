package sweet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MaterialSupplierTest {

    private MaterialSupplier supplier;

    @Before
    public void setUp() {
        // إعداد المورد لاختبار الوظائف
        supplier = new MaterialSupplier("supplierUser", "supplierPass", "supplier@example.com", "SupplierCountry");
    }

    @Test
    public void testMaterialSupplierCreation() {
        // تحقق من القيم الأولية عند إنشاء كائن MaterialSupplier
        assertEquals("supplierUser", supplier.getUsername());
        assertEquals("supplierPass", supplier.getPassword());
        assertEquals("supplier@example.com", supplier.getEmail());
        assertEquals("SupplierCountry", supplier.getCountry());
    }

    @Test
    public void testGetUsername() {
        // تحقق من الحصول على اسم المستخدم بشكل صحيح
        assertEquals("supplierUser", supplier.getUsername());
    }

    @Test
    public void testSetUsername() {
        // تعيين اسم مستخدم جديد والتحقق من أنه تم تعيينه بشكل صحيح
        supplier.setUsername("newSupplierUser");
        assertEquals("newSupplierUser", supplier.getUsername());
    }

    @Test
    public void testGetPassword() {
        // تحقق من الحصول على كلمة المرور بشكل صحيح
        assertEquals("supplierPass", supplier.getPassword());
    }

    @Test
    public void testSetPassword() {
        // تعيين كلمة مرور جديدة والتحقق من أنها تم تعيينها بشكل صحيح
        supplier.setPassword("newSupplierPass");
        assertEquals("newSupplierPass", supplier.getPassword());
    }

    @Test
    public void testGetEmail() {
        // تحقق من الحصول على البريد الإلكتروني بشكل صحيح
        assertEquals("supplier@example.com", supplier.getEmail());
    }

    @Test
    public void testSetEmail() {
        // تعيين بريد إلكتروني جديد والتحقق من أنه تم تعيينه بشكل صحيح
        supplier.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", supplier.getEmail());
    }

    @Test
    public void testGetCountry() {
        // تحقق من الحصول على الدولة بشكل صحيح
        assertEquals("SupplierCountry", supplier.getCountry());
    }

    @Test
    public void testSetCountry() {
        // تعيين دولة جديدة والتحقق من أنها تم تعيينها بشكل صحيح
        supplier.setCountry("NewSupplierCountry");
        assertEquals("NewSupplierCountry", supplier.getCountry());
    }
}
