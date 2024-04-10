package edu.ucalgary.oop;

import java.time.LocalDate;

/**
 * Represents a single log of an interaction with an inquirer.
 */
public class InquirerLog {
    private String details;
    private Inquirer inquirer;
    private LocalDate callDate;

    /**
     * Constructs an InquirerLog object with the given parameters.
     * 
     * @param inquirer the inquirer who made the call
     * @param details  the details of the call
     * @param callDate the date of the call
     */
    public InquirerLog(Inquirer inquirer, String details, LocalDate callDate) {
        setInquirer(inquirer);
        setDetails(details);
        setCallDate(callDate);
    }

    /**
     * Constructs an InquirerLog object with the given parameters.
     * 
     * @param id       the ID of the inquirer who made the call
     * @param details  the details of the call
     * @param callDate the date of the call
     */
    public InquirerLog(int id, String details, LocalDate callDate) {
        for (Inquirer inquirer : DriverApplication.inquirers) {
            if (inquirer.getInquirerID() == id) {
                setInquirer(inquirer);
                break;
            }
        }
        setDetails(details);
        setCallDate(callDate);
    }

    /**
     * Constructs an InquirerLog object with the given parameters.
     * 
     * @param inquirer the inquirer who made the call
     * @param details  the details of the call
     */
    public InquirerLog(Inquirer inquirer, String details) {
        setInquirer(inquirer);
        setDetails(details);
        setCallDate(LocalDate.now());
    }

    /**
     * Constructs an InquirerLog object with the given parameters.
     * 
     * @param id      the ID of the inquirer who made the call
     * @param details the details of the call
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details of the call.
     * 
     * @param details the details of the call
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Returns the inquirer who made the call.
     * 
     * @return the inquirer who made the call
     */
    public Inquirer getInquirer() {
        return inquirer;
    }

    /**
     * Sets the inquirer who made the call.
     * 
     * @param inquirer the inquirer who made the call
     */
    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    /**
     * Returns the date of the call.
     * 
     * @return the date of the call
     */
    public LocalDate getCallDate() {
        return callDate;
    }

    /**
     * Sets the date of the call.
     * 
     * @param callDate the date of the call
     */
    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }
}
