package baseNoStates;
import java.util.ArrayList;

/**
 * Representa el componente 'Composite' en el patrón Composite
 * Una Partition es un Área que puede contener otras Áreas, que pueden ser otras Partitions o Spaces
 */
public class Partition extends Area{
  private ArrayList<Area> children = new ArrayList<>();

  public Partition(String id) {
    super(id);
    children = new ArrayList<>();
  }

  @Override
  public ArrayList<Space> getSpaces() {
    ArrayList<Space> allSpaces = new ArrayList<>();
    // Pide a cada hijo sus espacios y los añade a la lista total.
    for (Area child : children) {
      allSpaces.addAll(child.getSpaces());
    }
    return allSpaces;
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    ArrayList<Door> allDoors = new ArrayList<>();
    // Pide a cada hijo sus puertas y las añade a la lista total
    for (Area child : children) {
      allDoors.addAll(child.getDoorsGivingAccess());
    }
    return allDoors;
  }

  @Override
  public Area findAreaById(String id) {
    // Primero, comprueba si esta partición es la que se busca
    if (this.getId().equals(id)) {
      return this;
    }
    // Si no, inicia la búsqueda recursiva en los hijos
    for (Area child : children) {
      Area actualArea = child.findAreaById(id);
      // Si el hijo encuentra el área, la devuelve
      if (actualArea != null) {
        return actualArea;
      }
    }
    return null;
  }

  public void add(Area area) {
    children.add(area);
  }
}