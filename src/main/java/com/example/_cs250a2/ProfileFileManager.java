package com.example._cs250a2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles the saving, loading, and printing of profiles to and from a file.
 * This class provides static methods to manage the persistence of profile data.
 *
 * @author Ben
 * @version 1.0
 */
public class ProfileFileManager {

    /**
     * Saves all profiles to a file.
     *
     * @param profiles The list of profiles to be saved.
     */
    public static void saveAllProfiles(final List<Profile> profiles) {
        String directoryPath = "src/main/resources/com/example/_cs250a2/Profiles";
        String filePath = directoryPath + "/allProfiles.ser";

        try {
            Files.createDirectories(Paths.get(directoryPath));
            ArrayList<Profile> profilesList = new ArrayList<>(profiles);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(profilesList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all profiles from a file.
     *
     * @return A list of loaded profiles.
     */
    @SuppressWarnings("unchecked") // Suppresses unchecked cast warning
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

    /**
     * Prints all profiles to the console.
     *
     * @param profiles The list of profiles to be printed.
     */
    public static void printAllProfiles(List<Profile> profiles) {
        System.out.println("All Profiles:");
        for (Profile profile : profiles) {
            System.out.println("Name: " + profile.getName());
            System.out.println("Level Reached: " + profile.getLevelReached());
            System.out.println("------------------------");
        }
    }
}
