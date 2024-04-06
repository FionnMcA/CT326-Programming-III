package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Player with a unique ID, username, country, join date, and a list of achievements.
 */
public class Player implements Serializable {
    private String id;
    private String userName;
    private Country country;
    private LocalDate joinDate;
    private transient List<Achievement> achievements;

    /**
     * Retrieves the player ID.
     *
     * @return the ID of the player.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the player ID.
     *
     * @param id The ID to be set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the player username.
     *
     * @return the username of the player.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the player username.
     *
     * @param userName The username to be set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the player's country.
     *
     * @return the country of the player.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the player's country.
     *
     * @param country The country to be set.
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Retrieves the player's join date.
     *
     * @return the join date of the player.
     */
    public LocalDate getJoinDate() {
        return joinDate;
    }
    /**
     * Sets the player's join date.
     *
     * @param joinDate The join date to be set.
     */
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
    /**
     * Adds an achievement to the player's list of achievements.
     *
     * @param achievement The achievement to be added.
     */
    public void addAchievement(Achievement achievement){
        achievements.add(achievement);
    }

    /**
     * Constructor to initialize a player.
     *
     * @param id       The unique ID of the player.
     * @param userName The username of the player.
     * @param country  The country of the player.
     * @param joinDate The date the player joined.
     */
    public Player(String id, String userName, Country country, LocalDate joinDate) {
        this.id = id;
        this.userName = userName;
        this.country = country;
        this.joinDate = joinDate;
        this.achievements = new ArrayList<>();

    }

    /**
     * Custom serialization method to write the player object.
     *
     * @param os The ObjectOutputStream to write to.
     * @throws IOException If an I/O error occurs.
     */
    private void writeObject(ObjectOutputStream os) throws IOException {
        os.writeUTF(id);
        os.writeUTF(userName);
        os.writeUTF(country.name());
        os.writeUTF(joinDate.toString());

        //dont serialize achievements instead Write the achievements to an CSV file
        BufferedWriter writer = new BufferedWriter(new FileWriter("achievements.csv", true));
        for (Achievement achievement : achievements) {
            writer.write(String.format("%s,%s,%s,%s", this.id, achievement.getAchievementName(), achievement.getDescription(), achievement.getDateOfAward()));
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Custom deserialization method to read the player object.
     *
     * @param is The ObjectInputStream to read from.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class for an object to be read cannot be found.
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        this.setId(is.readUTF());
        this.setUserName(is.readUTF());
        this.setCountry(Country.valueOf(is.readUTF()));
        this.setJoinDate(LocalDate.parse(is.readUTF()));
        this.achievements = new ArrayList<>();

        //read the achievements from the CSV file

        String line;
        BufferedReader reader = new BufferedReader(new FileReader("achievements.csv"));
        while ((line = reader.readLine()) != null) {
            Scanner scan = new Scanner(line);
            scan.useDelimiter(",");
            String playerId = scan.next();
            if (playerId.equals(this.getId())) {
                this.addAchievement(new Achievement(scan.next(), scan.next(), LocalDate.parse(scan.next())));
            }
        }
        reader.close();
    }

    /**
     * Converts the player object to a string representation.
     *
     * @return A string representation of the player.
     */
    @Override
    public String toString() {
        return "Id: " + id + '\n' +
                "userName: " + userName + '\n' +
                "Country: " + country + '\n' +
                "JoinDate: " + joinDate + '\n' +
                "Achievements: " + achievements + '\n';
    }


}