package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Registro para Grupos de Usuario (Roles).
 * Define los permisos (acciones, áreas, horarios) para cada grupo.
 */
public class DirectoryUserGroups
{
  private static final ArrayList<UserGroup> userGroups = new ArrayList<>();

  public static UserGroup findGroupByName(String name)
  {
    for (UserGroup group : userGroups)
    {
      if (group.getName().equals(name))
      {
        return group;
      }
    }
    return null;
  }

  public static void makeUserGroups()
  {
    // 1. Admins: Todo acceso, todo el tiempo.
    Area building = DirectoryAreas.findAreaById("building"); // Raíz incluye todo
    ArrayList<Area> allAreas = new ArrayList<>();
    if (building != null) allAreas.add(building);

    Schedule adminSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX, Arrays.asList(DayOfWeek.values()));
    ArrayList<String> adminActions = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.LOCK, Actions.UNLOCK, Actions.UNLOCK_SHORTLY));
    userGroups.add(new UserGroup("admin", adminActions, allAreas, adminSchedule));


    // 2. Managers: Acceso 8-20, Lun-Sab.
    Schedule managerSchedule = new Schedule(LocalTime.of(8, 0), LocalTime.of(20, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));
    userGroups.add(new UserGroup("managers", adminActions, allAreas, managerSchedule)); // Mismas acciones/áreas que admin


    // 3. Employees: Áreas limitadas, Lun-Vie 9-17. Acciones limitadas (no pueden bloquear/desbloquear permanentemente).
    ArrayList<Area> employeeAreas = new ArrayList<>();
    // Añadir áreas específicas (excluyendo parking, etc.)
    if (DirectoryAreas.findAreaById("exterior") != null) employeeAreas.add(DirectoryAreas.findAreaById("exterior"));
    if (DirectoryAreas.findAreaById("stairs") != null) employeeAreas.add(DirectoryAreas.findAreaById("stairs"));
    if (DirectoryAreas.findAreaById("ground floor") != null) employeeAreas.add(DirectoryAreas.findAreaById("ground floor"));
    if (DirectoryAreas.findAreaById("floor 1") != null) employeeAreas.add(DirectoryAreas.findAreaById("floor 1"));

    Schedule employeeSchedule = new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));

    ArrayList<String> employeeActions = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY));
    userGroups.add(new UserGroup("employees", employeeActions, employeeAreas, employeeSchedule));


    // 4. No Privilege: Grupo por defecto. Solo abrir/cerrar básico (la lógica maneja qué pueden abrir realmente).
    Schedule noPrivilegeSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX,
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    ArrayList<String> noPrivilegeActions = new ArrayList<>(Arrays.asList(Actions.OPEN, Actions.CLOSE));
    userGroups.add(new UserGroup("no_privilege", noPrivilegeActions, allAreas, noPrivilegeSchedule));
  }
}