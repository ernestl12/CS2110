package cs2110.ewy7.assignment1;

public class Song {

  private String _name, _artist;
  private int _ID;
  private Station[] _stations;
  private int[] _plays;
    
  public Song(String name, String artist, int ID, Station[] stations) {
    _name = name;
    _artist = artist;
    _ID = ID;
    _stations = stations;
    _plays = new int[0];
  }
  
  public String getName() {
    return _name;
  }

  public String getArtist() {
    return _artist;
  }

  public int getID() {
    return _ID;
  }

  /**
   * Returns the time this song was last played by the given station.
   * @param stationID The ID of the given station
   * @return the time this song was last played by the given station
   */
  public int getLastHeardByStation(int stationID) {
    int lastPlayed = 0;
    
    return 0;
  }
  
  /**
   * Returns the time this song was last played by any station.
   * @return The time this song was last played by any station
   */
  public int getLastPlayTime() {
    return 0;
  }

  public void addPlay(int stationId, int time) {
  }

  /* The returned array should be in this order:
   * [ avg plays on each station that carries it |
   *   total number of plays across all stations |
   *   this song is played most often on this station | 
   *   max plays on one station |
   *   this song is played least often on this station |
   *   min plays on one station |
   * ]
   */
  public int[] getStatistics() {
    int avg, plays, mostPlayedStationID, maxPlays, leastPlayedStationID, minPlays;
    
    avg = 0;
    plays = 0;
    mostPlayedStationID = 0;
    maxPlays = 0;
    leastPlayedStationID = 0;
    minPlays = 0;
    
    int[] stats = {avg, plays, mostPlayedStationID, maxPlays, leastPlayedStationID, minPlays};
    
    
    
    return stats;
  }

  public int getLastPlayed(int stationID) {
    return 0;
  }
  
  @Override
  public String toString() {
    return _ID + ". " + _artist + " - " + _name;
  }
}
