package calendar;

import calendar.event.EvenementPeriodique;
import calendar.event.Evenement;
import calendar.event.RendezVousPersonnel;
import calendar.event.Reunion;
import calendar.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalendarManagerTest {

    private CalendarManager manager;
    private RendezVousPersonnel rdv1;
    private RendezVousPersonnel rdv2;

    @BeforeEach
    void setUp() {
        manager = new CalendarManager();
        rdv1 = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Dentiste"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(10, 0)),
            new DureeEvenement(60)
        );
        rdv2 = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Kiné"),
            new DateEvenement(LocalDate.of(2026, 3, 20)),
            new HeureDebut(LocalTime.of(14, 0)),
            new DureeEvenement(45)
        );
    }

    // --- ajouter ---

    @Test
    void ajouterEventAugmenteLeNombreDEvents() {
        manager.ajouter(rdv1);
        assertEquals(1, manager.tousLesEvenements().size());
    }

    @Test
    void ajouterPlusieursEventsLesRetourneTous() {
        manager.ajouter(rdv1);
        manager.ajouter(rdv2);
        assertEquals(2, manager.tousLesEvenements().size());
    }

    @Test
    void listeRetourneeEstImmutable() {
        manager.ajouter(rdv1);
        assertThrows(UnsupportedOperationException.class,
            () -> manager.tousLesEvenements().add(rdv2));
    }

    // --- supprimer ---

    @Test
    void supprimerEventParIdSupprimeLEvent() {
        manager.ajouter(rdv1);
        manager.supprimer(rdv1.id());
        assertTrue(manager.tousLesEvenements().isEmpty());
    }

    @Test
    void supprimerIdInexistantNeFaitRien() {
        manager.ajouter(rdv1);
        manager.supprimer(EventId.nouveau());
        assertEquals(1, manager.tousLesEvenements().size());
    }

    @Test
    void supprimerUnEventParmiPlusieursSupprimeSeulemntLui() {
        manager.ajouter(rdv1);
        manager.ajouter(rdv2);
        manager.supprimer(rdv1.id());
        assertEquals(1, manager.tousLesEvenements().size());
        assertEquals(rdv2.id(), manager.tousLesEvenements().get(0).id());
    }

    // --- eventsDansPeriode ---

    @Test
    void eventsDansPeriodeRetourneSeulementLesEventsDansLIntervalle() {
        manager.ajouter(rdv1); // 2026-03-15
        manager.ajouter(rdv2); // 2026-03-20
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 3, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 3, 16));
        List<Evenement> result = manager.eventsDansPeriode(debut, fin);
        assertEquals(1, result.size());
        assertEquals(rdv1.id(), result.get(0).id());
    }

    @Test
    void eventsDansPeriodeSansResultatRetourneListeVide() {
        manager.ajouter(rdv1);
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 4, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 4, 30));
        assertTrue(manager.eventsDansPeriode(debut, fin).isEmpty());
    }

    @Test
    void eventsDansPeriodeAvecPeriodique() {
        EvenementPeriodique ep = new EvenementPeriodique(
            EventId.nouveau(),
            new TitreEvenement("Sport"),
            new DateEvenement(LocalDate.of(2026, 1, 1)),
            new HeureDebut(LocalTime.of(7, 0)),
            new DureeEvenement(45),
            new FrequenceJours(7)
        );
        manager.ajouter(ep);
        // occurrence le 2026-03-05 (environ le 9ème jeudi depuis 2026-01-01)
        DateEvenement debut = new DateEvenement(LocalDate.of(2026, 3, 1));
        DateEvenement fin = new DateEvenement(LocalDate.of(2026, 3, 31));
        assertFalse(manager.eventsDansPeriode(debut, fin).isEmpty());
    }

    // --- conflit ---

    @Test
    void conflitDetecteEntreDeuxRDVQuiSeChevauchent() {
        RendezVousPersonnel rdvChevauche = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Médecin"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(10, 30)),
            new DureeEvenement(60)
        );
        assertTrue(manager.conflit(rdv1, rdvChevauche));
    }

    @Test
    void pasDeConflitEntreDeuxRDVConsecutifs() {
        RendezVousPersonnel rdvApres = new RendezVousPersonnel(
            EventId.nouveau(),
            new TitreEvenement("Médecin"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(11, 0)),
            new DureeEvenement(60)
        );
        assertFalse(manager.conflit(rdv1, rdvApres));
    }

    @Test
    void conflitDetecteEntreRDVEtReunion() {
        Reunion reunion = new Reunion(
            EventId.nouveau(),
            new TitreEvenement("Sprint review"),
            new DateEvenement(LocalDate.of(2026, 3, 15)),
            new HeureDebut(LocalTime.of(10, 30)),
            new DureeEvenement(60),
            new Lieu("Salle A"),
            new ListeParticipants(List.of("Alice"))
        );
        assertTrue(manager.conflit(rdv1, reunion));
    }
}
