package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa una Puerta en el sistema.
 * Patrones de Diseño aplicados:
 * 1. **Patrón State (Contexto):** La puerta delega todo el comportamiento dinámico (abrir, cerrar, bloquear...)
 * a su objeto de estado actual (`state`).
 * 2. **Patrón Observer (Observador):** La puerta escucha al `Clock` (Observable).
 * Cada segundo (tick), actualiza su estado (necesario para estados temporales como UnlockedShortly).
 */
public class Door implements Observer
{
  private static final Logger logger = LoggerFactory.getLogger(Door.class);
  private final String id;
  private boolean closed;
  private DoorState state; // Referencia al estado actual (Patrón State)
  private final Space fromSpace;
  private final Space toSpace;

  public Door(String id, Space from, Space to)
  {
    this.id = id;
    this.closed = true; // Estado físico por defecto
    this.state = new Unlocked(this); // Estado lógico por defecto
    this.fromSpace = from;
    this.toSpace = to;
    // Registramos esta puerta como observadora del Reloj global para recibir los "ticks"
    Clock.getInstance().addObserver(this);
  }

  /**
   * Punto de entrada para procesar una petición de usuario en esta puerta específica.
   * La petición debe estar autorizada antes de realizar ninguna acción.
   */
  public void processRequest(RequestReader request) {
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
      logger.info("Door {} executing action: {}", id, action);
    } else {
      logger.warn("Door {}: Unauthorized request for action {}", id, request.getAction());
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
        // Ejemplo de log de advertencia solicitado
        if (getStateName().equals("unlocked"))
        {
          logger.warn("Puerta {} Ya está desbloqueada.", id);
        }
        state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlockShortly();
        break;
      default:
        logger.warn("Puerta {}: Acción desconocida '{}'", id, action);
    }
  }
  /**
   * Método llamado por el Reloj (Observable) cada segundo.
   * Delegamos esta señal de tiempo al estado actual, ya que solo algunos estados
   * (como UnlockedShortly) se preocupan por el paso del tiempo.
   */
  @Override
  public void update(Observable o, Object arg)
  {
    state.tick();
  }

  public String getId()
  {
    return id;
  }

  public String getStateName()
  {
    return state.getStateName();
  }

  // Permite cambiar el estado en tiempo de ejecución (Transición del Patrón State)
  public void setState(DoorState state)
  {
    this.state = state;
  }

  public boolean isClosed()
  {
    return closed;
  }

  public Space getFromSpace()
  {
    return fromSpace;
  }

  public Space getToSpace()
  {
    return toSpace;
  }

  public void setClosed(boolean closed)
  {
    this.closed = closed;
  }

  @Override
  public String toString()
  {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson()
  {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    json.put("from", fromSpace.getId());
    json.put("to", toSpace.getId());
    return json;
  }
}