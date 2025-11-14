package baseNoStates;

/**
 * Representa el estado 'Bloqueado' (Locked) de una puerta
 * En este estado, la puerta está cerrada y no se puede abrir hasta que sea desbloqueada
 */
public class Locked extends DoorState{
  public Locked(Door door){
    super(door);
  }

  @Override
  public String getStateName() {
    return "locked";
  }

  @Override
  public void close(){
    System.out.println("Door " + door.getId() + " is already closed.");
  }

  @Override
  public void open(){
    System.out.println("Can't open door " + door.getId() + " it's locked.");
  }

  @Override
  public void lock(){
    System.out.println("Door " + door.getId() + " is already locked");
  }

  @Override
  public void unlock(){
    door.setState(new Unlocked(door));
  }

  @Override
  public void unlockShortly(){
    door.setState(new UnlockedShortly(door));
  }
}