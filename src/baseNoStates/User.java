package baseNoStates;
import java.time.LocalDateTime;

/**
 * Representa un usuario del sistema.
 * Actúa como un proxy para comprobar permisos, delegando la lógica al UserGroup.
 */
public class User
{
  private final String name;
  private final String credential; // ID único / Número de tarjeta
  private final String nameGroup;  // ID del Grupo

  public User(String name, String credential, String nameGroup)
  {
    this.name = name;
    this.credential = credential;
    this.nameGroup = nameGroup;
  }

  public String getCredential()
  {
    return credential;
  }

  public String getName()
  {
    return name;
  }

  /**
   * Comprueba si el usuario tiene permiso en un espacio físico específico (Dónde).
   * Delega en el UserGroup para encontrar Áreas autorizadas.
   */
  public boolean canBeInSpace(Space space)
  {
    UserGroup group = DirectoryUserGroups.findGroupByName(this.nameGroup);
    if (group == null)
    {
      return false;
    }

    // Itera a través de todas las áreas autorizadas (Particiones o Espacios) para este grupo
    for (Area authorizedArea : group.getAuthorizedAreas())
    {
      // Chequeo (funciona si authorizedArea es el espacio mismo o una Partición padre)
      if (authorizedArea.getSpaces().contains(space))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Comprueba si el usuario puede realizar una acción en la hora actual (Cuándo).
   * Delega en el Horario (Schedule) del grupo.
   */
  public boolean canSendRequests(LocalDateTime time)
  {
    UserGroup group = DirectoryUserGroups.findGroupByName(this.nameGroup);
    if (group == null || group.getSchedule() == null)
    {
      return false;
    }
    return group.getSchedule().isTimeAuthorized(time);
  }

  /**
   * Comprueba si el usuario tiene permiso para un tipo de acción específica (Qué).
   * Delega en la lista de acciones del Grupo.
   */
  public boolean canDoAction(String action)
  {
    UserGroup group = DirectoryUserGroups.findGroupByName(this.nameGroup);
    if (group == null || group.getAuthorizedActions() == null)
    {
      return false;
    }
    return group.getAuthorizedActions().contains(action);
  }

  @Override
  public String toString()
  {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}