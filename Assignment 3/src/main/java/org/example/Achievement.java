package org.example;

import java.time.LocalDate;
/**
 * Represents an achievement that a player has gotten.
 * Each achievement has a name, description, and the date it was awarded.
 */
public class Achievement {
    private String achievementName;
    private String description;
    private LocalDate dateOfAward;


    /**
     * Retrieves the name of the achievement.
     *
     * @return the achievement name.
     */
    public String getAchievementName() {
        return achievementName;
    }

    /**
     * Retrieves the description of the achievement.
     *
     * @return the achievement description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the date when the achievement was awarded.
     *
     * @return the date of award.
     */
    public LocalDate getDateOfAward() {
        return dateOfAward;
    }

    /**
     *
     * @param achievementName   the name of the achievement.
     * @param description   a description of the achievement.
     * @param dateOfAward   the date the achievement was awarded.
     */
    public Achievement(String achievementName, String description, LocalDate dateOfAward){
        this.achievementName = achievementName;
        this.description = description;
        this.dateOfAward = dateOfAward;
    }

    /**
     * Returns a string representation of the achievement,
     *
     * @return a string representation of the achievement.
     */
    @Override
    public String toString() {
        return "Achievement Name: " + achievementName +
                "\nDescription: " + description +
                "\nDate of Award: " + dateOfAward;
    }

}
