package opixels.storage;

import opixels.model.GlobalStats;
import opixels.model.User;
import opixels.model.UserStats;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DataManager {

    private static final String USERS_FILE = "users.ser";
    private static final String STATS_FILE = "stats.ser";
    private static final String GLOBAL_FILE = "global.ser";

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, UserStats> userStats = new HashMap<>();
    private GlobalStats globalStats = new GlobalStats();
    private User currentUser;

    public DataManager() {
        loadAll();
    }

    @SuppressWarnings("unchecked")
    private void loadAll() {
        Object loaded = loadObject(USERS_FILE);
        if (loaded instanceof Map) {
            users.putAll((Map<String, User>) loaded);
        }

        loaded = loadObject(STATS_FILE);
        if (loaded instanceof Map) {
            userStats.putAll((Map<String, UserStats>) loaded);
        }

        loaded = loadObject(GLOBAL_FILE);
        if (loaded instanceof GlobalStats) {
            globalStats = (GlobalStats) loaded;
        }
    }

    private Object loadObject(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private void saveObject(String filename, Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Greska pri spremanju " + filename + ": " + e.getMessage());
        }
    }

    public void saveAll() {
        saveObject(USERS_FILE, users);
        saveObject(STATS_FILE, userStats);
        saveObject(GLOBAL_FILE, globalStats);
    }

    public String register(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return "Korisnicko ime je obavezno.";
        }
        if (users.containsKey(user.getUsername())) {
            return "Korisnicko ime vec postoji.";
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return "Lozinka je obavezna.";
        }
        users.put(user.getUsername(), user);
        userStats.put(user.getUsername(), new UserStats());
        saveAll();
        return null;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UserStats getCurrentUserStats() {
        if (currentUser == null) {
            return null;
        }
        return userStats.get(currentUser.getUsername());
    }

    public GlobalStats getGlobalStats() {
        return globalStats;
    }

    public void recordFlipCoin(boolean win) {
        UserStats stats = getCurrentUserStats();
        if (stats == null) {
            return;
        }
        stats.recordFlipCoin(win);
        globalStats.recordFlipCoin(win);
        saveAll();
    }

    public void recordSpeedClicker(int clicks) {
        UserStats stats = getCurrentUserStats();
        if (stats == null) {
            return;
        }
        stats.recordSpeedClicker(clicks);
        globalStats.recordSpeedClicker(clicks);
        saveAll();
    }

    public void recordRockPaperScissors(String result) {
        UserStats stats = getCurrentUserStats();
        if (stats == null) {
            return;
        }
        stats.recordRockPaperScissors(result);
        globalStats.recordRockPaperScissors(result);
        saveAll();
    }

    public void recordGuessNumber(boolean win, int attempts) {
        UserStats stats = getCurrentUserStats();
        if (stats == null) {
            return;
        }
        stats.recordGuessNumber(win, attempts);
        globalStats.recordGuessNumber(win);
        saveAll();
    }
}
