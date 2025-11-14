package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Area;
import baseNoStates.DirectoryAreas;
import baseNoStates.Door;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RequestArea implements Request {
  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();


  public RequestArea(String credential, String action, LocalDateTime now, String areaId) {
    this.credential = credential;
    this.areaId = areaId;
    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
            : "invalid action " + action + " for an area request";
    this.action = action;
    this.now = now;
  }

  /* public String getAction() {
    return action;
  } //No sabemos si será necesario en un futuro */

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests) {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);
    json.put("todo", "request areas not yet implemented");
    return json;
  }

  @Override
  public String toString() {
    String requestsDoorsStr;
    if (requests.isEmpty()) {
      requestsDoorsStr = "";
    } else {
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


  //Procesar la solicitud de un área es crear las solicitudes de puerta y enviarlas a todas sus puertas.
  //Para algunas se autorizará y su acción se hará, para otras no se autorizará y no les pasará nada.
  public void process() {

    //Hacer las solicitudes de puertas y ponerlas en el area request para que se autorizen y se procesen más tarde
    Area area = DirectoryAreas.findAreaById(areaId);
    // Un area es un baseNoStates.Space o una Partition
    if (area != null) {
      //Es null cuando desde la app pulsamos en una acción pero no se selecciona ningún lugar porque en Flutter no controlo
      // de la misma manera que en JavaScript (se dan todos los parámetros)



      //Hacemos todas las solicitudes de puertas, una por cada puerta en el área, y las procesamos.
      //Buscamos las puertas en los espacios de esta área que le dan acceso.
      for (Door door : area.getDoorsGivingAccess()) {
        RequestReader requestReader = new RequestReader(credential, action, now, door.getId());
        requestReader.process();
        // after process() the area request contains the answer as the answer
        // to each individual door request, that is read by the simulator/Flutter app
        //Después de process() la solicitud de area contiene la respuesta como la respuesta
        // a cada solicitud individual, que es leída por el simulador o la app en Flutter
        requests.add(requestReader);
      }
    }

  }
}
