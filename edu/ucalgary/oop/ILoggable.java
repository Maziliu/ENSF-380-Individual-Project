package edu.ucalgary.oop;

import java.util.ArrayList;

public interface ILoggable {
    String generateLog();

    void saveToDatabase();

    void loadFromDatabase();

    void logQueries(ArrayList<String> queries);
}
