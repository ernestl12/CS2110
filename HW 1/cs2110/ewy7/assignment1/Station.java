package cs2110.ewy7.assignment1;

public class Station {

  private String _name;
  private int _ID;
  private int _logLength;
  
  public Station(String name, int ID) {
    _name = name;
    _ID = ID;
    _logLength = 0;
  }
  
  public String getName() {
    return _name;
  }
  
  public int getID() {
    return _ID;
  }
  
  public int getLogLength() {
    return _logLength;
  }
  
  public void incrementLogLength() {
    _logLength++;
  }
  
  @Override
  public String toString() {
    return _ID + ". " + _name;
  }
}
