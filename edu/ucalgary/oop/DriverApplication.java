package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DriverApplication extends AppGui {
    private static Connection connection;
    private AppGui appGui = null;

    public static ArrayList<DisasterVictim> disasterVictims = new ArrayList<>();
    public static ArrayList<Inquirer> inquirers = new ArrayList<>();
    public static ArrayList<Location> locations = new ArrayList<>();

    public DriverApplication() {
        createConnection();
        DataGenerationGui.generateData();

        frame = new JFrame("Relief Services Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setVisible(true);
        initializeMenuBar();
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu switchMenu = new JMenu("Mode");
        JMenuItem switchToCentralGui = new JMenuItem("Central GUI");
        switchToCentralGui.addActionListener(e -> switchToGui(new CentralGui()));
        JMenuItem switchToLocalGui = new JMenuItem("Local GUI");
        switchToLocalGui.addActionListener(e -> switchToGui(new LocalGui()));

        switchMenu.add(switchToCentralGui);
        switchMenu.add(switchToLocalGui);

        menuBar.add(switchMenu);
        frame.setJMenuBar(menuBar);
    }

    @Override
    public JPanel getMainPanel() {
        return appGui.getMainPanel();
    }

    @Override
    public JMenuBar createMenuBar() {
        return appGui.createMenuBar();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new DriverApplication());
    }
}
