package baseNoStates;

public class Unlocked extends DoorState{

  public Unlocked(Door door) {
    super(door);
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
    if (door.getStateName().equals("locked")) {
      System.out.println("Can't lock the door " + door.getId() + ", is already locked.");
    }
    else if (door.isClosed())
    {
      door.setState();
      System.out.println("The door " + door.getId()  + " is now locked.");
    }
    else{
      System.out.println("Can't lock the door " + door.getId() + ", is open.");
    }
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + ", is already unlocked.");
    /*if (door.getStateName().equals("unlocked"))
    {
      System.out.println("Can't unlock the door " + door.getId() + ", is already unlocked.");
    }
    else if (door.isClosed())
    {
      door.setState(new Unlocked(door));
      System.out.println("The door " + door.getId()  + " is now unlocked.");
    }
    else{
      System.out.println("Can't unlock the door " + door.getId() + ", is open.");
    }*/
  }

  @Override
  public void unlockShortly(){
    System.out.println("Can't unlock the door " + door.getId() + ", is already unlocked.");
}
