package baseNoStates;

/**
 * Representa el estado 'Mantenida Abierta' (Propped) de una puerta
 * Este estado ocurre si una puerta estaba en 'UnlockedShortly', se abrió, y el temporizador expiró antes de que se cerrara
 */
public class Propped extends DoorState {

  public Propped(Door door) {
    super(door);
  }

  @Override
  public String getStateName() {
    return "propped";
  }

  @Override
  public void open() {
    System.out.println("The door " + door.getId() + " is already open and propped.");
  }

  @Override
  public void close() {
    door.setClosed(true);
    System.out.println("The door " + door.getId() + " is now closed.");
    // Transiciona a Locked, ya que el estado 'propped' implica que el permiso de desbloqueo expiró
    door.setState(new Locked(door));
    System.out.println("The door " + door.getId() + " is now locked (after being propped).");
  }

  @Override
  public void lock() {
    System.out.println("Can't lock the door " + door.getId() + " directly; must close it first.");
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + "; it's already propped open.");
  }

  @Override
  public void unlockShortly() {
    System.out.println("Can't unlock the door " + door.getId() + " shortly; it's already propped.");
  }

  @Override
  public void propped() {
    System.out.println("The door " + door.getId() + " is already propped.");
  }
}