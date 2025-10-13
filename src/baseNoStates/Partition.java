package baseNoStates;
import java.util.ArrayList;
import java.util.List;

public class Partition extends Area{
  private ArrayList<Area> children = new ArrayList<>();

  public Partition(String id) {
    super(id);
    children = new ArrayList<>();
  }

  @Override
  public ArrayList<Space> getSpaces() {
    ArrayList<Space> allSpaces = new ArrayList<>();
  // Recursively collect spaces from children
    for (Area child : children) {
      allSpaces.addAll(child.getSpaces());
    }
    return allSpaces;
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    List<Door> allDoors = new ArrayList<>();
  // Recursively collect doors from children
    for (Area child : children) {
      allDoors.addAll(child.getDoorsGivingAccess());
    }
    return (ArrayList<Door>) allDoors;
  }

  @Override
  public Area findAreaById(String id) {
    if (this.getId().equals(id)) {
      return this;
    }
  // Recursively search in children
    for (Area child : children) {
      Area actualArea = child.findAreaById(id);
      if (actualArea != null) {
        return actualArea;
      }
    }
    return null; // Not found
  }

  public void add(Area area) {
    children.add(area);
  }
}