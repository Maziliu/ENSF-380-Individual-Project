package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

public class InquirerLogTest {
    private InquirerLog inquirerLog;
    private Inquirer inquirer;
    private LocalDate callDate;

    @Before
    public void setUp() {
        inquirer = new Inquirer("John Doe", "", "999-000-9999", "info");
        callDate = LocalDate.of(2022, 1, 1);
        inquirerLog = new InquirerLog(inquirer, "Test details", callDate);
    }

    @After
    public void tearDown() {
        inquirerLog = null;
        inquirer = null;
        callDate = null;
    }

    @Test
    public void testGetDetails() {
        assertEquals("Test details", inquirerLog.getDetails());
    }

    @Test
    public void testSetDetails() {
        inquirerLog.setDetails("New details");
        assertEquals("New details", inquirerLog.getDetails());
    }

    @Test
    public void testGetInquirer() {
        assertEquals(inquirer, inquirerLog.getInquirer());
    }

    @Test
    public void testSetInquirer() {
        Inquirer newInquirer = new Inquirer("Jane", "Smith", "456-999-0000", "informatipon");
        inquirerLog.setInquirer(newInquirer);
        assertEquals(newInquirer, inquirerLog.getInquirer());
    }

    @Test
    public void testGetCallDate() {
        assertEquals(callDate, inquirerLog.getCallDate());
    }

    @Test
    public void testSetCallDate() {
        LocalDate newCallDate = LocalDate.of(2022, 2, 1);
        inquirerLog.setCallDate(newCallDate);
        assertEquals(newCallDate, inquirerLog.getCallDate());
    }
}