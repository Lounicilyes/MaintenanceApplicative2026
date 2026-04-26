package calendar.event;

import calendar.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class RendezVousPersonnelTest {

    private RendezVousPersonnel rdvBase;

    @BeforeEach
    void setUp() {
        rdvBase = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Dentiste"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(10, 0)),
            new DureeEvenement(60)
        );
    }

    // --- Construction et getters ---

    @Test
    void creationAvecTousLesChamps() {
        assertEquals("Dentiste", rdvBase.titre().valeur());
        assertEquals(LocalDate.of(2026, 3, 15), rdvBase.date().valeur());
        assertEquals(LocalTime.of(10, 0), rdvBase.heureDebut().valeur());
        assertEquals(60, rdvBase.duree().minutes());
    }

    @Test
    void idEstPresent() {
        assertNotNull(rdvBase.id());
    }

    // --- Description ---

    @Test
    void descriptionContientRDV() {
        assertTrue(rdvBase.description().contains("RDV"));
    }

    @Test
    void descriptionContientTitre() {
        assertTrue(rdvBase.description().contains("Dentiste"));
    }

    // --- estDansPeriode ---

    @Test
    void estDansPeriodeLorsqueDateDansIntervalle() {
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 3, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 3, 31));
        assertTrue(rdvBase.estDansPeriode(debut, fin));
    }

    @Test
    void nEstPasDansPeriodeLorsqueDateHorsIntervalle() {
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 4, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 4, 30));
        assertFalse(rdvBase.estDansPeriode(debut, fin));
    }

    @Test
    void estDansPeriodeSurLaLimiteDebut() {
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 3, 15));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 3, 31));
        assertTrue(rdvBase.estDansPeriode(debut, fin));
    }

    @Test
    void estDansPeriodeSurLaLimiteFin() {
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 3, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 3, 15));
        assertTrue(rdvBase.estDansPeriode(debut, fin));
    }

    // --- conflicteAvec ---

    @Test
    void conflicteAvecRDVQuiSeChevauche() {
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(10, 30)),
            new DureeEvenement(60)
        );
        assertTrue(rdvBase.conflicteAvec(rdv2));
    }

    @Test
    void pasDeCconflitAvecRDVConsecutif() {
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(11, 0)),
            new DureeEvenement(60)
        );
        assertFalse(rdvBase.conflicteAvec(rdv2));
    }

    @Test
    void pasDeCconflitAvecRDVJourDifferent() {
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 3, 16)),
            new HeureDebut(LocalTime.of(10, 0)),
            new DureeEvenement(60)
        );
        assertFalse(rdvBase.conflicteAvec(rdv2));
    }

    @Test
    void conflitEstSymetrique() {
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(10, 30)),
            new DureeEvenement(60)
        );
        assertEquals(rdvBase.conflicteAvec(rdv2), rdv2.conflicteAvec(rdvBase));
    }
}
