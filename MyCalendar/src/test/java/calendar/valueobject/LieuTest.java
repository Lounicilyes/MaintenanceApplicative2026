package calendar.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LieuTest {

    @Test
    void lieuValideEstCree() {
        Lieu l = new Lieu("Salle A");
        assertEquals("Salle A", l.valeur());
    }

    @Test
    void lieuNullLanceException() {
        assertThrows(NullPointerException.class, () -> new Lieu(null));
    }

    @Test
    void lieuVideLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Lieu(""));
    }

    @Test
    void lieuBlancLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Lieu("   "));
    }

    @Test
    void deuxLieuxIdentiquesEgaux() {
        assertEquals(new Lieu("Bureau 42"), new Lieu("Bureau 42"));
    }

    @Test
    void deuxLieuxDifferentsNonEgaux() {
        assertNotEquals(new Lieu("Salle A"), new Lieu("Salle B"));
    }
}
