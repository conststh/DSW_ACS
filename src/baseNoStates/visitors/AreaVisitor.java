package baseNoStates.visitors;

import baseNoStates.Partition;
import baseNoStates.Space;

/**
 * Interfaz del Patrón Visitor.
 * Esta interfaz define el contrato para las operaciones que pueden realizarse sobre
 * la estructura de áreas (Particiones y Espacios).
 * El patrón Visitor nos permite definir nuevas operaciones sobre la jerarquía de áreas
 * (como buscar un área por ID, obtener todas las puertas, validar configuraciones, etc.)
 * sin modificar las clases de los elementos (Space, Partition).
 */
public interface AreaVisitor
{
  /**
   * Visita un nodo hoja (Space).
   */
  void visit(Space space);

  /**
   * Visita un nodo compuesto (Partition).
   */
  void visit(Partition partition);
}