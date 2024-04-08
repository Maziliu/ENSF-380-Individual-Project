package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalGui extends AppGui {
    private JPanel localPanel;

    public LocalGui() {
        createLocalPanel();
        createMenuBar();
    }

    private void createLocalPanel() {
        localPanel = new JPanel(new CardLayout());
        localPanel.add(new DisasterVictimsPanel(this), "DisasterVictims");
        localPanel.add(getManageLocalResourcesPanel(), "Manage Local Resources");
        localPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu optionsMenu = new JMenu("Options");
        String[] options = { "Manage Local Resources", "DisasterVictims" };

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

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) (localPanel.getLayout());
        cl.show(localPanel, panelName);
    }

    @Override
    public JPanel getMainPanel() {
        return localPanel;
    }

    private JPanel getManageLocalResourcesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        return panel;
    }
}
