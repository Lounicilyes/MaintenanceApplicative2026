package calendar.valueobject;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class HeureDebutTest {

    @Test
    void heureValideEstCreee() {
        HeureDebut h = new HeureDebut(LocalTime.of(14, 30));
        assertEquals(LocalTime.of(14, 30), h.valeur());
    }

    @Test
    void heureNullLanceException() {
        assertThrows(NullPointerException.class, () -> new HeureDebut(null));
    }

    @Test
    void deuxHeuresIdentiquesEgales() {
        assertEquals(
            new HeureDebut(LocalTime.of(9, 0)),
            new HeureDebut(LocalTime.of(9, 0))
        );
    }

    @Test
    void deuxHeuresDifferentesNonEgales() {
        assertNotEquals(
            new HeureDebut(LocalTime.of(9, 0)),
            new HeureDebut(LocalTime.of(10, 0))
        );
    }
}
