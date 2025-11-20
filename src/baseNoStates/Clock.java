package baseNoStates;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Reloj del sistema implementado como Singleton y Observable.
 * Proporciona un "latido" (tick) global cada segundo.
 * Los observadores (como las Puertas) se suscriben a este reloj para manejar eventos basados en tiempo.
 */
public class Clock extends Observable
{
  private static final Clock instance = new Clock();
  private static final int PERIOD_MS = 1000; // 1 segundo

  private Clock()
  {
    Timer timer = new Timer();
    // Programa una tarea para ejecutarse cada segundo
    timer.scheduleAtFixedRate(new TimerTask()
    {
      @Override
      public void run()
      {
        setChanged();       // Marca el estado como cambiado
        notifyObservers();  // Notifica a todos los observadores (Puertas)
      }
    }, 0, PERIOD_MS);
  }

  public static Clock getInstance()
  {
    return instance;
  }
}