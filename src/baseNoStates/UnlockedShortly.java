package baseNoStates;

import java.time.LocalDateTime;

/**
 * Representa el estado 'Desbloqueado Temporalmente' (Unlocked Shortly).
 * En este estado, la puerta se desbloquea temporalmente (10 segundos).
 * Utiliza el mecanismo del Patrón Observer (vía `tick()`) para comprobar si el tiempo ha expirado.
 */
public class UnlockedShortly extends DoorState
{
  private final LocalDateTime expiryTime;
  private static final int DURATION_SECONDS = 10;

  public UnlockedShortly(Door door)
  {
    super(door);
    // Calculamos cuándo debe expirar este estado (Hora actual + 10 segundos)
    this.expiryTime = LocalDateTime.now().plusSeconds(DURATION_SECONDS);
  }

  @Override
  public String getStateName()
  {
    return "unlocked_shortly";
  }

  @Override
  public void open()
  {
    if (door.isClosed())
    {
      door.setClosed(false);
      System.out.println("Puerta " + door.getId() + ": Abierta (durante desbloqueo temporal).");
    }
    else
    {
      System.out.println("Puerta " + door.getId() + ": Ya está abierta.");
    }
  }

  @Override
  public void close()
  {
    if (door.isClosed())
    {
      System.out.println("Puerta " + door.getId() + ": Ya está cerrada.");
    }
    else
    {
      door.setClosed(true);
      System.out.println("Puerta " + door.getId() + ": Cerrada.");
    }
  }

  @Override
  public void lock()
  {
    if (door.isClosed())
    {
      door.setState(new Locked(door));
      System.out.println("Puerta " + door.getId() + ": Bloqueada manualmente.");
    }
    else
    {
      System.out.println("Puerta " + door.getId() + ": No se puede bloquear, está abierta.");
    }
  }

  @Override
  public void unlock()
  {
    System.out.println("Puerta " + door.getId() + ": Ya está desbloqueada (temporalmente).");
  }

  @Override
  public void unlockShortly()
  {
    System.out.println("Puerta " + door.getId() + ": Ya está en modo de desbloqueo temporal.");
  }

  /**
   * Llamado cada segundo por la Puerta. Comprueba si el temporizador de 10 segundos ha expirado.
   */
  @Override
  public void tick()
  {
    if (LocalDateTime.now().isAfter(expiryTime))
    {
      // El tiempo ha expirado. Comprobamos el estado físico de la puerta.
      if (door.isClosed())
      {
        // Si está cerrada, se bloquea automáticamente (comportamiento seguro)
        door.setState(new Locked(door));
        System.out.println("Puerta " + door.getId() + ": Tiempo expirado. Bloqueada automáticamente.");
      }
      else
      {
        // Si está abierta, pasa a Propped (estado de alarma/aviso)
        door.setState(new Propped(door));
        System.out.println("Puerta " + door.getId() + ": Tiempo expirado mientras estaba ABIERTA. Estado -> Propped.");
      }
    }
  }
}