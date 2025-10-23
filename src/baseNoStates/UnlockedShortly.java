package baseNoStates;

import java.time.LocalDateTime;

/**
 * Representa el estado 'Desbloqueado Temporalmente' (UnlockedShortly)
 * Este estado se activa con 'unlockShortly' y dura un tiempo limitado (10 segundos)
 * Utiliza el método 'tick' para gestionar el temporizador
 */
public class UnlockedShortly extends DoorState {
  private final LocalDateTime expiryTime;

  public UnlockedShortly(Door door) {
    super(door);
    // Define el tiempo de expiración (ahora + 10 segundos)
    this.expiryTime = LocalDateTime.now().plusSeconds(10);
  }

  @Override
  public String getStateName() {
    return "unlocked_shortly";
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      System.out.println("The door " + door.getId() + " is now open.");
    } else {
      System.out.println("Can't open the door " + door.getId() + ", is already open.");
    }
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      System.out.println("Can't close the door " + door.getId() + ", is already closed.");
    } else {
      door.setClosed(true);
      System.out.println("The door " + door.getId() + " is now closed.");
    }
  }

  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      System.out.println("The door " + door.getId() + " is now locked.");
    } else {
      System.out.println("Can't lock the door " + door.getId() + ", it's open.");
    }
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock the door " + door.getId() + ", it's already unlocked (shortly).");
  }

  @Override
  public void unlockShortly() {
    System.out.println("The door " + door.getId() + " is already temporarily unlocked.");
  }

  @Override
  public void propped() {}

  /**
   * Método llamado por la Puerta (Observer) cada vez que el Reloj (Observable) envía un 'tick'
   * Verifica si el tiempo de expiración ha pasado
   */
  @Override
  public void tick() {
    // Comprueba si la hora actual es posterior a la hora de expiración
    if (LocalDateTime.now().isAfter(expiryTime)) {
      // El temporizador ha terminado
      if (door.isClosed()) {
        // Si la puerta está CERRADA cuando expira el tiempo, se bloquea
        door.setState(new Locked(door));
        System.out.println("The door " + door.getId() + " is now locked (after short unlock).");
      } else {
        // Si la puerta está ABIERTA cuando expira el tiempo, entra en estado 'Propped'
        door.setState(new Propped(door));
        System.out.println("The door " + door.getId() + " is now propped (after short unlock timed out while open).");
      }
    }
  }
}