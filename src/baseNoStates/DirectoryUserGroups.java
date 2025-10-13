package baseNoStates;

import java.util.ArrayList;

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

  public void makeUserGroups(string name, ArrayList<string> actions, ArrayList<Area> areas, Schedule schedule){
    //TODO
    userGroups.add(new UserGroup(name, actions, areas, Schedule schedule));
  }
  public User findUserByCredential(string credential){
    //TODO
  }
}
