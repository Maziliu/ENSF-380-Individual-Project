package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DisasterVictimsPanel extends JPanel {
    private JTextField searchBar;
    private DefaultTableModel model;
    private JTable resultsTable;
    private JComboBox<String> locationsComboBox;

    public DisasterVictimsPanel(AppGui appGui) {
        super(new BorderLayout());
        if (appGui instanceof LocalGui) {
            initializeUi((LocalGui) appGui);
        } else if (appGui instanceof CentralGui) {
            initializeUI((CentralGui) appGui);
        }
        setupListeners();
    }

    private void initializeUi(LocalGui appGui) {
        // Search bar setup
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));

        JLabel searchLabel = new JLabel("Search for victims in: ");
        leftPanel.add(searchLabel);

        locationsComboBox = new JComboBox<>();
        DriverApplication.locations.forEach(location -> locationsComboBox.addItem(location.getName()));
        locationsComboBox.addActionListener(
                e -> updateDisasterVictimTable(searchBar, model));
        leftPanel.add(locationsComboBox);

        searchBar = new JTextField();
        searchBarPanel.add(leftPanel, BorderLayout.WEST);
        searchBarPanel.add(searchBar, BorderLayout.CENTER);
        this.add(searchBarPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = { "Id", "FirstName", "LastName", "Location" };
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(model);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
        this.add(resultsScrollPane, BorderLayout.CENTER);

    }

    private void initializeUI(CentralGui appGui) {
        // Search bar setup
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search for victims in all locations: ");
        searchBar = new JTextField();
        searchBarPanel.add(searchLabel, BorderLayout.WEST);
        searchBarPanel.add(searchBar, BorderLayout.CENTER);
        this.add(searchBarPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = { "Id", "FirstName", "LastName", "Location" };
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(model);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
        this.add(resultsScrollPane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        resultsTable.addMouseListener(new MouseAdapter() {
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
            public void keyReleased(KeyEvent e) {
                updateDisasterVictimTable(searchBar, model);
            }
        });
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

    private ArrayList<Pair<DisasterVictim, Location>> searchDisasterVictim(String searchText) {
        Map<Pair<DisasterVictim, Location>, Integer> scores = new HashMap<>();
        ArrayList<String> searchWords = new ArrayList<>(Arrays.asList(searchText.toLowerCase().split("\\s+")));

        if (locationsComboBox != null) {
            searchWords.add(locationsComboBox.getSelectedItem().toString().toLowerCase());
        }

        for (Location location : DriverApplication.locations) {
            for (DisasterVictim victim : location.getOccupants()) {
                if (matchesAllCriteria(victim, location, searchWords)) {
                    scores.put(new Pair<>(victim, location), calculateScore(victim, location, searchWords));
                }
            }
        }

        ArrayList<Map.Entry<Pair<DisasterVictim, Location>, Integer>> sortedEntries = new ArrayList<>(
                scores.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        ArrayList<Pair<DisasterVictim, Location>> results = new ArrayList<>();
        for (Map.Entry<Pair<DisasterVictim, Location>, Integer> entry : sortedEntries) {
            results.add(entry.getKey());
        }

        return results;
    }

    private boolean matchesAllCriteria(DisasterVictim victim, Location location, ArrayList<String> searchWords) {
        for (String word : searchWords) {
            if (!(victim.getFirstName().toLowerCase().contains(word) ||
                    victim.getLastName().toLowerCase().contains(word) ||
                    location.getName().toLowerCase().contains(word) ||
                    Integer.toString(victim.getAssignedSocialID()).equals(word))) {
                return false;
            }
        }
        return true;
    }

    private int calculateScore(DisasterVictim victim, Location location, ArrayList<String> searchWords) {
        int score = 0;
        for (String word : searchWords) {
            if (victim.getFirstName().toLowerCase().contains(word))
                score += 1;
            if (victim.getLastName().toLowerCase().contains(word))
                score += 1;
            if (location.getName().toLowerCase().contains(word))
                score += 1;
        }
        return score;
    }

    private Map<String, Object> generatePopupFrameComponents(String title, ArrayList<Pair<Object, Object>> fields) {
        JFrame popupFrame = new JFrame(title);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel popupPanel = new JPanel(new BorderLayout(10, 10));
        popupPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (Pair<Object, Object> field : fields) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rowPanel.add(new JLabel(field.first.toString()));
            rowPanel.add((Component) field.second);
            detailsPanel.add(rowPanel);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        popupPanel.add(detailsPanel, BorderLayout.CENTER);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);
        popupFrame.add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null);

        Map<String, Object> returnList = new HashMap<>();
        returnList.put("frame", popupFrame);
        returnList.put("popupPanel", popupPanel);
        returnList.put("detailsPanel", detailsPanel);
        returnList.put("buttonPanel", buttonPanel);
        returnList.put("saveButton", saveButton);
        returnList.put("cancelButton", cancelButton);

        return returnList;
    }

    private void displayVictimInfoPopup(String id, String firstName, String lastName, String location,
            JTextField searchBar, DefaultTableModel model) {
        ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
        fields.add(new Pair<Object, Object>("ID:", new JLabel(id)));
        fields.add(new Pair<Object, Object>("First Name:", new JTextField(firstName, 20)));
        fields.add(new Pair<Object, Object>("Last Name:", new JTextField(lastName, 20)));
        fields.add(new Pair<Object, Object>("Location:", new JComboBox<String>()));

        Map<String, Object> components = generatePopupFrameComponents("Victim Information", fields);
        JFrame popupFrame = (JFrame) components.get("frame");
        JPanel detailsPanel = (JPanel) components.get("detailsPanel");

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

        JComboBox<String> locationComboBox = (JComboBox<String>) fields.get(3).second;
        for (Location l : DriverApplication.locations) {
            locationComboBox.addItem(l.getName());
        }
        locationComboBox.setSelectedItem(location);

        JButton saveButton = (JButton) components.get("saveButton");
        JButton cancelButton = (JButton) components.get("cancelButton");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedFirstName = ((JTextField) fields.get(1).second).getText();
                String updatedLastName = ((JTextField) fields.get(2).second).getText();
                String updatedLocation = (String) locationComboBox.getSelectedItem();

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

        popupFrame.pack();
        popupFrame.setVisible(true);
    }
}
