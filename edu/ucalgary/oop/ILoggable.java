package edu.ucalgary.oop;

import java.util.ArrayList;

public interface ILoggable {
    public String generateLog();
    public void appendDetails(String details);
    public void saveToDatabase();
    public void loadFromDatabase();
    public void logQueries(ArrayList<String> queries);
}
