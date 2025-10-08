package baseNoStates;
import java.util.ArrayList;
import java.util.List;

public class User {
  private final String name;
  private final String credential;
  private List<Area> areas =  new ArrayList<>();

  public User(String name, String credential, List<Area> areas) {
    this.name = name;
    this.credential = credential;
    this.areas = areas;
  }

  public String getCredential() {
    return credential;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
  public boolen canBeInSpace(Space space)
  {
    //TODO
  }
