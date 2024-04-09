package edu.ucalgary.oop;

import java.util.*;

public class Supply {
    private String type;
    private int quantity;

    public Supply(String type, int quantity) {
        setType(type);
        setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void decrementQuantity(int quantity) {
        if (quantity == 0 || this.quantity - quantity < 0) {
            throw new IllegalStateException("Quantity cannot be negative");
        }
        this.quantity -= quantity;
    }

    public void incrementQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity += quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Supply) {
            Supply other = (Supply) obj;
            return type.equals(other.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return type + " (" + quantity + ")";
    }
}