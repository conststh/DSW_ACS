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
    System.out.println("Puerta " + door.getId() + ": Ya está abierta (Propped).");
  }

  @Override
  public void close()
  {
    door.setClosed(true);
    System.out.println("Puerta " + door.getId() + ": Cierre forzado desde estado Propped.");
    // Una vez cerrada, una puerta propped vuelve a Locked (medida de seguridad)
    door.setState(new Locked(door));
    System.out.println("Puerta " + door.getId() + ": Ahora Bloqueada.");
  }

  @Override
  public void lock()
  {
    System.out.println("Puerta " + door.getId() + ": No se puede bloquear directamente. Ciérrala primero.");
  }

  @Override
  public void unlock()
  {
    System.out.println("Puerta " + door.getId() + ": No se puede desbloquear. Está mantenida abierta (Propped).");
  }

  @Override
  public void unlockShortly()
  {
    System.out.println("Puerta " + door.getId() + ": No se puede desbloquear temporalmente. Está Propped.");
  }
}