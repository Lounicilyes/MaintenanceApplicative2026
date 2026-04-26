package calendar.event;

import java.time.LocalDateTime;
import java.time.LocalTime;

final class TemporelUtils {

    private TemporelUtils() {}

    static boolean seChevauche(LocalDateTime d1, LocalDateTime f1,
                               LocalDateTime d2, LocalDateTime f2) {
        return d1.isBefore(f2) && f1.isAfter(d2);
    }

    static boolean seChevaucheHoraire(LocalTime d1, LocalTime f1,
                                      LocalTime d2, LocalTime f2) {
        return d1.isBefore(f2) && f1.isAfter(d2);
    }
}
