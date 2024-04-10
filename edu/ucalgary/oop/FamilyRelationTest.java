package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class FamilyRelationTest {
    private DisasterVictim personOne;
    private DisasterVictim personTwo;
    private FamilyRelation familyRelation;

    @Before
    public void setUp() {
        personOne = new DisasterVictim("John", LocalDate.now());
        personTwo = new DisasterVictim("Jane", LocalDate.now());
        familyRelation = new FamilyRelation(personOne, personTwo, "Spouse");
    }

    @Test
    public void testGetPersonOne() {
        assertEquals(personOne, familyRelation.getPersonOne());
    }

    @Test
    public void testGetPersonTwo() {
        assertEquals(personTwo, familyRelation.getPersonTwo());
    }

    @Test
    public void testSetPersonOne() {
        DisasterVictim newPersonOne = new DisasterVictim("Alice", LocalDate.now());
        familyRelation.setPersonOne(newPersonOne);
        assertEquals(newPersonOne, familyRelation.getPersonOne());
    }

    @Test
    public void testSetPersonTwo() {
        DisasterVictim newPersonTwo = new DisasterVictim("Bob", LocalDate.now());
        familyRelation.setPersonTwo(newPersonTwo);
        assertEquals(newPersonTwo, familyRelation.getPersonTwo());
    }

    @Test
    public void testCommutativeEquals() {
        FamilyRelation commutativeRelation = new FamilyRelation(personTwo, personOne, "Spouse");
        assertTrue(familyRelation.equals(commutativeRelation));
    }

    @Test
    public void testEquals() {
        FamilyRelation sameRelation = new FamilyRelation(personOne, personTwo, "Spouse");
        FamilyRelation differentRelation = new FamilyRelation(personTwo, personOne, "Sibling");

        assertTrue(familyRelation.equals(sameRelation));
        assertTrue(familyRelation.equals(differentRelation));
    }

    @Test
    public void testNotEquals() {
        DisasterVictim differentPersonOne = new DisasterVictim("Alice", LocalDate.now());
        DisasterVictim differentPersonTwo = new DisasterVictim("Bob", LocalDate.now());
        FamilyRelation differentRelation = new FamilyRelation(differentPersonOne, differentPersonTwo, "Spouse");

        assertFalse(familyRelation.equals(differentRelation));
    }

    @Test
    public void testHashCode() {
        FamilyRelation sameRelation = new FamilyRelation(personOne, personTwo, "Spouse");
        FamilyRelation differentRelation = new FamilyRelation(personTwo, personOne, "Sibling");

        assertEquals(familyRelation.hashCode(), sameRelation.hashCode());
        assertEquals(familyRelation.hashCode(), differentRelation.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPersonOneNull() {
        familyRelation.setPersonOne(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPersonTwoNull() {
        familyRelation.setPersonTwo(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPersonOneEqualsPersonTwo() {
        familyRelation.setPersonOne(personTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPersonTwoEqualsPersonOne() {
        familyRelation.setPersonTwo(personOne);
    }

    @Test
    public void testSetRelation() {
        familyRelation.setRelation("Sibling");
        assertEquals("Sibling", familyRelation.getRelation());
    }

    @Test
    public void testGetRelation() {
        assertEquals("Spouse", familyRelation.getRelation());
    }

}