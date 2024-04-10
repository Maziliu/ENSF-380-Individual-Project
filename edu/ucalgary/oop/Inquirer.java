package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;

/**
 * Represents an inquirer who interacts with a system.
 * Inherits from the Person class and implements the ILoggable interface.
 */
public class Inquirer extends Person implements ILoggable {
    private static int nextID = 0;
    private final int inquirerID;
    private final ArrayList<InquirerLog> previousInteractions;
    private final ArrayList<InquirerLog> newInteractions;
    private String servicesPhone, info;

    /**
     * Constructs an Inquirer object with the given parameters.
     * 
     * @param firstName      the first name of the inquirer
     * @param lastName       the last name of the inquirer
     * @param SERVICES_PHONE the services phone number of the inquirer
     * @param INFO           additional information about the inquirer
     * @param id             the ID of the inquirer
     */
    public Inquirer(String firstName, String lastName, String SERVICES_PHONE, String INFO, int id) {
        super(firstName);
        setLastName(lastName);
        setServicesPhone(SERVICES_PHONE);
        setInfo(INFO);
        previousInteractions = new ArrayList<InquirerLog>();
        newInteractions = new ArrayList<InquirerLog>();
        inquirerID = id;

        if (id >= nextID) {
            nextID = id + 1;
        }
    }

    /**
     * Constructs an Inquirer object with the given parameters.
     * Uses the next available ID for the inquirer.
     * 
     * @param firstName      the first name of the inquirer
     * @param lastName       the last name of the inquirer
     * @param SERVICES_PHONE the services phone number of the inquirer
     * @param INFO           additional information about the inquirer
     */
    public Inquirer(String firstName, String lastName, String SERVICES_PHONE, String INFO) {
        this(firstName, lastName, SERVICES_PHONE, INFO, nextID);
    }

    /**
     * Gets the ID of the inquirer.
     * 
     * @return the ID of the inquirer
     */
    public int getInquirerID() {
        return inquirerID;
    }

    /**
     * Gets the services phone number of the inquirer.
     * 
     * @return the services phone number of the inquirer
     */
    public String getServicesPhone() {
        return servicesPhone;
    }

    /**
     * Sets the services phone number of the inquirer.
     * 
     * @param phoneNumber the services phone number to set
     * @throws IllegalArgumentException if the phone number format is invalid
     */
    public void setServicesPhone(String phoneNumber) {
        if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
            this.servicesPhone = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    /**
     * Gets the additional information about the inquirer.
     * 
     * @return the additional information about the inquirer
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the additional information about the inquirer.
     * 
     * @param info the additional information to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets the list of previous interactions of the inquirer.
     * 
     * @return the list of previous interactions of the inquirer
     */
    public ArrayList<InquirerLog> getPreviousInteractions() {
        return previousInteractions;
    }

    /**
     * Adds a new interaction to the list of new interactions of the inquirer.
     * 
     * @param interaction the interaction to add
     */
    public void addInteraction(InquirerLog interaction) {
        newInteractions.add(interaction);
    }

    /**
     * Adds a previous interaction to the list of previous interactions of the
     * inquirer.
     * 
     * @param interaction the previous interaction to add
     */
    public void addPreviousInteraction(InquirerLog interaction) {
        previousInteractions.add(interaction);
    }

    /**
     * Generates a log string representing the inquirer and their interactions.
     * 
     * @return the log string
     */
    @Override
    public String generateLog() {
        String log = "Inquirer: " + getFirstName() + " " + getLastName() + "\n";
        log += "Services Phone: " + getServicesPhone() + "\n";
        log += "Info: " + getInfo() + "\n";
        log += "Previous Interactions: \n";
        for (InquirerLog interaction : previousInteractions) {
            log += interaction.getCallDate() + " " + interaction.getDetails() + "\n";
        }
        for (InquirerLog interaction : newInteractions) {
            log += interaction.getCallDate() + " " + interaction.getDetails() + "\n";
        }
        return log;
    }

    /**
     * Saves the inquirer and their interactions to the database.
     * Inserts the inquirer if they do not exist, and inserts new interactions.
     * Clears the list of new interactions after saving.
     * 
     * @throws SQLException if a database error occurs
     */
    @Override
    public void saveToDatabase() {
        Connection connection = null;
        try {
            connection = DriverApplication.getConnection(); // Obtain connection

            // Check if the inquirer exists
            String selectQuery = "SELECT * FROM INQUIRER WHERE id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(selectQuery)) {
                checkStmt.setInt(1, inquirerID);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        String insertInquirerQuery = "INSERT INTO INQUIRER (id, firstName, lastName, phoneNumber) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertInquirerQuery)) {
                            insertStmt.setInt(1, inquirerID);
                            insertStmt.setString(2, getFirstName());
                            insertStmt.setString(3, getLastName());
                            insertStmt.setString(4, getServicesPhone());
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            // Insert new interactions
            int maxId = 0;
            String maxIdQuery = "SELECT MAX(id) AS maxId FROM INQUIRY_LOG";
            try (Statement maxIdStmt = connection.createStatement();
                    ResultSet rs = maxIdStmt.executeQuery(maxIdQuery)) {
                if (rs.next()) {
                    maxId = rs.getInt("maxId") + 1;
                }
            }

            String insertLogQuery = "INSERT INTO INQUIRY_LOG (id, inquirer, callDate, details) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertLogStmt = connection.prepareStatement(insertLogQuery)) {
                for (InquirerLog interaction : newInteractions) {
                    insertLogStmt.setInt(1, maxId++);
                    insertLogStmt.setInt(2, inquirerID);
                    insertLogStmt.setDate(3, Date.valueOf(interaction.getCallDate()));
                    insertLogStmt.setString(4, interaction.getDetails());
                    insertLogStmt.addBatch();
                }
                insertLogStmt.executeBatch();
            }

            previousInteractions.addAll(newInteractions);
            newInteractions.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
