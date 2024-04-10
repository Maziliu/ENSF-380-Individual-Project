package edu.ucalgary.oop;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The DisasterVictim class represents a person affected by a disaster. Each
 * victim has a unique social ID, entry date, and a list of family connections.
 * This class also inherits the attributes of the Person class, such as first
 * name, last name, and date of birth.
 */
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

    /**
     * Constructs a DisasterVictim with the given first name and entry date.
     * 
     * @param firstName  The first name of the victim
     * @param ENTRY_DATE The date the victim entered the shelter. This is a
     *                   LocalDate object.
     */
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

    /**
     * Constructs a DisasterVictim with the given first name, last name, entry date,
     * date
     * 
     * @param firstName   The first name of the victim
     * @param lastName    The last name of the victim
     * @param entryDate   The date the victim entered the shelter. This is a
     *                    LocalDate object.
     * @param dateOfBirth The date of birth of the victim. This is a LocalDate
     *                    object
     * @param gender      The gender of the victim
     */
    public DisasterVictim(String firstName, String lastName, LocalDate entryDate, LocalDate dateOfBirth,
            String gender) {
        this(firstName, entryDate);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
    }

    /**
     * Constructs a DisasterVictim with the given first name, last name, entry date,
     * approxAge
     * 
     * @param firstName The first name of the victim
     * @param lastName  The last name of the victim
     * @param entryDate The date the victim entered the shelter. This is a LocalDate
     *                  object.
     * @param approxAge The approximate age of the victim as an integer
     * @param gender    The gender of the victim
     */
    public DisasterVictim(String firstName, String lastName, LocalDate entryDate, int approxAge, String gender) {
        this(firstName, entryDate);
        setLastName(lastName);
        setDateOfBirth(approxAge);
        setGender(gender);
    }

    /**
     * This method returns the number of DisasterVictims created so far.
     * 
     * @return The number of DisasterVictims created so far. This is also the used
     *         for the social ID of the victim.
     */
    public static int getCounter() {
        return counter;
    }

    /**
     * This method returns the Dietary Restrictions of the victim.
     * 
     * @return The Dietary Restrictions of the victim as an EnumSet.
     */
    public EnumSet<DietaryRestrictions> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    /**
     * This method sets the Dietary Restrictions of the victim.
     * 
     * @param dietaryRestrictions The Dietary Restrictions of the victim as an
     *                            EnumSet.
     */
    public void setDietaryRestrictions(EnumSet<DietaryRestrictions> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    /**
     * This method returns the Date of birth of the victim.
     * 
     * @return The DOB as a LocalDate object.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * This method sets the Date of birth of the victim.
     * 
     * @param dateOfBirth The DOB as a LocalDate object.
     * @throws IllegalArgumentException if the date of birth is in the future.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        } else {
            this.dateOfBirth = dateOfBirth;
        }
    }

    /**
     * This method sets the Date of birth of the victim.
     * 
     * @param age The age of the victim as an integer.
     * @throws IllegalArgumentException if the age is negative. This is the same as
     *                                  setting the DOB in the future.
     */
    public void setDateOfBirth(int age) {
        setCalculateApproximateDateOfBirth(age);
    }

    /**
     * This method sets the Date of birth of the victim.
     * 
     * @param dateOfBirth The DOB as a string in the format yyyy-mm-dd.
     * @throws IllegalArgumentException if the date of birth is in the future or the
     *                                  date is not in the correct format.
     */
    public void setDateOfBirth(String dateOfBirth) {
        try {
            setDateOfBirth(LocalDate.parse(dateOfBirth));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    /**
     * This method returns the assigned social ID of the victim.
     * 
     * @return The social ID of the victim as an integer.
     */
    public int getAssignedSocialID() {
        return ASSINGNED_SOCIAL_ID;
    }

    /**
     * This method returns the entry date of the victim.
     * 
     * @return The entry date of the victim as a LocalDate object.
     */
    public LocalDate getEntryDate() {
        return ENTRY_DATE;
    }

    /**
     * This method returns the comments of the victim.
     * 
     * @return The comments of the victim as a string.
     */
    public String getComments() {
        return comments;
    }

    /**
     * This method sets the comments of the victim.
     * 
     * @param comments The comments of the victim as a string.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * This method returns the gender of the victim as a string
     * 
     * @return The gender of the victim as a string.
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method sets the gender of the victim. Gender must be one of the options
     * in the GenderOptions.txt file.
     * 
     * @param gender The gender of the victim as a string. If the given string is
     *               empty returns without setting the gender. Default is "Unknown".
     * @throws IllegalArgumentException If the gendertype is not in the
     *                                  GenderOptions.txt file.
     * @throws IllegalArgumentException If the GenderOptions.txt file is not found.
     */
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

    /**
     * This method returns the medical records of the victim.
     * 
     * @return The medical records of the victim as an ArrayList of MedicalRecord
     *         objects.
     */
    public ArrayList<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    /**
     * This method sets the medical records of the victim.
     * 
     * @param medicalRecords The medical records of the victim as an ArrayList of
     *                       MedicalRecord objects.
     */
    public void setMedicalRecords(ArrayList<MedicalRecord> medicalRecords) {
        medicalRecords.sort(
                (MedicalRecord m1, MedicalRecord m2) -> m1.getDateOfTreatment().compareTo(m2.getDateOfTreatment()));
        this.medicalRecords = medicalRecords;
    }

    /**
     * This method returns the personal belongings of the victim.
     * 
     * @return The personal belongings of the victim as an ArrayList of Supply
     *         objects.
     */
    public ArrayList<Supply> getPersonalBelongings() {
        return personalBelongings;
    }

    /**
     * This method sets the personal belongings of the victim.
     * 
     * @param personalBelongings The personal belongings of the victim as an
     *                           ArrayList of Supply objects.
     */
    public void setPersonalBelongings(ArrayList<Supply> personalBelongings) {
        this.personalBelongings = personalBelongings;
    }

    /**
     * This method returns the family connections of the victim.
     * 
     * @return The family connections of the victim as a HashSet of FamilyRelation
     *         objects.
     */
    public HashSet<FamilyRelation> getFamilyConnections() {
        return familyConnections;
    }

    /**
     * This method sets the family connections of the victim. It also sets the
     * family connections of the other person in the relation. Arryalists must be
     * the same size and each index must corespond (ie. familyConnections[0]
     * corresponds with relationTypes[0]).
     * 
     * @param familyConnections The family connections of the victim as an ArrayList
     *                          of DisasterVictim objects.
     * @param relationTypes     The relation types of the family connections as an
     *                          ArrayList of strings.
     */
    public void setFamilyConnections(ArrayList<DisasterVictim> familyConnections, ArrayList<String> relationTypes) {
        for (DisasterVictim personTwo : familyConnections) {
            addFamilyConnection(personTwo, relationTypes.get(familyConnections.indexOf(personTwo)));
        }
    }

    /**
     * This method adds a supply to the personal belongings of the victim. If the
     * supply already exists in the personal belongings, the quantity is
     * incremented. Otherwise, the supply is added to the personal belongings.
     * 
     * @param supply The supply to add to the personal belongings of the victim.
     */
    public void addPersonalBelonging(Supply supply) {
        if (personalBelongings.contains(supply)) {
            Supply temp = personalBelongings.get(personalBelongings.indexOf(supply));
            temp.incrementQuantity(supply.getQuantity());
        } else {
            personalBelongings.add(supply);
        }
    }

    /**
     * This method adds a family connection to the victim. It also adds the family
     * connection to the other person in the relation.
     * 
     * @param personTwo    The other person in the family connection.
     * @param relationType The relation type of the family connection (ie. Sibling,
     *                     Child, etc).
     * @throws IllegalArgumentException If the person is trying to add themselves as
     *                                  a family connection.
     */
    public void addFamilyConnection(DisasterVictim personTwo, String relationType) {
        if (this.equals(personTwo)) {
            throw new IllegalArgumentException("Cannot add self as family connection");
        }

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

    /**
     * This method adds a medical record to the victim.
     * 
     * @param medicalRecord The medical record to add to the victim. Must be a
     *                      MedicalRecord object.
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
    }

    /**
     * This method adds a dietary restriction to the victim.
     * 
     * @param dietaryRestriction The dietary restriction to add to the victim. Must
     *                           be a DietaryRestrictions enum.
     */
    public void addDietaryRestriction(DietaryRestrictions dietaryRestriction) {
        dietaryRestrictions.add(dietaryRestriction);
    }

    /**
     * This method removes a dietary restriction from the victim.
     * 
     * @param dietaryRestriction The dietary restriction to remove from the victim.
     *                           Must be a DietaryRestrictions enum.
     * @throws IllegalArgumentException If the dietary restriction is not found.
     */
    public void removeDietaryRestriction(DietaryRestrictions dietaryRestriction) {
        if (dietaryRestrictions.contains(dietaryRestriction)) {
            dietaryRestrictions.remove(dietaryRestriction);
        } else {
            throw new IllegalArgumentException("Dietary restriction not found");
        }
    }

    /**
     * This method removes a supply from the personal belongings of the victim. If
     * no exception is thrown, the supply is removed.
     * 
     * @param supply The supply to remove from the personal belongings of the
     *               victim. Must be a Supply object.
     * @throws IllegalArgumentException If the supply is not found.
     * @throws IllegalArgumentException If the quantity to remove exceeds the
     *                                  available quantity.
     */
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

    /**
     * This method removes a family connection from the victim. It also removes the
     * family connection from the other person in the relation.
     * 
     * @param personTwo    The other person in the family connection.
     * @param relationType The relation type of the family connection (ie. Sibling,
     *                     Child, etc).
     * @throws IllegalArgumentException If the family relation is not found.
     */
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

    /**
     * This method removes a medical record from the victim.
     * 
     * @param medicalRecord The medical record to remove from the victim. Must be a
     *                      MedicalRecord object.
     * @throws IllegalArgumentException If the medical record is not found.
     */
    public void removeMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecords.contains(medicalRecord)) {
            medicalRecords.remove(medicalRecord);
        } else {
            throw new IllegalArgumentException("Medical record not found");
        }
    }

    /**
     * This method calculates the approximate date of birth of the victim based on
     * the given age. Can be zero in the case of a newborns.
     * 
     * @param age The age of the victim as an integer.
     * @throws IllegalArgumentException If the age is negative.
     */
    public void setCalculateApproximateDateOfBirth(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        dateOfBirth = LocalDate.now().minusYears(age);
    }

    /**
     * This method returns true if the this victim is the same as the other victim.
     * 
     * @param obj The other victim to compare.
     * @return True or false if the victims are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DisasterVictim) {
            DisasterVictim other = (DisasterVictim) obj;
            return ASSINGNED_SOCIAL_ID == other.getAssignedSocialID();
        }
        return false;
    }
}