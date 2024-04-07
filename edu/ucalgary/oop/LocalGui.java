package edu.ucalgary.oop;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class LocalGui extends AppGui {
    private JPanel localPanel;
    private JMenu menu;

    public LocalGui() {
        createLocalPanel();
        createMenuOptions();
    }

    public void createLocalPanel() {

    }

    public void createMenuOptions() {

    }

    @Override
    public JPanel getPanel() {
        return localPanel;
    }

    @Override
    public JMenu getMenuOptions() {
        return menu;
    }
}
