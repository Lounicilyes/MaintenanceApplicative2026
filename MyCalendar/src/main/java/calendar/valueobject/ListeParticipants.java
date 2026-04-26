package calendar.valueobject;

import java.util.List;
import java.util.Objects;

public record ListeParticipants(List<String> valeur) {
    public ListeParticipants {
        Objects.requireNonNull(valeur, "La liste de participants ne peut pas être null");
        if (valeur.isEmpty()) {
            throw new IllegalArgumentException("Il faut au moins un participant");
        }
        valeur = List.copyOf(valeur);
    }
}
