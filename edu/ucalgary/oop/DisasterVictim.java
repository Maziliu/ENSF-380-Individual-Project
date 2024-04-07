package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

public class DisasterVictim extends Person implements ILoggable {
    private String comments;
    private ArrayList<MedicalRecord> medicalRecords;
    private ArrayList<Supply> personalBelongings;
    private HashSet<FamilyRelation> familyConnections;
    private EnumSet<DietaryRestrictions> dietaryRestrictions;
    private static int counter = 0;
    private final int ASSINGNED_SOCIAL_ID;
    private final LocalDate ENTRY_DATE;

    public DisasterVictim(String firstName, LocalDate ENTRY_DATE) {
        super(firstName);
        this.ENTRY_DATE = ENTRY_DATE;
        ASSINGNED_SOCIAL_ID = counter++;
        medicalRecords = new ArrayList<MedicalRecord>();
        personalBelongings = new ArrayList<Supply>();
        familyConnections = new HashSet<FamilyRelation>();
        dietaryRestrictions = EnumSet.noneOf(DietaryRestrictions.class);
    }

    public DisasterVictim(String firstName, String lastName, LocalDate now, LocalDate dateOfBirth, String gender) {
        this(firstName, now);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
    }

    public EnumSet<DietaryRestrictions> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public static int getCounter() {
        return counter;
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

    public ArrayList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public ArrayList<Supply> getPersonalBelongings() {
        return personalBelongings;
    }

    public HashSet<FamilyRelation> getFamilyConnections() {
        return familyConnections;
    }

    public void setDietaryRestrictions(EnumSet<DietaryRestrictions> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public void setPersonalBelongings(ArrayList<Supply> personalBelongings) {
        this.personalBelongings = personalBelongings;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DisasterVictim) {
            DisasterVictim other = (DisasterVictim) obj;
            return ASSINGNED_SOCIAL_ID == other.getAssignedSocialID();
        }
        return false;
    }

    @Override
    public String generateLog() {
        ArrayList<String> supplies = new ArrayList<String>(), family = new ArrayList<String>(),
                medical = new ArrayList<String>();

        for (Supply supply : personalBelongings) {
            supplies.add(supply.getType() + " x " + supply.getQuantity());
        }

        for (FamilyRelation relation : familyConnections) {
            DisasterVictim otherPerson = relation.getPersonOne().equals(this) ? relation.getPersonTwo()
                    : relation.getPersonOne();
            family.add(otherPerson.getFirstName());
        }
        Collections.sort(family);
        for (MedicalRecord record : medicalRecords) {
            medical.add(record.getDateOfTreatment() + " | " + record.getLocation().getName() + " | "
                    + record.getTreatmentDetails());
        }

        String log = String.format("Name: %s\n" +
                "Assigned Social ID: %d\n" +
                "Entry Date: %s\n" +
                "Comments: %s\n" +
                "Dietary Restrictions: %s\n" +
                "Medical Records: %s\n" +
                "Personal Belongings: %s\n" +
                "Family Connections: %s\n",
                getFirstName() + " " + getLastName(),
                getAssignedSocialID(),
                getEntryDate(),
                getComments(),
                getDietaryRestrictions(),
                medical.toString(),
                supplies.toString(),
                family.toString());

        return log;
    }

    @Override
    public void appendDetails(String details) {
        comments += details;
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

    public static void main(String[] args) {
        DisasterVictim personOne = new DisasterVictim("John", LocalDate.now());
        DisasterVictim personTwo = new DisasterVictim("Jane", LocalDate.now());
        DisasterVictim personThree = new DisasterVictim("Jack", LocalDate.now());
        DisasterVictim personFour = new DisasterVictim("Jill", LocalDate.now());
        personOne.addFamilyConnection(personTwo, "Spouse");
        personOne.addFamilyConnection(personThree, "Sibling");
        personOne.addFamilyConnection(personFour, "Sibling");
        personTwo.addFamilyConnection(personThree, "Sibling");
        personTwo.addFamilyConnection(personFour, "Sibling");
        personThree.addFamilyConnection(personFour, "Spouse");
        System.out.println(personOne.getFamilyConnections());
        System.out.println(personTwo.getFamilyConnections());
        System.out.println(personThree.getFamilyConnections());
        System.out.println(personFour.getFamilyConnections());
        personOne.removeFamilyConnection(personTwo, "Spouse");
        System.out.println(personOne.getFamilyConnections());
        System.out.println(personTwo.getFamilyConnections());
        System.out.println(personThree.getFamilyConnections());
        System.out.println(personFour.getFamilyConnections());
    }
}