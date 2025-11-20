package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Registro para todos los objetos Puerta (Door).
 * Responsable de crear puertas y vincularlas a sus respectivos Espacios.
 */
public final class DirectoryDoors
{
  private static ArrayList<Door> allDoors;

  /**
   * Crea instancias de Puerta y las conecta con Espacios (Origen/Destino).
   * Depende de que DirectoryAreas haya sido inicializado primero.
   */
  public static void makeDoors()
  {
    // Recuperar referencias a espacios
    Space exterior = (Space) DirectoryAreas.findAreaById("exterior");
    Space parking = (Space) DirectoryAreas.findAreaById("parking");
    Space stairs = (Space) DirectoryAreas.findAreaById("stairs");
    Space hall = (Space) DirectoryAreas.findAreaById("hall");
    Space room1 = (Space) DirectoryAreas.findAreaById("room 1");
    Space room2 = (Space) DirectoryAreas.findAreaById("room 2");
    Space corridor = (Space) DirectoryAreas.findAreaById("corridor");
    Space room3 = (Space) DirectoryAreas.findAreaById("room 3");
    Space it = (Space) DirectoryAreas.findAreaById("IT");

    // Crear Puertas con IDs y conexiones
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);
    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", hall, stairs);
    Door d5 = new Door("D5", hall, room1);
    Door d6 = new Door("D6", hall, room2);
    Door d7 = new Door("D7", corridor, stairs);
    Door d8 = new Door("D8", corridor, room3);
    Door d9 = new Door("D9", corridor, it);

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));

    // Establecer enlace bidireccional: Añadir puertas a los espacios que conectan
    exterior.addDoor(d1);     parking.addDoor(d1);
    stairs.addDoor(d2);       parking.addDoor(d2);
    exterior.addDoor(d3);     hall.addDoor(d3);
    hall.addDoor(d4);         stairs.addDoor(d4);
    hall.addDoor(d5);         room1.addDoor(d5);
    hall.addDoor(d6);         room2.addDoor(d6);
    corridor.addDoor(d7);     stairs.addDoor(d7);
    corridor.addDoor(d8);     room3.addDoor(d8);
    corridor.addDoor(d9);     it.addDoor(d9);
  }

  public static Door findDoorById(String id)
  {
    for (Door door : allDoors)
    {
      if (door.getId().equals(id))
      {
        return door;
      }
    }
    System.out.println("Puerta con id " + id + " no encontrada");
    return null;
  }

  public static ArrayList<Door> getAllDoors()
  {
    System.out.println(allDoors);
    return allDoors;
  }
}