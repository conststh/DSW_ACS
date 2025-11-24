package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa el estado 'Desbloqueado' (Unlocked) de una puerta.
 * En este estado:
 * - La puerta está desbloqueada y se puede abrir o cerrar libremente.
 * - Se puede bloquear (transición al estado Locked).
 */
public class Unlocked extends DoorState
{
  private static final Logger logger = LoggerFactory.getLogger(Unlocked.class);

  public Unlocked(Door door)
  {
    super(door);
  }

  @Override
  public String getStateName()
  {
    return "unlocked";
  }

  @Override
  public void open()
  {
    if (door.isClosed())
    {
      door.setClosed(false);
      logger.info("Puerta {}: Abierta.", door.getId());
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
    // Solo una puerta cerrada puede bloquearse
    if (door.isClosed())
    {
      door.setState(new Locked(door));
      logger.info("Puerta {}: Bloqueada.", door.getId());
    }
    else
    {
      logger.warn("Puerta {}: No se puede bloquear, está abierta. Ciérrala primero.", door.getId());
    }
  }

  @Override
  public void unlock()
  {
    logger.info("Puerta {}: Ya está desbloqueada.", door.getId());
  }

  @Override
  public void unlockShortly()
  {
    door.setState(new UnlockedShortly(door));
    logger.info("Puerta {}: Cambiando a modo de desbloqueo temporal.", door.getId());
  }
}