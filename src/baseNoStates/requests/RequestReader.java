package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.DirectoryUsers;
import baseNoStates.Door;
import baseNoStates.User;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Maneja una petición dirigida a un Lector específico (Puerta).
 * Valida las "4 Ws": Who (Quién), Where (Dónde), When (Cuándo) y What (Qué).
 */
public class RequestReader implements Request
{
  private final String credential; // Quién
  private final String action;     // Qué
  private final LocalDateTime now; // Cuándo
  private final String doorId;     // Dónde

  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // Explicaciones para el rechazo
  private String doorStateName;
  private boolean doorClosed;

  public RequestReader(String credential, String action, LocalDateTime now, String doorId)
  {
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public void setDoorStateName(String name)
  {
    doorStateName = name;
  }

  public String getAction()
  {
    return action;
  }

  public boolean isAuthorized()
  {
    return authorized;
  }

  public void addReason(String reason)
  {
    reasons.add(reason);
  }

  @Override
  public JSONObject answerToJson()

  {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    return json;
  }

  /**
   * Lógica principal de procesamiento:
   * 1. Encuentra al Usuario y la Puerta.
   * 2. Comprueba la lógica de autorización (authorize()).
   * 3. Si está autorizado, le dice a la Puerta que ejecute la acción.
   */
  public void process()
  {
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryDoors.findDoorById(doorId);

    // Verificación de integridad: el hardware simulado siempre debe existir
    assert door != null : "La puerta " + doorId + " no se encuentra en el directorio.";

    // Validar permisos
    authorize(user, door);

    // Delegar la ejecución a la puerta (ella volverá a comprobar isAuthorized)
    door.processRequest(this);

    // Almacenar el estado físico final para la respuesta
    doorClosed = door.isClosed();
  }

  /**
   * Valida si el usuario tiene permiso.
   * Comprueba:
   * 1. ¿Existe el usuario?
   * 2. DÓNDE: ¿Puede el usuario estar en los espacios 'origen' y 'destino'?
   * 3. QUÉ: ¿Está la acción permitida para el grupo del usuario?
   * 4. CUÁNDO: ¿Está la hora actual dentro del horario del grupo?
   */
  private void authorize(User user, Door door)
  {
    if (user == null)
    {
      this.authorized = false;
      addReason("Usuario no encontrado / credencial inválida.");
      this.userName = "desconocido";
      return;
    }

    this.userName = user.getName();

    // Comprobar permisos de Espacio (Dónde) y Acción (Qué)
    boolean allowedWhere = user.canBeInSpace(door.getFromSpace()) && user.canBeInSpace(door.getToSpace());
    boolean allowedWhat = user.canDoAction(getAction());

    if (allowedWhere && allowedWhat)
    {
      // Comprobar Horario (Cuándo)
      if (user.canSendRequests(now))
      {
        this.authorized = true;
      }
      else
      {
        this.authorized = false;
        addReason("Acceso denegado por Horario (Hora/Día no permitido).");
      }
    }
    else
    {
      this.authorized = false;
      addReason("El usuario " + this.userName + " no tiene permiso para esta Puerta/Acción.");
    }
  }

  @Override
  public String toString()
  {
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
}