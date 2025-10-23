package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.Door;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RequestRefresh implements Request {
  private final ArrayList<JSONObject> jsonsDoors = new ArrayList<>();

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("doors", new JSONArray(jsonsDoors));
    // jsonDoors ha sido establecido anteriormente por process()
    return json;
  }

  @Override
  public String toString() {
    return "RequestRefresh{"
        + jsonsDoors
        + "}";
  }


  //Esto también se usa para pintar el simulador cuando la página está cargada y para mostrar
  // puertas y lectores después de pasar de locked a propped y viceversa, pulsando el botón de "Refresh Request" del simulador.
  //Además, para probar rápidamente si la solicitud de partición enviada por la app cliente en Flutter funciona,
  // recoge el estado de todas las puertas para que el simulador pueda repintar los lectores
  public void process() {
    for (Door door : DirectoryDoors.getAllDoors()) {
      jsonsDoors.add(door.toJson());
    }
  }
}
