package baseNoStates;
import java.util.ArrayList;
import baseNoStates.visitors.AreaVisitor;

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

  public void add(Area area)
  {
    children.add(area);
  }

  public ArrayList<Area> getChildren() {
    return children;
  }

  @Override
  public void accept(AreaVisitor visitor) {
    visitor.visit(this);
  }
}