package baseNoStates;

public class UnlockedShortly extends DoorState {

  public UnlockedShortly(Door door) {
    super(door);
    startLockTimer();
  }


  @Override
  public String getStateName() {
    return "unlocked_shortly";
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      System.out.println("The door " + door.getId() + " is now open.");
    } else {
      System.out.println("Can't open the door " + door.getId() + ", is already open.");
    }
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      System.out.println("Can't close the door " + door.getId() + ", is already closed.");
    } else {
      door.setClosed(true);
      System.out.println("The door " + door.getId() + " is now closed.");
    }
  }

  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      System.out.println("The door " + door.getId() + " is now locked.");
    } else {
      System.out.println("Can't lock the door " + door.getId() + ", it's open.");
    }
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + ", it's already unlocked (shortly).");
  }

  @Override
  public void unlockShortly() {
    System.out.println("The door " + door.getId() + " is already temporarily unlocked.");
  }

  @Override
  public void propped() {
  }


  private void startLockTimer() {
    new Thread(() -> {
      try {
        Thread.sleep(10000); // 10 seconds wait

        if (door.isClosed()) {
          door.setState(new Locked(door));
          System.out.println("The door " + door.getId() + " is now locked (after short unlock).");
        }

      } catch (InterruptedException e) {

        door.setState(new Propped(door));
      }
    }).start();
  }
}