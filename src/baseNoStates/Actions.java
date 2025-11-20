package baseNoStates;

/**
 * Constantes para identificadores de Acción para evitar cadenas no definidas en el código.
 */
public final class Actions
{
  // Acciones para peticiones de Lector y Puerta
  public static final String LOCK = "lock";
  public static final String UNLOCK = "unlock";
  public static final String UNLOCK_SHORTLY = "unlock_shortly";
  public static final String OPEN = "open";
  public static final String CLOSE = "close";

  // Constructor privado para evitar instanciación
  private Actions() {}
}