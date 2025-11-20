package baseNoStates;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un Nodo Hoja en el Patrón Composite.
 * Un Espacio es un área física que contiene Puertas pero NO otras Áreas.
 */
public class Space extends Area
{
  private ArrayList<Door> doors;

  public Space(String id)
  {
    super(id);
    doors = new ArrayList<>();
  }

  /**
   * Como un Espacio es una hoja, la lista de espacios que contiene es simplemente él mismo.
   */
  @Override
  public ArrayList<Space> getSpaces()
  {
    return new ArrayList<>(List.of(this));
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess()
  {
    return doors;
  }

  /**
   * Implementación de hoja para la búsqueda.
   * Si esta es el área buscada, se devuelve a sí misma; si no, devuelve null.
   */
  @Override
  public Area findAreaById(String id)
  {
    if (this.getId().equals(id))
    {
      return this;
    }
    else
    {
      return null; // No encontrado y no hay hijos para explorar
    }
  }

  public void addDoor(Door door)
  {
    if (!this.doors.contains(door))
    {
      this.doors.add(door);
    }
  }
}