package baseNoStates;

/**
 * Represents the 'Propped' state of a door.
 * This state typically means the door is being held open by an external object.
 * Closing the door from this state will also lock it.
 */
public class Propped extends DoorState {
  /**
   * Constructs a Propped state object.
   * @param door The door this state belongs to.
   */
  public Propped(Door door) {
    super(door);
  }

  /**
   * Returns the name of the state.
   * @return The string "propped".
   */
  @Override
  public String getStateName() {
    return "propped";
  }

  // Action to open the door. Does nothing, as the door is already open.
  @Override
  public void open() {
    System.out.println("The door " + door.getId() + " is already open and propped.");
  }

  // Action to close the door. This action not only closes the door but also immediately transitions it to the Locked state.
  @Override
  public void close() {
    door.setClosed(true);
    System.out.println("The door " + door.getId() + " is now closed.");
    door.setState(new Locked(door));
    System.out.println("The door " + door.getId() + " is now locked (after being propped).");
  }

  // Action to lock the door. Fails because a propped door must be closed first.
  @Override
  public void lock() {
    System.out.println("Can't lock the door " + door.getId() + " directly; must close it first.");
  }

  // Action to unlock the door. Fails because the door is not locked.
  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + "; it's already propped open.");
  }

  // Action to unlock the door temporarily. Fails because the door is not locked.
  @Override
  public void unlockShortly() {
    System.out.println("Can't unlock the door " + door.getId() + " shortly; it's already propped.");
  }

  // Action to prop the door. Does nothing, as it's already in the Propped state.
  @Override
  public void propped() {
    System.out.println("The door " + door.getId() + " is already propped.");
  }
}