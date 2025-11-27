package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa el estado 'Mantenida Abierta' (Propped).
 * Este es un estado anormal de alarma que ocurre cuando una puerta estaba en UnlockedShortly,
 * se abrió, y no se cerró antes de que el temporizador expirara. La puerta permanece abierta
 * pero técnicamente "caducada".
 */
public class Propped extends DoorState
{
  private static final Logger logger = LoggerFactory.getLogger(Propped.class);
  public Propped(Door door)
  {
    super(door);
    logger.warn("ALARM: Door {} has entered PROPPED state!", door.getId());
  }

  @Override
  public String getStateName()
  {
    return "propped";
  }

  @Override
  public void open()
  {
    String info = "Puerta " + door.getId() + ": Ya está abierta (Propped).";
    logger.warn(info);
  }

  @Override
  public void close()
  {
    door.setClosed(true);
    String info = "Puerta " + door.getId() + ": Cierre forzado desde estado Propped.";
    logger.info(info);
    // Una vez cerrada, una puerta propped vuelve a Locked (medida de seguridad)
    door.setState(new Locked(door));
    info = "Puerta " + door.getId() + ": Ahora Bloqueada.";
    logger.info(info);
  }

  @Override
  public void lock() {
    String info = "Puerta " + door.getId() + ": No se puede bloquear directamente. Ciérrala primero.";
    logger.warn(info);
  }

  @Override
  public void unlock()
  {
    String info = "Puerta " + door.getId() + ": No se puede desbloquear. Está mantenida abierta (Propped).";
    logger.warn(info);
  }

  @Override
  public void unlockShortly()
  {
    String info = "Puerta " + door.getId() + ": No se puede desbloquear temporalmente. Está Propped.";
    logger.warn(info);
  }
}