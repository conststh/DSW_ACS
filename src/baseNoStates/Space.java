package baseNoStates;
import java.util.ArrayList;

public class Space extends Area{
  private final ArrayList<Door> doors = new ArrayList<>();

  public Space(String id) {
    super(id);
  }

  @Override
  public ArrayList<Space> getSpaces() {
    //TODO
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    //TODO
  }

  @Override
  public Area findAreaById(String id) {
    //TODO
  }

  public void addDoor(Door door) {
    if (!doors.contains(door)) {
      doors.add(door);
    }
  }
}