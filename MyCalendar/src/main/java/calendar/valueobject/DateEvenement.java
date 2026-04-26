package calendar.valueobject;

import java.time.LocalDate;
import java.util.Objects;

public record DateEvenement(LocalDate valeur) {
    public DateEvenement {
        Objects.requireNonNull(valeur, "La date ne peut pas être null");
    }

    public boolean estAvant(DateEvenement autre) {
        return this.valeur.isBefore(autre.valeur);
    }

    public boolean estApres(DateEvenement autre) {
        return this.valeur.isAfter(autre.valeur);
    }
}
