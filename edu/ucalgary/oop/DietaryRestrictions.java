package edu.ucalgary.oop;

public enum DietaryRestrictions {
    AVML("AVML"),
    DBML("DBML"),
    GFML("GFML"),
    KSML("KSML"),
    LCML("LCML"),
    LSML("LSML"),
    MOML("MOML"),
    PFML("PFML"),
    VGML("VGML"),
    VJML("VJML");

    private final String description;

    DietaryRestrictions(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
