package baseNoStates;
import java.util.List;
import baseNoStates.visitors.AreaVisitor;
import java.util.ArrayList;
/**
 * Representa un Nodo Hoja en el Patrón Composite.
 * Un Espacio es un área física que contiene Puertas pero NO otras Áreas.
 */
public class Space extends Area
{
  private final ArrayList<Door> doors;

  public Space(String id)
  {
    super(id);
    doors = new ArrayList<>();
  }

  public ArrayList<Door> getDoors()
  {
    return doors;
  }

  public void addDoor(Door door)
  {
    if (!this.doors.contains(door))
    {
      this.doors.add(door);
    }
  }

  @Override
  public void accept(AreaVisitor visitor)
  {
    visitor.visit(this);
  }
}