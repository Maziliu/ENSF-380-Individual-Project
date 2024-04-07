package edu.ucalgary.oop;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class CentralGui extends AppGui {
    private JPanel centralPanel;

    public CentralGui() {
        createMainPanel();
        createMenuBar();
    }

    private void createMainPanel() {
        centralPanel = new JPanel(new CardLayout());
        centralPanel.add(getDisasterVictimPanel(), "Disaster Victims");
        centralPanel.add(getInquirersPanel(), "Inquirers");
        centralPanel.add(new JPanel(), "Generate Data");
        centralPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // Add an action to exit
        fileMenu.add(exitItem);

        // Options menu with dynamically added items
        JMenu optionsMenu = new JMenu("Options");
        String[] options = { "Disaster Victims", "Inquirers", "Generate Data" };

        for (String option : options) {
            JMenuItem menuItem = new JMenuItem(option);
            menuItem.addActionListener(e -> switchPanel(option));
            optionsMenu.add(menuItem);
        }

        JMenu switchMenu = new JMenu("Switch GUI");
        JMenuItem toLocalGuiItem = new JMenuItem("Switch to Local GUI");
        toLocalGuiItem.addActionListener(e -> switchToGui(new LocalGui()));
        switchMenu.add(toLocalGuiItem);
        menuBar.add(switchMenu);

        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(switchMenu);

        return menuBar;
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) (centralPanel.getLayout());
        cl.show(centralPanel, panelName);
    }

    @Override
    public JPanel getMainPanel() {
        return centralPanel;
    }

    private void updateDisasterVictimTable(JTextField searchBar, DefaultTableModel model) {
        String searchText = searchBar.getText();
        ArrayList<Pair<DisasterVictim, Location>> results = searchDisasterVictim(searchText);
        model.setRowCount(0);

        for (Pair<DisasterVictim, Location> result : results) {
            model.addRow(new Object[] { result.first.getAssignedSocialID(), result.first.getFirstName(),
                    result.first.getLastName(), result.second.getName() });
        }
    }

    private void updateinquirerTable(JTextField searchBar, DefaultTableModel model) {
        String searchText = searchBar.getText();
        ArrayList<Inquirer> results = searchInquirer(searchText);
        model.setRowCount(0);

        for (Inquirer result : results) {
            model.addRow(
                    new Object[] { result.getServicesPhone(), result.getFirstName(), result.getLastName() });
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

        String[] columnNames = { "Id", "FirstName", "LastName", "Location" };
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable resultsTable = new JTable(model);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
        searchBarPanel.add(resultsScrollPane, BorderLayout.SOUTH);

        updateDisasterVictimTable(searchBar, model);
        resultsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = resultsTable.getSelectedRow();
                if (row >= 0) {
                    String id = resultsTable.getValueAt(row, 0).toString();
                    String firstName = resultsTable.getValueAt(row, 1).toString();
                    String lastName = resultsTable.getValueAt(row, 2).toString();
                    String location = resultsTable.getValueAt(row, 3).toString();

                    JFrame popupFrame = new JFrame("Victim Information");
                    JPanel popupPanel = new JPanel(new GridLayout(5, 2));

                    String[] labels = { "First Name:", "Last Name:", "Location:" };
                    JTextField[] textFields = { new JTextField(firstName), new JTextField(lastName),
                            new JTextField(location) };

                    JButton saveButton = new JButton("Save");
                    JButton cancelButton = new JButton("Cancel");

                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String updatedFirstName = textFields[0].getText();
                            String updatedLastName = textFields[1].getText();
                            String updatedLocation = textFields[2].getText();

                            DisasterVictim victim = null;
                            for (Location loc : DriverApplication.locations) {
                                for (DisasterVictim v : loc.getOccupants()) {
                                    if (v.getAssignedSocialID() == Integer.parseInt(id)) {
                                        victim = v;
                                        break;
                                    }
                                }
                            }

                            if (victim != null) {
                                victim.setFirstName(updatedFirstName);
                                victim.setLastName(updatedLastName);

                                for (Location loc : DriverApplication.locations) {
                                    loc.removeOccupant(victim);
                                }

                                for (Location loc : DriverApplication.locations) {
                                    if (loc.getName().equals(updatedLocation)) {
                                        loc.addOccupant(victim);
                                    }
                                }
                            }

                            updateDisasterVictimTable(searchBar, model);
                            popupFrame.dispose();
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            popupFrame.dispose();
                        }
                    });

                    popupPanel.add(new JLabel("ID: "));
                    popupPanel.add(new JLabel(id));

                    for (int i = 0; i < labels.length; i++) {
                        popupPanel.add(new JLabel(labels[i]));
                        popupPanel.add(textFields[i]);
                    }

                    popupPanel.add(saveButton);
                    popupPanel.add(cancelButton);

                    popupFrame.add(popupPanel);
                    popupFrame.pack();
                    popupFrame.setVisible(true);
                }
            }
        });

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateDisasterVictimTable(searchBar, model);
            }
        });

        return container;
    }

    private ArrayList<Pair<DisasterVictim, Location>> searchDisasterVictim(String searchText) {
        ArrayList<Pair<DisasterVictim, Location>> results = new ArrayList<>();

        for (Location location : DriverApplication.locations) {
            for (DisasterVictim victim : location.getOccupants()) {
                if (victim.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                        || victim.getLastName().toLowerCase().contains(searchText.toLowerCase())
                        || location.getName().toLowerCase().contains(searchText.toLowerCase())
                        || Integer.toString(victim.getAssignedSocialID()).equals(searchText)) {
                    results.add(new Pair<DisasterVictim, Location>(victim, location));
                }
            }
        }

        return results;
    }

    private JPanel getInquirersPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search for inquirers: ");
        JTextField searchBar = new JTextField();
        searchBarPanel.add(searchLabel, BorderLayout.WEST);
        searchBarPanel.add(searchBar, BorderLayout.CENTER);
        container.add(searchBarPanel, BorderLayout.NORTH);

        String[] columnNames = { "First Name", "Last Name", "Phone Number" };
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable resultsTable = new JTable(model);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
        searchBarPanel.add(resultsScrollPane, BorderLayout.SOUTH);

        updateinquirerTable(searchBar, model);
        resultsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = resultsTable.getSelectedRow();
                if (row >= 0) {
                    String phoneNumber = resultsTable.getValueAt(row, 2).toString();
                    String firstName = resultsTable.getValueAt(row, 0).toString();
                    String lastName = resultsTable.getValueAt(row, 1).toString();

                    JFrame popupFrame = new JFrame("Inquirer Information");
                    JPanel popupPanel = new JPanel(new GridLayout(4, 2));

                    String[] labels = { "First Name:", "Last Name:" };
                    JTextField[] textFields = { new JTextField(firstName), new JTextField(lastName) };

                    JButton saveButton = new JButton("Save");
                    JButton cancelButton = new JButton("Cancel");

                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String updatedFirstName = textFields[0].getText();
                            String updatedLastName = textFields[1].getText();

                            Inquirer inquirer = null;
                            for (Inquirer i : DriverApplication.inquirers) {
                                if (i.getServicesPhone().equals(phoneNumber)) {
                                    inquirer = i;
                                    break;
                                }
                            }

                            if (inquirer != null) {
                                inquirer.setFirstName(updatedFirstName);
                                inquirer.setLastName(updatedLastName);
                            }

                            popupFrame.dispose();
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            popupFrame.dispose();
                        }
                    });

                    for (int i = 0; i < labels.length; i++) {
                        popupPanel.add(new JLabel(labels[i]));
                        popupPanel.add(textFields[i]);
                    }

                    popupPanel.add(saveButton);
                    popupPanel.add(cancelButton);

                    popupFrame.add(popupPanel);
                    popupFrame.pack();
                    popupFrame.setVisible(true);
                }
            }
        });

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateinquirerTable(searchBar, model);
            }
        });

        return container;
    }

    private ArrayList<Inquirer> searchInquirer(String searchText) {
        ArrayList<Inquirer> results = new ArrayList<>();

        for (Inquirer inquirer : DriverApplication.inquirers) {
            if (inquirer.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                    || inquirer.getLastName().toLowerCase().contains(searchText.toLowerCase())
                    || inquirer.getServicesPhone().contains(searchText)) {
                results.add(inquirer);
            }
        }

        return results;
    }
}
