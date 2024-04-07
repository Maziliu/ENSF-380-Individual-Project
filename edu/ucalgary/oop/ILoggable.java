package edu.ucalgary.oop;

import java.sql.Connection;
import java.util.ArrayList;

public interface ILoggable {
    public String generateLog();

    public void appendDetails(String details);

    public void saveToDatabase(Connection connection);

    public void loadFromDatabase(Connection connection);

    public void logQueries(ArrayList<String> queries);
}
