package edu.ucalgary.oop;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;

public class DisasterVictim extends Person {
    private static int counter = 0;
    private final int ASSINGNED_SOCIAL_ID;
    private final LocalDate ENTRY_DATE;
    private final HashSet<FamilyRelation> familyConnections;
    private String comments;
    private LocalDate dateOfBirth;
    private String gender = "Unknown";
    private ArrayList<MedicalRecord> medicalRecords;
    private ArrayList<Supply> personalBelongings;
    private EnumSet<DietaryRestrictions> dietaryRestrictions;

    public DisasterVictim(String firstName, LocalDate ENTRY_DATE) {
        super(firstName);
        this.ENTRY_DATE = ENTRY_DATE;
        ASSINGNED_SOCIAL_ID = counter++;
        medicalRecords = new ArrayList<MedicalRecord>();
        personalBelongings = new ArrayList<Supply>();
        familyConnections = new HashSet<FamilyRelation>();
        dietaryRestrictions = EnumSet.noneOf(DietaryRestrictions.class);
        setDateOfBirth(LocalDate.of(0, 1, 1));
        setGender(gender);
    }

    public DisasterVictim(String firstName, String lastName, LocalDate entryDate, LocalDate dateOfBirth,
            String gender) {
        this(firstName, entryDate);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
    }

    public DisasterVictim(String firstName, String lastName, LocalDate entryDate, int approxAge, String gender) {
        this(firstName, entryDate);
        setLastName(lastName);
        setDateOfBirth(approxAge);
        setGender(gender);
    }

    public static int getCounter() {
        return counter;
    }

