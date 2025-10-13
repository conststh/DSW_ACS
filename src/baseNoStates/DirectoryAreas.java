package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// The DirectoryAreas class is implemented as a Singleton to ensure there is only one
// instance managing the building's structure. It builds the hierarchy of partitions,
// spaces, and doors, and provides global access to them.
public final class DirectoryAreas {
  private static final DirectoryAreas instance = new DirectoryAreas();
  private static Area rootArea;
  private ArrayList<Door> allDoors;

  // The private constructor initializes the door list and calls makeAreas()
  // to build the entire building structure upon creation of the single instance.
  private DirectoryAreas() {
    allDoors = new ArrayList<>();
    makeAreas();
  }

  public static DirectoryAreas getInstance() {
    return instance;
  }

  public static Area getAreaById(String id){
    return rootArea.findAreaById(id);
  }
  //Builds the entire hierarchy of partitions, spaces, and doors for the building as specified in page 14.

  private void makeAreas() {
    // Partitions for the main structure
    Partition building = new Partition("building");
    this.rootArea = building;
    Partition basement = new Partition("basement");
    Partition groundFloor = new Partition("ground floor");
    Partition floor1 = new Partition("floor 1");
    // Special areas that are direct children of the building
    Space exterior = new Space("exterior");
    Space stairs = new Space("stairs");

    building.add(basement);
    building.add(groundFloor);
    building.add(floor1);
    building.add(exterior);
    building.add(stairs);

    // Spaces within the basement
    Space parking = new Space("parking");
    basement.add(parking);

    // Spaces within the ground floor
    Space hall = new Space("hall");
    Space room1 = new Space("room 1");
    Space room2 = new Space("room 2");
    Space restRoomGF = new Space("rest room");
    groundFloor.add(hall);
    groundFloor.add(room1);
    groundFloor.add(room2);
    groundFloor.add(restRoomGF);


    // Spaces within floor 1
    Space room3 = new Space("room 3");
    Space corridor = new Space("corridor");
    Space it = new Space("IT");
    Space restRoomF1 = new Space("rest room f1"); // Assuming a separate restroom
    floor1.add(room3);
    floor1.add(corridor);
    floor1.add(it);
    floor1.add(restRoomF1);

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

    // Associate doors with the spaces they give access to
    parking.addDoor(d1);
    parking.addDoor(d2);
    hall.addDoor(d3);
    stairs.addDoor(d4); // Access to stairs from hall
    room1.addDoor(d5);
    room2.addDoor(d6);
    stairs.addDoor(d7); // Access to stairs from corridor
    room3.addDoor(d8);
    it.addDoor(d9);
  }

  /**
   * Finds an Area (Partition or Space) by its ID by starting the search
   * from the root of the building hierarchy.
   * @param id The identifier of the area to find.
   * @return The Area object if found, otherwise null.
   */
  public Area findAreaById(String id) {
    return rootArea.findAreaById(id);
  }

  /**
   * Finds a Door by its ID by searching the list of all doors.
   * @param id The identifier of the door to find.
   * @return The Door object if found, otherwise null.
   */
  public Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    return null;
  }

  public ArrayList<Door> getAllDoors() {
    return allDoors;
  }

  /**
   * Utility method that delegates the call to the Area object to get all doors
   * contained within it or its sub-areas.
   * @param area The area from which to get the doors.
   * @return A list of doors.
   */
  public ArrayList<Door> getDoorsGivingAccess(Area area) {
    return area.getDoorsGivingAccess();
  }

  /**
   * Utility method that delegates the call to the Area object to get all spaces
   * contained within it or its sub-areas.
   * @param area The area from which to get the spaces.
   * @return A list of spaces.
   */
  public ArrayList<Space> getSpaces(Area area) {
    return area.getSpaces();
  }
}