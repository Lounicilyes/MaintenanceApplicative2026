package calendar.event;

import calendar.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class EvenementPeriodiqueTest {

    private EvenementPeriodique epBase;

    @BeforeEach
    void setUp() {
        // Tous les 7 jours à partir du 2026-01-01, 10h00, durée 60 min
        epBase = new EvenementPeriodique(
            EventId.nouveau(),
            new TitreEvenement("Sport"),
            new DateEvenement(LocalDate.of(2026, 1, 1)),
            new HeureDebut(LocalTime.of(10, 0)),
            new DureeEvenement(60),
            new FrequenceJours(7)
        );
    }

    // --- Construction ---

    @Test
    void creationAvecFrequence() {
        assertEquals(7, epBase.frequence().valeur());
        assertEquals("Sport", epBase.titre().valeur());
    }

    // --- Description ---

    @Test
    void descriptionContientPeriodique() {
        assertTrue(epBase.description().contains("périodique")
                || epBase.description().contains("Périodique"));
    }

    @Test
    void descriptionContientFrequence() {
        assertTrue(epBase.description().contains("7"));
    }

    @Test
    void descriptionContientTitre() {
        assertTrue(epBase.description().contains("Sport"));
    }

    // --- estDansPeriode ---

    @Test
    void premiereOccurrenceEstDansPeriode() {
        // date d'origine = 2026-01-01, dans [2026-01-01, 2026-01-31]
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 1, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 1, 31));
        assertTrue(epBase.estDansPeriode(debut, fin));
    }

    @Test
    void occurrenceUlterieureEstDansPeriode() {
        // origine = 2026-01-01, freq=7 → occurrence le 2026-01-08
        // période [2026-01-08, 2026-01-14]
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 1, 8));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 1, 14));
        assertTrue(epBase.estDansPeriode(debut, fin));
    }

    @Test
    void aucuneOccurrenceDansPeriode() {
        // origine = 2026-01-01, freq=7 → occurrences: 01, 08, 15, ...
        // période [2026-01-04, 2026-01-06] : aucune occurrence
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 1, 4));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 1, 6));
        assertFalse(epBase.estDansPeriode(debut, fin));
    }

    @Test
    void periodeAvantOrigineRetourneFaux() {
        // origine = 2026-01-01, période dans le passé
        DateEvenement debut = new DateEvenement(LocalDate.of(2025, 6, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2025, 12, 31));
        assertFalse(epBase.estDansPeriode(debut, fin));
    }

    // --- conflicteAvec (avec RDV) ---

    @Test
    void conflicteAvecRDVSiOccurrenceChevauchement() {
        // occurrence le 2026-01-08 à 10:00 (dur=60min), RDV 10:30 dur=60min → chevauchement
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 1, 8)),
            new HeureDebut(LocalTime.of(10, 30)),
            new DureeEvenement(60)
        );
        assertTrue(epBase.conflicteAvec(rdv));
    }

    @Test
    void pasDeCconflitAvecRDVSiAucuneOccurrenceMemeJour() {
        // occurrence le 2026-01-08, RDV le 2026-01-09 → pas de conflit
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 1, 9)),
            new HeureDebut(LocalTime.of(10, 0)),
            new DureeEvenement(60)
        );
        assertFalse(epBase.conflicteAvec(rdv));
    }

    @Test
    void pasDeCconflitAvecRDVMemeJourSansChevauche() {
        // occurrence le 2026-01-08 à 10:00 (dur=60min), RDV 11:00 dur=30min → consécutifs
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Médecin"),
            new DateEvenement(LocalDate.of(2026, 1, 8)),
            new HeureDebut(LocalTime.of(11, 0)),
            new DureeEvenement(30)
        );
        assertFalse(epBase.conflicteAvec(rdv));
    }
}
