package calendar.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrequenceJoursTest {

    @Test
    void frequencePositiveEstCreee() {
        FrequenceJours f = new FrequenceJours(7);
        assertEquals(7, f.valeur());
    }

    @Test
    void frequenceUnEstValide() {
        assertDoesNotThrow(() -> new FrequenceJours(1));
    }

    @Test
    void frequenceZeroLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new FrequenceJours(0));
    }

    @Test
    void frequenceNegativeLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new FrequenceJours(-5));
    }

    @Test
    void deuxFrequencesIdentiquesEgales() {
        assertEquals(new FrequenceJours(30), new FrequenceJours(30));
    }

    @Test
    void deuxFrequencesDifferentesNonEgales() {
        assertNotEquals(new FrequenceJours(7), new FrequenceJours(14));
    }
}
