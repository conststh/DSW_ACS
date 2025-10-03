package baseNoStates;

/**
 * Abstract base class for all door states, defining the interface for handling actions.
 * This class follows the State design pattern. Concrete states must implement these methods.
 */
public abstract class DoorState {
  protected Door door;

  /**
   * Constructor for a DoorState.
   * @param door The context object (the door) this state is associated with.
   */
  protected DoorState(Door door){
    this.door = door;
  }

  /**
   * Gets the name of the state.
   * @return A string representing the state's name.
   */
  public abstract String getStateName();

  // Handles the 'open' action for the door in a specific state.
  public abstract void open();

  // Handles the 'close' action for the door in a specific state.
  public abstract void close();

  // Handles the 'lock' action for the door in a specific state.
  public abstract void lock();

  // Handles the 'unlock' action for the door in a specific state.
  public abstract void unlock();

  // Handles the 'unlock shortly' action, which typically transitions the door to a temporary unlocked state.
  public abstract void unlockShortly();

  // Handles the 'propped' action for the door in a specific state.
  public abstract void propped();
}