package edu.ucalgary.oop;

import java.time.LocalDate;

/**
 * Represents a single medical record of a disaster victim. Each medical record
 * has a location, treatment details, and a date of treatment.
 */
public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private LocalDate dateOfTreatment;

    /**
     * Constructs a MedicalRecord object with the given parameters.
     * 
     * @param location         the location of the treatment. This is a Location
     *                         object
     * @param treatmentDetails the details of the treatment
     * @param dateOfTreatment  the date of the treatment. This is a string with a
     *                         specific format. Format is specified in the
     *                         setDateOfTreatment method
     */
    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment) {
        this.location = location;
        this.treatmentDetails = treatmentDetails;
        setDateOfTreatment(dateOfTreatment);
    }

    /**
     * Gets the location of the treatment.
     * 
     * @return the location of the treatment as a Location object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the treatment.
     * 
     * @param location the location of the treatment
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the details of the treatment.
     * 
     * @return the details of the treatment
     */
    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    /**
     * Sets the details of the treatment.
     * 
     * @param treatmentDetails the details of the treatment
     */
    public void setTreatmentDetails(String treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
    }

    /**
     * Gets the date of the treatment.
     * 
     * @return the date of the treatment as a LocalDate object
     */
    public LocalDate getDateOfTreatment() {
        return dateOfTreatment;
    }

    /**
     * Sets the date of the treatment.
     * 
     * @param dateOfTreatment the date of the treatment as a string with the format
     *                        yyyy-mm-dd
     * @throws IllegalArgumentException if the date format is invalid
     */
    public void setDateOfTreatment(String dateOfTreatment) {
        try {
            LocalDate.parse(dateOfTreatment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-mm-dd.");
        }
        this.dateOfTreatment = LocalDate.parse(dateOfTreatment);
    }

    /**
     * Sets the date of the treatment.
     * 
     * @param dateOfTreatment the date of the treatment as a LocalDate object
     */
    public void setDateOfTreatment(LocalDate dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    /**
     * Overrides the equals method to compare two MedicalRecord objects.
     * if the location, treatment details, and date of treatment are the same, the
     * two MedicalRecord objects are equal.
     * 
     * @return true if the two MedicalRecord objects are equal, false otherwise
     */
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