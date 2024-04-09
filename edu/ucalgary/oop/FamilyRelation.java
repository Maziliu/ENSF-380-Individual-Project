package edu.ucalgary.oop;

public class FamilyRelation {
    private DisasterVictim personOne, personTwo;
    private String relation;

    public FamilyRelation(DisasterVictim personOne, DisasterVictim personTwo, String relation) {
        setPersonOne(personOne);
        setPersonTwo(personTwo);
        setRelation(relation);
    }

    public DisasterVictim getPersonOne() {
        return personOne;
    }

    public void setPersonOne(DisasterVictim personOne) {
        if (personOne == null) {
            throw new IllegalArgumentException("Person cannot be null");
        } else if (personOne.equals(personTwo)) {
            throw new IllegalArgumentException("Person cannot be related to themselves");
        }

        this.personOne = personOne;
    }

    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    public void setPersonTwo(DisasterVictim personTwo) {
        if (personTwo == null) {
            throw new IllegalArgumentException("Person cannot be null");
        } else if (personTwo.equals(personOne)) {
            throw new IllegalArgumentException("Person cannot be related to themselves");
        }

        this.personTwo = personTwo;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FamilyRelation) {
            FamilyRelation other = (FamilyRelation) obj;
            return (personOne.equals(other.getPersonOne()) && personTwo.equals(other.getPersonTwo()))
                    || (personOne.equals(other.getPersonTwo()) && personTwo.equals(other.getPersonOne()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(personOne.getAssignedSocialID()).hashCode()
                ^ Integer.valueOf(personTwo.getAssignedSocialID()).hashCode();
    }
}