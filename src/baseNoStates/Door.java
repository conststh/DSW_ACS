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
    switch (action) { //Cambiar cosas para que dependa de getStateName
      case Actions.OPEN:
          if (closed) { //Implementar estados?
              closed = false;
          }else {
              System.out.println("Can't open door " + id + " because it's already open");
          }

        break;
      case Actions.CLOSE:
        if (closed) { //Implementar estados?
          System.out.println("Can't close door " + id + " because it's already closed");
        } else {
          closed = true;
        }
        break;
      case Actions.LOCK:
          if (!closed){
              System.out.println("Can't lock door " + id + " because it's open.");
          }
          else{
              state = new Locked(this);
              break;
          }
      case Actions.UNLOCK:
        // TODO
        state = new Unlocked(this);
        break;
      case Actions.UNLOCK_SHORTLY:
        // TODO
        state = new UnlockedShortly(this);
        //añadir sleep de 10s? esperar cambio de estado? idk
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
