package baseNoStates;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

// Implementado como Singleton y Observable
public class Clock extends Observable {
  private static final Clock instance = new Clock();
  private final Timer timer;

  private Clock() {
    timer = new Timer();
    // Organiza una tarea para que corra cada segundo
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        setChanged(); // Marca este objeto como cambiado
        notifyObservers(); // Notifica a todos sus observadores
      }
    }, 0, 1000); // Delay 0, periodo de 1000ms (1 segundo)
  }

  public static Clock getInstance() {
    return instance;
  }
}