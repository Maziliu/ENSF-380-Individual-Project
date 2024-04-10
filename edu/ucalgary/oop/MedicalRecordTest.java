package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class MedicalRecordTest {
    private Location location;
    private MedicalRecord medicalRecord;

    @Before
    public void setUp() {
        location = new Location("University of Calgary", "2500 University Dr NW");
        medicalRecord = new MedicalRecord(location, "treated for a broken leg", "2021-11-01");
    }

    @After
    public void tearDown() {
        location = null;
        medicalRecord = null;
    }

    @Test
    public void testSetLocation() {
        Location newLocation = new Location("Foothills Medical Centre", "1403 29 St NW");
        medicalRecord.setLocation(newLocation);
        assertEquals(newLocation, medicalRecord.getLocation());
    }

    @Test
    public void testSetTreatmentDetails() {
        medicalRecord.setTreatmentDetails("treated for a broken arm");
        assertEquals("treated for a broken arm", medicalRecord.getTreatmentDetails());
    }

    @Test
    public void testSetDateOfTreatment() {
        medicalRecord.setDateOfTreatment("2021-11-02");
        assertEquals("2021-11-02", medicalRecord.getDateOfTreatment().toString());
    }

    @Test
    public void testGetLocation() {
        Location testLocation = new Location("University of Calgary", "2500 University Dr NW");
        assertEquals(testLocation, medicalRecord.getLocation());
    }

    @Test
    public void testGetTreatmentDetails() {
        assertEquals("treated for a broken leg", medicalRecord.getTreatmentDetails());
    }

    @Test
    public void testGetDateOfTreatment() {
        assertEquals("2021-11-01", medicalRecord.getDateOfTreatment().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidFormatingDateOfTreatment() {
        new MedicalRecord(location, "treated for a broken leg", "2021/11/01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidMonthBoundaryDateOfTreatment() {
        new MedicalRecord(location, "treated for a broken leg", "2021-13-01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidDayBoundaryDateOfTreatment() {
        new MedicalRecord(location, "treated for a broken leg", "2021-11-32");
    }
}
