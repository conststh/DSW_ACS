package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

// Clase que se encarga de crear y gestionar el directorio global de puertas

public final class DirectoryDoors {
  private static ArrayList<Door> allDoors; // Lista estática de todas las puertas del edificio

  /**
   * Crea todas las instancias de Puertas (Door) y las conecta con sus respectivos Espacios (Space)
   * Este método se apoya en DirectoryAreas para encontrar los espacios por su ID.
   */
  public static void makeDoors() {

    DirectoryAreas directoryAreas = DirectoryAreas.getInstance();

    // Obtener las instancias de los espacios necesarios
    Space exterior = (Space) directoryAreas.findAreaById("exterior");
    Space parking = (Space) directoryAreas.findAreaById("parking");
    Space stairs = (Space) directoryAreas.findAreaById("stairs");
    Space hall = (Space) directoryAreas.findAreaById("hall");
    Space room1 = (Space) directoryAreas.findAreaById("room 1");
    Space room2 = (Space) directoryAreas.findAreaById("room 2");
    Space corridor = (Space) directoryAreas.findAreaById("corridor");
    Space room3 = (Space) directoryAreas.findAreaById("room 3");
    Space it = (Space) directoryAreas.findAreaById("IT");

    // Crear cada puerta especificando su ID y los dos espacios que conecta
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);
    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", hall, stairs);
    Door d5 = new Door("D5", hall, room1);
    Door d6 = new Door("D6", hall, room2);
    Door d7 = new Door("D7", corridor, stairs);
    Door d8 = new Door("D8", corridor, room3);
    Door d9 = new Door("D9", corridor, it);

    // Añadir todas las puertas creadas a la lista estática global
    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));

    // Establecer la relación bidireccional: añadir cada puerta a los espacios que conecta
    exterior.addDoor(d1);
    parking.addDoor(d1);
    stairs.addDoor(d2);
    parking.addDoor(d2);
    exterior.addDoor(d3);
    hall.addDoor(d3);
    hall.addDoor(d4);
    stairs.addDoor(d4);
    hall.addDoor(d5);
    room1.addDoor(d5);
    hall.addDoor(d6);
    room2.addDoor(d6);
    corridor.addDoor(d7);
    stairs.addDoor(d7);
    corridor.addDoor(d8);
    room3.addDoor(d8);
    corridor.addDoor(d9);
    it.addDoor(d9);
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // Devuelve null si no se encuentra la puerta
  }

  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

}