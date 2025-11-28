package baseNoStates.visitors;

import baseNoStates.Area;
import baseNoStates.Partition;
import baseNoStates.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Visitante concreto para buscar un Área por su ID.
 * Recorre el árbol buscando una coincidencia. Encapsula el algoritmo de búsqueda,
 * manteniendo limpio el código de las clases Área/Space/Partition.
 */
public class FindAreaVisitor implements AreaVisitor
{
  private static final Logger logger = LoggerFactory.getLogger(FindAreaVisitor.class);
  private final String idToFind;
  private Area foundArea = null;

  public FindAreaVisitor(String id)
  {
    this.idToFind = id;
  }

  @Override
  public void visit(Space space)
  {
    // Comprobación directa en la hoja
    if (space.getId().equals(idToFind))
    {
      logger.debug("OBJETIVO ENCONTRADO: Espacio '{}'", space.getId());
      foundArea = space;
    }
  }

  @Override
  public void visit(Partition partition)
  {
    // Comprobamos si la partición misma es lo que buscamos
    if (partition.getId().equals(idToFind))
    {
      logger.debug("OBJETIVO ENCONTRADO: Partición '{}'", partition.getId());
      foundArea = partition;
      return;
    }
    logger.debug("Explorando dentro de Partición: '{}' buscando '{}'", partition.getId(), idToFind);

    // Si no, delegamos la búsqueda a los hijos (recursión controlada por el visitante)
    for (Area child : partition.getChildren())
    {
      // Si ya encontramos el área en una iteración anterior, detenemos la búsqueda
      if (foundArea != null) return;

      // Continuamos el recorrido
      child.accept(this);
    }
  }

  /**
   * Obtiene el resultado de la operación del visitante.
   * @return El área encontrada o null si no existe.
   */
  public Area getResult()
  {
    return foundArea;
  }
}