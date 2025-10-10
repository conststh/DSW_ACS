package baseNoStates;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
  private final String name;
  private final String credential;
  private final string nameGroup;
  //private List<Area> areas = new ArrayList<>();

  public User(String name, String credential, string nameGroup) {
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

    for (i=0; i < group.getAuthorizedSpaces().size(); i++){
      if (group.getAuthorizedSpaces()[i].getId() == space.getId()){
        return true;
      }
    }
    return false;
  }

  public boolean canSendRequests(LocalDateTime time){
    for (i = 0; i < group.getAuthorizedTimes().size(); i++){
      if (group.getAuthorizedTimes()[i] == time){
        return true;
      }
    }
    return false;
  }

  public boolean canDoAction(string action){
    //TODO; idk if "string action" is correct
  }

}
