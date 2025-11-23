package baseNoStates;
import java.util.ArrayList;
import baseNoStates.visitors.AreaVisitor;

/**
 * Componente Abstracto en el Patrón Composite.
 * Representa cualquier nodo en la jerarquía del edificio.
 * - Las Hojas son objetos Space (contienen puertas).
 * - Los Compuestos son objetos Partition (contienen otras Áreas).
 * Acepta visitantes para procesar operaciones sobre la jerarquía.
 */
public abstract class Area {
  private final String id;

  public Area(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  // Método fundamental del patrón Visitor
  public abstract void accept(AreaVisitor visitor);
}