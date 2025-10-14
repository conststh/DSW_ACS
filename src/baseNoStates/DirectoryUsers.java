package baseNoStates;

import java.util.ArrayList;
import java.util.Objects;

public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {
    //TODO: make user groups according to the specifications in the comments, because
    // now all are the same
    DirectoryUserGroups.makeUserGroups();
    users.clear();
    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    //users.add(new User("Bernat", "12345", "no_privilege"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("no_privilege")).addUser(new User("Bernat", "12345", "no_privilege"));
    //users.add(new User("Blai", "77532", "no_privilege"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("no_privilege")).addUser(new User("Blai", "77532", "no_privilege"));
    
    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    // users.add(new User("Ernest", "74984",  "employees"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("employees")).addUser(new User("Ernest", "74984",  "employees"));
    //users.add(new User("Eulalia", "43295",  "building"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("employees")).addUser(new User("Eulalia", "43295", "employees"));

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    //users.add(new User("Manel", "95783",  "managers"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("managers")).addUser(new User("Manel", "95783",  "managers"));
    //users.add(new User("Marta", "05827",   "managers"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("managers")).addUser(new User("Marta", "05827",   "managers"));

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    //users.add(new User("Ana", "11343",   "admin"));
    Objects.requireNonNull(DirectoryUserGroups.findGroupByName("admin")).addUser(new User("Ana", "11343",   "admin"));
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
