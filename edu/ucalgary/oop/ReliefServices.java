package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReliefServices implements ILoggable {
    Inquirer inquirer;
    DisasterVictim missingVictim;
    LocalDate dateOfInquiry;
    Location lastSeenLocation;
    String inquiryDetails;

    public ReliefServices(Inquirer inquirer, DisasterVictim missingVictim, LocalDate dateOfInquiry, Location lastSeenLocation, String inquiryDetails) {
        setMissingVictim(missingVictim);
        setDateOfInquiry(dateOfInquiry);
        setLastSeenLocation(lastSeenLocation); 
        setInquiryDetails(inquiryDetails); 
        setInquirer(inquirer);
    }

    public void setInquirer(Inquirer inquirer) 
    { ;
        this.inquirer = inquirer;
        //inquirer.loadFromDatabase();
        this.inquirer.addInteraction(generateLog());
        //inquirer.saveToDatabase(); 
    }
    public void setMissingVictim(DisasterVictim missingVictim) { this.missingVictim = missingVictim; }
    public void setDateOfInquiry(LocalDate dateOfInquiry) { this.dateOfInquiry = dateOfInquiry; }
    public void setDateOfInquiry(String dateOfInquiry) 
    { 
        try 
        {
            setDateOfInquiry(LocalDate.parse(dateOfInquiry));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid date");
        } 
    }
    public void setLastSeenLocation(Location lastSeenLocation) { this.lastSeenLocation = lastSeenLocation; }
    public void setInquiryDetails(String inquiryDetails) { this.inquiryDetails = inquiryDetails; }

    public Inquirer getInquirer() { return inquirer; }
    public DisasterVictim getMissingVictim() { return missingVictim; }
    public LocalDate getDateOfInquiry() { return dateOfInquiry; }
    public Location getLastSeenLocation() { return lastSeenLocation; }
    public String getInquiryDetails() { return inquiryDetails; }

    public Location searchMissingVictim() {
        if (shallowSearchMissingVictim() != null) {
            return lastSeenLocation;
        }
        else
        {
            //TODO: Implement Database search
            return null;
        }
    }

    public Location shallowSearchMissingVictim() {
        if (lastSeenLocation.getOccupants().contains(missingVictim)) {
            return lastSeenLocation;
        }
        else 
        {
            return null;
        }
    }

    @Override
    public String generateLog() {
        return "Inquirer: " + inquirer.getFirstName() + " " + inquirer.getLastName() + "\n" +
               "Missing Victim: " + missingVictim.getFirstName() + "\n" +
               "Date of Inquiry: " + dateOfInquiry + "\n" +
               "Last Seen Location: " + lastSeenLocation.getName() + ", " + lastSeenLocation.getAddress() + "\n" +
               "Inquiry Details: " + inquiryDetails;
    }

    @Override
    public void appendDetails(String details) {
        inquiryDetails += "\n" + details;
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
