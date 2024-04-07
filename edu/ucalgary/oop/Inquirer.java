package edu.ucalgary.oop;

import java.sql.Connection;
import java.util.ArrayList;

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
    public void appendDetails(String details) {
        return;
    }

    @Override
    public void saveToDatabase(Connection connection) {
        for (InquirerLog interaction : newInteractions) {

        }
    }

    @Override
    public void loadFromDatabase(Connection connection) {
        // TODO Auto-generated method stub
    }

    @Override
    public void logQueries(ArrayList<String> queries) {
        // TODO Auto-generated method stub
    }

}
