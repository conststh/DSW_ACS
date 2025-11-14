package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Representa la clase 'Contexto' en el patrón de diseño State
 * Una Puerta mantiene una referencia a su estado actual y delega las acciones a ese objeto de estado
 * También implementa Observer para reaccionar a los ticks del reloj
 */
public class Door implements Observer {
  private final String id;
  private boolean closed;
  private DoorState state;
  private final Space fromSpace;
  private final Space toSpace;

  public Door(String id, Space from, Space to) {
    this.id = id;
    this.closed = true;
    this.state = new Unlocked(this);
    this.fromSpace = from;
    this.toSpace = to;
    // Cada puerta se suscribe al Reloj al ser creada (Patrón Observer)
    Clock.getInstance().addObserver(this);
  }

  /**
   * Método llamado por el Reloj (Observable) cuando hay un 'tick'
   * Delega el manejo del tick al estado actual
   */
  @Override
  public void update(Observable o, Object arg) {
    state.tick();
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state.getStateName();
  }

  public void setState(DoorState state) {
    this.state = state;
  }

  public boolean isClosed() {
    return closed;
  }

  public Space getFromSpace() {
    return fromSpace;
  }

  public Space getToSpace() {
    return toSpace;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  // Procesa una solicitud de acción (RequestReader) para la puerta, solo si la solicitud está autorizada
  public void processRequest(RequestReader request) {
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  // Ejecuta una acción específica delegándola al estado actual
   private void doAction(String action) {
    // Este switch es el núcleo de la delegación del patrón State.
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
        // Asegura que no se pase una acción desconocida
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
    json.put("from", fromSpace.getId());
    json.put("to", toSpace.getId());
    return json;
  }
}