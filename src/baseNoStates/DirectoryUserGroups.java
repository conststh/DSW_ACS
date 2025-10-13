package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryUserGroups {
  private static ArrayList<UserGroup> userGroups;

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
    // Es importante limpiar la lista para evitar duplicados si se llama varias veces
    //userGroups.clear();

    // NOTA: La creación de Areas (ej. DirectoryAreas.getAllAreas()) se asume
    // que es manejada en otra parte del código, ya que no se proporciona su implementación.
    // Aquí usamos placeholders. Si no tienes una clase DirectoryAreas, deberás
    // crear y pasar ArrayList<Area> vacíos o poblados manualmente.
    ArrayList<Area> allAreas = new ArrayList<>(); // Placeholder para todas las áreas
    ArrayList<Area> employeeAreas = new ArrayList<>(); // Placeholder para áreas de empleados

    // Grupo "admin"
    Schedule adminSchedule = new Schedule(LocalTime.MIN, LocalTime.MAX, Arrays.asList(DayOfWeek.values()));
    ArrayList<String> adminActions = new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly"));
    userGroups.add(new UserGroup("admin", adminActions, allAreas, adminSchedule));

    // Grupo "managers"
    Schedule managerSchedule = new Schedule(LocalTime.of(8, 0), LocalTime.of(20, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));
    ArrayList<String> managerActions = new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly"));
    userGroups.add(new UserGroup("managers", managerActions, allAreas, managerSchedule));

    // Grupo "employees"
    Schedule employeeSchedule = new Schedule(LocalTime.of(9, 0), LocalTime.of(17, 0),
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    ArrayList<String> employeeActions = new ArrayList<>(Arrays.asList("unlock_shortly"));
    userGroups.add(new UserGroup("employees", employeeActions, employeeAreas, employeeSchedule));

    // Grupo sin privilegios
    Schedule noPrivilegeSchedule = new Schedule(LocalTime.MIN, LocalTime.MIN, new ArrayList<>()); // Un horario que nunca se cumple
    ArrayList<String> noPrivilegeActions = new ArrayList<>();
    ArrayList<Area> noPrivilegeAreas = new ArrayList<>();
    userGroups.add(new UserGroup("no_privilege", noPrivilegeActions, noPrivilegeAreas, noPrivilegeSchedule));
  }


  // El métode findUserByCredential no debería estar aquí, sino en DirectoryUsers.
  // Se omite para seguir la lógica del diseño.
}