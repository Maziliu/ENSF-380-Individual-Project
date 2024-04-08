package edu.ucalgary.oop;

import java.sql.Connection;
import java.util.ArrayList;

public interface ILoggable {
    public String generateLog();

    public void saveToDatabase();

    public void loadFromDatabase();

    public void logQueries(ArrayList<String> queries);
}
