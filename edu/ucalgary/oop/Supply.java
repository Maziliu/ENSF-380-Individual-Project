package edu.ucalgary.oop;

import java.util.Objects;

/**
 * Represents a supply item with a type and quantity.
 */
public class Supply {
    private String type;
    private int quantity;

    /**
     * Constructs a new Supply object with the specified type and quantity.
     *
     * @param type     the type of the supply
     * @param quantity the quantity of the supply
     */
    public Supply(String type, int quantity) {
        setType(type);
        setQuantity(quantity);
    }

    /**
     * Gets the quantity of the supply.
     *
     * @return the quantity of the supply
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the supply.
     *
     * @param quantity the new quantity of the supply
     * @throws IllegalArgumentException if the quantity is negative
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    /**
     * Gets the type of the supply.
     *
     * @return the type of the supply
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the supply.
     *
     * @param type the new type of the supply
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Decrements the quantity of the supply by the specified amount.
     *
     * @param quantity the amount to decrement the quantity by
     * @throws IllegalArgumentException if the quantity is zero or if the quantity
     *                                  would become negative
     */
    public void decrementQuantity(int quantity) {
        if (quantity == 0 || this.quantity - quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity -= quantity;
    }

    /**
     * Increments the quantity of the supply by the specified amount.
     *
     * @param quantity the amount to increment the quantity by
     * @throws IllegalArgumentException if the quantity is negative
     */
    public void incrementQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity += quantity;
    }

    /**
     * Checks if this supply is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Supply) {
            Supply other = (Supply) obj;
            return type.equals(other.getType());
        }
        return false;
    }

    /**
     * Generates a hash code for this supply.
     *
     * @return the hash code value for this supply
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    /**
     * Returns a string representation of this supply.
     *
     * @return a string representation of this supply
     */
    @Override
    public String toString() {
        return type + " (" + quantity + ")";
    }
}