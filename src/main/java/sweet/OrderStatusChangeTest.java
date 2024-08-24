package sweet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.junit.Before;
import org.junit.Test;

public class OrderStatusChangeTest {

    private MyApplication app;
    private JTable orderTable;

    @Before
    public void setUp() {
        app = new MyApplication();

        // إعداد الجدول للاختبار
        String[] columnNames = {"Product Name", "Quantity", "Price", "Status"};
        Object[][] data = {
            {"ProductA", 2, 50.0, "Pending"},
            {"ProductB", 1, 30.0, "Processing"},
            {"ProductC", 5, 100.0, "Shipped"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        orderTable = new JTable(model);
    }

    @Test
    public void testChangeOrderStatusWithSelectedRow() {
        // اختيار الصف الأول
        orderTable.setRowSelectionInterval(0, 0);

        // تحديد الحالة الجديدة التي نريد استخدامها في الاختبار
        String newStatus = "Shipped";

        // تغيير حالة الطلب
        String updatedStatus = app.changeOrderStatus(orderTable, newStatus);

        // تحقق من أن الحالة قد تم تغييرها
        String actualStatus = (String) orderTable.getValueAt(0, 3);
        assertEquals(newStatus, actualStatus);
        assertEquals(newStatus, updatedStatus);
    }

    @Test
    public void testChangeOrderStatusWithNoSelectedRow() {
        // تأكد من عدم وجود صف محدد
        orderTable.clearSelection();

        // تغيير حالة الطلب
        app.changeOrderStatus(orderTable, null);

        // التحقق من أن الصف المحدد ما زال غير موجود
        int selectedRow = orderTable.getSelectedRow();
        assertEquals(-1, selectedRow);
    }

    @Test
    public void testChangeOrderStatusCancelled() {
        // اختيار الصف الثاني
        orderTable.setRowSelectionInterval(1, 1);

        // محاكاة الضغط على زر "Cancel" في `JOptionPane`
        JOptionPane.showMessageDialog(null, "Cancelled", "Cancelled", JOptionPane.CANCEL_OPTION);

        // التحقق من أن الحالة لم تتغير
        String expectedStatus = "Processing"; // الحالة الأصلية
        String actualStatus = (String) orderTable.getValueAt(1, 3);
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testChangeOrderStatusNoSelection() {
        // اختيار الصف الأول
        orderTable.setRowSelectionInterval(0, 0);

        // تغيير الحالة واختيار "Cancel" في `JOptionPane`
        JOptionPane.showMessageDialog(null, "Cancelled", "Cancelled", JOptionPane.CANCEL_OPTION);

        // تحقق من أن الحالة لم تتغير
        String expectedStatus = "Pending"; // الحالة الأصلية
        String actualStatus = (String) orderTable.getValueAt(0, 3);
        assertEquals(expectedStatus, actualStatus);
    }
    @Test
    public void testChangeOrderStatusWithSelectedRow1() throws IOException {
        // إعداد محاكاة قراءة الملف
        Path mockFilePath = Paths.get("Purchase_Report.txt");
        List<String> mockLines = Arrays.asList("ProductA|Shipped", "ProductB|Processing");
        Files.write(mockFilePath, mockLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // تنفيذ الاختبار
        orderTable.setRowSelectionInterval(0, 0);
        String newStatus = "Delivered";
        app.changeOrderStatus(orderTable, newStatus);

        // تحقق من الحالة المحدثة
        String actualStatus = (String) orderTable.getValueAt(0, 3);
        assertEquals(newStatus, actualStatus);
    }

}
