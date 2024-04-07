package edu.ucalgary.oop;

import java.time.LocalDate;

public class InquirerLog {
    private String details;
    private Inquirer inquirer;
    private LocalDate callDate;

    public InquirerLog(Inquirer inquirer, String details, LocalDate callDate) {
        setInquirer(inquirer);
        setDetails(details);
        setCallDate(callDate);
    }

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

    public InquirerLog(Inquirer inquirer, String details) {
        setInquirer(inquirer);
        setDetails(details);
        setCallDate(LocalDate.now());
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Inquirer getInquirer() {
        return inquirer;
    }

    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    public LocalDate getCallDate() {
        return callDate;
    }

    public void setCallDate(LocalDate callDate) {
        this.callDate = callDate;
    }
}
