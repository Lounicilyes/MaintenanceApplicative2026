package calendar.valueobject;

import java.util.Objects;
import java.util.UUID;

public record EventId(UUID valeur) {
    public EventId {
        Objects.requireNonNull(valeur, "L'identifiant ne peut pas être null");
    }

    public static EventId nouveau() {
        return new EventId(UUID.randomUUID());
    }
}
