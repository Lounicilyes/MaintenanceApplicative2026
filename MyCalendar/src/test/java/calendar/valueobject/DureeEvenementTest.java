package calendar.valueobject;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class DureeEvenementTest {

    @Test
    void dureePositiveEstCreee() {
        DureeEvenement d = new DureeEvenement(60);
        assertEquals(60, d.minutes());
    }

    @Test
    void dureeZeroEstAutorisee() {
        assertDoesNotThrow(() -> new DureeEvenement(0));
    }

    @Test
    void dureeNegativeLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new DureeEvenement(-1));
    }

    @Test
    void deuxDureesIdentiquesEgales() {
        assertEquals(new DureeEvenement(30), new DureeEvenement(30));
    }

    @Test
    void calculeHeureFin() {
        HeureDebut debut = new HeureDebut(LocalTime.of(10, 0));
        DureeEvenement duree = new DureeEvenement(90);
        assertEquals(LocalTime.of(11, 30), duree.heureFinFrom(debut));
    }

    @Test
    void calculeHeureFinSurDeuxHeures() {
        HeureDebut debut = new HeureDebut(LocalTime.of(8, 15));
        DureeEvenement duree = new DureeEvenement(120);
        assertEquals(LocalTime.of(10, 15), duree.heureFinFrom(debut));
    }
}
