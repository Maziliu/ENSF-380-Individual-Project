package edu.ucalgary.oop;

import java.util.ArrayList;

/**
 * Represents a location where disaster victims are housed. Every location has a
 * name address, a list of supplies, and a list of occupants.
 */
public class Location {
    private String name, address;
    private ArrayList<Supply> supplies;
    private ArrayList<DisasterVictim> occupants;

    /**
     * Constructs a Location object with the given parameters.
     * 
     * @param name    the name of the location
     * @param address the address of the location
     */
    public Location(String name, String address) {
        this.name = name;
        this.address = address;
        supplies = new ArrayList<Supply>();
        occupants = new ArrayList<DisasterVictim>();
    }

    /**
     * Gets the name of the location.
     * 
     * @return the name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the location.
     * 
     * @param name the name of the location
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the location.
     * 
     * @return the address of the location as a string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the location.
     * 
     * @param address the address of the location
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of supplies at the location.
     * 
     * @return the list of supplies at the location
     */
    public ArrayList<Supply> getSupplies() {
        return supplies;
    }

    /**
     * Sets the list of supplies at the location.
     * 
     * @param supplies the list of supplies at the location
     */
    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = supplies;
    }

    /**
     * Gets the list of occupants at the location.
     * 
     * @return the list of occupants at the location
     */
    public ArrayList<DisasterVictim> getOccupants() {
        return occupants;
    }

    /**
     * Sets the list of occupants at the location.
     * 
     * @param occupants the list of occupants at the location
     */
    public void setOccupants(ArrayList<DisasterVictim> occupants) {
        this.occupants = occupants;
    }

    /**
     * Adds a supply to the location. If the supply already exists at the location,
     * the quantity of the supply is incremented. Otherwise, the supply is added to
     * the list of supplies.
     * 
     * @param supply the supply to add
     */
    public void addSupply(Supply supply) {
        if (!supplies.contains(supply)) {
            supplies.add(supply);
        } else {
            for (Supply sup : supplies) {
                if (sup.equals(supply)) {
                    sup.setQuantity(sup.getQuantity() + supply.getQuantity());
                    break;
                }
            }
        }
    }

    /**
     * Adds an occupant to the location. The occupant is of type DisasterVictim.
     * 
     * @param occupant the occupant to add
     */
    public void addOccupant(DisasterVictim occupant) {
        occupants.add(occupant);
    }

    /**
     * Removes a supply from the location if it exists. Does nothing if the supply
     * does not exist.
     * 
     * @param supply the supply to remove
     */
    public void removeSupply(Supply supply) {
        for (Supply sup : supplies) {
            if (sup.equals(supply)) {
                supplies.remove(supply);
                break;
            }
        }
    }

    /**
     * Removes an occupant from the location if they exist. Does nothing if the
     * occupant does not exist.
     * 
     * @param occupant the occupant to remove
     */
    public void removeOccupant(DisasterVictim occupant) {
        for (DisasterVictim occ : occupants) {
            if (occ.equals(occupant)) {
                occupants.remove(occupant);
                break;
            }
        }
    }

    /**
     * Assigns a supply to an occupant. The quantity of the supply is decremented by
     * the quantity assigned to the occupant.
     * If the supply quantity reaches 0, the supply is removed from the location.
     * 
     * @param supply   the supply to assign
     * @param occupant the occupant to assign the supply to
     * @param quantity the quantity of the supply to assign
     */
    public void assignSupply(Supply supply, DisasterVictim occupant, int quantity) {
        if (supplies.contains(supply) && occupants.contains(occupant)) {
            occupant.addPersonalBelonging(new Supply(supply.getType(), quantity));
            supply.decrementQuantity(quantity);
            if (supply.getQuantity() == 0) {
                removeSupply(supply);
            }
        }
    }

    /**
     * Overrides the equals method to compare two Location objects.
     * Two Location objects are the same if they have the same name and address.
     * 
     * @return true if the two Location objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return name.equals(other.getName()) && address.equals(other.getAddress());
        }
        return false;
    }
}