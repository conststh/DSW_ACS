package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.Door;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Maneja la petición de "refresh" enviada por el Simulador.
 * Propósito: El frontend necesita conocer el estado actual de TODAS las puertas
 * para pintarlas correctamente (mostrar rojo para Locked, verde para Unlocked, etc.).
 * Esta petición recolecta esos datos.
 */
public class RequestRefresh implements Request
{
  // Lista para guardar la representación JSON de cada puerta
  private final ArrayList<JSONObject> jsonsDoors = new ArrayList<>();

  @Override
  public JSONObject answerToJson()
  {
    JSONObject json = new JSONObject();
    // Envolver la lista de objetos puerta en un array padre "doors"
    json.put("doors", new JSONArray(jsonsDoors));
    return json;
  }

  @Override
  public String toString()
  {
    return "RequestRefresh{"
        + "doorsCount=" + jsonsDoors.size()
        + "}";
  }

  /**
   * El procesamiento implica iterar sobre el directorio global de puertas
   * y pedir a cada puerta que serialice su estado actual a JSON.
   */
  public void process()
  {
    // limpiar datos previos si los hubiera (aunque se crea una nueva instancia por petición)
    jsonsDoors.clear();

    for (Door door : DirectoryDoors.getAllDoors())
    {
      jsonsDoors.add(door.toJson());
    }
  }
}