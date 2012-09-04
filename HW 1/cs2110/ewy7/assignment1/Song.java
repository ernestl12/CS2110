package cs2110.ewy7.assignment1;

public class Song {

  public String getName() {
    return null;
  }

  public String getArtist() {
    return null;
  }

  public int getID() {
    return 0;
  }

  /**
   * Returns the time this song was last played by the given station.
   * @param stationID The ID of the given station
   * @return the time this song was last played by the given station
   */
  public int getLastHeardByStation(int stationID) {
    return 0;
  }
  
  /**
   * Returns the time this song was last played by any station.
   * @return The time this song was last played by any station
   */
  public int getLastPlayTime() {
    return 0;
  }

  public Song(String name, String artist, int ID, Station[] stations) {
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
    return null;
  }

  public int getLastPlayed(int stationID) {
    return 0;
  }  
}
