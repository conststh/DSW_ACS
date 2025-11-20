package baseNoStates.requests;

import org.json.JSONObject;

/**
 * Interfaz común para todas las Peticiones (Requests) procesadas por el WebServer.
 * Esto permite al Servidor tratar diferentes tipos de peticiones
 * (Peticiones de Lector, Peticiones de Área, Peticiones de Refresh).
 */
public interface Request
{
  /**
   * Genera la respuesta JSON que se enviará de vuelta al cliente (Simulador/App).
   */
  JSONObject answerToJson();

  /**
   * Devuelve una representación en cadena para propósitos de log/depuración.
   */
  String toString();

  /**
   * Ejecuta la lógica de negocio de la petición.
   * - Para peticiones de Lector: autoriza y mueve la puerta.
   * - Para peticiones de Área: delega a múltiples puertas.
   * - Para peticiones de Refresco: recolecta el estado de todas las puertas.
   */
  void process();
}