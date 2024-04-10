package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class LocationTest {
    private Location location;
    private Supply supply1;
    private Supply supply2;
    private DisasterVictim occupant1;
    private DisasterVictim occupant2;

    @Before
    public void setUp() {
        location = new Location("Test Location", "123 Main St");
        supply1 = new Supply("Food", 10);
        supply2 = new Supply("Water", 5);
        occupant1 = new DisasterVictim("John Doe", LocalDate.of(2022, 5, 22));
        occupant2 = new DisasterVictim("Jane Smith", LocalDate.of(2012, 1, 22));
    }

    @After
    public void tearDown() {
        location = null;
        supply1 = null;
        supply2 = null;
        occupant1 = null;
        occupant2 = null;
    }

    @Test
    public void testGetName() {
        assertEquals("Test Location", location.getName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("123 Main St", location.getAddress());
    }

    @Test
    public void testGetSupplies() {
        ArrayList<Supply> supplies = location.getSupplies();
        assertNotNull(supplies);
        assertEquals(0, supplies.size());
    }

    @Test
    public void testGetOccupants() {
        ArrayList<DisasterVictim> occupants = location.getOccupants();
        assertNotNull(occupants);
        assertEquals(0, occupants.size());
    }

    @Test
    public void testSetName() {
        location.setName("New Location");
        assertEquals("New Location", location.getName());
    }

    @Test
    public void testSetAddress() {
        location.setAddress("456 Elm St");
        assertEquals("456 Elm St", location.getAddress());
    }

    @Test
    public void testSetSupplies() {
        ArrayList<Supply> supplies = new ArrayList<>();
        supplies.add(supply1);
        supplies.add(supply2);
        location.setSupplies(supplies);
        assertEquals(supplies, location.getSupplies());
    }

    @Test
    public void testSetOccupants() {
        ArrayList<DisasterVictim> occupants = new ArrayList<>();
        occupants.add(occupant1);
        occupants.add(occupant2);
        location.setOccupants(occupants);
        assertEquals(occupants, location.getOccupants());
    }

    @Test
    public void testAddSupply() {
        location.addSupply(supply1);
        ArrayList<Supply> supplies = location.getSupplies();
        assertEquals(1, supplies.size());
        assertTrue(supplies.contains(supply1));
    }

    @Test
    public void testAddSupplyExisting() {
        location.addSupply(supply1);
        location.addSupply(supply1);
        ArrayList<Supply> supplies = location.getSupplies();
        assertEquals(1, supplies.size());
        assertEquals(20, supplies.get(0).getQuantity());
    }

    @Test
    public void testAddOccupant() {
        location.addOccupant(occupant1);
        ArrayList<DisasterVictim> occupants = location.getOccupants();
        assertEquals(1, occupants.size());
        assertTrue(occupants.contains(occupant1));
    }

    @Test
    public void testRemoveSupply() {
        location.addSupply(supply1);
        location.removeSupply(supply1);
        ArrayList<Supply> supplies = location.getSupplies();
        assertEquals(0, supplies.size());
    }

    @Test
    public void testRemoveOccupant() {
        location.addOccupant(occupant1);
        location.removeOccupant(occupant1);
        ArrayList<DisasterVictim> occupants = location.getOccupants();
        assertEquals(0, occupants.size());
    }

    @Test
    public void testAssignSupply() {
        location.addSupply(supply1);
        location.addOccupant(occupant1);
        location.assignSupply(supply1, occupant1, 5);
        ArrayList<Supply> occupantSupplies = occupant1.getPersonalBelongings();
        assertEquals(1, occupantSupplies.size());
        assertEquals(5, occupantSupplies.get(0).getQuantity());
        assertEquals(5, supply1.getQuantity());
    }

    @Test
    public void testEquals() {
        Location other = new Location("Test Location", "123 Main St");
        assertEquals(true, location.equals(other));
    }
}