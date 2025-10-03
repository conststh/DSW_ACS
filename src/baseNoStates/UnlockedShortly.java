package baseNoStates;

/**
 * Represents a temporary 'Unlocked' state.
 * Upon entering this state, a timer starts. When the timer expires, if the door
 * is closed, it automatically transitions to the 'Locked' state.
 */
public class UnlockedShortly extends DoorState {
  /**
   * Constructs an UnlockedShortly state object and starts the auto-lock timer.
   * @param door The door this state belongs to.
   */
  public UnlockedShortly(Door door) {
    super(door);
    startLockTimer();
  }

  /**
   * Returns the name of the state.
   * @return The string "unlocked_shortly".
   */
  @Override
  public String getStateName() {
    return "unlocked_shortly";
  }

  // Action to open the door. Succeeds if the door is physically closed.
  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      System.out.println("The door " + door.getId() + " is now open.");
    } else {
      System.out.println("Can't open the door " + door.getId() + ", is already open.");
    }
  }

  // Action to close the door. Succeeds if the door is physically open.
  @Override
  public void close() {
    if (door.isClosed()) {
      System.out.println("Can't close the door " + door.getId() + ", is already closed.");
    } else {
      door.setClosed(true);
      System.out.println("The door " + door.getId() + " is now closed.");
    }
  }

  // Action to lock the door. Succeeds if the door is closed, transitioning it to the Locked state.
  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      System.out.println("The door " + door.getId() + " is now locked.");
    } else {
      System.out.println("Can't lock the door " + door.getId() + ", it's open.");
    }
  }

  // Action to unlock the door. Does nothing, as it is already in an unlocked state.
  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + ", it's already unlocked (shortly).");
  }

  // Action to unlock the door temporarily. Does nothing, as it is already in this state.
  @Override
  public void unlockShortly() {
    System.out.println("The door " + door.getId() + " is already temporarily unlocked.");
  }

  // Action to prop the door. This action has no effect in this state.
  @Override
  public void propped() {
    // This action is not applicable in this state.
  }

  /**
   * Starts a background thread that waits for a specific duration (10 seconds).
   * After the delay, if the door is still closed, it changes its state to Locked.
   * If the thread is interrupted, it transitions the door to the Propped state.
   */
  private void startLockTimer() {
    new Thread(() -> {
      try {
        Thread.sleep(10000); // 10 seconds wait

        if (door.isClosed()) {
          door.setState(new Locked(door));
          System.out.println("The door " + door.getId() + " is now locked (after short unlock).");
        }
        // If the door is open after the timer, it remains in the UnlockedShortly state,
        // which might be an unintended behavior. It will not auto-lock.
      } catch (InterruptedException e) {
        // This block is executed if the timer thread is interrupted.
        // The business logic implies that an interruption suggests the door is propped.
        door.setState(new Propped(door));
      }
    }).start();
  }
}