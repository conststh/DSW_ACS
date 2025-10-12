package baseNoStates;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    UserGroup group = DirectoryUserGroups.findGroupByName(this.name);

    if (group == null) {return false;}

  // Delegate to the group to check the "where" permission
    for (Area authorizedArea : group.getAuthorizedAreas()) {
      if (authorizedArea.getSpaces().contains(space)) {
        return true;
      }
    }
    return false;
  }

  public boolean canSendRequests(LocalDateTime time){
    UserGroup group = DirectoryUserGroups.findGroupByName(this.name);
    if (group == null || group.getSchedule() == null) {
      return false;
    }
    // Delegate to the schedule to check the "when" permission
    return group.getSchedule().isTimeAuthorized(time);
  }

  public boolean canDoAction(String action){
    UserGroup group = DirectoryUserGroups.findGroupByName(this.name);

    if (group == null || group.getAuthorizedActions() == null) {
      return false;
    }
  // Delegate to the group to check the "what" permission
    return group.getAuthorizedActions().contains(action);
  }
}