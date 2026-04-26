package calendar.valueobject;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ListeParticipantsTest {

    @Test
    void listeAvecParticipantsValide() {
        ListeParticipants lp = new ListeParticipants(List.of("Alice", "Bob"));
        assertEquals(2, lp.valeur().size());
        assertTrue(lp.valeur().contains("Alice"));
        assertTrue(lp.valeur().contains("Bob"));
    }

    @Test
    void listeNullLanceException() {
        assertThrows(NullPointerException.class, () -> new ListeParticipants(null));
    }

    @Test
    void listeVideLanceException() {
        assertThrows(IllegalArgumentException.class, () -> new ListeParticipants(List.of()));
    }

    @Test
    void listeEstImmutable() {
        List<String> mutable = new ArrayList<>(List.of("Alice"));
        ListeParticipants lp = new ListeParticipants(mutable);
        mutable.add("Eve");
        assertEquals(1, lp.valeur().size());
    }

    @Test
    void modificationListeRetourneeImpossible() {
        ListeParticipants lp = new ListeParticipants(List.of("Alice", "Bob"));
        assertThrows(UnsupportedOperationException.class, () -> lp.valeur().add("Eve"));
    }

    @Test
    void deuxListesIdentiquesEgales() {
        assertEquals(
            new ListeParticipants(List.of("Alice")),
            new ListeParticipants(List.of("Alice"))
        );
    }
}
