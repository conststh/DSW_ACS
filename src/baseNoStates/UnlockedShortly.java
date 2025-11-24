package baseNoStates;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa el estado 'Desbloqueado Temporalmente' (Unlocked Shortly).
 * En este estado, la puerta se desbloquea temporalmente (10 segundos).
 * Utiliza el mecanismo del Patrón Observer (vía `tick()`) para comprobar si el tiempo ha expirado.
 */
public class UnlockedShortly extends DoorState
{
  private static final Logger logger = LoggerFactory.getLogger(UnlockedShortly.class);
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
      logger.info("Puerta {}: Abierta (durante desbloqueo temporal).", door.getId());
    }
    else
    {
      logger.info("Puerta {}: Ya está abierta.", door.getId());
    }
  }

  @Override
  public void close()
  {
    if (door.isClosed())
    {
      logger.info("Puerta {}: Ya está cerrada.", door.getId());
    }
    else
    {
      door.setClosed(true);
      logger.info("Puerta {}: Cerrada.", door.getId());
    }
  }

  @Override
  public void lock()
  {
    if (door.isClosed())
    {
      door.setState(new Locked(door));
      logger.info("Puerta {}: Bloqueada manualmente.", door.getId());
    }
    else
    {
      logger.warn("Puerta {}: No se puede bloquear, está abierta.", door.getId());
    }
  }

  @Override
  public void unlock()
  {
    logger.info("Puerta {}: Ya está desbloqueada (temporalmente).", door.getId());
  }

  @Override
  public void unlockShortly()
  {
    logger.info("Puerta {}: Ya está en modo de desbloqueo temporal.", door.getId());
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
        logger.info("Puerta {}: Tiempo expirado. Bloqueada automáticamente.", door.getId());
      }
      else
      {
        // Si está abierta, pasa a Propped (estado de alarma/aviso)
        door.setState(new Propped(door));
        logger.warn("Door {}: Tiempo expirado mientras estaba abierta. Entrando en modo PROPPED", door.getId());
      }
    }
  }
}