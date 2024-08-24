package najah.edu.acceptance;

import static org.junit.Assert.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Test;

import sweet.MyApplication;

public class OrderTrackingTest {

    private MyApplication app;
    private JTable orderTable;

    @Before
    public void setUp() {
        app = new MyApplication();

        // Create the JTable with some columns and rows for testing
        String[] columnNames = {"Product Name", "Quantity", "Price", "Status"};
        Object[][] data = {
            {"ProductA", 2, 50.0, "Shipped"},
            {"ProductB", 1, 30.0, "Processing"},
            {"ProductC", 5, 100.0, "Delivered"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        orderTable = new JTable(model);
    }

    @Test
    public void testTrackOrderWithSelectedRow() {
        // Select a specific row in the table (e.g., row 1 which is ProductB)
        orderTable.setRowSelectionInterval(1, 1);

        // Track order
        boolean result = app.trackOrder(orderTable);

        // Verify the result
        assertTrue(result);

        // You can also verify the selected row's status as an additional check
        int selectedRow = orderTable.getSelectedRow();
        String productName = (String) orderTable.getValueAt(selectedRow, 0);
        String status = (String) orderTable.getValueAt(selectedRow, 3);

        assertEquals("ProductB", productName);
        assertEquals("Processing", status);
    }

 
}
