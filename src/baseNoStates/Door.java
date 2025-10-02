package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;


public class Door {
  private final String id;
  private boolean closed; // physically
  private DoorState state;

  public Door(String id) {
    this.id = id;
    closed = true;
    state = new Unlocked(this);
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    //TODO, Need to repass
    return state.getStateName();
  }

  public void setState(DoorState state) {
    this.state = state;
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

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

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }
}
