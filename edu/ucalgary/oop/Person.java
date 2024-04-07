package edu.ucalgary.oop;
import java.time.LocalDate;
import java.util.*;
import java.io.File;
import java.util.Scanner;

public class Person {
    private String firstName, lastName;
    private LocalDate dateOfBirth;
    private String gender = "Unknown";

    public Person(String firstName, String lastName, LocalDate dateOfBirth, String gender) {
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
    }

    public Person(String firstName) {
        this(firstName, "", null, "");
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) 
    { 
        try {
            setDateOfBirth(LocalDate.parse(dateOfBirth));
        } 
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-mm-dd.");
        }
    }
    public void setGender(String gender) 
    { 
        if(gender.isEmpty()) {
            return;
        }

        ArrayList<String> availableGenders = new ArrayList<String>();
        try {
            File genderFile = new File("edu/ucalgary/oop/GenderOptions.txt");
            Scanner sr = new Scanner(genderFile);
            while (sr.hasNextLine()) {
                availableGenders.add(sr.nextLine());
            }
            sr.close();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("File not found");
        }

        boolean genderFound = false;
        for (String option : availableGenders) {
            if (option.toLowerCase().equals(gender.toLowerCase())) {
                this.gender = gender;
                genderFound = true;
                break;   
            }
        }

        if (!genderFound)
        {
            throw new IllegalArgumentException("Gender not found in the list of available genders. Please use one of the following: " + availableGenders);
        }
    }

    public void setCalculateApproximateDateOfBirth(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        else if (age > 120) {
            throw new IllegalArgumentException("Age cannot be greater than 120.");
        }
        dateOfBirth = LocalDate.now().minusYears(age);
    }

    // public static void main(String[] args) {
    //     Person person = new Person("John");
    //     person.setGender("oy");
    //     System.out.println(person.getGender());
    // }
}