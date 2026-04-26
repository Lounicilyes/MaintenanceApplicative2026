package calendar.valueobject;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DateEvenementTest {

    @Test
    void dateValideEstCreee() {
        DateEvenement d = new DateEvenement(LocalDate.of(2026, 1, 15));
        assertEquals(LocalDate.of(2026, 1, 15), d.valeur());
    }

    @Test
    void dateNullLanceException() {
        assertThrows(NullPointerException.class, () -> new DateEvenement(null));
    }

    @Test
    void dateAvantEstAvant() {
        DateEvenement d1 = new DateEvenement(LocalDate.of(2026, 1, 1));
        DateEvenement d2 = new DateEvenement(LocalDate.of(2026, 6, 1));
        assertTrue(d1.estAvant(d2));
        assertFalse(d2.estAvant(d1));
    }

    @Test
    void dateApresEstApres() {
        DateEvenement d1 = new DateEvenement(LocalDate.of(2026, 1, 1));
        DateEvenement d2 = new DateEvenement(LocalDate.of(2026, 6, 1));
        assertTrue(d2.estApres(d1));
        assertFalse(d1.estApres(d2));
    }

    @Test
    void deuxDatesMemeJourEgales() {
        assertEquals(
            new DateEvenement(LocalDate.of(2026, 3, 3)),
            new DateEvenement(LocalDate.of(2026, 3, 3))
        );
    }

    @Test
    void deuxDatesDifferentesNonEgales() {
        assertNotEquals(
            new DateEvenement(LocalDate.of(2026, 1, 1)),
            new DateEvenement(LocalDate.of(2026, 12, 31))
        );
    }
}
