package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;

/**
 * The LocalGui class represents the GUI for the local relief services
 * application. It is responsible for creating the main panel for the local GUI
 * of the
 * relief services application. The LocalGui class is a subclass of AppGui.
 */
public class LocalGui extends AppGui {
    private JPanel localPanel;

    /**
     * Constructs a LocalGui object with the main panel for the local GUI of the
     * relief services application.
     */
    public LocalGui() {
        createLocalPanel();
        createMenuBar();
    }

    /**
     * Creates the main panel for the local GUI of the relief services application.
     */
    private void createLocalPanel() {
        localPanel = new JPanel(new CardLayout());
        localPanel.add(new DisasterVictimsPanel(this), "DisasterVictims");
        localPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Creates the menu bar for the local GUI of the relief services
     * application.This is a method that overrides the createMenuBar method in the
     * AppGui class.
     * 
     * @return the menu bar for the local GUI of the relief services application
     */
    @Override
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu optionsMenu = new JMenu("Options");
        String[] options = { "DisasterVictims" };

        for (String option : options) {
            JMenuItem menuItem = new JMenuItem(option);
            menuItem.addActionListener(e -> switchPanel(option));
            optionsMenu.add(menuItem);
        }

        JMenu switchMenu = new JMenu("Switch GUI");
        JMenuItem toLocalGuiItem = new JMenuItem("Switch to Central GUI");
        toLocalGuiItem.addActionListener(e -> switchToGui(new CentralGui()));
        switchMenu.add(toLocalGuiItem);

        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(switchMenu);
        return menuBar;
    }

    /**
     * Switches the card layout of the main panel to the panel with the given name.
     * 
     * @param panelName the name of the panel to switch to
     */
    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) (localPanel.getLayout());
        cl.show(localPanel, panelName);
    }

    /**
     * Gets the main panel of the local GUI of the relief services application. This
     * is a method that overrides the getMainPanel method in the AppGui class.
     * 
     * @return the main panel of the local GUI of the relief services application
     */
    @Override
    public JPanel getMainPanel() {
        return localPanel;
    }
}
