package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

public class DisasterVictimTest {
    private DisasterVictim victim;

    @Before
    public void setUp() {
        victim = new DisasterVictim("John", LocalDate.now());
    }

    @After
    public void tearDown() {
        victim = null;
    }

    @Test
    public void testCounter() {
        new DisasterVictim("Bob", LocalDate.now());
        new DisasterVictim("Tim", LocalDate.now());
        assertTrue(DisasterVictim.getCounter() > 0);
    }

    @Test
    public void testSetDietaryRestrictions() {
        EnumSet<DietaryRestrictions> restrictions = EnumSet.of(DietaryRestrictions.AVML, DietaryRestrictions.DBML);
        victim.setDietaryRestrictions(restrictions);
        assertEquals(restrictions, victim.getDietaryRestrictions());
    }

    @Test
    public void testGetDietaryRestrictions() {
        EnumSet<DietaryRestrictions> restrictions = EnumSet.of(DietaryRestrictions.AVML, DietaryRestrictions.DBML);
        victim.setDietaryRestrictions(restrictions);
        assertEquals(restrictions, victim.getDietaryRestrictions());
    }

    @Test
    public void testGetCounter() {
        int counter = DisasterVictim.getCounter();
        assertEquals(counter, victim.getAssignedSocialID() + 1);
    }

    @Test
    public void testGetAssignedSocialID() {
        int assignedSocialID = victim.getAssignedSocialID();
        assertEquals(assignedSocialID, DisasterVictim.getCounter() - 1);
    }

    @Test
    public void testGetEntryDate() {
        LocalDate entryDate = victim.getEntryDate();
        assertEquals(entryDate, LocalDate.now());
    }

    @Test
    public void testGetComments() {
        String comments = "Test comments";
        victim.setComments(comments);
        assertEquals(comments, victim.getComments());
    }

