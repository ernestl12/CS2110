package cs2110.ewy7.assignment1;

public class Song {

  /*
   * Variable explanation for _stationPlays & _lastPlayOnStation
   * _stationPlays - For index i, tracks the number of times this song has been played
   *                 on the station at _stations[i]
   * _lastPlayOnStation - For index i, tracks the last time this song has been played
   *                      on the station at _stations[i]
   */
  private String _name, _artist;
  private int _ID;
  private Station[] _stations;
  private int[] _stationPlays;
  private int[] _lastPlayOnStation;
  private int _lastPlayed;
  
  public Song(String name, String artist, int ID, Station[] stations) {
    _name = name;
    _artist = artist;
    _ID = ID;
    _stations = stations;
    _stationPlays = new int[stations.length];
    _lastPlayOnStation = new int[stations.length];
    _lastPlayed = 0;
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
  
  public int[] getPlays() {
    return _stationPlays;
  }
  
  public int getPlaysByStation(int stationID) {
    int index = 0;
    while (index < _stations.length && _stations[index].getID() != stationID) {
      index++;
    }
    int plays = _stationPlays[index];
    return plays;
  }

  /**
   * Returns the time this song was last played by the given station.
   * @param stationID The ID of the given station
   * @return the time this song was last played by the given station
   */
  public int getLastHeardByStation(int stationID) {
    int index = 0;
    while (index < _stations.length && _stations[index].getID() != stationID) {
      index++;
    }
    int lastTime = _lastPlayOnStation[index];
    return lastTime;
  }
  
  /**
   * Returns the time this song was last played by any station.
   * @return The time this song was last played by any station
   */
  public int getLastPlayTime() {
    return _lastPlayed;
  }
  
  /**
   * Adds a song play to a station & records time this song was last played by this station.
   * 
   * @param stationID ID of the station to add the play to
   * @param time Time that the song was played on this station
   */
  public void addPlay(int stationID, int time) {
    int index = 0;
    while (index < _stations.length && _stations[index].getID() != stationID) {
      index++;
    }
    _stations[index].incrementLogLength();
    _stationPlays[index]++;
    _lastPlayOnStation[index] = time;
    _lastPlayed = time > _lastPlayed ? time : _lastPlayed;
  }

  /**
   * Calculates statistics for given song.
   * 
   * @return Array in this order:
   *  [ int avg plays on each station that carries it |
   *    total number of plays across all stations |
   *    this song is played the most times on this station | 
   *    max plays on one station |
   *    this song is played the least times on this station |
   *    min plays on one station |
   *  ]
   */
  public int[] getStatistics() {
    int avg = 0,
        stationsThatPlayThisSong = 0,
        plays = 0,
        mostPlayedStationID = 0,
        maxPlays = 0,
        leastPlayedStationID = 0,
        minPlays = 1000;
    
    for (int i = 0; i < _stations.length; i++) {
      int stationPlays = _stationPlays[i];
      
      // Add to total plays
      plays += stationPlays;
      
      // Add to count of stations that play this song for average
      if (stationPlays > 0) {
        stationsThatPlayThisSong++;
      }
      
      // If this song has been played more times than the "record" max times, record ID & change maxPlays
      // If tie, see if new stationID is less than the current "most played" stationID
      if (stationPlays > maxPlays) {
        maxPlays = stationPlays;
        mostPlayedStationID = _stations[i].getID();
      } else if (stationPlays == maxPlays && _stations[i].getID() < mostPlayedStationID) {
        mostPlayedStationID = _stations[i].getID();
      }
      
      // If this song has been played less times than the "record" least times, record ID & change minPlays
      // If tie, see if new stationID is greater than the current "least played" stationID
      if (stationPlays < minPlays) {
        minPlays = stationPlays;
        leastPlayedStationID = _stations[i].getID();
      } else if (stationPlays == minPlays && _stations[i].getID() > leastPlayedStationID) {
        leastPlayedStationID = _stations[i].getID();
      }
    }
    
    // Calculate integer average of plays; 0 if song is never played on any station
    avg = stationsThatPlayThisSong == 0 ? 0 : plays / stationsThatPlayThisSong;

    int[] stats = {avg, plays, mostPlayedStationID, maxPlays, leastPlayedStationID, minPlays};
    return stats;
  }

  /** 
   * Returns the last time the song was played on a given station.
   * 
   * Station is assumed to be in the list of stations.
   * Returns 0 if the song was never played on the station.
   * 
   * @param stationID ID of the station to find last playtime
   * @return Last time this song was played on the station
   */
  public int getLastPlayed(int stationID) {
    return getLastHeardByStation(stationID);
  }
  
  @Override
  public String toString() {
    return _ID + ". " + _artist + " - " + _name;
  }
}
