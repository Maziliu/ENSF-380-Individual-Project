package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class DataGenerationTest {
    private DataGeneration dataGeneration;

    @Before
    public void setUp() {
        DriverApplication.locations.clear();
        DriverApplication.disasterVictims.clear();
        DriverApplication.supplies.clear();
    }

    @After
    public void tearDown() {
        DriverApplication.locations.clear();
        DriverApplication.disasterVictims.clear();
        DriverApplication.supplies.clear();
    }

    @Test
    public void testGenerateSupplies() {
        ArrayList<Supply> supplies = DataGeneration.generateSupplies();
        assertNotNull(supplies);
        assertEquals(100, supplies.size());
    }

    @Test
    public void testGenerateVictims() {
        ArrayList<DisasterVictim> victims = DataGeneration.generateVictims();
        assertNotNull(victims);
        assertTrue(victims.size() >= 0 && victims.size() <= 10);
    }

    @Test
    public void testGenerateData() {
        DriverApplication.locations.clear();
        DriverApplication.disasterVictims.clear();
        DriverApplication.supplies.clear();

        DataGeneration.generateData();

        assertFalse(DriverApplication.locations.isEmpty());
        assertFalse(DriverApplication.disasterVictims.isEmpty());
        assertFalse(DriverApplication.supplies.isEmpty());
    }
}