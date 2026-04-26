package calendar.valueobject;

import java.util.Objects;

public record Lieu(String valeur) {
    public Lieu {
        Objects.requireNonNull(valeur, "Le lieu ne peut pas être null");
        if (valeur.isBlank()) {
            throw new IllegalArgumentException("Le lieu ne peut pas être vide");
        }
    }
}
