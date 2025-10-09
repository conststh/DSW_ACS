package baseNoStates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Space extends Area{
  private final ArrayList<Door> doors = new ArrayList<>();

  public Space(String id) {
    super(id);
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
      return this;
    }
    System.out.println("Can't find area by his ID ");
    return null; // No se encontró y no hay más áreas hijas que explorar
  }

  public void addDoor(Door door) {
    if (!doors.contains(door)) {
      doors.add(door);
    }
  }
}