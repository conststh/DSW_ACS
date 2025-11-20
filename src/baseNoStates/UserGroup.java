package baseNoStates;

import java.util.ArrayList;

/**
 * Representa los Roles del sistema.
 * Agrega:
 * - QUIÉN (lista de usuarios, aunque actualmente gestionada por DirectoryUsers)
 * - QUÉ (Acciones Autorizadas)
 * - DÓNDE (Áreas Autorizadas)
 * - CUÁNDO (Horario)
 */
public class UserGroup
{
  private final String name;
  private final ArrayList<String> authorizedActions;
  private final ArrayList<Area> authorizedAreas;
  private final Schedule schedule;

  public UserGroup(String name, ArrayList<String> actions, ArrayList<Area> areas, Schedule schedule)
  {
    this.name = name;
    this.authorizedActions = actions;
    this.authorizedAreas = areas;
    this.schedule = schedule;
    //ArrayList<User> users = new ArrayList<>(); no se llega a utilizar
  }

  public ArrayList<Area> getAuthorizedAreas()
  {
    return authorizedAreas;
  }
  public Schedule getSchedule()
  {
    return schedule;
  }
  public ArrayList<String> getAuthorizedActions()
  {
    return authorizedActions;
  }
  public String getName()
  {
    return name;
  }
}