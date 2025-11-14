package baseNoStates;
import java.util.ArrayList;

/**
 * Clase abstracta que representa el componente en el patrón Composite
 * Define la interfaz común para todos los elementos de la jerarquía del edificio,tanto Space como Partition
 */
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