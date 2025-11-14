package baseNoStates;
import java.time.LocalDateTime;

public class User {
  private final String name;
  private final String credential;
  private final String nameGroup;

  public User(String name, String credential, String nameGroup) {
    this.name = name;
    this.credential = credential;
    this.nameGroup = nameGroup;
  }

  public String getCredential() {
    return credential;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }

  public boolean canBeInSpace(Space space) {
    UserGroup group = DirectoryUserGroups.findGroupByName(this.nameGroup);

    if (group == null) {return false;}

  // Delega al grupo para encontrar el permiso de "dónde" (Area)
    for (Area authorizedArea : group.getAuthorizedAreas()) {
      if (authorizedArea.getSpaces().contains(space)) {
        return true;
      }
    }
    return false;
  }

  public boolean canSendRequests(LocalDateTime time){
    UserGroup group = DirectoryUserGroups.findGroupByName(this.nameGroup);
    if (group == null || group.getSchedule() == null) {
      return false;
    }
    // Delega al horario para comprobar el permiso de "cuándo" (Horario)
    return group.getSchedule().isTimeAuthorized(time);
  }

  public boolean canDoAction(String action){
    UserGroup group = DirectoryUserGroups.findGroupByName(this.nameGroup);

    if (group == null || group.getAuthorizedActions() == null) {
      return false;
    }
  // Delega al grupo para encontrar el permiso "qué" (acción)
    return group.getAuthorizedActions().contains(action);
  }

  public String getName() { return name; }
}