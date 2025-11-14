package baseNoStates;
import java.util.ArrayList;
import java.util.List;

public class Space extends Area{
  private ArrayList<Door> doors = new ArrayList<>();

  public Space(String id) {
    super(id);
    doors = new ArrayList<>();
  }

  @Override
  public ArrayList<Space> getSpaces() {
    // Un Space es una hoja en la jerarquía, por lo que la lista de espacios que contiene
    // es simplemente una lista que se contiene a sí mismo.
    return new ArrayList<>(List.of(this));
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    return doors;
  }

  @Override
  public Area findAreaById(String id) {
    if (this.getId().equals(id)) {
      System.out.println("Found area by his ID: " + id);
      return this;
    }
    else {
      return null; // No se encontró y no hay más áreas hijas que explorar
    }
  }

  public void addDoor(Door door) {
    if (!this.doors.contains(door)) {
      this.doors.add(door);
    }
  }
}