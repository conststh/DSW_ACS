package baseNoStates;
import java.util.ArrayList;

/**
 * Representa un Nodo Compuesto en el Patrón Composite.
 * Una Partición puede contener múltiples sub-áreas (Espacios u otras Particiones).
 */
public class Partition extends Area
{
  private final ArrayList<Area> children; // Lista de nodos hijos

  public Partition(String id)
  {
    super(id);
    children = new ArrayList<>();
  }

  /**
   * Recolecta recursivamente todos los espacios de todos los hijos.
   */
  @Override
  public ArrayList<Space> getSpaces()
  {
    ArrayList<Space> allSpaces = new ArrayList<>();
    for (Area child : children)
    {
      allSpaces.addAll(child.getSpaces());
    }
    return allSpaces;
  }

  /**
   * Recolecta recursivamente todas las puertas de todos los hijos.
   */
  @Override
  public ArrayList<Door> getDoorsGivingAccess()
  {
    ArrayList<Door> allDoors = new ArrayList<>();
    for (Area child : children)
    {
      allDoors.addAll(child.getDoorsGivingAccess());
    }
    return allDoors;
  }

  /**
   * Búsqueda recursiva de un Área por ID.
   * 1. Comprueba si esta partición es la buscada.
   * 2. Si no, pide a cada hijo que la busque.
   */
  @Override
  public Area findAreaById(String id)
  {
    if (this.getId().equals(id))
    {
      return this;
    }
    for (Area child : children)
    {
      Area found = child.findAreaById(id);
      if (found != null)
      {
        return found;
      }
    }
    return null; // No encontrado en esta rama
  }

  public void add(Area area)
  {
    children.add(area);
  }
}