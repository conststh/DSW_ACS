package baseNoStates.visitors;

import baseNoStates.Area;
import baseNoStates.Partition;
import baseNoStates.Space;
import java.util.ArrayList;

/**
 * Visitor para obtener la lista de todos los espacios (Space) dentro de un Área (Area).
 * Equivalente al antiguo método getSpaces().
 */
public class GetSpacesVisitor implements AreaVisitor
{
  private final ArrayList<Space> spaces = new ArrayList<>();

  @Override
  public void visit(Space space)
  {
    // Si el nodo es un espacio, lo añadimos a la lista
    spaces.add(space);
  }

  @Override
  public void visit(Partition partition)
  {
    // Si es una partición, recorremos sus hijos recursivamente
    for (Area child : partition.getChildren())
    {
      child.accept(this);
    }
  }

  public ArrayList<Space> getSpaces()
  {
    return spaces;
  }
}