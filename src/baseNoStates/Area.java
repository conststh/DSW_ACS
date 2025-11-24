package baseNoStates;

import baseNoStates.visitors.AreaVisitor;

/**
 * Componente Abstracto en el Patrón Composite.
 * Define la interfaz base para todos los elementos de la estructura del edificio.
 * Se han eliminado los métodos de lógica de negocio (como getDoorsGivingAccess y findAreaById)
 * y se han reemplazado por el método `accept`. Esto desacopla la estructura de datos
 * de los algoritmos que operan sobre ella.
 */
public abstract class Area
{
  private final String id;

  public Area(String id)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  /**
   * Método fundamental del Patrón Visitor.
   * Permite que un visitante procese este elemento. El elemento "acepta" al visitante
   * y se llama a sí mismo en el método correspondiente del visitante (`visit(this)`),
   * resolviendo el tipo concreto (Space o Partition) en tiempo de ejecución.
   */
  public abstract void accept(AreaVisitor visitor);
}