package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CentralGuiTest {
    private CentralGui centralGui;

    @Before
    public void setUp() {
        centralGui = new CentralGui();
        DriverApplication.inquirers.add(new Inquirer("John Doe", "", "999-000-9999", "info", 0));
        DriverApplication.inquirers.add(new Inquirer("Jane Doe", "", "999-000-9999", "info", 1));
        DriverApplication.inquirers.add(new Inquirer("John Smith", "", "999-000-9999", "info", 2));
    }

    @After
    public void tearDown() {
        centralGui = null;
        DriverApplication.inquirers.clear();
    }

    @Test
    public void testFindInquirerById() {
        Inquirer inquirer = DriverApplication.inquirers.get(2);
        assertEquals(inquirer.getInquirerID(), centralGui.findInquirerByID("2").getInquirerID());
    }

    @Test
    public void testFindInquirerByIdNotFound() {
        assertEquals(null, centralGui.findInquirerByID("5"));
    }

    @Test
    public void testSearchInquirers() {
        ArrayList<Inquirer> inquirers = centralGui.searchInquirer("Jo");
        inquirers.sort((i1, i2) -> (i1.getInquirerID() + "").compareTo(i2.getInquirerID() + ""));

        assertEquals(2, inquirers.size());
        assertEquals("John Doe", inquirers.get(0).getFirstName());
        assertEquals("John Smith", inquirers.get(1).getFirstName());
    }
}