package edu.ucalgary.oop;

import javax.swing.JPanel;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class CentralGui extends AppGui {
    private JPanel centralPanel;
    private JMenu menu;

    public CentralGui() {
        createCentralPanel();
        createMenuBar();
    }

    private void createCentralPanel() {
        centralPanel = new JPanel(new CardLayout());
        centralPanel.add(getDisasterVictimPanel(), "Disaster Victims");
        centralPanel.add(new JPanel(), "Inquirers");
        centralPanel.add(new JPanel(), "Generate Data");
    }

    private void createMenuBar() {
        menu = new JMenu("Central Worker Features");
        ArrayList<JMenuItem> buttons = new ArrayList<>(Arrays.asList(new JMenuItem("Disaster Victims"),
                new JMenuItem("Inquirers"), new JMenuItem("Generate Data")));

        for (JMenuItem button : buttons) {
            button.addActionListener(e -> ((CardLayout) centralPanel.getLayout()).show(centralPanel, button.getText()));
            menu.add(button);
        }
    }

    private JPanel getDisasterVictimPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search for victims in all locations: ");
        JTextField searchBar = new JTextField();
        searchBarPanel.add(searchLabel, BorderLayout.WEST);
        searchBarPanel.add(searchBar, BorderLayout.CENTER);
        container.add(searchBarPanel, BorderLayout.NORTH);

        JTable resultsTable = new JTable();
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
        searchBarPanel.add(resultsScrollPane, BorderLayout.SOUTH);

        return container;
    }

    @Override
    public JPanel getPanel() {
        return centralPanel;
    }

    @Override
    public JMenu getMenuOptions() {
        return menu;
    }
}