    public EnumSet<DietaryRestrictions> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(EnumSet<DietaryRestrictions> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        } else {
            this.dateOfBirth = dateOfBirth;
        }
    }

    public void setDateOfBirth(int age) {
        setCalculateApproximateDateOfBirth(age);
    }

    public void setDateOfBirth(String dateOfBirth) {
        try {
            setDateOfBirth(LocalDate.parse(dateOfBirth));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    public int getAssignedSocialID() {
        return ASSINGNED_SOCIAL_ID;
    }

    public LocalDate getEntryDate() {
        return ENTRY_DATE;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.isEmpty()) {
            return;
        }

        ArrayList<String> availableGenders = new ArrayList<String>();
        try {
            File genderFile = new File(new File(new File("edu", "ucalgary"), "oop"), "GenderOptions.txt");
            Scanner sr = new Scanner(genderFile);
            while (sr.hasNextLine()) {
                availableGenders.add(sr.nextLine());
            }
            sr.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("File not found");
        }

        boolean genderFound = false;
        for (String option : availableGenders) {
            if (option.equalsIgnoreCase(gender)) {
                this.gender = gender;
                genderFound = true;
                break;
            }
        }

        if (!genderFound && !gender.equals("Unknown")) {
            throw new IllegalArgumentException(
                    gender + " not found in the list of available genders. Please use one of the following: "
                            + availableGenders);
        }
    }

    public ArrayList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        medicalRecords.sort(
                (MedicalRecord m1, MedicalRecord m2) -> m1.getDateOfTreatment().compareTo(m2.getDateOfTreatment()));
        this.medicalRecords = medicalRecords;
    }

    public ArrayList<Supply> getPersonalBelongings() {
        return personalBelongings;
    }

    public void setPersonalBelongings(ArrayList<Supply> personalBelongings) {
        this.personalBelongings = personalBelongings;
    }

    public HashSet<FamilyRelation> getFamilyConnections() {
        return familyConnections;
    }

    public void setFamilyConnections(ArrayList<DisasterVictim> familyConnections, ArrayList<String> relationTypes) {
        for (DisasterVictim personTwo : familyConnections) {
            addFamilyConnection(personTwo, relationTypes.get(familyConnections.indexOf(personTwo)));
        }
    }

    public void addPersonalBelonging(Supply supply) {
        if (personalBelongings.contains(supply)) {
            Supply temp = personalBelongings.get(personalBelongings.indexOf(supply));
            temp.incrementQuantity(supply.getQuantity());
        } else {
            personalBelongings.add(supply);
        }
    }

    public void addFamilyConnection(DisasterVictim personTwo, String relationType) {
        FamilyRelation newFamilyRelation = new FamilyRelation(this, personTwo, relationType);

        for (FamilyRelation relation : personTwo.getFamilyConnections()) {
            DisasterVictim otherPerson = relation.getPersonOne().equals(personTwo) ? relation.getPersonTwo()
                    : relation.getPersonOne();
            FamilyRelation tempRelation = new FamilyRelation(this, otherPerson, relationType);
            this.familyConnections.add(tempRelation);
            otherPerson.getFamilyConnections().add(tempRelation);
        }

        for (FamilyRelation relation : this.familyConnections) {
            DisasterVictim otherPerson = relation.getPersonOne().equals(this) ? relation.getPersonTwo()
                    : relation.getPersonOne();
            FamilyRelation tempRelation = new FamilyRelation(personTwo, otherPerson,
                    relation.getRelation() + " of " + relationType);
            personTwo.getFamilyConnections().add(tempRelation);
            otherPerson.getFamilyConnections().add(tempRelation);
        }

        this.familyConnections.add(newFamilyRelation);
        personTwo.getFamilyConnections().add(newFamilyRelation);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
    }

    public void addDietaryRestriction(DietaryRestrictions dietaryRestriction) {
        dietaryRestrictions.add(dietaryRestriction);
    }

    public void removeDietaryRestriction(DietaryRestrictions dietaryRestriction) {
        if (dietaryRestrictions.contains(dietaryRestriction)) {
            dietaryRestrictions.remove(dietaryRestriction);
        } else {
            throw new IllegalArgumentException("Dietary restriction not found");
        }
    }

    public void removePersonalBelonging(Supply supply) {
        if (personalBelongings.contains(supply)) {
            Supply temp = personalBelongings.get(personalBelongings.indexOf(supply));
            if (temp.getQuantity() > supply.getQuantity()) {
                temp.decrementQuantity(supply.getQuantity());
            } else if (temp.getQuantity() == supply.getQuantity()) {
                personalBelongings.remove(supply);
            } else {
                throw new IllegalArgumentException("Quantity to remove exceeds available quantity");
            }
        } else {
            throw new IllegalArgumentException("Supply not found");
        }
    }

    public void removeFamilyConnection(DisasterVictim personTwo, String relationType) {
        if (familyConnections.contains(new FamilyRelation(this, personTwo, relationType))) {
            this.familyConnections.remove(new FamilyRelation(this, personTwo, relationType));
            for (FamilyRelation relation : this.familyConnections) {
                DisasterVictim otherPerson = relation.getPersonOne().equals(this) ? relation.getPersonTwo()
                        : relation.getPersonOne();
                for (FamilyRelation relation2 : otherPerson.getFamilyConnections()) {
                    DisasterVictim otherPerson2 = relation2.getPersonOne().equals(otherPerson)
                            ? relation2.getPersonTwo()
                            : relation2.getPersonOne();
                    if (otherPerson2.equals(personTwo)) {
                        otherPerson.getFamilyConnections().remove(relation2);
                        personTwo.getFamilyConnections().remove(relation2);
                        break;
                    }
                }
            }

            for (FamilyRelation relation : this.familyConnections) {
                DisasterVictim otherPerson = relation.getPersonOne().equals(this) ? relation.getPersonTwo()
                        : relation.getPersonOne();
                for (FamilyRelation relation2 : otherPerson.getFamilyConnections()) {
                    DisasterVictim otherPerson2 = relation2.getPersonOne().equals(otherPerson)
                            ? relation2.getPersonTwo()
                            : relation2.getPersonOne();
                    if (otherPerson2.equals(personTwo)) {
                        personTwo.getFamilyConnections().remove(relation2);
                    }
                }
            }
            personTwo.getFamilyConnections().remove(new FamilyRelation(this, personTwo, relationType));
        } else {
            throw new IllegalArgumentException("Family relation not found");
        }
    }

    public void removeMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecords.contains(medicalRecord)) {
            medicalRecords.remove(medicalRecord);
        } else {
            throw new IllegalArgumentException("Medical record not found");
        }
    }

    public void setCalculateApproximateDateOfBirth(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        } else if (age > 120) {
            throw new IllegalArgumentException("Age cannot be greater than 120.");
        }
        dateOfBirth = LocalDate.now().minusYears(age);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DisasterVictim) {
            DisasterVictim other = (DisasterVictim) obj;
            return ASSINGNED_SOCIAL_ID == other.getAssignedSocialID();
        }
        return false;
    }
}