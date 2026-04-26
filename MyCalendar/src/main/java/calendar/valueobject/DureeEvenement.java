package calendar.valueobject;

import java.time.LocalTime;

public record DureeEvenement(int minutes) {
    public DureeEvenement {
        if (minutes < 0) {
            throw new IllegalArgumentException("La durée ne peut pas être négative");
        }
    }

    public LocalTime heureFinFrom(HeureDebut debut) {
        return debut.valeur().plusMinutes(minutes);
    }
}
