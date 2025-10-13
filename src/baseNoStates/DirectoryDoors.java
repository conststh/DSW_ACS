package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryDoors {
  private static ArrayList<Door> allDoors;

  public static void makeDoors() {
    DirectoryAreas directoryAreas = DirectoryAreas.getInstance();

    Space exterior = (Space) directoryAreas.findAreaById("exterior");
    Space parking = (Space) directoryAreas.findAreaById("parking");
    Space stairs = (Space) directoryAreas.findAreaById("stairs");
    Space hall = (Space) directoryAreas.findAreaById("hall");
    Space room1 = (Space) directoryAreas.findAreaById("room 1");
    Space room2 = (Space) directoryAreas.findAreaById("room 2");
    Space corridor = (Space) directoryAreas.findAreaById("corridor");
    Space room3 = (Space) directoryAreas.findAreaById("room 3");
    Space it = (Space) directoryAreas.findAreaById("IT");

    // Door name is on the space the door gives access to [cite: 38]
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
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

}
