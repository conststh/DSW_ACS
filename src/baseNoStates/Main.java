package baseNoStates;
/**
 * Punto de entrada de la aplicación.
 * Inicializa los directorios de datos (Puertas, Usuarios) e inicia el WebServer.
 */

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

public class Main
{
  static void main(String[] args)
  {
    // Inicializar datos de simulación
    DirectoryDoors.makeDoors();
    DirectoryUsers.makeUsers();

    // Iniciar el WebServer para escuchar peticiones
    new WebServer();
  }
}
