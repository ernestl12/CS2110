Assignment #1: RadioRecommenderSystem  
Submission for Edwin Young (ewy7)

Help/Assistance received:  
Did not work with anyone on the assignment, and did not seek help beyond asking questions on Piazza.

Variables & methods/functions added:  
  Song:  
    Vars:  
      _stationPlays = int[]  
      _lastPlayOnStation = int[]  
      _lastPlayed = int[]  
    Methods/functions:  
      public getPlays() - returns _stationPlays  
      public getPlaysByStation(stationID) - returns # plays of song on station w/ ID stationID  
  RadioRecommenderSystem:  
    Vars:  
      _songsPlayedPerStation = int[]; contains # of songs played on a certain station  
    Methods/functions:  
      initializeSystem() - Meant to read userInput before while loop can iterate once.  
      printHelpMessage()  
      findSong(int id, Parser) - find specific Song in Parser's song array  
      findStation(int id, Parser)  
      calculateSimilarity(int[] s1, int[] s2) - Split out similarity calculation since it's essentially
                                                identical for songs and stations  

Known issues:
 - Crashes if Song, Station, and Playlog files are not properly formatted
                                                
Good karma changes:
  - Will recognize bad input & print out message
  - Capitalization does not matter on commands, nor does whitespace before, after, & in between tokens
  
For more info, you can find my entire work history @ https://github.com/edwinyoung/CS2110