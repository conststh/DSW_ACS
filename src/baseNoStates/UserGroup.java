package baseNoStates;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserGroup {
  private string name;
  private ArrayList<User> users;
  private ArrayList<Space> authorizedSpaces;
  private ArrayList<LocalDateTime> authorizedTimes; //horario? array de horas?

  public ArrayList<Space> getAuthorizedSpaces() {return authorizedSpaces;}
  public void setAuthorizedSpaces(ArrayList<Space> authorizedSpaces) {this.authorizedSpaces = authorizedSpaces;}

  public ArrayList<User> getUsers() {return users;}
  public void setUsers(ArrayList<User> users) {this.users = users;}

  public ArrayList<LocalDateTime> getAuthorizedTimes() {return authorizedTimes;}
  public void setAuthorizedTimes(ArrayList<LocalDateTime> authorizedTimes){this.authorizedTimes = authorizedTimes;}
}
