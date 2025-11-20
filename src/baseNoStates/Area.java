package baseNoStates;
import java.util.ArrayList;

/**
 * Componente Abstracto en el Patrón Composite.
 * Representa cualquier nodo en la jerarquía del edificio.
 * - Las Hojas son objetos Space (contienen puertas).
 * - Los Compuestos son objetos Partition (contienen otras Áreas).
 */
public abstract class Area
{
  protected final String id;

  public Area(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  // Métodos abstractos que todas las áreas deben implementar (polimorfismo)
  public abstract ArrayList<Space> getSpaces();
  public abstract ArrayList<Door> getDoorsGivingAccess();
  public abstract Area findAreaById(String id);
}