package edu.ucalgary.oop;
import java.time.LocalDate;
import java.util.ArrayList;

public class MedicalRecord implements ILoggable {
    private Location location;
    private String treatmentDetails;
    private String dateOfTreatment;

    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment) {
        this.location = location;
        this.treatmentDetails = treatmentDetails;
        setDateOfTreatment(dateOfTreatment);
    }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String getTreatmentDetails() { return treatmentDetails; }
    public void setTreatmentDetails(String treatmentDetails) { this.treatmentDetails = treatmentDetails; }

    public String getDateOfTreatment() { return dateOfTreatment; }
    public void setDateOfTreatment(String dateOfTreatment) {
        try {
            LocalDate.parse(dateOfTreatment);
        } 
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-mm-dd.");
        }
        this.dateOfTreatment = dateOfTreatment;
    }

    @Override
    public String generateLog() {
        String log = "Medical Record: \n";
        log += "Location: " + location.getName() + " "  + location.getAddress() + "\n";
        log += "Treatment Details: " + treatmentDetails + "\n";
        log += "Date of Treatment: " + dateOfTreatment + "\n";
        return log;
    }

    @Override
    public void appendDetails(String details) {
        treatmentDetails += "\n" + details;
    }

    @Override
    public void saveToDatabase() {
        //TODO: Implement database save
    }

    @Override
    public void loadFromDatabase() {
        //TODO: Implement database load
    }

    @Override
    public void logQueries(ArrayList<String> queries) {
        //TODO: Implement database logging
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MedicalRecord)) {
            return false;
        }
        MedicalRecord record = (MedicalRecord) obj;
        return record.location.equals(location) && record.treatmentDetails.equals(treatmentDetails) && record.dateOfTreatment.equals(dateOfTreatment);
    }
}