package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.DirectoryUsers;
import baseNoStates.Door;
import baseNoStates.User;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;

  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
            + "credential=" + credential
            + ", userName=" + userName
            + ", action=" + action
            + ", now=" + now
            + ", doorID=" + doorId
            + ", closed=" + doorClosed
            + ", authorized=" + authorized
            + ", reasons=" + reasons
            + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  // see if the request is authorized and put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryDoors.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // Esto establece el atributo booleano "authorize" de la solicitud
    door.processRequest(this);
    //aunque no hayamos autorizado la solicitud, para que si queremos podamos registrar
    // todas las solicitudes hechas al servidor como parte del procesamiento de la solicitud
    doorClosed = door.isClosed();
  }


  //El resultado se introduce en el objeto de solicitud y si no está autorizado también (solo para testing)
  private void authorize(User user, Door door) {
    if (user == null) { //Procesa que el usuario es null, aunque el usuario existe.
      this.authorized = false;
      addReason("user doesn't exists");
      this.userName = "unknownUser";
    } else {
      this.userName = user.getName();
      this.authorized = true;

    // Quién y dónde
      if (user.canBeInSpace(door.getFromSpace()) && user.canBeInSpace(door.getToSpace()) && user.canDoAction(getAction())) {
        this.authorized = true;
        addReason("User " + this.userName + " has no permissions for door " + door.getId());
      }
    // Cuándo
      if (!user.canSendRequests(now)) { //!door.isTimeAllowed
        this.authorized = false;
        addReason("Access to door " + door.getId() + " is not allowed at this time");
      }
    }
  }
}

