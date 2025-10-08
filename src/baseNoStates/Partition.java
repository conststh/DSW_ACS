package baseNoStates;
import java.util.ArrayList;
import java.util.List;

public class Partition extends Area{
  private final ArrayList<Area> children = new ArrayList<>();

  public Partition(String id) {
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

  public void add(Area area) {
    children.add(area);
  }
}