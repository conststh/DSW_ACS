package baseNoStates;

/**
 * Clase base abstracta para el patrón State.
 * Define la interfaz común que todos los estados concretos (Locked, Unlocked, etc.) deben implementar.
 * Cada estado mantiene una referencia al contexto (Door) para disparar transiciones
 */
public abstract class DoorState
{
  protected final Door door; // Referencia al Contexto

  protected DoorState(Door door)
  {
    this.door = door;
  }

  // Identificador para el simulador/UI
  public abstract String getStateName();

  // Acciones que cambian dependiendo del estado
  public abstract void open();
  public abstract void close();
  public abstract void lock();
  public abstract void unlock();
  public abstract void unlockShortly();

  /**
   * Maneja el "tick" temporal del Reloj (Patrón Observer).
   * Por defecto, la mayoría de estados ignoran el tiempo, así que la implementación base está vacía.
   * Solo los estados sensibles al tiempo (como UnlockedShortly) sobrescriben esto.
   */
  public void tick() {}
}