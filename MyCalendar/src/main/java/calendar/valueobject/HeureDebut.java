package calendar.valueobject;

import java.time.LocalTime;
import java.util.Objects;

public record HeureDebut(LocalTime valeur) {
    public HeureDebut {
        Objects.requireNonNull(valeur, "L'heure de début ne peut pas être null");
    }
}
