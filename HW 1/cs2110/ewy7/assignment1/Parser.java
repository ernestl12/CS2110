package cs2110.ewy7.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {  
  
  private Song[] songs;
  private Station[] stations;
  
  /* We are given the following format for 
   * 
   * station files:
   *    Total number of stations
   *     station1 name ;station1id
   *     station2 name ;station2id
   * Spaces can surround name but they should be trimmed.
   * IDs are in no particular order.
   * 
   * song files:
   *    Total number of songs
   *     song1 name ; song1 artist ;songid1 
   *     song2 name ; song2 artist ;songid2 
   * Spaces can surround name or artist (or both)
   * but they should be trimmed.
   * IDs are in no particular order.
   */
  public Parser(String path, String songFilename, String stationFilename) {
    File songFile = new File(path, songFilename);
    File stationFile = new File(path, stationFilename);
    
    try {
      Scanner songReader = new Scanner(songFile);
      Scanner stationReader = new Scanner(stationFile);
      
      int numSongs = songReader.nextInt();
      int numStations = stationReader.nextInt();
      
      songs = new Song[numSongs];
      stations = new Station[numStations];
      
      for (Song a : songs) {
        a = parseOneSong(songReader.next(), 0);
      }
      for (Station a : stations) {
        a = parseOneStation(stationReader.next());
      }
      
      songReader.close();
      stationReader.close();
    }
    catch (FileNotFoundException e) {
      System.err.println("FileNotFoundException: " + e.getMessage());
    }
  }

  /* playlist files:
   *     stationid1;songid1
   *     stationid1;songid2
   * They are in no particular order, Total numbers are not given; read
   * till end of file. Songs may be repeated
   */

  /**
   * Processes the song log from the given file name.
   * The file is assumed to be in the same path as given when
   * the parser was constructed.
   * Returns the current time after the log is processed.
   * For instance, if given curTime is 1 and the log contain N plays,
   * the returned time should be N+1.
   * @param logFilename The name of the play log
   * @param curTime The current time of the recommendation system
   * @return The current time after importing the log
   */
  public int processSongLog(String logFilename, int curTime) {
    return 0;
  }

  public Station parseOneStation(String line) {
    String[] data = line.split(";");
    for (String a : data) {
      a = a.trim();
    }
    
    String name = data[0];
    int ID = Integer.parseInt(data[1]);
    
    Station parsedStation = new Station(name, ID);
    
    return parsedStation;
  }

  public Song parseOneSong(String line, int numberOfStations) {
    String[] data = line.split(";");
    for (String a : data) {
      a = a.trim();
    }
    
    String name = data[0];
    String artist = data[1];
    int ID = Integer.parseInt(data[2]);
    
    Song parsedSong = new Song(name, artist, ID, new Station[numberOfStations]);
    
    return parsedSong;
  }

  public Song[] getSongs() {
    return songs;
  }

  public Station[] getStations() {
    return stations;
  }

}