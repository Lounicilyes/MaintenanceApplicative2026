package calendar.valueobject;

import java.util.Objects;

public record TitreEvenement(String valeur) {
    public TitreEvenement {
        Objects.requireNonNull(valeur, "Le titre ne peut pas être null");
        if (valeur.isBlank()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
    }
}
