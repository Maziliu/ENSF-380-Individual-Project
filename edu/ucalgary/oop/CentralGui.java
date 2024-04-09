package edu.ucalgary.oop;

import java.util.*;
import java.util.List;

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
        centralPanel.add(new DisasterVictimsPanel(this), "Disaster Victims");
        centralPanel.add(getInquirersPanel(), "Inquirers");
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
        String[] options = { "Disaster Victims", "Inquirers" };

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

    private void updateInquirerTable(JTextField searchBar, DefaultTableModel model) {
        String searchText = searchBar.getText();
        ArrayList<Inquirer> results = searchInquirer(searchText);
        model.setRowCount(0);

        for (Inquirer result : results) {
            model.addRow(
                    new Object[] { result.getFirstName(), result.getLastName(), result.getServicesPhone() });
        }
    }

    private void displayInquirerInfoPopup(String phoneNumber, String firstName, String lastName, JTextField searchBar,
            DefaultTableModel model) {

        ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
        fields.add(new Pair<Object, Object>("Phone Number:", new JLabel(phoneNumber)));
        fields.add(new Pair<Object, Object>("First Name:", new JTextField(firstName, 20)));
        fields.add(new Pair<Object, Object>("Last Name:", new JTextField(lastName, 20)));

        Map<String, Object> components = generatePopupFrameComponents("Inquirer Information", fields);
        JFrame popupFrame = (JFrame) components.get("frame");
        JPanel detailsPanel = (JPanel) components.get("detailsPanel");

        final Inquirer inquirer = findInquirerByPhoneNumber(phoneNumber);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnNames = { "Inquirer", "Date", "Details" };
        ArrayList<Object[]> dataList = new ArrayList<>();

        if (inquirer != null) {

            for (InquirerLog log : inquirer.getPreviousInteractions()) {
                dataList.add(new Object[] { log.getInquirer().getFirstName() + " " + log.getInquirer().getLastName(),
                        log.getCallDate(),
                        log.getDetails() });
            }

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

        JButton saveButton = (JButton) components.get("saveButton");
        JButton cancelButton = (JButton) components.get("cancelButton");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel addLogPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel addLogLabel = new JLabel("Add Log: ");
        JTextArea addLogTextArea = new JTextArea(5, 40);
        JButton addLogButton = new JButton("Add Log");

        addLogPanel.add(addLogLabel);
        addLogPanel.add(addLogTextArea);
        buttonPanel.add(addLogButton);

        detailsPanel.add(addLogPanel);
        detailsPanel.add(buttonPanel);

        popupFrame.pack();
        popupFrame.setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedFirstName = ((JTextField) fields.get(1).second).getText();
                String updatedLastName = ((JTextField) fields.get(2).second).getText();

                if (inquirer != null) {
                    inquirer.setFirstName(updatedFirstName);
                    inquirer.setLastName(updatedLastName);
                }

                updateInquirerTable(searchBar, model);

                inquirer.saveToDatabase();

                popupFrame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupFrame.dispose();
            }
        });

        addLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logDetails = addLogTextArea.getText();
                InquirerLog log = new InquirerLog(inquirer, logDetails, LocalDate.now());
                inquirer.addInteraction(log);
                dataList.add(new Object[] { inquirer.getFirstName() + " " + inquirer.getLastName(), LocalDate.now(),
                        logDetails });
                modelInner.addRow(new Object[] { inquirer.getFirstName() + " " + inquirer.getLastName(),
                        LocalDate.now(), logDetails });
                addLogTextArea.setText("");
            }
        });

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
        container.add(resultsScrollPane, BorderLayout.CENTER);

        updateInquirerTable(searchBar, model);

        JButton addInquirerButton = new JButton("Add Inquirer");
        container.add(addInquirerButton, BorderLayout.SOUTH);

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
                updateInquirerTable(searchBar, model);
            }
        });

        addInquirerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pair<Object, Object>> fields = new ArrayList<>();
                fields.add(new Pair<Object, Object>("First Name:", new JTextField(20)));
                fields.add(new Pair<Object, Object>("Last Name:", new JTextField(20)));
                fields.add(new Pair<Object, Object>("Phone Number:", new JTextField(20)));

                Map<String, Object> components = generatePopupFrameComponents("Add Inquirer", fields);
                JFrame popupFrame = (JFrame) components.get("frame");
                JButton saveButton = (JButton) components.get("saveButton");
                JButton cancelButton = (JButton) components.get("cancelButton");

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String firstName = ((JTextField) fields.get(0).second).getText();
                        String lastName = ((JTextField) fields.get(1).second).getText();
                        String phoneNumber = ((JTextField) fields.get(2).second).getText();

                        Inquirer inquirer = new Inquirer(firstName, lastName, phoneNumber, "info");
                        DriverApplication.inquirers.add(inquirer);
                        inquirer.saveToDatabase();
                        updateInquirerTable(searchBar, model);
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
        });

        return container;
    }

    private Inquirer findInquirerByPhoneNumber(String phoneNumber) {
        for (Inquirer i : DriverApplication.inquirers) {
            if (i.getServicesPhone().equals(phoneNumber)) {
                return i;
            }
        }
        return null;
    }

    private ArrayList<Inquirer> searchInquirer(String searchText) {
        Map<Inquirer, Integer> scores = new HashMap<>();
        String[] searchWords = searchText.toLowerCase().split("\\s+");

        for (Inquirer inquirer : DriverApplication.inquirers) {
            if (matchesAllCriteria(inquirer, searchWords)) {
                int score = calculateScore(inquirer, searchWords);
                scores.put(inquirer, score);
            }
        }

        List<Map.Entry<Inquirer, Integer>> sortedEntries = new ArrayList<>(scores.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        ArrayList<Inquirer> results = new ArrayList<>();
        for (Map.Entry<Inquirer, Integer> entry : sortedEntries) {
            results.add(entry.getKey());
        }

        return results;
    }

    private boolean matchesAllCriteria(Inquirer inquirer, String[] searchWords) {
        for (String word : searchWords) {
            if (!(inquirer.getFirstName().toLowerCase().contains(word) ||
                    inquirer.getLastName().toLowerCase().contains(word) ||
                    inquirer.getServicesPhone().toLowerCase().contains(word))) {
                return false;
            }
        }
        return true;
    }

    private int calculateScore(Inquirer inquirer, String[] searchWords) {
        int score = 0;
        for (String word : searchWords) {
            if (inquirer.getFirstName().toLowerCase().contains(word))
                score += 1;
            if (inquirer.getLastName().toLowerCase().contains(word))
                score += 1;
            if (inquirer.getServicesPhone().toLowerCase().contains(word))
                score += 1;
        }
        return score;
    }
}
