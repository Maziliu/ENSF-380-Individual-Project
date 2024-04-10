package edu.ucalgary.oop;

/**
 * The FamilyRelation class represents a relationship between two DisasterVictim
 * objects.
 * It contains the two DisasterVictim objects and the relation between them.
 */
public class FamilyRelation {
    private DisasterVictim personOne, personTwo;
    private String relation;

    /**
     * Constructs a FamilyRelation object with the given DisasterVictim objects and
     * relation.
     * 
     * @param personOne the first DisasterVictim object
     * @param personTwo the second DisasterVictim object
     * @param relation  the relation between the two DisasterVictim objects
     */
    public FamilyRelation(DisasterVictim personOne, DisasterVictim personTwo, String relation) {
        setPersonOne(personOne);
        setPersonTwo(personTwo);
        setRelation(relation);
    }

    /**
     * Returns the first DisasterVictim object.
     * 
     * @return the first DisasterVictim object
     */
    public DisasterVictim getPersonOne() {
        return personOne;
    }

    /**
     * Sets the first DisasterVictim object.
     * 
     * @param personOne the first DisasterVictim object
     * @throws IllegalArgumentException if the person is null or if the person is
     *                                  related to themselves
     */
    public void setPersonOne(DisasterVictim personOne) {
        if (personOne == null) {
            throw new IllegalArgumentException("Person cannot be null");
        } else if (personOne.equals(personTwo)) {
            throw new IllegalArgumentException("Person cannot be related to themselves");
        }

        this.personOne = personOne;
    }

    /**
     * Returns the second DisasterVictim object.
     * 
     * @return the second DisasterVictim object
     */
    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    /**
     * Sets the second DisasterVictim object.
     * 
     * @param personTwo the second DisasterVictim object
     * @throws IllegalArgumentException if the person is null or if the person is
     *                                  related to themselves
     */
    public void setPersonTwo(DisasterVictim personTwo) {
        if (personTwo == null) {
            throw new IllegalArgumentException("Person cannot be null");
        } else if (personTwo.equals(personOne)) {
            throw new IllegalArgumentException("Person cannot be related to themselves");
        }

        this.personTwo = personTwo;
    }

    /**
     * Returns the relation between the two DisasterVictim objects.
     * 
     * @return the relation between the two DisasterVictim objects
     */
    public String getRelation() {
        return relation;
    }

    /**
     * Sets the relation between the two DisasterVictim objects.
     * 
     * @param relation the relation between the two DisasterVictim objects
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * Boolean method to check if the two FamilyRelation objects are the same.
     * Two FamilyRelation objects are the same if they contain the same two
     * DisasterVictim objects.
     * 
     * @return true if the two FamilyRelation objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FamilyRelation) {
            FamilyRelation other = (FamilyRelation) obj;
            return (personOne.equals(other.getPersonOne()) && personTwo.equals(other.getPersonTwo()))
                    || (personOne.equals(other.getPersonTwo()) && personTwo.equals(other.getPersonOne()));
        }
        return false;
    }

    /**
     * Returns the hash code of the FamilyRelation object. The hash code is
     * calculated based on the xor of the assigned social IDs of the two
     * DisasterVictim objects.
     * 
     * @return the hash code of the FamilyRelation object
     */
    @Override
    public int hashCode() {
        return Integer.valueOf(personOne.getAssignedSocialID()).hashCode()
                ^ Integer.valueOf(personTwo.getAssignedSocialID()).hashCode();
    }
}