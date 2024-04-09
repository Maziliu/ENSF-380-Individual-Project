package edu.ucalgary.oop;

import java.util.*;
import java.io.File;
import java.time.LocalDate;

public class DataGenerationGui {
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
            },
            supplies = {
                    "Water", "Food", "Clothing", "Medicine", "Shelter", "Blankets", "Toiletries", "First Aid Kits",
                    "Flashlights", "Batteries", "Generators", "Fuel", "Tents", "Sleeping Bags", "Masks", "Gloves",
                    "Hand Sanitizer", "Soap", "Towels", "Toothbrushes", "Toothpaste", "Feminine Hygiene Products",
                    "Diapers", "Baby Formula", "Pet Food", "Leashes", "Collars", "Toys", "Books", "Games", "Puzzles",
                    "Coloring Books", "Crayons", "Pencils", "Pens", "Paper", "Notebooks", "Backpacks",
                    "School Supplies",
                    "Laptops", "Tablets", "Chargers", "Headphones", "Speakers", "Televisions", "Radios", "Phones",
                    "Chargers", "Batteries", "Power Banks", "Solar Panels", "Cables", "Adapters", "Converters",
                    "Inverters", "Tools", "Nails", "Screws", "Screwdrivers", "Hammers", "Saws", "Drills", "Wrenches",
                    "Pliers", "Tape", "Glue", "Paint", "Brushes", "Rollers", "Tarps", "Ropes", "Chains", "Locks",
                    "Keys", "Bags", "Suitcases", "Backpacks", "Purses", "Wallets", "Money", "Coins", "Cards",
                    "Identification", "Passports", "Visas", "Tickets", "Maps", "Guides", "Compasses", "GPS",
                    "Binoculars", "Cameras", "Film"
            },
            medicalStrings = {
                    "Broken Arm", "Broken Leg", "Broken Rib", "Concussion", "Burn", "Cut", "Bruise", "Sprain", "Strain",
                    "Fracture", "Dislocation", "Tear", "Laceration", "Abrasions", "Puncture", "Bite", "Sting",
                    "Rash", "Infection", "Fever", "Cold", "Flu", "Cough", "Sore Throat", "Headache", "Migraine",
                    "Nausea", "Vomiting", "Diarrhea", "Constipation", "Stomach Ache", "Cramps", "Bloating", "Gas",
                    "Heartburn", "Indigestion", "Acid Reflux", "Ulcer", "Hernia", "Appendicitis", "Gallstones",
                    "Kidney Stones", "Bladder Infection", "UTI", "Prostate Infection", "STD", "STI", "HIV", "AIDS",
                    "Cancer", "Tumor", "Cyst", "Polyp", "Hemorrhoid", "Varicose Vein", "Blood Clot", "Anemia",
                    "Leukemia", "Lymphoma", "Myeloma", "Sickle Cell", "Hemophilia", "Thalassemia", "Diabetes",
                    "Hypoglycemia", "Hyperglycemia", "Thyroid", "Goiter", "Hyperthyroidism", "Hypothyroidism",
                    "Cushing's", "Addison's", "Pituitary", "Adrenal", "Infertility",
                    "Pregnancy", "Miscarriage", "Abortion", "Ectopic Pregnancy", "Placenta", "Umbilical Cord",
                    "Amniotic Fluid", "Fetal", "Neonatal", "Pediatric", "Geriatric", "Obesity", "Anorexia",
                    "Bulimia", "Malnutrition", "Dehydration", "Electrolyte Imbalance", "Vitamin Deficiency",
            };

    private static ArrayList<Supply> generateSupplies() {
        ArrayList<Supply> generatedSupplies = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Supply supply = new Supply(supplies[random.nextInt(supplies.length)], random.nextInt(1000));
            generatedSupplies.add(supply);
            DriverApplication.supplies.add(supply);
        }

        return generatedSupplies;
    }

    private static ArrayList<DisasterVictim> generateVictims() {
        ArrayList<DisasterVictim> victims = new ArrayList<>();
        ArrayList<String> availableGenders = new ArrayList<String>();
        try {
            File genderFile = new File("edu/ucalgary/oop/GenderOptions.txt");
            Scanner sr = new Scanner(genderFile);
            while (sr.hasNextLine()) {
                availableGenders.add(sr.nextLine());
            }
            availableGenders.add("");
            sr.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("File not found");
        }

        Random random = new Random();
        for (int i = 0; i < random.nextInt(10); i++) {
            DisasterVictim victim = new DisasterVictim(
                    firstNames[random.nextInt(firstNames.length)],
                    lastNames[random.nextInt(lastNames.length)],
                    LocalDate.now().minusYears(random.nextInt(100)).minusMonths(random.nextInt(12))
                            .minusDays(random.nextInt(31)),
                    random.nextInt(100),
                    availableGenders.get(random.nextInt(availableGenders.size())));
            victims.add(victim);
            DriverApplication.disasterVictims.add(victim);
        }

        return victims;
    }

    public static void generateData() {

        // Generate Locations and Victims
        for (int i = 0; i < locations.length; i++) {
            Location location = new Location(locations[i], "" + i);

            ArrayList<DisasterVictim> victims = generateVictims();
            for (DisasterVictim victim : victims) {
                location.addOccupant(victim);
            }

            ArrayList<Supply> supplies = generateSupplies();
            for (Supply supply : supplies) {
                location.addSupply(supply);
            }

            DriverApplication.locations.add(location);
        }

        // Add Family Connections
        for (int i = 0; i < 4; i++) {
            Random rand = new Random();
            DisasterVictim victim = null;
            for (DisasterVictim v : DriverApplication.disasterVictims) {
                if (v.getAssignedSocialID() == 0) {
                    victim = v;
                    break;
                }
            }

            DisasterVictim victim2 = DriverApplication.disasterVictims
                    .get(rand.nextInt(DriverApplication.disasterVictims.size()));
            victim.addFamilyConnection(victim2, "sibling");
        }

        // Add Medical Records
        for (DisasterVictim victim : DriverApplication.disasterVictims) {
            Random rand = new Random();
            for (int i = 0; i < rand.nextInt(5); i++) {
                Location location = DriverApplication.locations.get(rand.nextInt(DriverApplication.locations.size()));
                String treatmentDetails = "Treatment for " + medicalStrings[rand.nextInt(medicalStrings.length)];
                String dateOfTreatment = LocalDate.now().minusYears(rand.nextInt(50)).minusMonths(rand.nextInt(12))
                        .minusDays(rand.nextInt(31)).toString();
                MedicalRecord record = new MedicalRecord(location, treatmentDetails, dateOfTreatment);
                victim.addMedicalRecord(record);
            }
        }

        // Add Personal Belongings
        for (DisasterVictim victim : DriverApplication.disasterVictims) {
            Random rand = new Random();
            for (int i = 0; i < rand.nextInt(5); i++) {
                Supply supply = DriverApplication.supplies.get(rand.nextInt(DriverApplication.supplies.size()));
                victim.addPersonalBelonging(supply);
            }
        }

    }
}
