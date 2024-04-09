package edu.ucalgary.oop;

import java.time.LocalDate;

public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private LocalDate dateOfTreatment;

    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment) {
        this.location = location;
        this.treatmentDetails = treatmentDetails;
        setDateOfTreatment(dateOfTreatment);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    public void setTreatmentDetails(String treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
    }

    public LocalDate getDateOfTreatment() {
        return dateOfTreatment;
    }

    public void setDateOfTreatment(String dateOfTreatment) {
        try {
            LocalDate.parse(dateOfTreatment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-mm-dd.");
        }
        this.dateOfTreatment = LocalDate.parse(dateOfTreatment);
    }

    public void setDateOfTreatment(LocalDate dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
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
        return record.location.equals(location) && record.treatmentDetails.equals(treatmentDetails)
                && record.dateOfTreatment.equals(dateOfTreatment);
    }
}