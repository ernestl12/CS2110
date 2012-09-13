package cs2110.ewy7.assignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RadioRecommenderSystem {
  
  private Song[] _songList;
  private Station[] _stationList;
  private int _currentTime;
  private int[] _songsPlayedPerStation;

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
    
    System.out.print("Welcome to the Radio Recommender System!\n" +
                     "Before we begin, I'll need to import your songs, radio stations, and play history.\n" +
                     "What's the name of your song file?\n> ");
    
    String userInput = "";
    // Set up to loop until song & station files are successfully opened
    // Bottom of this do-while loop set to exit upon successful user input
    do {
      try {
        String songFilename = inputReader.readLine();
        
        System.out.print("\nAnd what's the name of your stations file?\n> ");
        String stationFilename = inputReader.readLine();
        
        Parser logParser = new Parser(filepath, songFilename, stationFilename);
        
        RadioRecommenderSystem recommender = 
            new RadioRecommenderSystem(logParser.getSongs(), logParser.getStations());
        
        boolean playLogProcessed = false;
        userInput = initializeSystem(inputReader);
        while (!(userInput.equalsIgnoreCase("quit") ||
            userInput.equalsIgnoreCase("exit") || 
            userInput.equalsIgnoreCase("q")    )) {
          
          String[] input = userInput.split(" ");
          String command = input[0];
          
          if (command.equalsIgnoreCase("importlog")) {
            if (input.length < 2) {
              System.out.println("Sorry, I don't see a log name to import.");
            } else if (input.length > 2) {
              System.out.println("Sorry, I'm not sure what log you want me to import.");
            } else {
              int result = logParser.processSongLog(input[1], recommender.getCurTime());
              if (result > recommender.getCurTime()) {
                System.out.println("Song log \"" + input[1] + "\" processed.");
                playLogProcessed = true;
              } else {
                System.out.println("Song log \"" + input[1] + "\" could not be processed.");
              }
            }
          }
          else if (command.equalsIgnoreCase("similarsong")) {
            if (!playLogProcessed) {
              System.out.println("I can't do that without a play log.");
            } else if (input.length < 2) {
              System.out.println("Sorry, I'll need a song ID to find a similar song.");
            } else if (input.length > 2) {
              System.out.println("Sorry, I don't know which song you want me find a similar song for.");
            } else {
              try {
                int songID = Integer.parseInt(input[1]);
                Song similarSong = recommender.closestSong(songID);
                Song originalSong = findSong(songID, logParser);
                System.out.println("The most similar song to " + originalSong + " is " + similarSong + ".");
              } catch (NumberFormatException e) {
                System.out.println("I'm confused. What's ID #" + input[1] + "?");
              } catch (InsufficientSongsException e) {
                System.out.println(e.getMessage());
              } catch (IncorrectSongIDException e) {
                System.out.println(e.getMessage());
              }
            }
          }
          else if (command.equalsIgnoreCase("similarradio")) {
            if (!playLogProcessed) {
              System.out.println("I can't do that without a play log.");
            } else if (input.length < 2) {
              System.out.println("Sorry, I'll need a station ID to find a similar station.");
            } else if (input.length > 2) {
              System.out.println("Sorry, I don't know which station you want me find a similar station for.");
            } else {
              try {
                int stationID = Integer.parseInt(input[1]);
                Station similarStation = recommender.closestStation(stationID);
                Station originalStation = findStation(stationID, logParser);
                System.out.println("The most similar station to " + originalStation + " is " + similarStation);
              } catch (NumberFormatException e) {
                System.out.println("I'm confused. What's ID #" + input[1] + "?");
              } catch (InsufficientStationsException e) {
                System.out.println(e.getMessage());
              } catch (IncorrectStationIDException e) {
                System.out.println(e.getMessage());
              }
            }
          }
          else if (command.equalsIgnoreCase("stats")) {
            if (input.length < 2) {
              System.out.println("Sorry, I'll need a song ID to find stats.");
            } else if (input.length > 2) {
              System.out.println("Sorry, I don't know which song you want to find stats for.");
            } else {
              try {
                int songID = Integer.parseInt(input[1]);
                Song targetSong = findSong(songID, logParser);
                if (targetSong == null) {
                  System.out.println("Song with ID #" + songID + " doesn't exist.");
                } else {
                  int[] songStats = targetSong.getStatistics();
                  System.out.println("Stats for " + targetSong + ":\n" +
                      "Average plays: " + songStats[0] + "\n" +
                      "Total plays: " + songStats[1] + "\n" +
                      "Most played on station: " + songStats[2] + " with " + songStats[3] + " plays" + "\n" + 
                      "Least played on station: " + songStats[4] + " with " + songStats[5] + " plays");
                }
              } catch (NumberFormatException e) {
                System.out.println("I'm confused. What's ID #" + input[1] + "?");
              }
            }
          }
          else if (command.equalsIgnoreCase("lastheardon")) {
            if (!playLogProcessed) {
              System.out.println("I can't do that without a play log.");
            } else if (input.length < 3) {
              System.out.println("Sorry, I'll need a song AND station ID to find that.");
            } else if (input.length > 3) {
              System.out.println("Sorry, I only take one song ID and one station ID.");
            } else {
              try {
                int stationID = Integer.parseInt(input[1]);
                int songID = Integer.parseInt(input[2]);
                Station targetStation = findStation(stationID, logParser);
                Song targetSong = findSong(songID, logParser);
                
                if (targetSong == null) {
                  System.out.println("Song with ID #" + songID + " doesn't exist.");
                } else if (targetStation == null) {
                  System.out.println("Station with ID #" + stationID + " doesn't exist.");
                } else {
                  int time = targetSong.getLastPlayed(stationID);
                  // Since Song class's lastPlayed always starts w/ 0, have to check that it's been played on the station
                  if (time == 0 && targetSong.getPlaysByStation(stationID) == 0) {
                    System.out.println(targetSong + " has never been played on " + targetStation + ".");
                  } else {
                    System.out.println(targetSong + " was last played on " + targetStation + " at time " + time + ".");
                  }
                }
              } catch (NumberFormatException e) {
                System.out.println("One of these IDs looks fishy to me, so I'm not following your command.");
              }
            }
          }
          else if (command.equalsIgnoreCase("lastplayed")) {
            if (!playLogProcessed) {
              System.out.println("I can't do that without a play log.");
            } else if (input.length < 2) {
              System.out.println("Sorry, I'll need a song ID to do this.");
            } else if (input.length > 2) {
              System.out.println("Sorry, I don't know which song you want.");
            } else {
              try {
                int songID = Integer.parseInt(input[1]);
                Song targetSong = findSong(songID, logParser);
                if (targetSong == null) {
                  System.out.println("Song with ID #" + songID + " doesn't exist.");
                } else {
                  System.out.println(targetSong + " was last played at time " + targetSong.getLastPlayTime() + ".");
                }
              } catch (NumberFormatException e) {
                System.out.println("Huh? What's ID #" + input[1] + "?");
              }
            }
          }
          else if (command.equalsIgnoreCase("recommend")) {
            if (!playLogProcessed) {
              System.out.println("I can't do that without a play log.");
            } else if (input.length < 2) {
              System.out.println("Sorry, I'll need a song ID to do this.");
            } else if (input.length > 2) {
              System.out.println("Sorry, I don't know which song you want.");
            } else {
              try {
                int stationID = Integer.parseInt(input[1]);
                Station targetStation = findStation(stationID, logParser);
                if (targetStation == null) {
                  System.out.println("Station with ID #" + stationID + " doesn't exist.");
                } else {
                  try {
                    Song recommendation = recommender.bestRecommendation(stationID);
                    System.out.println("I recommend that you play " + recommendation + 
                        " on station " + targetStation + ".");
                  } catch (IncorrectStationIDException e) {
                    System.out.println(e.getMessage());
                  } catch (InsufficientStationsException e) {
                    System.out.println(e.getMessage());
                  }
                }
              } catch (NumberFormatException e) {
                System.out.println("Huh? What's ID #" + input[1] + "?");
              }
            }
          }
          else if (command.equalsIgnoreCase("exit") ||
              command.equalsIgnoreCase("quit") ||
              command.equalsIgnoreCase("q")) {
            continue;
          }
          else if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("h")) {
            printHelpMessage();
          }
          else {
            if (!(command.trim().equalsIgnoreCase(""))) {
              System.out.println("Input unrecognized. Type 'help' for a list of commands.");
            }
          }
          
          try {
            System.out.print("> ");
            userInput = inputReader.readLine();
          } catch (IOException e) {
            System.out.println("Input unrecognized. Type 'help' for a list of commands.");
            userInput = "";
          }
        }
      } catch (IOException e) {
        System.out.println("Whoops! Something went wrong. Let's do that again.");
      }
    } while (userInput.equals(""));
    
    System.out.println("Goodbye!");
  }
  
  private static String initializeSystem(BufferedReader reader) {
    System.out.println("\nProcessing Complete.");
    
    try {
      printHelpMessage();
      
      System.out.print("> ");
      String userInput = reader.readLine();
      return userInput;
    } catch (IOException e) {
      System.out.println("Input unrecognized.");
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
                       "exit, quit, q                        Exits the program.\n" +
                       "To display this message again, type \"help\".");
  }
  
  // Helper functions to find a song or station in the Parser's songs & stations lists
  private static Song findSong(int songID, Parser log) {
    Song targetSong = null;
    for (Song s : log.getSongs()) {
      if (s.getID() == songID) {
        targetSong = s;
      }
    }
    return targetSong;
  }
  
  private static Station findStation(int stationID, Parser log) {
    Station targetStation = null;
    for (Station s : log.getStations()) {
      if (s.getID() == stationID) {
        targetStation = s;
      }
    }
    return targetStation;
  }

  /**
   * Constructor. Should store a local copy of the arrays.
   * @param songs
   * @param stations
   */
  public RadioRecommenderSystem(Song[] songs, Station[] stations) {
    _songList = songs;
    _stationList = stations;
    
    int plays = 0;
    for (Song s : _songList) {
      plays = s.getLastPlayTime() > plays ? s.getLastPlayTime() : plays;
    }
    
    _currentTime = plays++;
    
    _songsPlayedPerStation = new int[_stationList.length];
    for (int i = 0; i < _stationList.length; i++) {
      for (Song s : _songList) {
        _songsPlayedPerStation[i] += s.getPlays()[i];
      }
    }
  }

  /**
   * Returns the current time, which is one more than the total number of plays.
   * @return The current time of this recommendation system.
   */
  public int getCurTime() {
    return _currentTime;
  }

  /**
   * Sets the current time of this recommendation system.
   * @param curTime The time to be set
   */
  public void setCurTime(int curTime) {
    _currentTime = curTime;
  }

  /**
   * Returns the song which is most similar to the song with the corresponding songID
   * @param songID ID of the original song
   * @return Most similar song
   * @throws InsufficientSongsException
   * @throws IncorrectSongIDException
   */
  public Song closestSong(int songID) throws InsufficientSongsException, IncorrectSongIDException {
    if (_songList.length < 2) {
      throw new InsufficientSongsException();
    }
    
    Song original = null;
    for (Song s : _songList) {
      if (s.getID() == songID) {
        original = s;
      }
    }
    
    if (original == null) {
      throw new IncorrectSongIDException(songID);
    }
    
    double maxSimilarity = 0;
    Song mostSimilarSong = null;
    for (Song s : _songList) {
      if (s.getID() == songID) {
        continue;
      }
      double similarity = songSimilarity(original, s);
      if (similarity > maxSimilarity) {
        maxSimilarity = similarity;
        mostSimilarSong = s;
      } else if (similarity == maxSimilarity && s.getID() < mostSimilarSong.getID()) {
        mostSimilarSong = s;
      }
    }
    return mostSimilarSong;
  }

  /**
   * Computes the similarity of two given songs
   * 
   * @pre s1 & s2 must have been played a minimum of 1 time
   * @param s1 First song
   * @param s2 Second song
   * @return Double representing the similarity between the songs
   */
  public double songSimilarity(Song s1, Song s2) {

    int[] s1Plays = s1.getPlays();
    int[] s2Plays = s2.getPlays();
    
    return calculateSimilarity(s1Plays, s2Plays);
  }
  
  /**
   * Helper method to calculate similarity between two arrays of integers.
   * 
   * Based on vector sum similarity algorithm given in PDF.
   * @param s1, s2 int arrays of identical length
   * @return vector similarity between the two arrays, 0 <= t <= 1
   */
  private double calculateSimilarity(int[] s1, int[] s2) {
    double productSum = 0;
    for (int i = 0; i < s1.length; i++) {
      productSum += s1[i] * s2[i];
    }
    
    double s1Distance = 0;
    double s2Distance = 0;
    for (int p : s1) {
      s1Distance += Math.pow(p, 2);
    }
    for (int p : s2) {
      s2Distance += Math.pow(p, 2);
    }
    
    double result = productSum / (Math.sqrt(s1Distance) * Math.sqrt(s2Distance));
    
    return result;
  }

  /**
   * Returns the station which is most similar to the station with the corresponding radioID
   * @param radioID ID of the original radio station
   * @return Most similar radio station
   * @throws InsufficientStationsException
   * @throws IncorrectStationIDException
   */
  public Station closestStation(int radioID)  throws InsufficientStationsException, IncorrectStationIDException {
    if (_stationList.length < 2) {
      throw new InsufficientStationsException();
    }
    
    Station original = null;
    for (Station s : _stationList) {
      if (s.getID() == radioID) {
        original = s;
      }
    }
    
    if (original == null) {
      throw new IncorrectStationIDException(radioID);
    }
    
    double maxSimilarity = 0;
    Station mostSimilarStation = null;
    for (Station s : _stationList) {
      if (s.getID() == radioID) {
        continue;
      }
      double similarity = stationSimilarity(original, s);
      if (similarity > maxSimilarity) {
        maxSimilarity = similarity;
        mostSimilarStation = s;
      } else if (similarity == maxSimilarity && mostSimilarStation != null && s.getID() < mostSimilarStation.getID()) {
        mostSimilarStation = s;
      }
    }
    return mostSimilarStation;
  }

  /**
   * Computes the similarity of two given stations
   * @param s1 First station
   * @param s2 Second station
   * @return Double representing the similarity between the stations
   */
  public double stationSimilarity(Station s1, Station s2) {
    int s1ID = s1.getID();
    int s2ID = s2.getID();
    
    int[] s1Plays = new int[_songList.length];
    int[] s2Plays = new int[_songList.length];
    for (int i = 0; i < _songList.length; i++) {
      s1Plays[i] = _songList[i].getPlaysByStation(s1ID);
      s2Plays[i] = _songList[i].getPlaysByStation(s2ID);
    }
    
    return calculateSimilarity(s1Plays, s2Plays);
  }

  /**
   * Gets the song with the highest recommendation for the given station.
   * @param radioID ID of the station for which we want a recommendation.
   * @return Song which is most highly recommended for this station.
   * @throws InsufficientStationsException
   * @throws IncorrectStationIDException
   */
  public Song bestRecommendation(int radioID) throws InsufficientStationsException, IncorrectStationIDException {
    if (_stationList.length < 2) {
      throw new InsufficientStationsException();
    }
    
    Station targetStation = null;
    for (Station s : _stationList) {
      if (s.getID() == radioID) {
        targetStation = s;
      }
    }
    if (targetStation == null) {
      throw new IncorrectStationIDException(radioID);
    }
    
    Song bestSong = null;
    double maxRecommendationIndex = 0;
    for (Song s : _songList) {
      try {
        double recommendationIndex = makeRecommendation(radioID, s.getID());
        if (recommendationIndex > maxRecommendationIndex) {
          bestSong = s;
          maxRecommendationIndex = recommendationIndex;
        }
      } catch (IncorrectStationIDException e) {
        System.out.println(e.getMessage());
      } catch (IncorrectSongIDException e) {
        System.out.println(e.getMessage());
      }
    }
    
    return bestSong;
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
    Song targetSong = null;
    for (Song s : _songList) {
      if (s.getID() == recSongID) {
        targetSong = s;
      }
    }
    if (targetSong == null) {
      throw new IncorrectSongIDException(recSongID);
    }
    
    Station targetStation = null;
    for (Station s : _stationList) {
      if (s.getID() == radioID) {
        targetStation = s;
      }
    }
    if (targetStation == null) {
      throw new IncorrectStationIDException(radioID);
    }
    
    /* Get data for numerator & denominator.
     * Used two separate index values to ensure continuity of lists when station
     * of ID radioID was encountered, removing duplicates.
     * 
     * In short, the variables below:
     * stationPlays - Times song was played on all station; station with ID radioID not
     *                filtered out
     * stationSim - Similarity between targetStation and any station in _stationLists,
     *              provided the station doesn't have ID radioID
     * avgPlaysPerSong - Average plays per song
     *                   (number of plays on station / number of songs in database)
     */
    int[] stationPlays = targetSong.getPlays();
    
    double[] stationSim = new double[_stationList.length - 1];
    int[] songPlaysOnStationI = new int[_stationList.length - 1];
    double[] avgPlaysPerSong = new double[_stationList.length - 1];
    int stationIndex = 0;
    for (int i = 0; i < _stationList.length; i++) {
      if (_stationList[i].getID() != radioID) {
        stationSim[stationIndex] = stationSimilarity(targetStation, _stationList[stationIndex]);
        songPlaysOnStationI[stationIndex] = stationPlays[i];
        
        int totalPlays = 0;
        for (Song s : _songList) {
          totalPlays += s.getPlaysByStation(_stationList[i].getID());
        }
        avgPlaysPerSong[stationIndex] = totalPlays / (double) _songList.length;
        
        stationIndex++;
      }
    }
    
    // Formula Variables
    int curTime = 0; // N
    for (Song s : _songList) {
      curTime = s.getLastPlayTime() > curTime ? s.getLastPlayTime() : curTime;
    }
    int lastPlayed = targetSong.getLastPlayed(radioID);
    double multFactor = Math.pow(Math.E, -1 / Math.sqrt(curTime + 1 - lastPlayed));
    double avgPlaysOnStation = targetSong.getPlaysByStation(radioID) / ((double) _songList.length);
    
    double numerator = 0;
    double denominator = 0;
    for (int i = 0; i < stationSim.length; i++) {
      numerator += (songPlaysOnStationI[i] - avgPlaysPerSong[i]) * stationSim[i];
      denominator += stationSim[i];
    }
    
    double recommendation = multFactor * (avgPlaysOnStation + numerator / denominator);
    return recommendation;
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
