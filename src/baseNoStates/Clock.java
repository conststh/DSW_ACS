package baseNoStates;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;

// Implemented as a Singleton and Observable
public class Clock extends Observable {
  private static final Clock instance = new Clock();
  private final Timer timer;

  private Clock() {
    timer = new Timer();
    // Schedule a task to run every second
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        setChanged(); // Mark this object as having been changed
        notifyObservers(); // Notify all of its observers
      }
    }, 0, 1000); // Delay 0, period 1000ms (1 second)
  }

  public static Clock getInstance() {
    return instance;
  }
}