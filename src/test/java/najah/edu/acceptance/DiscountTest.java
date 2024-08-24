package najah.edu.acceptance;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import sweet.MyApplication;

public class DiscountTest {

    private MyApplication app;

    // This method will be run before each test to initialize the application object
    @Before
    public void setUp() {
        app = new MyApplication();
    }

    @Test
    public void testApplyDiscountQuantityGreaterThanTen() {
        double price = 100.0;
        int quantity = 15;
        double expectedDiscount = price * 0.10;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountQuantityEqualToTen() {
        double price = 100.0;
        int quantity = 10;
        double expectedDiscount = 0.0;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountQuantityLessThanTen() {
        double price = 100.0;
        int quantity = 5;
        double expectedDiscount = 0.0;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountPriceZero() {
        double price = 0.0;
        int quantity = 15;
        double expectedDiscount = 0.0;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountPricePositiveWithQuantityZero() {
        double price = 50.0;
        int quantity = 0;
        double expectedDiscount = 0.0;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountPriceNegativeWithQuantityGreaterThanTen() {
        double price = -100.0;
        int quantity = 15;
        double expectedDiscount = price * 0.10;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountPricePositiveWithQuantityGreaterThanTen() {
        double price = 50.0;
        int quantity = 12;
        double expectedDiscount = price * 0.10;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }

    @Test
    public void testApplyDiscountWithPriceAndQuantityEdgeCases() {
        double price = 0.01;
        int quantity = 11;
        double expectedDiscount = price * 0.10;
        assertEquals(expectedDiscount, app.applyDiscount(price, quantity), 0.01);
    }
}
