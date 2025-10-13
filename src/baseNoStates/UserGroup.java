package baseNoStates;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {
  private final String name;
  private final ArrayList<User> users; // who
  private final ArrayList<string> authorizedActions; // what
  private final ArrayList<Area> authorizedAreas;     // where
  private final Schedule schedule;              // when

  public UserGroup(string name, ArrayList<string> actions, ArrayList<Area> areas, Schedule schedule) {
    this.name = name;
    this.authorizedActions = actions;
    this.authorizedAreas = areas;
    this.schedule = schedule;
    this.users = new ArrayList<>();
  }

  // Getters
  public ArrayList<User> getUsers() {return users;}
  public ArrayList<Area> getAuthorizedAreas() {return authorizedAreas;  }
  public Schedule getSchedule() {return schedule;  }
  public ArrayList<String> getAuthorizedActions() {return authorizedActions;  }
  public String getName() {return name;  }

  public void addUser(User user) {
    if (!users.contains(user)) {
      users.add(user);
    }
    else{
      System.out.println("User "+ user.getName() + " is already in this group");
    }
  }

  public void removeUser(User user) {
    if (users.contains(user)) {
      users.remove(user);
    }
    else{
      System.out.println("Error in REMOVE USER");
      System.out.println("User "+ user.getName() + " isn't in this group");
    }
  }
}