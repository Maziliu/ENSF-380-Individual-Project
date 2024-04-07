package edu.ucalgary.oop;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class DriverApplication {
    private static Connection connection;
    private AppGui appGui = null;

    // Constructor
    public DriverApplication() {
        createConnection();
    }

    // Database connection
    private void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ensf380project", "oop",
                    "ucalgary");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    // Main Frame (Page)
    public JFrame getMainFrame() {
        JFrame frame = new JFrame("Relief Application");
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel cards = new JPanel(new CardLayout());
        cards.add(createWelcomePage(), "Welcome");
        cards.add(new CentralGui().getPanel(), "Central");
        cards.add(new JPanel(), "Location Based");
        // cards.add(createDataGenerationPanel(), "Generate Data");
        cards.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.setJMenuBar(createMenuBar(cards));
        frame.add(cards);

        return frame;
    }

    private JPanel createWelcomePage() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to the Relief Application!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        return welcomePanel;
    }

    private JMenuBar createMenuBar(JPanel cards) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Mode");
        ArrayList<JMenuItem> buttons = new ArrayList<>(Arrays.asList(new JMenuItem("Central"),
                new JMenuItem("Location Based"), new JMenuItem("Generate Data")));

        for (JMenuItem button : buttons) {
            button.addActionListener(e -> {
                switch (button.getText()) {
                    case "Central":
                        appGui = new CentralGui();
                        break;
                    case "Location Based":
                        appGui = new LocalGui();
                        break;
                    case "Generate Data":
                        // appGui = new DataGenerationGui();
                        break;
                    default:
                        appGui = new CentralGui();
                        break;
                }

                if (menuBar.getMenuCount() == 2) {
                    menuBar.remove(1);
                    menuBar.revalidate();
                    menuBar.repaint();
                }

                menuBar.add(appGui.getMenuOptions());
                ((CardLayout) cards.getLayout()).show(cards, button.getText());
            });
            menu.add(button);
        }

        menuBar.add(menu);

        return menuBar;
    }

    // Main method
    public static void main(String[] args) {
        DriverApplication driverApp = new DriverApplication();
        EventQueue.invokeLater(() -> {
            JFrame mainFrame = driverApp.getMainFrame();
            mainFrame.setVisible(true);
        });
    }
}