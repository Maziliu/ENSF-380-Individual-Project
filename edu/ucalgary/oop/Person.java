package edu.ucalgary.oop;

/**
 * The abstract class Person represents a person with a first name and a last
 * name.
 */
public class Person {
    private String firstName, lastName;

    /**
     * Constructs a Person object with the given first name and last name.
     * 
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     */
    public Person(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * Constructs a Person object with the given first name and an empty last name.
     * 
     * @param firstName the first name of the person
     */
    public Person(String firstName) {
        this(firstName, "");
    }

    /**
     * Returns the first name of the person.
     * 
     * @return the first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     * 
     * @param firstName the first name of the person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the person.
     * 
     * @return the last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person. If the given last name is null, an empty
     * string is set as the last name.
     * 
     * @param lastName the last name of the person
     */
    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        } else {
            this.lastName = "";
        }
    }

}