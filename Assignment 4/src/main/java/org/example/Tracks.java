package org.example;

/**
 * Represents a track within an album and stores its track number, track name, and track length.
 */
public class Tracks {
    private String trackNo;
    private String trackName;
    private  String  trackLength;

    /**
     * Constructor to create a new Tracks instance.
     *
     * @param trackNo The track number.
     * @param trackName The name of the track.
     * @param trackLength The length of the track.
     */
    Tracks(String trackNo, String trackName,  String  trackLength){
        this.trackNo = trackNo;
        this.trackName = trackName;
        this.trackLength = trackLength;
    }

    /**
     * @return The track number.
     */
    public String getTrackNo() {
        return trackNo;
    }

    /**
     * @return The name of the track.
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * @return The length of the track.
     */
    public String getTrackLength() {
        return trackLength;
    }

    /**
     * @return A string representation of the track with its number, name, and length.
     */
    @Override
    public String toString(){
        return trackNo + "., " + trackName + ", " + trackLength;
    }

}
