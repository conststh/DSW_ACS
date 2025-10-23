package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Schedule {
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final List<DayOfWeek> allowedWeekdays;

  public Schedule(LocalTime startTime, LocalTime endTime, List<DayOfWeek> allowedWeekdays) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.allowedWeekdays = allowedWeekdays;
  }


  public boolean isTimeAuthorized(LocalDateTime time) {
    DayOfWeek day = time.getDayOfWeek();
    LocalTime now = time.toLocalTime();

    // Miramos si el dia actual de la semana está permitido
    boolean isDayAllowed = allowedWeekdays.contains(day);
    if (!isDayAllowed) {
      return false;
    }

    // Comprobamos que el tiempo esté en el intervalo de inicio/final
    boolean isAfterOrAtStart = !now.isBefore(startTime);
    boolean isBeforeEnd = now.isBefore(endTime);

    return isAfterOrAtStart && isBeforeEnd;
  }
}