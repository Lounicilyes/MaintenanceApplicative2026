package calendar.valueobject;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EventIdTest {

    @Test
    void deuxIdsNouveauxSontDifferents() {
        assertNotEquals(EventId.nouveau(), EventId.nouveau());
    }

    @Test
    void memeUUIDEgaux() {
        UUID u = UUID.randomUUID();
        assertEquals(new EventId(u), new EventId(u));
    }

    @Test
    void uuidsDistinctsNonEgaux() {
        assertNotEquals(new EventId(UUID.randomUUID()), new EventId(UUID.randomUUID()));
    }

    @Test
    void idNullLanceException() {
        assertThrows(NullPointerException.class, () -> new EventId(null));
    }

    @Test
    void nouveauRetourneUnEventId() {
        assertNotNull(EventId.nouveau());
    }
}
