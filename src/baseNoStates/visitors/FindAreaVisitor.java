package baseNoStates.visitors;

import baseNoStates.Area;
import baseNoStates.Partition;
import baseNoStates.Space;

public class FindAreaVisitor implements AreaVisitor {
  private final String idToFind;
  private Area foundArea = null;

  public FindAreaVisitor(String id) {
    this.idToFind = id;
  }

  @Override
  public void visit(Space space) {
    if (space.getId().equals(idToFind)) {
      foundArea = space;
    }
  }

  @Override
  public void visit(Partition partition) {
    if (partition.getId().equals(idToFind)) {
      foundArea = partition;
      return;
    }
    for (Area child : partition.getChildren()) {
      child.accept(this);
      if (foundArea != null) return; // Detener si ya se encontró
    }
  }

  public Area getResult() {
    return foundArea;
  }
}

