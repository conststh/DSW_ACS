package baseNoStates;

import baseNoStates.visitors.FindAreaVisitor;

/**
 * Singleton que gestiona la jerarquía estructural del edificio (Particiones y Espacios).
 * Crea y almacena el árbol de Áreas.
 */
public final class DirectoryAreas
{
  private static final DirectoryAreas instance = new DirectoryAreas();
  private static Area rootArea;

  private DirectoryAreas()
  {
    makeAreas();
  }

  public static DirectoryAreas getInstance()
  {
    return instance;
  }

  /**
   * Construye el árbol Composite del edificio.
   * Estructura: Edificio -> Plantas -> Habitaciones/Pasillos -> Puertas (vinculadas en DirectoryDoors)
   */
  private void makeAreas()
  {
    // Crear Particiones (Compuestos)
    Partition building = new Partition("building");
    rootArea = building;

    Partition basement = new Partition("basement");
    Partition groundFloor = new Partition("ground floor");
    Partition floor1 = new Partition("floor 1");

    // Espacios Especiales
    Space exterior = new Space("exterior");
    Space stairs = new Space("stairs");

    // Construir Jerarquía
    building.add(basement);
    building.add(groundFloor);
    building.add(floor1);
    building.add(exterior);
    building.add(stairs);

    // Sótano
    Space parking = new Space("parking");
    basement.add(parking);

    // Planta Baja
    Space hall = new Space("hall");
    Space room1 = new Space("room 1");
    Space room2 = new Space("room 2");
    Space restRoomGF = new Space("rest room");
    groundFloor.add(hall);
    groundFloor.add(room1);
    groundFloor.add(room2);
    groundFloor.add(restRoomGF);

    // Primera Planta
    Space room3 = new Space("room 3");
    Space corridor = new Space("corridor");
    Space it = new Space("IT");
    Space restRoomF1 = new Space("rest room f1");
    floor1.add(room3);
    floor1.add(corridor);
    floor1.add(it);
    floor1.add(restRoomF1);
  }

  public static Area findAreaById(String id)
  {
    // Usamos el Visitor para buscar en el árbol
    FindAreaVisitor finder = new FindAreaVisitor(id);
    if (rootArea != null) {
      rootArea.accept(finder);
    }
    return finder.getResult();
  }
}