package edu.ucalgary.oop;

/**
 * The ILoggable interface represents the interface for classes that can
 * generate logs and save them to a database.
 */
public interface ILoggable {
    String generateLog();

    void saveToDatabase();
}
