package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DriverApplication extends AppGui {
    private static Connection connection;
    private AppGui appGui = null;

    public static ArrayList<DisasterVictim> disasterVictims = new ArrayList<>();
    public static ArrayList<Inquirer> inquirers = new ArrayList<>();
    public static ArrayList<Location> locations = new ArrayList<>();

    public DriverApplication() {
        createConnection();
        DataGenerationGui.generateData();
        loadInquirers();

        frame = new JFrame("Relief Services Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setVisible(true);
        initializeMenuBar();
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu switchMenu = new JMenu("Mode");
        JMenuItem switchToCentralGui = new JMenuItem("Central GUI");
        switchToCentralGui.addActionListener(e -> switchToGui(new CentralGui()));
        JMenuItem switchToLocalGui = new JMenuItem("Local GUI");
        switchToLocalGui.addActionListener(e -> switchToGui(new LocalGui()));

        switchMenu.add(switchToCentralGui);
        switchMenu.add(switchToLocalGui);

        menuBar.add(switchMenu);
        frame.setJMenuBar(menuBar);
    }

    private void loadInquirers() {
        // Create a list to hold Inquirers if not already done
        if (inquirers == null) {
            inquirers = new ArrayList<>();
        } else {
            inquirers.clear(); // Clear existing inquirers if reloading
        }

        try {
            Statement statement = connection.createStatement();

            // First, load all inquirers
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INQUIRER");
            while (resultSet.next()) {
                Inquirer inquirer = new Inquirer(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("phoneNumber"),
                        "info", // Assuming 'info' is static or placeholder data
                        resultSet.getInt("id"));

                inquirers.add(inquirer);
            }
            resultSet.close(); // Close the ResultSet to free resources

            // Next, load all inquiry logs
            ResultSet logResultSet = statement.executeQuery("SELECT * FROM INQUIRY_LOG");
            while (logResultSet.next()) {
                int inquirerID = logResultSet.getInt("inquirer"); // Assuming correct column name is 'inquirerID'
                Inquirer inquirer = findInquirerById(inquirerID);

                if (inquirer != null) {
                    inquirer.addPreviousInteraction(new InquirerLog(
                            inquirer,
                            logResultSet.getString("details"),
                            logResultSet.getDate("callDate").toLocalDate()));
                }
            }
            logResultSet.close(); // Close the ResultSet to free resources

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Inquirer findInquirerById(int inquirerID) {
        for (Inquirer i : inquirers) {
            if (i.getInquirerID() == inquirerID) {
                return i;
            }
        }
        System.out.println("Inquirer with ID " + inquirerID + " not found");
        return null;
    }

    @Override
    public JPanel getMainPanel() {
        return appGui.getMainPanel();
    }

    @Override
    public JMenuBar createMenuBar() {
        return appGui.createMenuBar();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new DriverApplication());
    }
}
