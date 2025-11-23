package baseNoStates.visitors;

import baseNoStates.Partition;
import baseNoStates.Space;

/**
 * Interfaz para el Patrón Visitor.
 * Permite separar los algoritmos de recorrido de la estructura de objetos (Área/Partition/Space).
 * Reemplaza los métodos hardcoded en las clases del árbol.
 */
public interface AreaVisitor {
  void visit(Space space);
  void visit(Partition partition);
}

