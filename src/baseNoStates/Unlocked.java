package baseNoStates;

/**
 * Representa el estado 'Desbloqueado' (Unlocked) de una puerta
 * En este estado, la puerta puede ser abierta, cerrada y bloqueada
 */
public class Unlocked extends DoorState {

  public Unlocked(Door door) {
    super(door);
  }

  @Override
  public String getStateName() {
    return "unlocked";
  }

  @Override
  public void open() {
    if (door.isClosed())
    {
      door.setClosed(false);
      System.out.println("The door " + door.getId() + " is now open.");
    }
    else
    {
      System.out.println("Can't open the door " + door.getId() + ", is already open.");
    }
  }

  @Override
  public void close() {
    if (door.isClosed())
    {
      System.out.println("Can't close the door " + door.getId() + ", is already closed.");
    }
    else
    {
      door.setClosed(true);
      System.out.println("The door " + door.getId() + " is now closed.");
    }
  }

  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      System.out.println("The door " + door.getId()  + " is now locked.");
    }
    else{
      System.out.println("Can't lock the door " + door.getId() + ", it's open.");
    }
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + ", it's already unlocked.");
  }

  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
    System.out.println("The door " + door.getId() + " is now unlocked shortly.");
  }

  @Override
  public void propped() {}
}