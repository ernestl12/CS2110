package cs2110.ewy7.assignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RadioRecommenderSystem {
  
  private Song[] songList;
  private Station[] stationList;

  /**
   * Initializes the Parser and the RadioRecommenderSystem. Asks for user input through the console afterwards.
   * Should keep asking for input indefinitely. The user can input the following commands:
   *   importlog <log filename>           - Imports a play log appends it to the current log. 
   *   similarsong <song ID>              - Finds the most similar song to the given song.
   *   similarradio <station ID>          - Finds the most similar radio station to the given station.
   *   stats <song ID>                    - Prints statistics of the given song.
   *   lastheardon <station ID> <song ID> - Finds the most recent time the song is played on the station.
   *   lastplayed <song ID>               - Finds the most recent time the song is played on any station.
   *   recommend <station ID>             - Recommends a song to the given station.
   *   exit                               - Exits the program.
   * 
   * @param args The first argument should contain the folder path for the three files. 
   */
  public static void main(String[] args) {
    String filepath = args[0];
    
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    String[] recognizedCommands = {"", "importlog", "similarsong", "similarradio", 
        "stats", "lastheardon", "lastplayed", "recommend", "exit", "quit", "help", "h"};
    
    System.out.print("Welcome to the Radio Recommender System!\n\n" +
                     "Before we begin, I'll need to import your songs, radio stations, and play history.\n\n" +
                     "What's the name of your song file?\n> ");
    
    try {
      String songFilename = inputReader.readLine();
      
      System.out.print("\nAnd what's the name of your stations file?\n> ");
      String stationFilename = inputReader.readLine();
      
      Parser logParser = new Parser(filepath, songFilename, stationFilename);
      
      RadioRecommenderSystem recommender = 
          new RadioRecommenderSystem(logParser.getSongs(), logParser.getStations());
      
      String userInput = "";
      userInput = initializeSystem(inputReader);
      while (!(userInput.equalsIgnoreCase("quit") ||
               userInput.equalsIgnoreCase("exit") || 
               userInput.equalsIgnoreCase("q")    )) {
        
        String[] input = userInput.split(" ");
        String command = input[0];
        
        if (command.equalsIgnoreCase("importlog")) {
        } else if (command.equalsIgnoreCase("similarsong")) {
        } else if (command.equalsIgnoreCase("similarradio")) {
        } else if (command.equalsIgnoreCase("stats")) {
        } else if (command.equalsIgnoreCase("lastheardon")) {
        } else if (command.equalsIgnoreCase("lastplayed")) {
        } else if (command.equalsIgnoreCase("recommend")) {
        } else if (command.equalsIgnoreCase("similarsong")) {
        } else if (command.equalsIgnoreCase("similarsong")) {
        } else if (command.equalsIgnoreCase("similarsong")) {
        }
        try {
          System.out.print("> ");
          userInput = inputReader.readLine();
        } catch (IOException e) {
          System.err.println("IOException: " + e.getMessage());
          userInput = "";
        }
      }
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
    }
  }
  
  private static String initializeSystem(BufferedReader reader) {
    System.out.println("\nProcessing Complete.");
    
    try {
      printHelpMessage();
      
      System.out.print("> ");
      String userInput = reader.readLine();
      return userInput;
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
      return "";
    }
  }
  
  private static void printHelpMessage() {
    System.out.println("Here's the list of commands this system recognizes:\n" +
                       "importlog <log filename>             Imports a play log appends it to the current log.\n" +
                       "similarsong <song ID>                Finds the most similar song to the chosen song.\n" +
                       "similarradio <station ID>            Finds the most similar radio station to the chosen station.\n" +
                       "stats <song ID>                      Prints statistics of the chosen song.\n" +
                       "lastheardon <station ID> <song ID>   Finds the most recent time the song is played on the station.\n" +
                       "lastplayed <song ID>                 Finds the most recent time the song is played on any station.\n" +
                       "recommend <station ID>               Recommends a song to the chosen station.\n" +
                       "exit, quit                           Exits the program.\n\n" +
                       "To display this message again, type \"help\"\n.");
  }

  /**
   * Constructor. Should store a local copy of the arrays.
   * @param songs
   * @param stations
   */
  public RadioRecommenderSystem(Song[] songs, Station[] stations) {
    songList = songs;
    stationList = stations;
  }

  /**
   * Returns the current time, which is one more than the total number of plays.
   * @return The current time of this recommendation system.
   */
  public int getCurTime() {
    return 0;
  }

  /**
   * Sets the current time of this recommendation system.
   * @param curTime The time to be set
   */
  public void setCurTime(int curTime) {
  }

  /**
   * Returns the song which is most similar to the song with the corresponding songID
   * @param songID ID of the original song
   * @return Most similar song
   * @throws InsufficientSongsException
   * @throws IncorrectSongIDException
   */
  public Song closestSong(int songID) throws InsufficientSongsException, IncorrectSongIDException {
    return null;
  }

  /**
   * Computes the similarity of two given songs
   * @param s1 First song
   * @param s2 Second song
   * @return Double representing the similarity between the songs
   */
  public double songSimilarity(Song s1, Song s2) {
    return 0;
  }

  /**
   * Returns the station which is most similar to the station with the corresponding radioID
   * @param radioID ID of the original radio station
   * @return Most similar radio station
   * @throws InsufficientStationsException
   * @throws IncorrectStationIDException
   */
  public Station closestStation(int radioID)  throws InsufficientStationsException, IncorrectStationIDException {
    return null;
  }

  /**
   * Computes the similarity of two given stations
   * @param s1 First station
   * @param s2 Second station
   * @return Double representing the similarity between the stations
   */
  public double stationSimilarity(Station s1, Station s2) {
    return 0;
  }

  /**
   * Gets the song with the highest recommendation for the given station.
   * @param radioID ID of the station for which we want a recommendation.
   * @return Song which is most highly recommended for this station.
   * @throws InsufficientStationsException
   * @throws IncorrectStationIDException
   */
  public Song bestRecommendation(int radioID) throws InsufficientStationsException, IncorrectStationIDException {
    return null;
  }

  /**
   * Computes the recommendation of a given song to a particular radio station
   * @param radioID ID of station being recommended to
   * @param recSongID Recommended song ID
   * @return Value indicating how highly recommended is the song
   * @throws IncorrectStationIDException
   * @throws IncorrectSongIDException
   */
  public double makeRecommendation(int radioID, int recSongID) throws IncorrectStationIDException, IncorrectSongIDException {
    return 0;
  }

  /**
   * Returns the most recent time the given song is played on the given station.
   * @param radioID ID of station
   * @param songID ID of song
   * @return The most recent time the given song is played on the given station,
   * or 0 if this song was never played on this station.
   * @throws IncorrectStationIDException
   * @throws IncorrectSongIDException
   */
  public int lastHeardByStation(int radioID, int songID) throws IncorrectStationIDException, IncorrectSongIDException {
    return 0;
  }

  /**
   * Returns the most recent time the given song is played on any station.
   * @param songID ID of song
   * @return The most recent time the given song is played on any station,
   * or 0 if this song was never played.
   * @throws IncorrectSongIDException
   */
  public int lastPlayed(int songID) throws IncorrectSongIDException {
    return 0;
  }  

  public class IncorrectSongIDException extends Exception {
    private static final long serialVersionUID = 1L;
    public int wrongID;

    public IncorrectSongIDException(int ID) {
      super("Song with ID #" + ID + " doesn't exist.");
      wrongID = ID;
    }
  }

  public class IncorrectStationIDException extends Exception {
    private static final long serialVersionUID = 1L;
    public int wrongID;

    public IncorrectStationIDException(int ID) {
      super("Station with ID #" + ID + " doesn't exist.");
      wrongID = ID;
    }
  }

  public class InsufficientSongsException extends Exception {
    private static final long serialVersionUID = 1L;

    public InsufficientSongsException() {
      super("Insufficient number of songs required for operation.");
    }
  }

  public class InsufficientStationsException extends Exception {
    private static final long serialVersionUID = 1L;

    public InsufficientStationsException() {
      super("Insufficient number of stations required for operation.");
    }
  }
}
