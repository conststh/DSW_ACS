package baseNoStates;

/**
 * Representa el estado 'Desbloqueado' (Unlocked) de una puerta.
 * En este estado:
 * - La puerta está desbloqueada y se puede abrir o cerrar libremente.
 * - Se puede bloquear (transición al estado Locked).
 */
public class Unlocked extends DoorState
{
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
      System.out.println("Puerta " + door.getId() + ": Abierta.");
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
    // Solo una puerta cerrada puede bloquearse
    if (door.isClosed())
    {
      door.setState(new Locked(door));
      System.out.println("Puerta " + door.getId()  + ": Bloqueada.");
    }
    else
    {
      System.out.println("Puerta " + door.getId() + ": No se puede bloquear, está abierta. Ciérrala primero.");
    }
  }

  @Override
  public void unlock()
  {
    System.out.println("Puerta " + door.getId() + ": Ya está desbloqueada.");
  }

  @Override
  public void unlockShortly()
  {
    door.setState(new UnlockedShortly(door));
    System.out.println("Puerta " + door.getId() + ": Cambiado a modo desbloqueo temporal.");
  }
}