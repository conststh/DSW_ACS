package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Punto de entrada de la aplicación.
 * Inicializa los directorios de datos (Puertas, Usuarios) e inicia el WebServer.
 */

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main
{
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  static void main(String[] args)
  {
    logger.info("Iniciando Access Control System...");
    // Inicializar datos de simulación
    DirectoryDoors.makeDoors();
    DirectoryUsers.getInstance();
    DirectoryUsers.makeUsers();

    // Iniciar el WebServer para escuchar peticiones
    new WebServer();
  }
}
