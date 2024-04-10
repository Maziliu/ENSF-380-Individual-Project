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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DisasterVictimsPanelTest {
    private DisasterVictimsPanel panel;

    @Before
    public void setUp() {
        panel = new DisasterVictimsPanel(new LocalGui());
    }

    @After
    public void tearDown() {
        panel = null;
    }

    @Test
    public void testGetCenterDisasterVictimPanel() {
        JPanel centerPanel = panel.getCenterDisasterVictimPanel();
        assertTrue(centerPanel.getComponent(0) instanceof JScrollPane);
        JScrollPane scrollPane = (JScrollPane) centerPanel.getComponent(0);
        assertTrue(scrollPane.getViewport().getView() instanceof JTable);
        JTable table = (JTable) scrollPane.getViewport().getView();
        assertTrue(table.getModel() instanceof DefaultTableModel);
    }

    @Test
    public void testGeneratePopupFrameComponents() {
        ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
        fields.add(new Pair<>("First Name:", new JTextField(20)));
        fields.add(new Pair<>("Last Name:", new JTextField(20)));
        fields.add(new Pair<>("Date of Birth or Age:", new JTextField(20)));

        Map<String, Object> components = panel.generatePopupFrameComponents("Add Victim", fields);
        assertTrue(components.containsKey("frame"));
        assertTrue(components.containsKey("saveButton"));
        assertTrue(components.containsKey("cancelButton"));

        JFrame frame = (JFrame) components.get("frame");
        JButton saveButton = (JButton) components.get("saveButton");
        JButton cancelButton = (JButton) components.get("cancelButton");

        assertEquals("Add Victim", frame.getTitle());
        assertEquals("Save", saveButton.getText());
        assertEquals("Cancel", cancelButton.getText());
    }

    @Test
    public void testCalculateScore() {

    }
}