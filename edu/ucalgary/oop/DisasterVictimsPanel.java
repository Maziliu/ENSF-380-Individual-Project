package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * The DisasterVictimsPanel class represents a panel that displays information
 * about disaster victims.
 * It allows users to search for victims, view their information, and add new
 * victims.
 * It also allows users to view and add medical records, family connections, and
 * personal belongings for each victim.
 * The panel is used in both the LocalGui and CentralGui classes.
 */
public class DisasterVictimsPanel extends JPanel {
    private final AppGui appGui;
    private final JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    private JTextField searchBar;
    private DefaultTableModel model;
    private JTable resultsTable;
    private JComboBox<String> locationsComboBox;

    /**
     * Constructs a DisasterVictimsPanel with the given AppGui. This uses
     * polymorphism to allow for both LocalGui and CentralGui objects to be passed
     * in and change the GUI.
     * 
     * @param appGui the AppGui object that the panel is being created for
     */
    public DisasterVictimsPanel(AppGui appGui) {
        super(new BorderLayout());
        this.appGui = appGui;
        if (appGui instanceof LocalGui) {
            createPanel((LocalGui) appGui);
        } else if (appGui instanceof CentralGui) {
            createPanel((CentralGui) appGui);
        }
        setupListeners();
    }

    /**
     * Creates the panel for a LocalGui object. This is the Local Worker version of
     * the DisasterVictimsPanel.
     * 
     * @param appGui the LocalGui object that the panel is being created for
     */
    private void createPanel(LocalGui appGui) {
        // Search bar setup
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(), leftPanelOuter = new JPanel();
        leftPanelOuter.setLayout(new BoxLayout(leftPanelOuter, BoxLayout.Y_AXIS));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));

        locationsComboBox = new JComboBox<>();
        DriverApplication.locations.forEach(location -> locationsComboBox.addItem(location.getName()));
        locationsComboBox.addActionListener(
                e -> updateDisasterVictimTable(searchBar, model));
        leftPanelOuter.add(locationsComboBox);

        JLabel searchLabel = new JLabel("Search for victims: ");
        leftPanel.add(searchLabel);

        searchBar = new JTextField(80);

        leftPanel.add(searchBar);
        leftPanelOuter.add(leftPanel);
        searchBarPanel.add(leftPanelOuter, BorderLayout.WEST);
        this.add(searchBarPanel, BorderLayout.NORTH);

        JPanel centerPanel = getCenterDisasterVictimPanel();
        this.add(centerPanel, BorderLayout.CENTER);

        String[] columnNames = new String[] { "Type", "Quantity" };
        ArrayList<Object[]> dataList = new ArrayList<>();

        JPanel suppliesPanel = createTablePanel(dataList, columnNames, new Dimension(450, 150), "Supplies");
        this.add(suppliesPanel, BorderLayout.SOUTH);

        locationsComboBox.addActionListener(e -> {
            dataList.clear();
            Location loc = null;
            for (Location l : DriverApplication.locations) {
                if (l.getName().equals(locationsComboBox.getSelectedItem())) {
                    loc = l;
                    break;
                }
            }

            if (loc != null) {
                for (Supply supply : loc.getSupplies()) {
                    dataList.add(new Object[] { supply.getType(), supply.getQuantity() });
                }
            }

            suppliesPanel.removeAll();
            suppliesPanel.add(createTablePanel(dataList, columnNames, new Dimension(450, 150), "Supplies"));
            suppliesPanel.revalidate();
            suppliesPanel.repaint();
        });
    }

    /**
     * Creates the panel for a CentralGui object. This is the Central Worker version
     * of the DisasterVictimsPanel.
     * 
     * @param appGui the CentralGui object that the panel is being created for
     */
    private void createPanel(CentralGui appGui) {
        // Search bar setup
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search for victims in all locations: ");
        searchBar = new JTextField();
        searchBarPanel.add(searchLabel, BorderLayout.WEST);
        searchBarPanel.add(searchBar, BorderLayout.CENTER);
        this.add(searchBarPanel, BorderLayout.NORTH);

        // Table setup
        JPanel centerPanel = getCenterDisasterVictimPanel();
        this.add(centerPanel, BorderLayout.CENTER);

        updateDisasterVictimTable(searchBar, model);
    }

    /**
     * Returns the panel that displays information about disaster victims. This is
     * the popup panel when a victim is clicked on.
     * 
     * @return the panel that displays information about disaster victims
     */
    private JPanel getCenterDisasterVictimPanel() {
        String[] columnNames = { "Id", "FirstName", "LastName", "Location" };
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultsTable = new JTable(model);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(resultsScrollPane, BorderLayout.CENTER);

        if (appGui instanceof LocalGui) {
            JButton addVictimButton = new JButton("Add Victim");
            addVictimButton.addActionListener(e -> {
                ArrayList<String> availableGenders = new ArrayList<String>();
                try {
                    File genderFile = new File(new File(new File("edu", "ucalgary"), "oop"), "GenderOptions.txt");
                    Scanner sr = new Scanner(genderFile);
                    while (sr.hasNextLine()) {
                        availableGenders.add(sr.nextLine());
                    }
                    availableGenders.add("");
                    sr.close();
                } catch (Exception ex) {
                    throw new IllegalArgumentException("File not found");
                }

                JComboBox<String> genderComboBox = new JComboBox<>(availableGenders.toArray(new String[0]));
                ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
                fields.add(new Pair<Object, Object>("First Name:", new JTextField(20)));
                fields.add(new Pair<Object, Object>("Last Name:", new JTextField(20)));
                fields.add(new Pair<Object, Object>("Date of Birth:", new JTextField(20)));
                fields.add(new Pair<Object, Object>("Gender", genderComboBox));
                fields.add(new Pair<Object, Object>("Comments:", new JTextField(20)));
                ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
                DietaryRestrictions[] restrictions = DietaryRestrictions.values();
                for (DietaryRestrictions restriction : restrictions) {
                    JCheckBox checkBox = new JCheckBox(restriction.toString());
                    fields.add(new Pair<Object, Object>("", checkBox));
                    checkBoxes.add(checkBox);
                }

                if (locationsComboBox == null) {
                    JComboBox<String> locations = new JComboBox<>();
                    DriverApplication.locations.forEach(location -> locations.addItem(location.getName()));
                    fields.add(new Pair<Object, Object>("Location:", locations));
                } else {
                    fields.add(new Pair<Object, Object>("Location:",
                            new JLabel(locationsComboBox.getSelectedItem().toString())));
                }

                Map<String, Object> components = generatePopupFrameComponents("Add Victim", fields);
                JFrame popupFrame = (JFrame) components.get("frame");
                JButton saveButton = (JButton) components.get("saveButton");
                JButton cancelButton = (JButton) components.get("cancelButton");

                popupFrame.pack();
                popupFrame.setVisible(true);

                saveButton.addActionListener(e2 -> {
                    String firstName = ((JTextField) fields.get(0).second).getText();
                    String lastName = ((JTextField) fields.get(1).second).getText();
                    String dateOfBirth = ((JTextField) fields.get(2).second).getText();
                    String comments = ((JTextField) fields.get(4).second).getText();
                    EnumSet<DietaryRestrictions> dietaryRestrictions = EnumSet.noneOf(DietaryRestrictions.class);

                    for (int i = 0; i < checkBoxes.size(); i++) {
                        if (checkBoxes.get(i).isSelected()) {
                            dietaryRestrictions.add(DietaryRestrictions.values()[i]);
                        }
                    }

                    String location = locationsComboBox.getSelectedItem().toString();
                    String gender = (String) genderComboBox.getSelectedItem();

                    Location loc = null;
                    for (Location l : DriverApplication.locations) {
                        if (l.getName().equals(location)) {
                            loc = l;
                            break;
                        }
                    }

                    if (loc != null) {
                        DisasterVictim victim = new DisasterVictim(firstName, lastName, LocalDate.now(),
                                LocalDate.parse(dateOfBirth), gender);

                        victim.setDietaryRestrictions(dietaryRestrictions);
                        victim.setComments(comments);
                        loc.addOccupant(victim);
                        DriverApplication.disasterVictims.add(victim);
                        updateDisasterVictimTable(searchBar, model);
                    }

                    popupFrame.dispose();
                });

                cancelButton.addActionListener(e2 -> popupFrame.dispose());
            });

            centerPanel.add(addVictimButton, BorderLayout.SOUTH);
        }

        return centerPanel;
    }

    /**
     * Sets up the listeners for the panel. This includes the search bar and the
     * table. When the search bar is used, the table is updated with the search
     * results. When a victim is clicked on, a popup is displayed with the victim's
     * information.
     */
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

    /**
     * Updates the disaster victim table with the search results.
     * 
     * @param searchBar the search bar that the user is typing in. This is a
     *                  JTextField object
     * @param model     the table model that is being updated. This is a
     *                  DefaultTableModel object
     */
    private void updateDisasterVictimTable(JTextField searchBar, DefaultTableModel model) {
        String searchText = searchBar.getText();
        ArrayList<Pair<DisasterVictim, Location>> results = searchDisasterVictim(searchText);
        model.setRowCount(0);
        for (Pair<DisasterVictim, Location> result : results) {
            model.addRow(new Object[] { result.first.getAssignedSocialID(), result.first.getFirstName(),
                    result.first.getLastName(), result.second.getName() });
        }
    }

    /**
     * Searches for disaster victims based on the given search text. The search text
     * is used to search for victims based on their first name, last name, location,
     * and social ID. The search results are sorted based on the number of
     * matches(Score). This allows for the most relevant results to be displayed
     * first.
     * 
     * @param searchText the text that the user is searching for
     * @return an ArrayList of Pair objects that contain the disaster victim and
     *         their location
     */
    private ArrayList<Pair<DisasterVictim, Location>> searchDisasterVictim(String searchText) {
        Map<Pair<DisasterVictim, Location>, Integer> scores = new HashMap<>();
        ArrayList<String> searchWords = new ArrayList<>(Arrays.asList(searchText.toLowerCase().split("\\s+")));

        if (locationsComboBox != null) {
            searchWords.add(locationsComboBox.getSelectedItem().toString().toLowerCase());
        }

        for (Location location : DriverApplication.locations) {
            for (DisasterVictim victim : location.getOccupants()) {
                if (anyMatches(victim, location, searchWords)) {
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

    /**
     * Checks if the victim matches any of the search criteria.
     * 
     * @param victim      The victim to check for matches with
     * @param location    The location of the victim
     * @param searchWords The words in the search bar to search for
     * @return true if the victim matches any of the search criteria, false
     *         otherwise (if no matches are found)
     */
    private boolean anyMatches(DisasterVictim victim, Location location, ArrayList<String> searchWords) {
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

    /**
     * Calculates the score of the victim based on the search criteria.
     * 
     * @param victim      The victim to calculate the score for. The information of
     *                    the victim is used to calculate the score
     * @param location    The location of the victim
     * @param searchWords The words in the search bar to search for
     * @return The score of the search criteria based on the victim's information
     */
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

    /**
     * Generates the components for the popup frame. This includes the title,
     * fields, and buttons.
     * 
     * @param title  The title of the popup frame
     * @param fields The fields to display in the popup frame. Each field is a Pair
     *               object with the first element being the label and the second
     *               element being the component. These fields are displayed inline
     *               vertically.
     * @return A map of the components of the popup frame
     */
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

    /**
     * Creates a panel with a table that displays the given data.
     * 
     * @param dataList    The data to display in the table. Each element in the list
     *                    is an array of objects that represent a row in the table
     * @param columnNames The names of the columns in the table
     * @param dimension   The dimension of the table
     * @param title       The title of the table
     * @return A JPanel with a table that displays the given data
     */
    private JPanel createTablePanel(ArrayList<Object[]> dataList, String[] columnNames, Dimension dimension,
            String title) {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        Object[][] data = dataList.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(dimension);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    /**
     * Returns the JTable object from the given container. This is used to get the
     * table from a JScrollPane.
     * 
     * @param container The container to get the table from
     * @return The JTable object from the container
     */
    private static JTable getTableFromScrollPane(Container container) {
        Component comp = container.getLayout() instanceof BorderLayout
                ? ((BorderLayout) container.getLayout()).getLayoutComponent(BorderLayout.CENTER)
                : null;

        if (comp instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) comp;
            Component view = scrollPane.getViewport().getView();
            if (view instanceof JTable) {
                return (JTable) view;
            }
        }
        return null;
    }

    /**
     * Displays a popup with information about the victim. This includes the
     * victim's personal information, medical records, family connections, and
     * personal belongings.
     * 
     * @param id        The ID of the victim
     * @param firstName The first name of the victim
     * @param lastName  The last name of the victim
     * @param location  The location of the victim
     * @param searchBar The search bar that the user is typing in. This is a
     *                  JTextField object
     * @param model     The table model that is being updated. This is a
     *                  DefaultTableModel object
     */
    private void displayVictimInfoPopup(String id, String firstName, String lastName, String location,
            JTextField searchBar, DefaultTableModel model) {

        DisasterVictim victim = null;
        for (DisasterVictim v : DriverApplication.disasterVictims) {
            if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                victim = v;
                break;
            }
        }

        ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
        fields.add(new Pair<Object, Object>("ID:", new JLabel(id)));
        fields.add(new Pair<Object, Object>("First Name:", new JTextField(firstName, 20)));
        fields.add(new Pair<Object, Object>("Last Name:", new JTextField(lastName, 20)));
        fields.add(new Pair<Object, Object>("Gender:", new JLabel(victim.getGender())));
        fields.add(new Pair<Object, Object>("Dietary Restrictions:",
                new JLabel((new ArrayList<>(victim.getDietaryRestrictions())).toString())));
        fields.add(new Pair<Object, Object>("Comments:", new JLabel(victim.getComments())));
        fields.add(new Pair<Object, Object>("Entry Date:", new JLabel(victim.getEntryDate().toString())));
        fields.add(new Pair<Object, Object>("Birth Date:", new JLabel(victim.getDateOfBirth().toString())));
        JComboBox<String> locationsComboBox = new JComboBox<String>();
        for (Location l : DriverApplication.locations) {
            locationsComboBox.addItem(l.getName());
        }
        locationsComboBox.setSelectedItem(location);
        fields.add(new Pair<Object, Object>("Location:", locationsComboBox));

        Map<String, Object> components = generatePopupFrameComponents("Victim Information", fields);
        JFrame popupFrame = (JFrame) components.get("frame");
        JPanel detailsPanel = (JPanel) components.get("detailsPanel");

        ArrayList<Object[]> dataList = new ArrayList<>();
        String[] columnNames = { "Treatment Date", "Location", "Details" };

        for (MedicalRecord record : victim.getMedicalRecords()) {
            dataList.add(
                    new Object[] { record.getDateOfTreatment(), record.getLocation().getName(),
                            record.getTreatmentDetails() });
        }

        JPanel medicalRecordsPanel = createTablePanel(dataList, columnNames, new Dimension(450, 150),
                "Medical Records");
        detailsPanel.add(medicalRecordsPanel);

        JTable medicalRecordTable = getTableFromScrollPane(medicalRecordsPanel);

        medicalRecordTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = medicalRecordTable.getSelectedRow();
                if (row >= 0) {
                    // Remove medical record
                    String date = medicalRecordTable.getValueAt(row, 0).toString();
                    String location = medicalRecordTable.getValueAt(row, 1).toString();
                    String details = medicalRecordTable.getValueAt(row, 2).toString();

                    DisasterVictim victim = null;
                    for (DisasterVictim v : DriverApplication.disasterVictims) {
                        if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                            victim = v;
                            break;
                        }
                    }

                    for (MedicalRecord record : victim.getMedicalRecords()) {
                        if (record.getDateOfTreatment().toString().equals(date)
                                && record.getLocation().getName().equals(location)
                                && record.getTreatmentDetails().equals(details)) {
                            victim.removeMedicalRecord(record);
                            break;
                        }
                    }

                    ArrayList<Object[]> dataList = new ArrayList<>();
                    for (MedicalRecord record : victim.getMedicalRecords()) {
                        dataList.add(new Object[] { record.getDateOfTreatment(), record.getLocation().getName(),
                                record.getTreatmentDetails() });
                    }

                    DefaultTableModel model = (DefaultTableModel) medicalRecordTable.getModel();
                    model.setRowCount(0);
                    for (Object[] data : dataList) {
                        model.addRow(data);
                    }

                    medicalRecordTable.revalidate();
                    medicalRecordTable.repaint();

                }
            }
        });

        JPanel centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton addMedicalRecordButton = new JButton("Add Medical Record");

        addMedicalRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
                ArrayList<Location> locations = new ArrayList<>();

                Location loc = null;
                for (Location l : DriverApplication.locations) {
                    for (DisasterVictim v : l.getOccupants()) {
                        if (v.getAssignedSocialID() == Integer.parseInt(id)) {
                            loc = l;
                            break;
                        }
                    }
                }

                locations.add(loc);

                JComboBox<String> locationsComboBox = new JComboBox<>();
                for (Location l : locations) {
                    locationsComboBox.addItem(l.getName());
                }

                fields.add(new Pair<Object, Object>("Location:", new JLabel(
                        locationsComboBox.getSelectedItem().toString())));
                fields.add(new Pair<Object, Object>("Treatment Details:", new JTextField(20)));
                fields.add(new Pair<Object, Object>("Date of Treatment:", new JTextField(20)));

                Map<String, Object> components = generatePopupFrameComponents("Add Medical Record", fields);
                JFrame popupFrame = (JFrame) components.get("frame");
                JButton saveButton = (JButton) components.get("saveButton");
                JButton cancelButton = (JButton) components.get("cancelButton");

                popupFrame.pack();
                popupFrame.setVisible(true);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String location = locationsComboBox.getSelectedItem().toString();
                        String treatmentDetails = ((JTextField) fields.get(1).second).getText();
                        String dateOfTreatment = ((JTextField) fields.get(2).second).getText();

                        Location loc = null;
                        for (Location l : DriverApplication.locations) {
                            if (l.getName().equals(location)) {
                                loc = l;
                                break;
                            }
                        }

                        DisasterVictim currentPerson = null;
                        for (DisasterVictim v : loc.getOccupants()) {
                            if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                                currentPerson = v;
                                break;
                            }
                        }

                        if (loc != null && currentPerson != null) {
                            MedicalRecord record = new MedicalRecord(loc, treatmentDetails, dateOfTreatment);
                            currentPerson.addMedicalRecord(record);
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
            }
        });

        centeringPanel.add(addMedicalRecordButton);
        detailsPanel.add(centeringPanel, BorderLayout.SOUTH);

        dataList = new ArrayList<>();
        columnNames = new String[] { "ID", "Name", "Relation" };

        for (FamilyRelation connection : victim.getFamilyConnections()) {
            DisasterVictim other = connection.getPersonOne().equals(victim) ? connection.getPersonTwo()
                    : connection.getPersonOne();
            dataList.add(
                    new Object[] { other.getAssignedSocialID(), other.getFirstName() + " " + other.getLastName(),
                            connection.getRelation() });
        }

        JPanel familyConnectionsPanel = createTablePanel(dataList, columnNames, new Dimension(450, 150),
                "Family Connections");
        detailsPanel.add(familyConnectionsPanel);

        JTable table = getTableFromScrollPane(familyConnectionsPanel);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                // Rmove family connection
                if (row >= 0) {
                    String otherId = table.getValueAt(row, 0).toString();
                    String relation = table.getValueAt(row, 2).toString();
                    DisasterVictim other = null;
                    for (DisasterVictim v : DriverApplication.disasterVictims) {
                        if (Integer.toString(v.getAssignedSocialID()).equals(otherId)) {
                            other = v;
                            break;
                        }
                    }

                    DisasterVictim currentPerson = null;
                    for (DisasterVictim v : DriverApplication.disasterVictims) {
                        if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                            currentPerson = v;
                            break;
                        }
                    }

                    currentPerson.removeFamilyConnection(other, relation);

                    ArrayList<Object[]> dataList = new ArrayList<>();

                    for (FamilyRelation connection : currentPerson.getFamilyConnections()) {
                        DisasterVictim otherPerson = connection.getPersonOne().equals(currentPerson)
                                ? connection.getPersonTwo()
                                : connection.getPersonOne();
                        dataList.add(new Object[] { otherPerson.getAssignedSocialID(),
                                otherPerson.getFirstName() + " " + otherPerson.getLastName(),
                                connection.getRelation() });
                    }

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (Object[] data : dataList) {
                        model.addRow(data);
                    }

                    table.revalidate();
                    table.repaint();
                }

            }
        });

        JButton addFamilyConnectionButton = new JButton("Add Family Connection");

        addFamilyConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
                ArrayList<DisasterVictim> occupants = new ArrayList<>();

                if (appGui instanceof CentralGui) {
                    DriverApplication.locations.forEach(location -> occupants.addAll(location.getOccupants()));
                } else {
                    Location loc = null;
                    for (Location l : DriverApplication.locations) {
                        for (DisasterVictim v : l.getOccupants()) {
                            if (v.getAssignedSocialID() == Integer.parseInt(id)) {
                                loc = l;
                                break;
                            }
                        }
                    }

                    occupants.addAll(loc.getOccupants());
                }

                JComboBox<String> occupantsComboBox = new JComboBox<>();
                for (DisasterVictim v : occupants) {
                    occupantsComboBox
                            .addItem(v.getAssignedSocialID() + " " + v.getFirstName() + " " + v.getLastName());
                }

                fields.add(new Pair<Object, Object>("Person:", occupantsComboBox));
                fields.add(new Pair<Object, Object>("Relation:", new JTextField(20)));

                Map<String, Object> components = generatePopupFrameComponents("Add Family Connection", fields);
                JFrame popupFrame = (JFrame) components.get("frame");
                JButton saveButton = (JButton) components.get("saveButton");
                JButton cancelButton = (JButton) components.get("cancelButton");

                popupFrame.pack();
                popupFrame.setVisible(true);

                saveButton.addActionListener(new ActionListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String person = ((JComboBox<String>) fields.get(0).second).getSelectedItem().toString();
                        String relation = ((JTextField) fields.get(1).second).getText();
                        String otherId = person.split(" ")[0];

                        DisasterVictim currentPerson = null;
                        for (DisasterVictim v : DriverApplication.disasterVictims) {
                            if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                                currentPerson = v;
                                break;
                            }
                        }

                        DisasterVictim other = null;
                        for (DisasterVictim v : DriverApplication.disasterVictims) {
                            if (Integer.toString(v.getAssignedSocialID()).equals(otherId)) {
                                other = v;
                                break;
                            }
                        }

                        if (other != null) {
                            currentPerson.addFamilyConnection(other, relation);
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
            }
        });

        centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeringPanel.add(addFamilyConnectionButton);
        detailsPanel.add(centeringPanel, BorderLayout.SOUTH);
        if (appGui instanceof LocalGui) {

            dataList = new ArrayList<>();
            columnNames = new String[] { "Type", "Quantity" };

            for (Supply supply : victim.getPersonalBelongings()) {
                dataList.add(new Object[] { supply.getType(), supply.getQuantity() });
            }

            JPanel personalBelongingsPanel = createTablePanel(dataList, columnNames, new Dimension(450, 150),
                    "Personal Belongings");
            detailsPanel.add(personalBelongingsPanel);

            JButton addPersonalBelongingButton = new JButton("Add Personal Belonging");

            addPersonalBelongingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
                    ArrayList<Supply> supplies = new ArrayList<>();

                    if (appGui instanceof CentralGui) {
                        DriverApplication.locations.forEach(location -> supplies.addAll(location.getSupplies()));
                    } else {
                        Location loc = null;
                        for (Location l : DriverApplication.locations) {
                            for (DisasterVictim v : l.getOccupants()) {
                                if (v.getAssignedSocialID() == Integer.parseInt(id)) {
                                    loc = l;
                                    break;
                                }
                            }
                        }

                        supplies.addAll(loc.getSupplies());
                    }

                    JComboBox<String> suppliesComboBox = new JComboBox<>();
                    for (Supply supply : supplies) {
                        suppliesComboBox.addItem(supply.toString());
                    }
                    suppliesComboBox.setSelectedIndex(0);

                    fields.add(new Pair<Object, Object>("Supply:", suppliesComboBox));
                    fields.add(new Pair<Object, Object>("Quantity:", quantitySpinner));

                    Map<String, Object> components = generatePopupFrameComponents("Add Personal Belonging", fields);
                    JFrame popupFrame = (JFrame) components.get("frame");
                    JButton saveButton = (JButton) components.get("saveButton");
                    JButton cancelButton = (JButton) components.get("cancelButton");

                    popupFrame.pack();
                    popupFrame.setVisible(true);

                    saveButton.addActionListener(new ActionListener() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String supply = ((JComboBox<String>) fields.get(0).second).getSelectedItem().toString();
                            int quantity = (int) quantitySpinner.getValue();

                            DisasterVictim currentPerson = null;
                            for (DisasterVictim v : DriverApplication.disasterVictims) {
                                if (Integer.toString(v.getAssignedSocialID()).equals(id)) {
                                    currentPerson = v;
                                    break;
                                }
                            }

                            Location loc = null;
                            for (Location l : DriverApplication.locations) {
                                for (DisasterVictim v : l.getOccupants()) {
                                    if (v.getAssignedSocialID() == Integer.parseInt(id)) {
                                        loc = l;
                                        break;
                                    }
                                }
                            }

                            Supply currentSupply = null;
                            for (Supply s : loc.getSupplies()) {
                                if (s.toString().equals(supply)) {
                                    currentSupply = s;
                                    break;
                                }
                            }

                            loc.assignSupply(currentSupply, currentPerson, quantity);
                            popupFrame.dispose();
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            popupFrame.dispose();
                        }
                    });
                }
            });

            centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centeringPanel.add(addPersonalBelongingButton);
            detailsPanel.add(centeringPanel, BorderLayout.SOUTH);
        }

        // Add button to edit victim information
        JButton saveButton = (JButton) components.get("saveButton");
        JButton cancelButton = (JButton) components.get("cancelButton");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedFirstName = ((JTextField) fields.get(1).second).getText();
                String updatedLastName = ((JTextField) fields.get(2).second).getText();
                String updatedLocation = (String) locationsComboBox.getSelectedItem();

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
