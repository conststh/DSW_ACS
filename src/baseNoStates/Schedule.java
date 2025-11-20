package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Define las restricciones temporales (CUÁNDO) para un UserGroup.
 * Comprueba si una marca de tiempo específica cae dentro de las horas de trabajo y días permitidos.
 */
public class Schedule
{
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final List<DayOfWeek> allowedWeekdays;

  public Schedule(LocalTime startTime, LocalTime endTime, List<DayOfWeek> allowedWeekdays)
  {
    this.startTime = startTime;
    this.endTime = endTime;
    this.allowedWeekdays = allowedWeekdays;
  }

  /**
   * Comprueba si el LocalDateTime proporcionado está autorizado.
   */
  public boolean isTimeAuthorized(LocalDateTime time)
  {
    DayOfWeek day = time.getDayOfWeek();
    LocalTime now = time.toLocalTime();

    // Comprobar día de la semana
    boolean isDayAllowed = allowedWeekdays.contains(day);
    if (!isDayAllowed) {
      return false;
    }

    // Comprobar rango horario
    // Usando !isBefore en lugar de isAfter para ser inclusivo con el minuto de inicio
    boolean isAfterOrAtStart = !now.isBefore(startTime);
    boolean isBeforeEnd = now.isBefore(endTime);

    return isAfterOrAtStart && isBeforeEnd;
  }
}