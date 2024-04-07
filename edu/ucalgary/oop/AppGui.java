package edu.ucalgary.oop;

import javax.swing.*;

public abstract class AppGui {
    protected static JFrame frame;

    protected void switchToGui(AppGui newGui) {
        frame.setContentPane(newGui.getMainPanel());
        frame.setJMenuBar(newGui.createMenuBar());
        frame.validate();
    }

    public abstract JPanel getMainPanel();

    public abstract JMenuBar createMenuBar();

}
