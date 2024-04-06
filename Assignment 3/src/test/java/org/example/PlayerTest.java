package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    List<Player> players = new ArrayList<>();
    /**
     * The init() method initializes the testing data.
     * Creating a list of 5 player objects assigning each player 1 or 2 achievements and adding them to the list
     */
    @BeforeEach
    void init() {
        Player player1 = new Player("1", "Harry Kane", Country.UNITED_KINGDOM,LocalDate.of(2023,9,1));
        Achievement achievement1 = new Achievement("Premier League Golden Boot", "Awarded to the top goal scorer in the Premier League", LocalDate.of(2021, 5, 23));
        player1.addAchievement(achievement1);

        //Testing  a player that has more than one achievement
        Player player2 = new Player("2", " Lionel Messi", Country.ARGENTINA,LocalDate.of(2023,9,1));
        Achievement achievement2 = new Achievement("FIFA Ballon d'Or", "Awarded to the best male footballer in the world.", LocalDate.of(2019,2,12));
        Achievement achievement3 = new Achievement("La Liga Top Scorer", "Top goal scorer in La Liga.", LocalDate.of(2021,5,23));
        player2.addAchievement(achievement2);
        player2.addAchievement(achievement3);


        Player player3 = new Player("3", "Cristiano Ronaldo", Country.PORTUGAL, LocalDate.of(2023,9,1));
        Achievement achievement4 = new Achievement("UEFA European Championship Golden Boot", "Top scorer of the UEFA European Championship", LocalDate.of(2021, 7, 11));
        player3.addAchievement(achievement4);


        Player player4 = new Player("4", "Nikola Jokić", Country.SERBIA, LocalDate.of(2023,9,1));
        Achievement achievement5 = new Achievement("NBA Most Valuable Player", "Most Valuable Player in the NBA season", LocalDate.of(2021, 6, 8));
        player4.addAchievement(achievement5);


        Player player5 = new Player("5", "Tom Brady", Country.USA, LocalDate.of(2023,9,1));
        Achievement achievement6 = new Achievement("NFL Super Bowl MVP", "Most Valuable Player in the Super Bowl", LocalDate.of(2021, 2, 7));
        player5.addAchievement(achievement6);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
    }

    /**
     * This test method is designed to serialize a list of Player objects to a file named players.ser.
     * Then deserialize the player and compare the deserialized object to the original player object to see if they
     * Match
     */
    @Test
    void testValidSerialisationOfPlayer() {
        //Serialize the objects
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("players.ser"))) {
            for (Player player : players) {
                os.writeObject(player);
            }
        } catch (FileNotFoundException e) {
        System.out.println("File not found during serialization: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("Error while serializing: " + e.getMessage());
    }

        //Deserialize the objects
        List<Player> deserializedPlayers = new ArrayList<>();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("players.ser"))) {
            for (int i = 0; i < players.size(); i++) {
                try {
                    Player player = (Player) is.readObject();
                    deserializedPlayers.add(player);
                } catch (EOFException e) {
                    // End of file breaks the loop
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found: " + e.getMessage());
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found during deserialization: " + e.getMessage());
        }catch (IOException e) {
            System.out.println("Error while deserializing: " + e.getMessage());
        }

        //Compare the original object and its deserialized object
        for (int i = 0; i < players.size(); i++) {
            Player original = players.get(i);
            Player deserialized = deserializedPlayers.get(i);
            System.out.println("Deserialized player: " +  deserializedPlayers.get(i).toString() + "Original Player:" + players.get(i)+ "\n"); //Just printing out the deserialised player toString so I can compare for myself
            //checking if all the players attributes equals the deserialized attributes
            assertEquals(original.toString(), deserialized.toString());
            assertEquals(original.getId(), deserialized.getId());
            assertEquals(original.getUserName(), deserialized.getUserName());
            assertEquals(original.getCountry(), deserialized.getCountry());
            assertEquals(original.getJoinDate(), deserialized.getJoinDate());
        }

    }
}


