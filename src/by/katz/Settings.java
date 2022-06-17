package by.katz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashSet;

public class Settings {

    private static Settings instance;
    private static final File SETTINGS_FILE = new File("settings.json");
    private String lastDirectory = "";
    private String lastSymlink = "";
    private String lastTarget = "";

    public static Settings get() { return instance == null ? instance = new Settings() : instance; }

    public static void init() { get().loadSettings(); }

    public synchronized void saveSettings() {
        try (var fw = new FileWriter(SETTINGS_FILE)) {
            fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    public synchronized void loadSettings() {
        if (!SETTINGS_FILE.exists())
            saveSettings();
        try (var fr = new FileReader(SETTINGS_FILE)) {
            instance = new Gson().fromJson(fr, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    public void setLastDirectory(String currentDirectory) {
        lastDirectory = currentDirectory;
        saveSettings();
    }

    public void setLastSymlink(String lastSymlink) {
        this.lastSymlink = lastSymlink;
        saveSettings();
    }

    public void setLastTarget(String lastTarget) {
        this.lastTarget = lastTarget;
        saveSettings();
    }

    public String getLastDirectory() { return lastDirectory; }

    public String getLastSymlink() { return lastSymlink; }

    public String getLastTarget() { return lastTarget; }
}
