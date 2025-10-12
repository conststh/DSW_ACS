package baseNoStates;

public class DirectoryUserGroups {
  private final ArrayList<UserGroup> userGroups;

  public UserGroup findGroupByName(String name) {
    for (UserGroup group : userGroups) {
      if (group.getName().equals(name)) {
        return group;
      }
    }
    return null;
  }

  public void makeUserGroups(){
    //TODO
  }
  public User findUserByCredential(string credential){
    //TODO
  }
}
