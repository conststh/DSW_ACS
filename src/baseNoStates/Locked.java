package baseNoStates;

/**
 * Represents the 'Locked' state of a door.
 * In this state, the door is closed and cannot be opened until it is unlocked.
 */
public class Locked extends DoorState{
  /**
   * Constructs a Locked state object.
   * @param door The door this state belongs to.
   */
  public Locked(Door door){
    super(door);
  }

  /**
   * Returns the name of the state.
   * @return The string "locked".
   */
  @Override
  public String getStateName() {
    return "locked";
  }

  // Action to close the door. Does nothing, as a locked door is already closed.
  @Override
  public void close(){
    System.out.println("Door " + door.getId() + " is already closed.");
  }

  // Action to open the door. Fails because the door is locked.
  @Override
  public void open(){
    System.out.println("Can't open door " + door.getId() + " it's locked.");
  }

  // Action to lock the door. Does nothing, as the door is already locked.
  @Override
  public void lock(){
    System.out.println("Door " + door.getId() + " is already locked");
  }

  // Action to unlock the door. Transitions the door's state to Unlocked.
  @Override
  public void unlock(){
    door.setState(new Unlocked(door));
  }

  // Action to unlock the door temporarily. Transitions the door's state to UnlockedShortly.
  @Override
  public void unlockShortly(){
    door.setState(new UnlockedShortly(door));
  }

  // Action to prop the door. This action has no effect in the Locked state.
  @Override
  public void propped() {
    // This action is not applicable when the door is locked.
  }
}