package baseNoStates;

/**
 * Clase base abstracta para todos los estados de una puerta (Patrón State)
 * Define la interfaz que todos los estados deben implementar para manejar las acciones que puede recibir una puerta
 */
public abstract class DoorState {
  protected final Door door;

  protected DoorState(Door door){
    this.door = door;
  }

  public abstract String getStateName();

  public abstract void open();

  public abstract void close();

  public abstract void lock();

  public abstract void unlock();

  public abstract void unlockShortly();


  /**
   * Maneja un 'tick' del reloj (Patrón Observer)
   * Por defecto no hace nada, pero es sobreescrito por estados que necesiten reaccionar al paso del tiempo (UnlockedShortly)
   */
  public void tick(){}
}