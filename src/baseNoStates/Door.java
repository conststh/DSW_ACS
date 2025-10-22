package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;
import java.time.LocalDateTime;

/**
 * Represents the main context class in the State design pattern.
 * A Door object holds a specific state and delegates actions to its current state object.
 * It also manages its physical condition (open or closed).
 */
public class Door implements Observer {
  private final String id;
  private boolean closed; // Represents the physical state of the door (true if closed, false if open)
  private DoorState state; // The current state of the door (e.g., Locked, Unlocked)
  private final Space fromSpace;
  private final Space toSpace;

  /**
   * Constructs a new Door with a given ID.
   * By default, a new door is physically closed and in the 'Unlocked' state.
   * @param id The unique identifier for the door.
   */
  public Door(String id, Space from, Space to) {
    this.id = id;
    this.closed = true;
    this.state = new Unlocked(this);
    this.fromSpace = from;
    this.toSpace = to;
    // Every door, when created, starts observing the clock
    Clock.getInstance().addObserver(this);
  }
  @Override
  public void update(Observable o, Object arg) {
    // We delegate the handling of the "tick" to the current state
    state.tick();
  }

  /**
   * Gets the unique identifier of the door.
   * @return The door's ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name of the current state of the door.
   * @return A string representing the current state (e.g., "locked", "unlocked").
   */
  public String getStateName() {
    return state.getStateName();
  }

  /**
   * Sets the current state of the door. This method is typically called by
   * state objects to transition the door to a new state.
   * @param state The new state for the door.
   */
  public void setState(DoorState state) {
    this.state = state;
  }

  /**
   * Checks if the door is physically closed.
   * @return true if the door is closed, false otherwise.
   */
  public boolean isClosed() {
    return closed;
  }

  public Space getFromSpace() {
    return fromSpace;
  }

  public Space getToSpace() {
    return toSpace;
  }

  /**
   * Sets the physical state of the door.
   * @param closed true to set the door as closed, false to set it as open.
   */
  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  /**
   * Processes an action request for the door, but only if the request is authorized.
   * It delegates the specific action to the current state object.
   * @param request The request containing the action to be performed.
   */
  public void processRequest(RequestReader request) {
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  /**
   * Executes a specific action by delegating it to the current state.
   * This is a helper method for processRequest.
   * @param action The string representing the action to perform (e.g., "OPEN", "LOCK").
   */
  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        state.open();
        break;
      case Actions.CLOSE:
        state.close();
        break;
      case Actions.LOCK:
        state.lock();
        break;
      case Actions.UNLOCK:
        state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlockShortly();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  /**
   * Provides a string representation of the Door object, including its ID,
   * physical state, and logical state.
   * @return A descriptive string for the door.
   */
  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  /**
   * Converts the door's current state to a JSONObject.
   * @return A JSONObject containing the door's id, state name, and closed status.
   */
  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    json.put("from", fromSpace.getId());
    json.put("to", toSpace.getId());
    return json;
  }
}