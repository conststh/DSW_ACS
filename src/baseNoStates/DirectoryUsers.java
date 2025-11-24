package baseNoStates;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registro para Usuarios.
 * Crea usuarios específicos y los asigna a Grupos de Usuario.
 * Implementa el patrón Singleton para asegurar una única instancia del directorio.
 */
public final class DirectoryUsers
{
  private static final Logger logger = LoggerFactory.getLogger(DirectoryUsers.class);
  private static DirectoryUsers instance;
  private static final ArrayList<User> users = new ArrayList<>();

  // Método de acceso global sincronizado
  public static synchronized DirectoryUsers getInstance()
  {
    if (instance == null)
    {
      instance = new DirectoryUsers();
    }
    return instance;
  }

  public static void makeUsers()
  {
    DirectoryUserGroups.makeUserGroups();
    users.clear();
    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    users.add(new User("Bernat", "12345", "no_privilege"));
    users.add(new User("Blai", "77532", "no_privilege"));
    
    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    users.add(new User("Ernest", "74984",  "employees"));
    users.add(new User("Eulalia", "43295",  "employees"));

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    users.add(new User("Manel", "95783",  "managers"));
    users.add(new User("Marta", "05827",   "managers"));

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    users.add(new User("Ana", "11343",   "admin"));

    logger.info("Usuarios cargados en el directorio. Total: {}", users.size());
  }

  public static User findUserByCredential(String credential)
  {
    for (User user : users)
    {
      if (user.getCredential().equals(credential))
      {
        return user;
      }
    }
    logger.warn("Usuario con credencial '{}' no encontrado", credential);
    return null; // otherwise we get a Java error
  }
}
