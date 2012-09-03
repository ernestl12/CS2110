
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {	
	/* We are given the following format for 
	 * 
	 * station files:
	 *    Total number of stations
	 * 	  station1 name ;station1id
	 * 	  station2 name ;station2id
	 * Spaces can surround name but they should be trimmed.
	 * IDs are in no particular order.
	 * 
	 * song files:
	 * 	  Total number of songs
	 *    song1 name ; song1 artist ;songid1 
	 *    song2 name ; song2 artist ;songid2 
	 * Spaces can surround name or artist (or both)
	 * but they should be trimmed.
	 * IDs are in no particular order.
	 */
	public Parser(String path, String songFilename, String stationFilename){
	}

	/* playlist files:
	 * 		stationid1;songid1
	 * 		stationid1;songid2
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

	public Station parseOneStation(String line){
		return null;
	}

	public Song parseOneSong(String line, int numberOfStations){
		return null;
	}

	public Song[] getSongs() {
		return null;
	}

	public Station[] getStations() {
		return null;
	}

}
