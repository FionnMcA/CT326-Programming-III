package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads in music data from text files and displays it GUI format.
 * Based on the data from text files it creates a GUI that displays the albums Cover
 * and when you click on the album cover it will display the track list
 * and provide a back button to go to the main music library.
 *
 * I designed an Album class to store details like artist, album name, and cover file path.
 * Within the Album class, there's a List to store Tracks objects representing each track of the album.
 * And a Tracks class to store details such as track number, name, and length.
 * When processing the music_library.txt looping through each line, it reads the artist, followed by the album name,
 * and then the image path. These values are used to instantiate a new Album object.
 * The last data from a line in music_library.txt is the path to the albums.txt file which contains the tracks information.
 * So it calls a method addTracksToAlbum to read in the tracks of that specific album and creates a track object
 * consisting of the track number, name and length and adds the tracks to the albums trackslist list.
 * When this method is completed the current album is added to an ArrayList of albums
 * It then calls  musicLibraryJFrame() to display the information as a GUI
 */
public class DisplayMusicLibrary extends JFrame {
    private static final List<Album> albums = new ArrayList<>();
    private static String trackNo = "";
    private static String trackName = "";
    private static String trackLength = "";

    /**
     * The main method calls the {@code ReadInMusicLibrary} method.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        ReadInMusicLibrary();
    }

    /**
     * Reads in the music library text file and stores the information in a list of Album objects
     * and adds them to the albums list. It also calls a helper method to add track information to each album.
     * After reading in all the data, it initializes the music library JFrame.
     *
     */
    public static void ReadInMusicLibrary(){
        String artist;
        String albumName;
        String coverFilePath;
        String projectRoot = "/Users/fionnmcardle/Desktop/Assignment4_Fionn_McArdle/";
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/fionnmcardle/Desktop/Assignment4_Fionn_McArdle/music_library.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    artist = scanner.next();
                    albumName = scanner.next();
                    coverFilePath = scanner.next().trim();
                    String fullCoverPath = projectRoot.trim() + coverFilePath;
                    Album album = new Album(artist, albumName, fullCoverPath);
                    String trackFilePath = scanner.next().trim();
                    String fullPath = projectRoot + trackFilePath;

                    addTracksToAlbum(album, fullPath); //addTracksToAlbum reads in the file path of the tracks and adds the track objects to the tracks list of the album class

                    albums.add(album);
                }
            }
            musicLibraryJFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads track information from a file and adds the track objects to the album's track list.
     *
     * This method is called for each Album object to process track information file.
     * It creates Track objects for each track in the file and adds them to the album's track list.
     *
     * @param album           The album to which tracks are to be added.
     * @param coverImagePath  The file path to the text file containing track information.
     */
    public static void addTracksToAlbum(Album album, String coverImagePath){
        try (BufferedReader reader1 = new BufferedReader(new FileReader(coverImagePath))) {
            String line;
            while ((line = reader1.readLine()) != null) {
                Scanner scanner1 = new Scanner(line);
                scanner1.useDelimiter(",\t|,\s|,");
                if (scanner1.hasNext()) {
                    trackNo = scanner1.next();
                }
                if (scanner1.hasNext()) {
                    trackName = scanner1.next();
                }
                if (scanner1.hasNext()) {
                    trackLength = scanner1.next();
                }
                Tracks tracks = new Tracks(trackNo, trackName, trackLength);
                album.addTracks(tracks);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes and displays the main JFrame for the music library.
     * This method creates a JFrame to showcase the album covers from the music library.
     * Each album cover is displayed as a button. When an album cover button is clicked,
     * a new panel displays a table with tracks from the clicked album. There's also a
     * 'Back' button to return to the main music library view with album covers.
     *
     * to display the albums as buttons, it loops through the albums array and called each albums
     * getCoverImagePath()
     * Each button uses setActionCommand(StringValueof()) to ensure each click displayed that
     * specific albums info
     * Then we loop through that albums tracks and call the getTrackNo(), getTrackName() and getTrackLength()
     * of the individual tracks and store them in a 2d array. Then create a String array that hold strings
     * "Track No", "Track Name", "Track Length" and we add the 2D array of track info and the array of
     * Strings to a new JTable.
     * Each JTable contains a back button, we use frame.setContentPane(albumPanel) this will change the main
     * content pane of the frame to display the albumPanel. Use frame.revalidate() so the frame revalidates
     * its layout. Then use frame.repaint() so the frame to redraw its contents.
     * @throws IOException If there's an error reading the album cover images.
     */

    public static void musicLibraryJFrame() throws IOException {
        JFrame frame = new JFrame("Your Music Library");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JPanel albumPanel = new JPanel();
        albumPanel.setLayout(new FlowLayout());

        for (int j = 0; j < 4; j++) {
            String filePath = albums.get(j).getCoverImagePath();
            BufferedImage bi = ImageIO.read(new File(filePath));
            Image dimg = bi.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon i = new ImageIcon(dimg);
            JButton button = new JButton(i);
            button.setActionCommand(String.valueOf(j));

            button.addActionListener(e -> {
                String command = e.getActionCommand();
                int index = Integer.parseInt(command);
                Album clickedAlbum = albums.get(index);
                Object[][] data = new Object[clickedAlbum.getTracks().size()][3];
                for (int i1 = 0; i1 < clickedAlbum.getTracks().size(); i1++) {
                    data[i1][0] = clickedAlbum.getTrack(i1).getTrackNo();
                    data[i1][1] = clickedAlbum.getTrack(i1).getTrackName();
                    data[i1][2] = clickedAlbum.getTrack(i1).getTrackLength();
                }
                String[] columnNames = {"Track No", "Track Name", "Track Length"};
                JTable table = new JTable(data, columnNames);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(600, 600));

                JPanel trackPanel = new JPanel();
                trackPanel.setLayout(new BorderLayout());
                trackPanel.add(scrollPane, BorderLayout.CENTER);

                JButton backButton = new JButton("Back");
                trackPanel.add(backButton, BorderLayout.SOUTH);

                backButton.addActionListener(e1 -> {
                    frame.setContentPane(albumPanel);
                    frame.revalidate();
                    frame.repaint();
                });
                frame.setContentPane(trackPanel);
                frame.revalidate();
                frame.repaint();
            });
            albumPanel.add(button);
        }
        frame.setContentPane(albumPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);  // To center the frame on screen
        frame.setVisible(true);
    }
}

