package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * The DriverApplication class represents the main application for the Relief
 * Services Application. It is responsible for creating the connection to the
 * database, generating data, and loading inquirers. It also initializes the
 * main frame and menu bar for the application. The DriverApplication class is a
 * subclass of AppGui. This is the class that contains the main method for the
 * application. This class also has the global array of DisasterVictim objects,
 * Inquirer, Location, and Supply objects. Inquirers are loaded from the
 * database and stored in the inquirers list.
 */
public class DriverApplication extends AppGui {
    public static ArrayList<DisasterVictim> disasterVictims = new ArrayList<>();
    public static ArrayList<Inquirer> inquirers = new ArrayList<>();
    public static ArrayList<Location> locations = new ArrayList<>();
    public static ArrayList<Supply> supplies = new ArrayList<>();
    private static Connection connection;
    private final AppGui appGui = null;

    /**
     * The constructor for the DriverApplication class. It creates a connection to
     * the database, generates data, loads inquirers, and initializes the main frame
     * and menu bar for the application.
     */
    public DriverApplication() {
        createConnection();
        DataGeneration.generateData();
        loadInquirers();

        frame = new JFrame("Relief Services Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setVisible(true);
        initializeMenuBar();
    }

    /**
     * Returns the connection to the database.
     * 
     * @return the connection to the database. This is a static method and is a
     *         Connection object.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * The main method for the application. It creates an instance of the
     * DriverApplication class.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(DriverApplication::new);
    }

    /**
     * Creates a connection to the database. This method creates the connection to
     * the database using the DriverManager.getConnection method with the url
     * "jdbc:postgresql://localhost/ensf380project", the user oop, and the password
     * ucalgary. This user must have editing/admin access to the database.
     */
    private void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes the menu bar for the application. This method creates a menu bar
     * with a "Mode" menu that contains two menu items: "Central GUI" and "Local
     * GUI". The "Central GUI" menu item switches the application to the Central
     * GUI, while the "Local GUI" menu item switches the application to the Local
     * GUI. The menu bar is added to the main frame.
     */
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

    /**
     * Executes an SQL query to load all inquirers and their logs from the database.
     * This method creates a list of Inquirer objects and populates it with the data
     * from the database. It then creates a list of InquirerLog objects and
     * populates it with the data from the database. The Inquirer objects are then
     * updated with the InquirerLog objects. The Inquirer objects are then added to
     * the inquirers list.
     */
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
            resultSet.close();

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
            logResultSet.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Finds an Inquirer object by its ID.
     * 
     * @param inquirerID the ID of the Inquirer object to find
     * @return the Inquirer object with the given ID, or null if not found
     */
    private Inquirer findInquirerById(int inquirerID) {
        for (Inquirer i : inquirers) {
            if (i.getInquirerID() == inquirerID) {
                return i;
            }
        }
        System.out.println("Inquirer with ID " + inquirerID + " not found");
        return null;
    }

    /**
     * Gets the main panel of the GUI screen. This is an overridden method from the
     * AppGui class.
     */
    @Override
    public JPanel getMainPanel() {
        return appGui.getMainPanel();
    }

    /**
     * Creates the menu bar for the GUI screen. This is an overridden method from
     * the AppGui class.
     */
    @Override
    public JMenuBar createMenuBar() {
        return appGui.createMenuBar();
    }
}
