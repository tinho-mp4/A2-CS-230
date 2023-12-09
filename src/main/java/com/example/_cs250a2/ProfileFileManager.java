package com.example._cs250a2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;

public class ProfileFileManager {

    public static void saveAllProfiles(List<Profile> profiles) {
        String directoryPath = "src/main/resources/com/example/_cs250a2/Profiles";
        String filePath = directoryPath + "/allProfiles.ser";

        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(directoryPath));

            // Convert List to ArrayList
            ArrayList<Profile> profilesList = new ArrayList<>(profiles);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(profilesList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Profile> loadAllProfiles() {
        String directoryPath = "src/main/resources/com/example/_cs250a2/Profiles";
        String filePath = directoryPath + "/allProfiles.ser";

        List<Profile> loadedProfiles = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            loadedProfiles = (ArrayList<Profile>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No profiles found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return loadedProfiles;
    }

    public static void printAllProfiles(List<Profile> profiles) {
        System.out.println("All Profiles:");

        for (Profile profile : profiles) {
            System.out.println("Name: " + profile.getName());
            System.out.println("Level Reached: " + profile.getLevelReached());
            // Add more information as needed
            System.out.println("------------------------");
        }
    }
}