package calendar.valueobject;

public record FrequenceJours(int valeur) {
    public FrequenceJours {
        if (valeur <= 0) {
            throw new IllegalArgumentException("La fréquence doit être strictement positive");
        }
    }
}
