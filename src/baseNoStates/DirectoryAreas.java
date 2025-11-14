package baseNoStates;

import java.util.ArrayList;

/**
 * Implementa el patrón Singleton para asegurar una única instancia que gestiona la estructura del edificio (Particiones, Espacios)
 * Construye la jerarquía de áreas y proporciona acceso global a ellas
 */
public final class DirectoryAreas {
  // La única instancia de la clase (Patrón Singleton)
  private static final DirectoryAreas instance = new DirectoryAreas();
  private static Area rootArea;

  private final ArrayList<Door> allDoors;

  /**
   * Constructor privado para implementar el patrón Singleton
   * Inicializa la lista de puertas y llama a makeAreas() para construir la estructura del edificio
   */
  private DirectoryAreas() {
    allDoors = new ArrayList<>();
    makeAreas();
  }

  public static DirectoryAreas getInstance() {
    return instance;
  }

  /**
   * Construye la jerarquía completa de particiones y espacios del edificio
   * Utiliza el patrón Composite, donde 'Partition' es un nodo compuesto y 'Space' es un nodo hoja
   */
  private void makeAreas() {
    // Particiones principales
    Partition building = new Partition("building");
    rootArea = building; // El edificio es la raíz de la jerarquía

    Partition basement = new Partition("basement");
    Partition groundFloor = new Partition("ground floor");
    Partition floor1 = new Partition("floor 1");

    // Áreas especiales que son hijas directas del edificio
    Space exterior = new Space("exterior");
    Space stairs = new Space("stairs");

    // Añadir las particiones y espacios principales al edificio
    building.add(basement);
    building.add(groundFloor);
    building.add(floor1);
    building.add(exterior);
    building.add(stairs);

    // Espacios dentro del sótano
    Space parking = new Space("parking");
    basement.add(parking);

    // Espacios dentro de la planta baja
    Space hall = new Space("hall");
    Space room1 = new Space("room 1");
    Space room2 = new Space("room 2");
    Space restRoomGF = new Space("rest room");
    groundFloor.add(hall);
    groundFloor.add(room1);
    groundFloor.add(room2);
    groundFloor.add(restRoomGF);


    // Espacios dentro de la primera planta
    Space room3 = new Space("room 3");
    Space corridor = new Space("corridor");
    Space it = new Space("IT");
    Space restRoomF1 = new Space("rest room f1");
    floor1.add(room3);
    floor1.add(corridor);
    floor1.add(it);
    floor1.add(restRoomF1);
  }

  public static Area findAreaById(String id) {
    // Delega la búsqueda al método recursivo de la raíz
    return rootArea.findAreaById(id);
  }

  /*public Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    return null;
  } //No sabemos si será necesario en un futuro */

  //public Area getRootArea() {return rootArea;} -> No sabemos si será necesario en un futuro

  /*public ArrayList<Door> getAllDoors() {
    return allDoors;
  } //No sabemos si será necesario en un futuro */

  /*public ArrayList<Door> getDoorsGivingAccess(Area area) {
    return area.getDoorsGivingAccess();
  } //No sabemos si será necesario en un futuro */

  /*public ArrayList<Space> getSpaces(Area area) {
    return area.getSpaces();
  } //No sabemos si será necesario en un futuro */
}