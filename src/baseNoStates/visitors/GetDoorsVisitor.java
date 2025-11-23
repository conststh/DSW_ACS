package baseNoStates.visitors;

import baseNoStates.Door;
import baseNoStates.Partition;
import baseNoStates.Space;
import baseNoStates.Area;
import java.util.ArrayList;

public class GetDoorsVisitor implements AreaVisitor {
  private final ArrayList<Door> doors = new ArrayList<>();

  @Override
  public void visit(Space space) {
    doors.addAll(space.getDoors());
  }

  @Override
  public void visit(Partition partition) {
    for (Area child : partition.getChildren()) {
      child.accept(this);
    }
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }
}

