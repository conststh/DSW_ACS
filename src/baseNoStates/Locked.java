package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Representa el estado 'Bloqueado' (Locked) de una puerta.
 * En este estado:
 * - La puerta está físicamente cerrada.
 * - No se puede abrir.
 * - Se puede desbloquear (transición al estado Unlocked).
 */
public class Locked extends DoorState
{
  private static final Logger logger = LoggerFactory.getLogger(Locked.class);

  public Locked(Door door)
  {
    super(door);
  }

  @Override
  public String getStateName()
  {
    return "locked";
  }

  @Override
  public void close()
  {
    logger.warn("Puerta {}: Ya está cerrada (y bloqueada).", door.getId());
  }

  @Override
  public void open()
  {
    logger.warn("Puerta {}: No se puede abrir. La puerta está BLOQUEADA.", door.getId());
  }

  @Override
  public void lock()
  {
    logger.info("Puerta {}: Ya está bloqueada.", door.getId());
  }

  @Override
  public void unlock()
  {
    // Transición al estado Unlocked
    door.setState(new Unlocked(door));
    logger.info("Puerta {}: Desbloqueada.", door.getId());
  }

  @Override
  public void unlockShortly()
  {
    // Transición al estado UnlockedShortly
    door.setState(new UnlockedShortly(door));
    logger.info("Puerta {}: Desbloqueada temporalmente.", door.getId());
  }
}