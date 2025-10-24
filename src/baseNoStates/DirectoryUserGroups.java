package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryUserGroups {
  private static final ArrayList<UserGroup> userGroups = new ArrayList<>();

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
    allAreas es la array que guardará todas las áreas del edificio. La usaremos para los grupos "admin" y "manager", que tienen acceso a todas las zonas.
    Solo es necesario guardar "building", porque este contiene a todas las demás áreas.
     */
    Area building = DirectoryAreas.findAreaById("building");
    ArrayList<Area> allAreas = new ArrayList<>();
    if (building != null) {
      allAreas.add(building);
    }

    /*
    employeeAreas es la array que guardará todas las zonas a las que pueden acceder los empleados (grupo "employees")
    Las areas que hay que guardar son "exterior", "stairs", "ground floor" y "floor 1"
     */

    ArrayList<Area> employeeAreas = new ArrayList<>();
    Area exterior = DirectoryAreas.findAreaById("exterior");
    Area stairs = DirectoryAreas.findAreaById("stairs");
    Area groundFloor = DirectoryAreas.findAreaById("ground floor");
    Area floor1 = DirectoryAreas.findAreaById("floor 1");

    if (exterior != null) employeeAreas.add(exterior);
    if (stairs != null) employeeAreas.add(stairs);
    if (groundFloor != null) employeeAreas.add(groundFloor);
    if (floor1 != null) employeeAreas.add(floor1);

    //Creamos los grupos

    // Admin: pueden entrar cualquier día a cualquier hora, y hacer todas las acciones.
    Schedule adminSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX, Arrays.asList(DayOfWeek.values()));
    ArrayList<String> adminActions = new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly"));
    userGroups.add(new UserGroup("admin", adminActions, allAreas, adminSchedule));

    //Managers: Solo pueden acceder de 8 a 20 en días entre semana y sábados, también pueden hacer cualquier acción.
    Schedule managerSchedule = new Schedule(LocalTime.of(8, 0), LocalTime.of(20, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)); //Work days from 8:00 to 20:00
    ArrayList<String> managerActions = new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")); //All actions
    userGroups.add(new UserGroup("managers", managerActions, allAreas, managerSchedule)); //We create "managers" group

    //Employees: Pueden acceder a sus espacios designados de lunes a viernes de 9 a 17, solo puede abrir, cerrar y desbloquear brevemente.
    Schedule employeeSchedule = new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    ArrayList<String> employeeActions = new ArrayList<>(Arrays.asList("open", "close", "unlock_shortly"));
    userGroups.add(new UserGroup("employees", employeeActions, employeeAreas, employeeSchedule));

    //Grupo de usuarios sin privilegios: Solo pueden abrir y cerrar puertas.
    // Al no ser parte del personal del edificio, solo pueden abrir y cerrar puertas
    Schedule noPrivilegeSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX,
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    ArrayList<String> noPrivilegeActions = new ArrayList<>(Arrays.asList("open", "close"));
    userGroups.add(new UserGroup("no_privilege", noPrivilegeActions, allAreas, noPrivilegeSchedule));
  }
}