package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Area;
import baseNoStates.DirectoryAreas;
import baseNoStates.Door;
import baseNoStates.visitors.GetDoorsVisitor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Representa una petición de alto nivel aplicada a un Área completa (Espacio o Partición).
 * Actúa como un Comando Compuesto: desglosa una petición de área en múltiples objetos RequestReader
 * (uno por cada puerta en el área).
 */
public class RequestArea implements Request
{
  private static final Logger logger = LoggerFactory.getLogger(RequestArea.class);

  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();

  public RequestArea(String credential, String action, LocalDateTime now, String areaId)
  {
    this.credential = credential;
    this.areaId = areaId;
    this.now = now;
    // Validación: Las peticiones de área normalmente solo soportan lógica de Bloqueo/Desbloqueo
    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
        : "Acción inválida " + action + " para Petición de Área (solo LOCK/UNLOCK permitidos).";
    this.action = action;
  }

  @Override
  public JSONObject answerToJson()
  {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);

    // Agregar respuestas de todas las peticiones hijas
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests)
    {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);
    return json;
  }

  /**
   * Procesa la petición de área:
   * 1. Encontrando el Área objetivo (Espacio o Partición).
   * 2. Encontrando todas las puertas que dan acceso a este Área (recursivo vía Composite).
   * 3. Creando y procesando un `RequestReader` genérico para cada puerta encontrada.
   */
  public void process()
  {
    Area area = DirectoryAreas.findAreaById(areaId);

    if (area != null)
    {
      GetDoorsVisitor visitor = new GetDoorsVisitor();
      area.accept(visitor);
      // Iterar sobre todas las puertas pertenecientes a esta área
      for (Door door : visitor.getDoors())
      {
        // Crear una petición específica para esta puerta individual
        RequestReader requestReader = new RequestReader(credential, action, now, door.getId());

        // Procesarla inmediatamente
        requestReader.process();

        // Almacenarla para agregar los resultados más tarde en answerToJson()
        requests.add(requestReader);
      }
    }
    else
    {
      logger.warn("Área '{}' no encontrada al procesar RequestArea con credencial '{}'.", areaId, credential);
    }
  }

  @Override
  public String toString()
  {
    String requestsDoorsStr;
    if (requests.isEmpty())
    {
      requestsDoorsStr = "";
    }
    else
    {
      requestsDoorsStr = requests.toString();
    }
    return "Request{"
        + "credential=" + credential
        + ", action=" + action
        + ", now=" + now
        + ", areaId=" + areaId
        + ", requestsDoors=" + requestsDoorsStr
        + "}";
  }
}