package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class InquirerTest {
    private Inquirer inquirer;

    @Before
    public void setUp() {
        inquirer = new Inquirer("John", "Doe", "123-456-7890", "Additional info", 1);
    }

    @After
    public void tearDown() {
        inquirer = null;
    }

    @Test
    public void testGetInquirerID() {
        assertEquals(1, inquirer.getInquirerID());
    }

    @Test
    public void testGetServicesPhone() {
        assertEquals("123-456-7890", inquirer.getServicesPhone());
    }

    @Test
    public void testSetServicesPhone() {
        inquirer.setServicesPhone("987-654-3210");
        assertEquals("987-654-3210", inquirer.getServicesPhone());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetServicesPhoneInvalidFormat() {
        inquirer.setServicesPhone("1234567890");
    }

    @Test
    public void testGetInfo() {
        assertEquals("Additional info", inquirer.getInfo());
    }

    @Test
    public void testSetInfo() {
        inquirer.setInfo("New info");
        assertEquals("New info", inquirer.getInfo());
    }

    @Test
    public void testGetPreviousInteractions() {
        assertNotNull(inquirer.getPreviousInteractions());
        assertEquals(0, inquirer.getPreviousInteractions().size());
    }

    @Test
    public void testAddInteraction() {
        InquirerLog interaction = new InquirerLog(inquirer, "Interaction details", LocalDate.now());
        inquirer.addInteraction(interaction);
        assertEquals(1, inquirer.getNewInteractions().size());
        assertEquals(interaction, inquirer.getNewInteractions().get(0));
    }

    @Test
    public void testAddPreviousInteraction() {
        InquirerLog interaction = new InquirerLog(inquirer, "Interaction details", LocalDate.now());
        inquirer.addPreviousInteraction(interaction);
        assertEquals(1, inquirer.getPreviousInteractions().size());
        assertEquals(interaction, inquirer.getPreviousInteractions().get(0));
    }

    @Test
    public void testGenerateLog() {
        InquirerLog interaction1 = new InquirerLog(inquirer, "Interaction 1 details", LocalDate.parse("2022-01-01"));
        InquirerLog interaction2 = new InquirerLog(inquirer, "Interaction 2 details", LocalDate.parse("2022-01-02"));
        inquirer.addPreviousInteraction(interaction1);
        inquirer.addInteraction(interaction2);

        String expectedLog = "Inquirer: John Doe\n";
        expectedLog += "Services Phone: 123-456-7890\n";
        expectedLog += "Info: Additional info\n";
        expectedLog += "Previous Interactions: \n";
        expectedLog += "2022-01-01 Interaction 1 details\n";
        expectedLog += "2022-01-02 Interaction 2 details\n";

        assertEquals(expectedLog, inquirer.generateLog());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("John", inquirer.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", inquirer.getLastName());
    }

    @Test
    public void testSetFirstName() {
        inquirer.setFirstName("Jane");
        assertEquals("Jane", inquirer.getFirstName());
    }

    @Test
    public void testSetLastName() {
        inquirer.setLastName("Smith");
        assertEquals("Smith", inquirer.getLastName());
    }

    @Test
    public void testSetLastNameNull() {
        inquirer.setLastName(null);
        assertEquals("", inquirer.getLastName());
    }
}