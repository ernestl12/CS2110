package cs2110.ewy7.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// import java.util.Arrays;

public class Parser {  
  
  private String defaultPath;
  // private int numSongs, numStations;
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
    defaultPath = path;
    
    File songFile = new File(path, songFilename);
    File stationFile = new File(path, stationFilename);
    
    try {
      
      // Following code rolled out rather than placed in a function due to
      // casting difficulties (i.e., function would have to operate on and
      // return a generic Object[] variable, requiring up & downcasting
      Scanner songReader = new Scanner(songFile);
      Scanner stationReader = new Scanner(stationFile);
      
      int numSongs = Integer.parseInt(songReader.nextLine());
      int numStations = Integer.parseInt(stationReader.nextLine());
      
      int[] songIDs = new int[numSongs]; 
      int[] stationIDs = new int[numStations];
      
      Song[] tempSongs = new Song[numSongs];
      Station[] tempStations = new Station[numStations];
      
      // Find max ID numbers for Songs & Stations
      for (int songNum = 0; songNum < numSongs; songNum++) {
        tempSongs[songNum] = parseOneSong(songReader.nextLine(), 0);
        songIDs[songNum] = tempSongs[songNum].getID();
      }
      for (int stationNum = 0; stationNum < numStations; stationNum++) {
        tempStations[stationNum] = parseOneStation(stationReader.nextLine());
        stationIDs[stationNum] = tempStations[stationNum].getID();
      }
      
      // Sorting implementation with java.util.Arrays
      /* Arrays.sort(songIDs);
       * Arrays.sort(stationsIDs);
       * 
       */
      
      int maxSongID = 0;
      int maxStationID = 0;
      
      for (int a : songIDs) {
        if (a > maxSongID) {
          maxSongID = a;
        }
      }
      for (int a : stationIDs) {
        if (a > maxStationID) {
          maxStationID = a;
        }
      }
      
      songs = new Song[maxSongID];
      stations = new Station[maxStationID];
      
      for (Song a : tempSongs) {
        songs[a.getID() - 1] = a;
      }
      for (Station b : tempStations) {
        stations[b.getID() - 1] = b;
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
    File logFile = new File(defaultPath, logFilename);
    
    int logTime = 0;
    
    try {
      Scanner logReader = new Scanner(logFile);
      
      // TODO: Implement Log parsing method
      while (logReader.hasNext()) {
        logReader.next();
        logTime++;
      }
      
      logReader.close();
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFoundException: " + e.getMessage());
      return curTime;
    }
    
    return logTime + curTime;
  }

  public Station parseOneStation(String line) {
    String[] data = line.split(";");
    
    // Didn't use for (String a : data) because it wasn't changing the values in the array
    for (int i = 0; i < data.length; i++) {
      data[i] = data[i].trim();
    }
    
    String name = data[0];
    int ID = Integer.parseInt(data[1]);
    
    Station parsedStation = new Station(name, ID);
    
    return parsedStation;
  }

  public Song parseOneSong(String line, int numberOfStations) {
    String[] data = line.split(";");
    for (int i = 0; i < data.length; i++) {
      data[i] = data[i].trim();
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