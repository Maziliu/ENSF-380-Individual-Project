package edu.ucalgary.oop;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class DataGenerationGui {
    private JPanel dataGenerationPanel;
    private static String[] firstNames = {
            "Liam", "Olivia", "Noah", "Emma", "Oliver", "Ava", "Elijah", "Charlotte", "William", "Sophia",
            "James", "Amelia", "Benjamin", "Isabella", "Lucas", "Mia", "Henry", "Harper", "Alexander", "Evelyn",
            "Mason", "Abigail", "Michael", "Emily", "Ethan", "Elizabeth", "Daniel", "Sofia", "Matthew", "Ella",
            "Logan", "Madison", "Jackson", "Avery", "Sebastian", "Scarlett", "David", "Grace", "Carter", "Chloe",
            "Joseph", "Lily", "Mateo", "Riley", "Samuel", "Layla", "Luke", "Penelope", "Gabriel", "Zoey",
            "Owen", "Nora", "Jack", "Lillian", "Wyatt", "Victoria", "Jayden", "Hannah", "Dylan", "Zoe",
            "Grayson", "Stella", "Levi", "Aurora", "Isaac", "Leah", "Christopher", "Violet", "Joshua", "Savannah"
    },
            lastNames = {
                    "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez",
                    "Martinez",
                    "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson",
                    "Martin",
                    "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis",
                    "Robinson",
                    "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
                    "Green",
                    "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts",
                    "Gomez",
                    "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes",
                    "Stewart"
            },
            locations = {
                    "London, UK",
                    "New York City, USA",
                    "Paris, France",
                    "Tokyo, Japan",
                    "Sydney, Australia",
                    "San Francisco, USA",
                    "Hong Kong",
                    "Berlin, Germany",
                    "Moscow, Russia",
                    "Chicago, USA",
                    "Buenos Aires, Argentina",
                    "Sao Paulo, Brazil",
                    "Berlin, Germany",
                    "Madrid, Spain",
                    "Melbourne, Australia",
                    "Beijing, China",
                    "Rome, Italy",
                    "Seoul, South Korea",
                    "Toronto, Canada",
                    "Mexico City, Mexico",
                    "Bangkok, Thailand",
                    "Cairo, Egypt",
                    "Mumbai, India",
                    "Istanbul, Turkey",
                    "Dubai, UAE",
                    "Los Angeles, USA",
                    "Osaka, Japan",
                    "Rio de Janeiro, Brazil",
                    "Jakarta, Indonesia",
                    "Vienna, Austria"
            };

    public DataGenerationGui(String type) {
        switch (type) {
            case "Disaster Victims":
                createDataGenerationPanel();
                break;

            default:
                break;
        }
    }

    public void createDataGenerationPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton generateButton = new JButton("Generate Data");

        generateButton.addActionListener(e -> generateData());
        panel.add(generateButton);

        this.dataGenerationPanel = panel;
    }

    public static void generateData() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Location location = new Location(locations[random.nextInt(locations.length)], "" + i);
            for (int j = 0; j < 10; j++) {
                location.addOccupant(new DisasterVictim(
                        firstNames[random.nextInt(firstNames.length)],
                        lastNames[random.nextInt(lastNames.length)],
                        LocalDate.now().minusYears(j).minusMonths(j).minusDays(j),
                        null,
                        "boy"));
            }
            DriverApplication.locations.add(location);
        }
    }
}
