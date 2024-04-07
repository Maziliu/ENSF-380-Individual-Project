package edu.ucalgary.oop;

import java.util.ArrayList;

public class Inquirer extends Person implements ILoggable {
    private String servicesPhone, info;
    private ArrayList<String> previousInteractions;

    public Inquirer(String firstName, String lastName, String SERVICES_PHONE, String INFO) {
        super(firstName);
        setLastName(lastName);
        setServicesPhone(SERVICES_PHONE);
        setInfo(INFO);
        previousInteractions = new ArrayList<String>();
    }

    public String getServicesPhone() { return servicesPhone; }
    public String getInfo() { return info; }
    public ArrayList<String> getPreviousInteractions() { return previousInteractions; }

    public void setServicesPhone(String phoneNumber) 
    { 
        if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
            this.servicesPhone = phoneNumber;
        }
        else
        {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    public void setInfo(String info) { this.info = info; }

    public void addInteraction() { addInteraction(info); }

    public void addInteraction(String interaction) {
        previousInteractions.add(interaction);
    }

    @Override
    public String generateLog() {
        String log = "Inquirer: " + getFirstName() + " " + getLastName() + "\n";
        log += "Services Phone: " + getServicesPhone() + "\n";
        log += "Info: " + getInfo() + "\n";
        log += "Previous Interactions: \n";
        for (String interaction : previousInteractions) {
            log += interaction + "\n";
        }
        return log;
    }

    @Override
    public void appendDetails(String details) {
        if (previousInteractions.size() > 0) {
            previousInteractions.add(previousInteractions.size() - 1, details + "\n");
        }
        else
        {
            previousInteractions.add(details + "\n");
        }
    }

    @Override
    public void saveToDatabase() {
        // TODO Auto-generated method stub
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
