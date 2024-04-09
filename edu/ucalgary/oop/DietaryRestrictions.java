package edu.ucalgary.oop;

/**
 * The DietaryRestrictions enum represents different dietary restrictions.
 * Each restriction is represented by a code and a description.
 */
public enum DietaryRestrictions {
    AVML("AVML"), // Asian Vegetarian Meal
    DBML("DBML"), // Diabetic Meal
    GFML("GFML"), // Gluten Intolerant Meal
    KSML("KSML"), // Kosher Meal
    LCML("LCML"), // Low Calorie Meal
    LSML("LSML"), // Low Salt Meal
    MOML("MOML"), // Muslim Meal
    PFML("PFML"), // Peanut Free Meal
    VGML("VGML"), // Vegetarian Meal
    VJML("VJML"); // Vegan Meal

    private final String description;

    /**
     * Constructs a DietaryRestrictions enum with the given description.
     * 
     * @param description the description of the dietary restriction
     */
    DietaryRestrictions(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the dietary restriction.
     * 
     * @return the description of the dietary restriction
     */
    @Override
    public String toString() {
        return description;
    }
}
