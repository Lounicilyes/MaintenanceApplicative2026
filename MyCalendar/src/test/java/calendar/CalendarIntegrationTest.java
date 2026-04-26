package calendar;

import calendar.event.Evenement;
import calendar.event.EvenementPeriodique;
import calendar.event.RendezVousPersonnel;
import calendar.event.Reunion;
import calendar.valueobject.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalendarIntegrationTest {

    @Test
    void scenarioCompletCalendrier() {
        CalendarManager cm = new CalendarManager();

        // 1. Ajouter un RDV
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Dentiste"),
            new DateEvenement(LocalDate.of(2026, 5, 10)),
            new HeureDebut(LocalTime.of(10, 0)),
            new DureeEvenement(60)
        );
        cm.ajouter(rdv);

        // 2. Ajouter une réunion au même horaire → conflit
        Reunion reunion = new Reunion(
            EventId.nouveau(),
            new TitreEvenement("Stand-up"),
            new DateEvenement(LocalDate.of(2026, 5, 10)),
            new HeureDebut(LocalTime.of(10, 30)),
            new DureeEvenement(30),
            new Lieu("Salle A"),
            new ListeParticipants(List.of("Alice", "Bob"))
        );
        cm.ajouter(reunion);

        // 3. Vérifier le conflit
        assertTrue(cm.conflit(rdv, reunion));

        // 4. Query période → les deux sont trouvés
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 5, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 5, 31));
        List<Evenement> enMai = cm.eventsDansPeriode(debut, fin);
        assertEquals(2, enMai.size());

        // 5. Supprimer le RDV par ID
        cm.supprimer(rdv.id());
        assertEquals(1, cm.tousLesEvenements().size());

        // 6. Re-query → seulement la réunion
        List<Evenement> apresSuppr = cm.eventsDansPeriode(debut, fin);
        assertEquals(1, apresSuppr.size());
        assertEquals(reunion.id(), apresSuppr.get(0).id());

        // 7. Ajouter un périodique et vérifier qu'il apparaît dans une période
        EvenementPeriodique sport = new EvenementPeriodique(
            EventId.nouveau(),
            new TitreEvenement("Sport"),
            new DateEvenement(LocalDate.of(2026, 1, 1)),
            new HeureDebut(LocalTime.of(7, 0)),
            new DureeEvenement(60),
            new FrequenceJours(7)
        );
        cm.ajouter(sport);
        assertFalse(cm.eventsDansPeriode(debut, fin).isEmpty());

        // 8. Vérifier la description de chaque type
        assertTrue(rdv.description().contains("RDV"));
        assertTrue(reunion.description().contains("Réunion"));
        assertTrue(sport.description().contains("périodique"));
    }
}
