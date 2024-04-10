package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SupplyTest {
    private Supply supply;

    public SupplyTest() {
    }

    @Before
    public void setUp() {
        supply = new Supply("test", 10);
    }

    @After
    public void tearDown() {
        supply = null;
    }

    @Test
    public void testGetQuantity() {
        assertEquals(10, supply.getQuantity());
    }

    @Test
    public void testGetType() {
        assertEquals("test", supply.getType());
    }

    @Test
    public void testSetQuantity() {
        supply.setQuantity(20);
        assertEquals(20, supply.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetQuantityNegative() {
        supply.setQuantity(-1);
    }

    @Test
    public void testDecrementQuantity() {
        supply.decrementQuantity(1);
        assertEquals(9, supply.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecrementQuantityNegativeWhenAvailableIsZero() {
        supply.setQuantity(0);
        supply.decrementQuantity(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecrementQuantityByMoreThanAvailable() {
        supply.decrementQuantity(11);
    }

    @Test
    public void testEquals() {
        Supply other = new Supply("test", 5);
        assertEquals(true, supply.equals(other));
    }
}
