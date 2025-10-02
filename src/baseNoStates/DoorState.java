package baseNoStates;

public abstract class DoorState {
  protected Door door;

  protected DoorState(Door door){
    this.door = door;
  }
  public abstract String getStateName();
  public abstract void open();
  public abstract void close();
  public abstract void lock();
  public abstract void unlock();
  public abstract void unlockShortly();
  public abstract void propped();


}