package edu.ucalgary.oop;

import java.util.*;

public class Location {
    private String name, address;
    private ArrayList<Supply> supplies;
    private ArrayList<DisasterVictim> occupants;

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
        supplies = new ArrayList<Supply>();
        occupants = new ArrayList<DisasterVictim>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Supply> getSupplies() {
        return supplies;
    }

    public ArrayList<DisasterVictim> getOccupants() {
        return occupants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = supplies;
    }

    public void setOccupants(ArrayList<DisasterVictim> occupants) {
        this.occupants = occupants;
    }

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

    public void addOccupant(DisasterVictim occupant) {
        occupants.add(occupant);
    }

    public void removeSupply(Supply supply) {
        for (Supply sup : supplies) {
            if (sup.equals(supply)) {
                supplies.remove(supply);
                break;
            }
        }
    }

    public void removeOccupant(DisasterVictim occupant) {
        for (DisasterVictim occ : occupants) {
            if (occ.equals(occupant)) {
                occupants.remove(occupant);
                break;
            }
        }
    }

    public void assignSupply(Supply supply, DisasterVictim occupant, int quantity) {
        if (supplies.contains(supply) && occupants.contains(occupant)) {
            occupant.addPersonalBelonging(new Supply(supply.getType(), quantity));
            supply.decrementQuantity(quantity);
            if (supply.getQuantity() == 0) {
                removeSupply(supply);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return name.equals(other.getName()) && address.equals(other.getAddress());
        }
        return false;
    }
}