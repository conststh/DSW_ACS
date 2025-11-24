package baseNoStates;
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

  /**
   * Implementación del Visitor.
   * Llama al método `visit(Space space)` del visitante, permitiéndole ejecutar
   * la lógica específica para un Espacio (ej: recolectar sus puertas).
   */
  @Override
  public void accept(AreaVisitor visitor)
  {
    visitor.visit(this);
  }
}