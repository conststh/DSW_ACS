package baseNoStates.visitors;

import baseNoStates.Area;
import baseNoStates.Partition;
import baseNoStates.Space;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Visitor para obtener la lista de todos los espacios (Space) dentro de un Área (Area).
 * Equivalente al antiguo método getSpaces().
 */
public class GetSpacesVisitor implements AreaVisitor
{
  private static final Logger logger = LoggerFactory.getLogger(GetSpacesVisitor.class);
  private final ArrayList<Space> spaces = new ArrayList<>();

  @Override
  public void visit(Space space)
  {
    logger.debug("Recolectando Espacio: '{}'", space.getId());
    // Si el nodo es un espacio, lo añadimos a la lista
    spaces.add(space);
  }

  @Override
  public void visit(Partition partition)
  {
    logger.debug("Entrando en Partición: '{}' para recolectar espacios.", partition.getId());

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