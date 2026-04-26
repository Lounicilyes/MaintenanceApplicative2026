package calendar.event;

import calendar.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReunionTest {

    private Reunion reunionBase;

    @BeforeEach
    void setUp() {
        reunionBase = new Reunion(
            EventId.nouveau(),
            new TitreEvenement("Stand-up"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(9, 0)),
            new DureeEvenement(30),
            new Lieu("Salle A"),
            new ListeParticipants(List.of("Alice", "Bob"))
        );
    }

    // --- Construction et getters ---

    @Test
    void creationAvecTousLesChamps() {
        assertEquals("Stand-up", reunionBase.titre().valeur());
        assertEquals("Salle A", reunionBase.lieu().valeur());
        assertEquals(2, reunionBase.participants().valeur().size());
    }

    @Test
    void idEstPresent() {
        assertNotNull(reunionBase.id());
    }

    // --- Description ---

    @Test
    void descriptionContientReunion() {
        assertTrue(reunionBase.description().contains("Réunion"));
    }

    @Test
    void descriptionContientTitre() {
        assertTrue(reunionBase.description().contains("Stand-up"));
    }

    @Test
    void descriptionContientLieu() {
        assertTrue(reunionBase.description().contains("Salle A"));
    }

    @Test
    void descriptionContientParticipants() {
        assertTrue(reunionBase.description().contains("Alice"));
        assertTrue(reunionBase.description().contains("Bob"));
    }

    // --- estDansPeriode ---

    @Test
    void estDansPeriodeLorsqueDateDansIntervalle() {
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 3, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 3, 31));
        assertTrue(reunionBase.estDansPeriode(debut, fin));
    }

    @Test
    void nEstPasDansPeriodeLorsqueDateHorsIntervalle() {
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 4, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 4, 30));
        assertFalse(reunionBase.estDansPeriode(debut, fin));
    }

    // --- conflicteAvec (homogène : Reunion/Reunion) ---

    @Test
    void conflicteAvecReunionQuiSeChevauche() {
        Reunion r2 = new Reunion(
            EventId.nouveau(),
            new TitreEvenement("Sprint review"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(9, 15)),
            new DureeEvenement(30),
            new Lieu("Salle B"),
            new ListeParticipants(List.of("Charlie"))
        );
        assertTrue(reunionBase.conflicteAvec(r2));
    }

    @Test
    void pasDeCconflitAvecReunionConsecutive() {
        Reunion r2 = new Reunion(
            EventId.nouveau(),
            new TitreEvenement("Sprint review"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(9, 30)),
            new DureeEvenement(30),
            new Lieu("Salle B"),
            new ListeParticipants(List.of("Charlie"))
        );
        assertFalse(reunionBase.conflicteAvec(r2));
    }

    // --- conflicteAvec (croisé : Reunion/RendezVousPersonnel) ---

    @Test
    void conflicteAvecRDVQuiSeChevauche() {
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Appel client"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(9, 15)),
            new DureeEvenement(30)
        );
        assertTrue(reunionBase.conflicteAvec(rdv));
    }

    @Test
    void pasDeCconflitAvecRDVJourDifferent() {
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Appel client"),
            new DateEvenement(LocalDate.of(2026, 3, 16)),
            new HeureDebut(LocalTime.of(9, 0)),
            new DureeEvenement(30)
        );
        assertFalse(reunionBase.conflicteAvec(rdv));
    }
}
