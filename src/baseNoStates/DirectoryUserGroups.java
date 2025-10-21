package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryUserGroups {
  private static ArrayList<UserGroup> userGroups = new ArrayList<>();

  public DirectoryUserGroups() {
    // this.userGroups = userGroups;
  }

  public static UserGroup findGroupByName(String name) {
    for (UserGroup group : userGroups) {
      if (group.getName().equals(name)) {
        return group;
      }
    }
    return null;
  }


  // Métode generado para crear los grupos de usuarios
  public static void makeUserGroups() {
    /*
    For allAreas, we just need to add the building in our array, as it contains all its children and therefore, it'll let us do any action to any door.
    For employeeAreas, we need to search every area that employees can access, and add each one to an array.
     */
    Area building = DirectoryAreas.findAreaById("building");
    ArrayList<Area> allAreas = new ArrayList<>();
    if (building != null) {
      allAreas.add(building);
    }

    ArrayList<Area> employeeAreas = new ArrayList<>();
    Area exterior = DirectoryAreas.findAreaById("exterior");
    Area stairs = DirectoryAreas.findAreaById("stairs");
    Area groundFloor = DirectoryAreas.findAreaById("ground floor");
    Area floor1 = DirectoryAreas.findAreaById("floor 1");

    if (exterior != null) employeeAreas.add(exterior);
    if (stairs != null) employeeAreas.add(stairs);
    if (groundFloor != null) employeeAreas.add(groundFloor);
    if (floor1 != null) employeeAreas.add(floor1);

    //Group creation

    // Admin group can enter any day, at any time, to all areas, and can do any action
    Schedule adminSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX, Arrays.asList(DayOfWeek.values())); //Any day, anytime
    ArrayList<String> adminActions = new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")); //All actions
    userGroups.add(new UserGroup("admin", adminActions, allAreas, adminSchedule)); //We create "admin" group

    //Managers can only enter during from 8:00 to 20:00, on work days, to all areas and can do any action
    Schedule managerSchedule = new Schedule(LocalTime.of(8, 0), LocalTime.of(20, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)); //Work days from 8:00 to 20:00
    ArrayList<String> managerActions = new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")); //All actions
    userGroups.add(new UserGroup("managers", managerActions, allAreas, managerSchedule)); //We create "managers" group

    //Employees can enter from 9 to 17, Monday to Friday, and can only unlock shortly, apart from physically acting. They can only enter to the areas specified in employeeAreas
    Schedule employeeSchedule = new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)); //Mon-Fri; 9 to 17
    ArrayList<String> employeeActions = new ArrayList<>(Arrays.asList("open", "close", "unlock_shortly")); //Only unlock shortly and act physically (open, close)
    userGroups.add(new UserGroup("employees", employeeActions, employeeAreas, employeeSchedule)); //We create "employee" group

    //Users with no privilege can't do anything but open and close doors, but still need a schedule
    Schedule noPrivilegeSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX,
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)); //Can come at any time, but have no permissions
    ArrayList<String> noPrivilegeActions = new ArrayList<>(Arrays.asList("open", "close")); //Can just open/close doors
    ArrayList<Area> noPrivilegeAreas = new ArrayList<>(); //We create empty list (can't enter to any space)
    userGroups.add(new UserGroup("no_privilege", noPrivilegeActions, noPrivilegeAreas, noPrivilegeSchedule)); //We create "no_privilege" group
  }
}