package calendar.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TitreEvenementTest {

    @Test
    void titreValideEstCree() {
        TitreEvenement t = new TitreEvenement("Dentiste");
        assertEquals("Dentiste", t.valeur());
    }

    @Test
    void titreNullLanceException() {
        assertThrows(NullPointerException.class, () -> new TitreEvenement(null));
    }

    @Test
    void titreVideLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(""));
    }

    @Test
    void titreBlancLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement("   "));
    }

    @Test
    void deuxTitresIdentiquesEgaux() {
        assertEquals(new TitreEvenement("Reunion"), new TitreEvenement("Reunion"));
    }

    @Test
    void deuxTitresDifferentsNonEgaux() {
        assertNotEquals(new TitreEvenement("A"), new TitreEvenement("B"));
    }
}
