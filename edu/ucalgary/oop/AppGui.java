package edu.ucalgary.oop;

import javax.swing.*;

/**
 * The abstract class AppGui represents the base class for all GUI classes in
 * the application.
 * It provides common functionality for switching between different GUI screens.
 */
public abstract class AppGui {
    protected static JFrame frame;

    /**
     * Switches the current GUI screen to a new GUI screen.
     * 
     * @param newGui The new GUI screen to switch to.
     */
    protected void switchToGui(AppGui newGui) {
        frame.setContentPane(newGui.getMainPanel());
        frame.setJMenuBar(newGui.createMenuBar());
        frame.validate();
    }

    /**
     * Gets the main panel of the GUI screen.
     * 
     * @return The main panel of the GUI screen.
     */
    public abstract JPanel getMainPanel();

    /**
     * Creates the menu bar for the GUI screen.
     * 
     * @return The menu bar for the GUI screen.
     */
    public abstract JMenuBar createMenuBar();

}
