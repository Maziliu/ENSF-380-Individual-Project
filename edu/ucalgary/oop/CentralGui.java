package edu.ucalgary.oop;

import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

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
                    new Object[] { result.getFirstName(), result.getLastName(), result.getServicesPhone() });
        }
    }

    private void displayVictimInfoPopup(String id, String firstName, String lastName, String location,
            JTextField searchBar, DefaultTableModel model) {
        JFrame popupFrame = new JFrame("Victim Information");
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel popupPanel = new JPanel(new BorderLayout(10, 10));
        popupPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(new JLabel("ID: "));
        idPanel.add(new JLabel(id));
        detailsPanel.add(idPanel);

        String[] labels = { "First Name:", "Last Name:" };
        JTextField[] textFields = { new JTextField(firstName), new JTextField(lastName) };
        for (int i = 0; i < labels.length; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rowPanel.add(new JLabel(labels[i]));
            textFields[i].setColumns(20);
            rowPanel.add(textFields[i]);
            detailsPanel.add(rowPanel);
        }

        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        locationPanel.add(new JLabel("Location: "));
        JComboBox<String> locationComboBox = new JComboBox<>();
        for (Location l : DriverApplication.locations) {
            locationComboBox.addItem(l.getName());
        }
        locationComboBox.setSelectedItem(location);
        locationPanel.add(locationComboBox);
        detailsPanel.add(locationPanel);

        DisasterVictim victim = null;
        for (DisasterVictim v : DriverApplication.disasterVictims) {
            if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                victim = v;
                break;
            }
        }

        if (victim != null) {
            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Optional, for padding

            String[] columnNames = { "Name", "Relation" };
            ArrayList<Object[]> dataList = new ArrayList<>();

            for (FamilyRelation connection : victim.getFamilyConnections()) {
                DisasterVictim other = connection.getPersonOne().equals(victim) ? connection.getPersonTwo()
                        : connection.getPersonOne();
                dataList.add(
                        new Object[] { other.getFirstName() + " " + other.getLastName(), connection.getRelation() });
            }

            Object[][] data = dataList.toArray(new Object[0][]);

            DefaultTableModel modelInner = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable resultsTable = new JTable(modelInner);
            JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
            resultsScrollPane.setPreferredSize(new Dimension(450, 150));

            infoPanel.add(resultsScrollPane, BorderLayout.CENTER);
            detailsPanel.add(infoPanel);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        popupPanel.add(detailsPanel, BorderLayout.CENTER);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedFirstName = textFields[0].getText();
                String updatedLastName = textFields[1].getText();
                String updatedLocation = locationComboBox.getSelectedItem().toString();

                DisasterVictim victim = null;
                for (Location l : DriverApplication.locations) {
                    for (DisasterVictim v : l.getOccupants()) {
                        if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                            victim = v;
                            break;
                        }
                    }
                }

                if (victim != null) {
                    victim.setFirstName(updatedFirstName);
                    victim.setLastName(updatedLastName);

                    for (Location l : DriverApplication.locations) {
                        if (l.getName().equals(location)) {
                            l.removeOccupant(victim);
                            break;
                        }
                    }

                    for (Location l : DriverApplication.locations) {
                        if (l.getName().equals(updatedLocation)) {
                            l.addOccupant(victim);
                            break;
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

        popupFrame.add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setVisible(true);
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
        container.add(resultsScrollPane, BorderLayout.CENTER);

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
                    displayVictimInfoPopup(id, firstName, lastName, location, searchBar, model);
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

    private void displayInquirerInfoPopup(String phoneNumber, String firstName, String lastName, JTextField searchBar,
            DefaultTableModel model) {
        JFrame popupFrame = new JFrame("Inquirer Information");
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel popupPanel = new JPanel(new BorderLayout(10, 10));
        popupPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phonePanel.add(new JLabel("Phone Number: "));
        phonePanel.add(new JLabel(phoneNumber));
        detailsPanel.add(phonePanel);

        String[] labels = { "First Name:", "Last Name:" };
        JTextField firstNameField = new JTextField(firstName, 20);
        JTextField lastNameField = new JTextField(lastName, 20);
        JTextField[] fields = { firstNameField, lastNameField };

        for (int i = 0; i < labels.length; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rowPanel.add(new JLabel(labels[i]));
            rowPanel.add(fields[i]);
            detailsPanel.add(rowPanel);
        }

        Inquirer inquirer = null;
        for (Inquirer i : DriverApplication.inquirers) {
            if (i.getServicesPhone().equals(phoneNumber)
                    && i.getFirstName().equals(firstName)) {
                inquirer = i;
                break;
            }
        }

        if (inquirer != null) {
            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Optional, for padding

            String[] columnNames = { "Inquirer", "Date", "Details" };
            ArrayList<Object[]> dataList = new ArrayList<>();

            for (InquirerLog log : inquirer.getPreviousInteractions()) {
                dataList.add(new Object[] { log.getInquirer().getFirstName() + " " + log.getInquirer().getLastName(),
                        log.getCallDate(),
                        log.getDetails() });
            }

            Object[][] data = dataList.toArray(new Object[0][]);

            DefaultTableModel modelInner = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable resultsTable = new JTable(modelInner);
            JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
            resultsScrollPane.setPreferredSize(new Dimension(450, 150));

            infoPanel.add(resultsScrollPane, BorderLayout.CENTER);
            detailsPanel.add(infoPanel);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedFirstName = fields[0].getText();
                String updatedLastName = fields[1].getText();

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

                updateinquirerTable(searchBar, model);
                popupFrame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupFrame.dispose();
            }
        });

        popupPanel.add(detailsPanel, BorderLayout.CENTER);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setVisible(true);
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
                    displayInquirerInfoPopup(phoneNumber, firstName, lastName, searchBar, model);
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
