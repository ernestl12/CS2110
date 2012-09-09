package cs2110.ewy7.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {  
  
  private String _filePath;
  private Song[] _songs;
  private Station[] _stations;
  
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
    // Compatible for both Windows & *nix Operating Systems!
    _filePath = path + File.separator;
    
    File stationFile = new File(path, stationFilename);
    File songFile = new File(path, songFilename);
    
    try {
      
      /* Following code rolled out rather than placed in a function due to
       * casting difficulties (i.e., function would have to operate on and
       * return a generic Object[] variable, requiring up & downcasting
       *
       * Stations processed prior to Songs because each Song object must have
       * full access to all the stations.
       */
      Scanner stationReader = new Scanner(stationFile);
      int numStations = Integer.parseInt(stationReader.nextLine());
      _stations = new Station[numStations];
      for (int stationNum = 0; stationNum < numStations; stationNum++) {
        _stations[stationNum] = parseOneStation(stationReader.nextLine());
      }
      
      Scanner songReader = new Scanner(songFile);
      int numSongs = Integer.parseInt(songReader.nextLine());
      _songs = new Song[numSongs];
      for (int songNum = 0; songNum < numSongs; songNum++) {
        _songs[songNum] = parseOneSong(songReader.nextLine(), _stations);
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
    File logFile = new File(_filePath, logFilename);
    
    int logTime = 0;
    
    try {
      Scanner logReader = new Scanner(logFile);
      
      while (logReader.hasNext()) {
        String[] logEntry = logReader.nextLine().split(";");
        int stationID = Integer.parseInt(logEntry[0]);
        int songID = Integer.parseInt(logEntry[1]);
        
        int i = 0; 
        while (i < _songs.length && _songs[i].getID() != songID) {
          i++;
        }
        _songs[i].addPlay(stationID, logTime + curTime);
        
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

  public Song parseOneSong(String line, Station[] stations) {
    String[] data = line.split(";");
    for (int i = 0; i < data.length; i++) {
      data[i] = data[i].trim();
    }
    
    String name = data[0];
    String artist = data[1];
    int ID = Integer.parseInt(data[2]);
    
    Song parsedSong = new Song(name, artist, ID, stations);
    
    return parsedSong;
  }

  public Song[] getSongs() {
    return _songs;
  }

  public Station[] getStations() {
    return _stations;
  }

}