package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.plaf.nimbus.State;

public class Inquirer extends Person implements ILoggable {
    private int inquirerID;
    private String servicesPhone, info;
    private ArrayList<InquirerLog> previousInteractions;
    private ArrayList<InquirerLog> newInteractions;
    private static int nextID = 0;

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

    public Inquirer(String firstName, String lastName, String SERVICES_PHONE, String INFO) {
        this(firstName, lastName, SERVICES_PHONE, INFO, nextID);
    }

    public int getInquirerID() {
        return inquirerID;
    }

    public String getServicesPhone() {
        return servicesPhone;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<InquirerLog> getPreviousInteractions() {
        return previousInteractions;
    }

    public void setServicesPhone(String phoneNumber) {
        if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
            this.servicesPhone = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void addInteraction(InquirerLog interaction) {
        newInteractions.add(interaction);
    }

    public void addPreviousInteraction(InquirerLog interaction) {
        previousInteractions.add(interaction);
    }

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

    @Override
    public void loadFromDatabase() {
        // TODO Auto-generated method stub
    }

    @Override
    public void logQueries(ArrayList<String> queries) {
        // TODO Auto-generated method stub
    }

}
