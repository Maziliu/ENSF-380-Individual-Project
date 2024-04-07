package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReliefServices {
    Inquirer inquirer;
    DisasterVictim missingVictim;
    LocalDate dateOfInquiry;
    Location lastSeenLocation;
    String inquiryDetails;

    public ReliefServices(Inquirer inquirer, DisasterVictim missingVictim, LocalDate dateOfInquiry,
            Location lastSeenLocation, String inquiryDetails) {
        setMissingVictim(missingVictim);
        setDateOfInquiry(dateOfInquiry);
        setLastSeenLocation(lastSeenLocation);
        setInquiryDetails(inquiryDetails);
        setInquirer(inquirer);
    }

    public void setInquirer(Inquirer inquirer) {
        ;
        this.inquirer = inquirer;
        // inquirer.loadFromDatabase();
        this.inquirer.addInteraction(new InquirerLog(inquirer,
                "Inquiry made for " + missingVictim.getFirstName() + " " + missingVictim.getLastName(),
                LocalDate.now()));
        // inquirer.saveToDatabase();
    }

    public void setMissingVictim(DisasterVictim missingVictim) {
        this.missingVictim = missingVictim;
    }

    public void setDateOfInquiry(LocalDate dateOfInquiry) {
        this.dateOfInquiry = dateOfInquiry;
    }

    public void setDateOfInquiry(String dateOfInquiry) {
        try {
            setDateOfInquiry(LocalDate.parse(dateOfInquiry));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    public void setLastSeenLocation(Location lastSeenLocation) {
        this.lastSeenLocation = lastSeenLocation;
    }

    public void setInquiryDetails(String inquiryDetails) {
        this.inquiryDetails = inquiryDetails;
    }

    public Inquirer getInquirer() {
        return inquirer;
    }

    public DisasterVictim getMissingVictim() {
        return missingVictim;
    }

    public LocalDate getDateOfInquiry() {
        return dateOfInquiry;
    }

    public Location getLastSeenLocation() {
        return lastSeenLocation;
    }

    public String getInquiryDetails() {
        return inquiryDetails;
    }

    public Location searchMissingVictim() {
        if (shallowSearchMissingVictim() != null) {
            return lastSeenLocation;
        } else {
            // TODO: Implement Database search
            return null;
        }
    }

    public Location shallowSearchMissingVictim() {
        if (lastSeenLocation.getOccupants().contains(missingVictim)) {
            return lastSeenLocation;
        } else {
            return null;
        }
    }

}
