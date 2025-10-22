package baseNoStates;

import java.time.LocalDateTime;

public class UnlockedShortly extends DoorState {

  private final LocalDateTime expiryTime;

  public UnlockedShortly(Door door) {
    super(door);
    // Set the expiry time to 10 seconds from now
    this.expiryTime = LocalDateTime.now().plusSeconds(10);
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

  /**
   * This method is called by the Door (Observer) every time the Clock (Observable) ticks.
   */
  @Override
  public void tick() {
    // Check if the current time is after the expiry time
    if (LocalDateTime.now().isAfter(expiryTime)) {
        // Timer has finished. Now, apply the correct transition logic based on the door's physical state.
      if (door.isClosed()) {
        door.setState(new Locked(door));
        System.out.println("The door " + door.getId() + " is now locked (after short unlock).");
      } else {
      // This is the logic that was missing/buggy
        door.setState(new Propped(door));
        System.out.println("The door " + door.getId() + " is now propped (after short unlock timed out while open).");
      }
    }
  }
}