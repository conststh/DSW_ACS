package baseNoStates;

/**
 * Representa el estado 'Bloqueado' (Locked) de una puerta.
 * En este estado:
 * - La puerta está físicamente cerrada.
 * - No se puede abrir.
 * - Se puede desbloquear (transición al estado Unlocked).
 */
public class Locked extends DoorState
{
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
    System.out.println("Puerta " + door.getId() + ": Ya está cerrada (y bloqueada).");
  }

  @Override
  public void open()
  {
    System.out.println("Puerta " + door.getId() + ": No se puede abrir. La puerta está BLOQUEADA.");
  }

  @Override
  public void lock()
  {
    System.out.println("Puerta " + door.getId() + ": Ya está bloqueada.");
  }

  @Override
  public void unlock()
  {
    // Transición al estado Unlocked
    door.setState(new Unlocked(door));
    System.out.println("Puerta " + door.getId() + ": Desbloqueada.");
  }

  @Override
  public void unlockShortly()
  {
    // Transición al estado UnlockedShortly
    door.setState(new UnlockedShortly(door));
    System.out.println("Puerta " + door.getId() + ": Desbloqueada temporalmente.");
  }
}