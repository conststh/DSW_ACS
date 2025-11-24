package baseNoStates.visitors;

import baseNoStates.Door;
import baseNoStates.Partition;
import baseNoStates.Space;
import baseNoStates.Area;
import java.util.ArrayList;

/**
 * Visitante concreto para recolectar todas las puertas de un Área.
 * Acumula todas las puertas encontradas en la estructura jerárquica en una lista plana.
 * Reemplaza al antiguo método `getDoorsGivingAccess()` que estaba en Area.
 */
public class GetDoorsVisitor implements AreaVisitor
{
  private final ArrayList<Door> doors = new ArrayList<>();

  @Override
  public void visit(Space space)
  {
    // En un espacio, simplemente agregamos sus puertas a la colección
    doors.addAll(space.getDoors());
  }

  @Override
  public void visit(Partition partition)
  {
    // En una partición, debemos explorar recursivamente todos sus hijos
    // para encontrar los espacios que contienen las puertas.
    for (Area child : partition.getChildren())
    {
      child.accept(this);
    }
  }

  public ArrayList<Door> getDoors()
  {
    return doors;
  }
}