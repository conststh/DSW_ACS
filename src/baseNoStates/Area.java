package baseNoStates;
import java.util.ArrayList;
import java.util.List;

public abstract class Area {
  protected final String id;

  public Area(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public abstract ArrayList<Space> getSpaces();
  public abstract ArrayList<Door> getDoorsGivingAccess();
  public abstract Area findAreaById(String id);
}