    @Test
    public void testGetMedicalRecords() {
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord(new Location("test1", "123 st ne"), "Record 1", "2021-01-01"));
        medicalRecords.add(new MedicalRecord(new Location("test2", "567 st ne"), "Record 2", "2021-01-02"));
        victim.setMedicalRecords(medicalRecords);
        assertEquals(medicalRecords, victim.getMedicalRecords());
    }

    @Test
    public void testGetPersonalBelongings() {
        ArrayList<Supply> personalBelongings = new ArrayList<>();
        personalBelongings.add(new Supply("Item 1", 5));
        personalBelongings.add(new Supply("Item 2", 10));
        victim.setPersonalBelongings(personalBelongings);
        assertEquals(personalBelongings, victim.getPersonalBelongings());
    }

    @Test
    public void testSetFamilyConnections() {
        ArrayList<String> testRelationships = new ArrayList<>();
        ArrayList<DisasterVictim> testDisasterVictims = new ArrayList<>();
        testDisasterVictims.add(new DisasterVictim("Jane", LocalDate.now()));
        testDisasterVictims.add(new DisasterVictim("Joe", LocalDate.now()));
        testDisasterVictims.add(new DisasterVictim("Jack", LocalDate.now()));
        testDisasterVictims.add(new DisasterVictim("Jill", LocalDate.now()));

        HashSet<FamilyRelation> testRelations = new HashSet<>();
        for (DisasterVictim personTwo : testDisasterVictims) {
            FamilyRelation relation = new FamilyRelation(victim, personTwo, "Test relation");
            testRelations.add(relation);
            testRelationships.add("Test relation");
        }
        victim.setFamilyConnections(testDisasterVictims, testRelationships);
        assertEquals(testRelations, victim.getFamilyConnections());
    }

    @Test
    public void testGetFamilyConnections() {
        ArrayList<String> testRelationships = new ArrayList<>();
        ArrayList<DisasterVictim> testDisasterVictims = new ArrayList<>();
        testDisasterVictims.add(new DisasterVictim("Jane", LocalDate.now()));
        testDisasterVictims.add(new DisasterVictim("Joe", LocalDate.now()));
        testDisasterVictims.add(new DisasterVictim("Jack", LocalDate.now()));
        testDisasterVictims.add(new DisasterVictim("Jill", LocalDate.now()));

        HashSet<FamilyRelation> testRelations = new HashSet<>();
        for (DisasterVictim personTwo : testDisasterVictims) {
            FamilyRelation relation = new FamilyRelation(victim, personTwo, "Test relation");
            testRelations.add(relation);
            testRelationships.add("Test relation");
        }
        victim.setFamilyConnections(testDisasterVictims, testRelationships);
        assertEquals(testRelations, victim.getFamilyConnections());
    }

    @Test
    public void testAddPersonalBelonging() {
        Supply supply = new Supply("Item 1", 5);
        victim.addPersonalBelonging(supply);
        assertEquals(supply, victim.getPersonalBelongings().get(0));
    }

    @Test
    public void testAddFamilyConnection() {
        DisasterVictim personTwo = new DisasterVictim("Jane", LocalDate.now());
        victim.addFamilyConnection(personTwo, "Test relation");
        FamilyRelation relation = new FamilyRelation(victim, personTwo, "Test relation");
        assertTrue(victim.getFamilyConnections().contains(relation));
        assertTrue(personTwo.getFamilyConnections().contains(relation));
    }

    @Test
    public void testAddMedicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord(new Location("test1", "123 st ne"), "Record 1", "2021-01-01");
        victim.addMedicalRecord(medicalRecord);
        assertTrue(victim.getMedicalRecords().contains(medicalRecord));
    }

    @Test
    public void testAddDietaryRestriction() {
        DietaryRestrictions restriction = DietaryRestrictions.AVML;
        victim.addDietaryRestriction(restriction);
        assertTrue(victim.getDietaryRestrictions().contains(restriction));
    }

    @Test
    public void testRemoveDietaryRestriction() {
        EnumSet<DietaryRestrictions> restrictions = EnumSet.of(DietaryRestrictions.AVML, DietaryRestrictions.DBML);
        victim.setDietaryRestrictions(restrictions);
        victim.removeDietaryRestriction(DietaryRestrictions.AVML);
        assertFalse(victim.getDietaryRestrictions().contains(DietaryRestrictions.AVML));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDietaryRestrictionException() {
        victim.removeDietaryRestriction(DietaryRestrictions.AVML);
    }

    @Test
    public void testRemovePersonalBelonging() {
        Supply supply = new Supply("Item 1", 5);
        victim.addPersonalBelonging(supply);
        victim.removePersonalBelonging(supply);
        assertFalse(victim.getPersonalBelongings().contains(supply));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovePersonalBelongingException() {
        Supply supply = new Supply("Item 1", 5);
        victim.removePersonalBelonging(supply);
    }

    @Test
    public void testRemoveFamilyConnectionSingular() {
        DisasterVictim personTwo = new DisasterVictim("Jane", LocalDate.now());
        victim.addFamilyConnection(personTwo, "Test relation");
        victim.removeFamilyConnection(personTwo, "Test relation");
        FamilyRelation relation = new FamilyRelation(victim, personTwo, "Test relation");
        assertFalse(victim.getFamilyConnections().contains(relation));
        assertFalse(personTwo.getFamilyConnections().contains(relation));
    }

    @Test
    public void testRemoveFamilyConnectionMultiple() {
        DisasterVictim personTwo = new DisasterVictim("Jane", LocalDate.now());
        DisasterVictim personThree = new DisasterVictim("Joe", LocalDate.now());
        DisasterVictim personFour = new DisasterVictim("Jack", LocalDate.now());
        victim.addFamilyConnection(personTwo, "Test relation");
        victim.addFamilyConnection(personThree, "Test relation2");
        victim.addFamilyConnection(personFour, "Test relation3");
        victim.removeFamilyConnection(personThree, "Test relation2");
        victim.removeFamilyConnection(personTwo, "Test relation");
        FamilyRelation relation = new FamilyRelation(victim, personTwo, "Test relation");
        assertFalse(victim.getFamilyConnections().contains(relation));
        assertFalse(personTwo.getFamilyConnections().contains(relation));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFamilyConnectionException() {
        DisasterVictim personTwo = new DisasterVictim("Jane", LocalDate.now());
        victim.removeFamilyConnection(personTwo, "Test relation");
    }

    @Test
    public void testRemoveMedicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord(new Location("test1", "123 st ne"), "Record 1", "2021-01-01");
        victim.addMedicalRecord(medicalRecord);
        victim.removeMedicalRecord(medicalRecord);
        assertFalse(victim.getMedicalRecords().contains(medicalRecord));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMedicalRecordException() {
        MedicalRecord medicalRecord = new MedicalRecord(new Location("test1", "123 st ne"), "Record 1", "2021-01-01");
        victim.removeMedicalRecord(medicalRecord);
    }

    @Test
    public void testEquals() {
        DisasterVictim other = new DisasterVictim("John", LocalDate.now());
        assertFalse(victim.equals(other));
    }

    @Test
    public void testFamilyRelationship() {
        DisasterVictim personTwo = new DisasterVictim("Jane", LocalDate.now());
        DisasterVictim personThree = new DisasterVictim("Joe", LocalDate.now());
        personThree.addFamilyConnection(personTwo, "Test relation");
        FamilyRelation relation = personThree.getFamilyConnections().iterator().next();
        relation.setRelation("Test relation2");
        assertTrue(personTwo.getFamilyConnections().iterator().next().getRelation().equals("Test relation2"));
        assertTrue(personThree.getFamilyConnections().iterator().next().getRelation().equals("Test relation2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSelfFamilyRelationship() {
        DisasterVictim victim = new DisasterVictim("Jane", LocalDate.now());
        victim.addFamilyConnection(victim, "Test relation");
    }

    @Test
    public void testConcurrentAccessToFamilyConnections() throws InterruptedException {
        final DisasterVictim sharedVictim = new DisasterVictim("Shared", LocalDate.now());
        Thread thread1 = new Thread(() -> {
            sharedVictim.addFamilyConnection(new DisasterVictim("Thread1", LocalDate.now()), "Sibling");
        });
        Thread thread2 = new Thread(() -> {
            sharedVictim.addFamilyConnection(new DisasterVictim("Thread2", LocalDate.now()), "Parent");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals("Expecting 2 connections", 2, sharedVictim.getFamilyConnections().size());
    }
}
