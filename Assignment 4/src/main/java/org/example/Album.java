package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an album with its details such as artist name, album name, and cover image path.
 * Also contains a list of the albums tracks.
 */
public class Album {
    private String artist;
    private String albumName;
    private String coverImagePath;
    private Tracks tracks;
    private List<Tracks> trackslist = new ArrayList<>();

    /**
     * @param artist The artist of the album.
     * @param albumName The name of the album.
     * @param coverImagePath The path to the cover image of the album.
     */
    public Album(String artist, String albumName, String coverImagePath) {
        this.artist = artist;
        this.albumName = albumName;
        this.coverImagePath = coverImagePath;
    }

    /**
     * @return The path to the cover image of the album.
     */
    public String getCoverImagePath(){
        return coverImagePath;
    }

    /**
     * Get a specific track from the album using its index.
     * @param i The index of the track in the list.
     * @return The track at that index.
     */
    public Tracks getTrack(int i) {
        return trackslist.get(i);
    }

    /**
     * @return The list of tracks in the album.
     */
    public List<Tracks> getTracks() {
        return trackslist;
    }

    /**
     * Add a track to the album.
     * @param tracks The track to be added to album.
     */
    public void addTracks(Tracks tracks){
        trackslist.add(tracks);
    }

    /**
     * @return A string representation of the album with its artist, name, and cover image path.
     */
    public String toString() {
        return "Artist: " + artist + "\nAlbum name: " + albumName + "\nCover Image: " + coverImagePath;
    }
}
