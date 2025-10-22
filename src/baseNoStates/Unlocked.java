package baseNoStates;

/**
 * Represents the 'Unlocked' state of a door.
 * In this state, the door can be opened, closed, and locked.
 */
public class Unlocked extends DoorState {

  /**
   * Constructs an Unlocked state object.
   * @param door The door this state belongs to.
   */
  public Unlocked(Door door) {
    super(door);
  }

  /**
   * Returns the name of the state.
   * @return The string "unlocked".
   */
  @Override
  public String getStateName() {
    return "unlocked";
  }

  // Action to open the door. Succeeds if the door is physically closed.
  @Override
  public void open() {
    if (door.isClosed())
    {
      door.setClosed(false);
      System.out.println("The door " + door.getId() + " is now open.");
    }
    else
    {
      System.out.println("Can't open the door " + door.getId() + ", is already open.");
    }
  }

  // Action to close the door. Succeeds if the door is physically open.
  @Override
  public void close() {
    if (door.isClosed())
    {
      System.out.println("Can't close the door " + door.getId() + ", is already closed.");
    }
    else
    {
      door.setClosed(true);
      System.out.println("The door " + door.getId() + " is now closed.");
    }
  }

  // Action to lock the door. Succeeds if the door is physically closed, transitioning its state to Locked.
  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      System.out.println("The door " + door.getId()  + " is now locked.");
    }
    else{
      System.out.println("Can't lock the door " + door.getId() + ", it's open.");
    }
  }

  // Action to unlock the door. Does nothing as the door is already unlocked.
  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + ", it's already unlocked.");
  }

  // Action to unlock the door temporarily. Does nothing as the door is already unlocked.
  @Override
  public void unlockShortly() {
    // El estado debe cambiar a UnlockedShortly para iniciar el temporizador.
    door.setState(new UnlockedShortly(door));
    System.out.println("The door " + door.getId() + " is now unlocked shortly.");
  }

  // Action to prop the door. This action has no effect in the Unlocked state.
  @Override
  public void propped() {
    // This action is not applicable in this state.
    // Propping might be an event that transitions the door to a 'Propped' state.
  }
}