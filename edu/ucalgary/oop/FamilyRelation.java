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
        this.personOne = personOne;
    }

    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    public void setPersonTwo(DisasterVictim personTwo) {
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
            return (personOne.equals(other.getPersonOne()) && personTwo.equals(other.getPersonTwo())) || (personOne.equals(other.getPersonTwo()) && personTwo.equals(other.getPersonOne()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(personOne.getAssignedSocialID()).hashCode() ^ Integer.valueOf(personTwo.getAssignedSocialID()).hashCode();
    }
}