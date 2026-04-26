package calendar.event;

import calendar.valueobject.DateEvenement;
import calendar.valueobject.DureeEvenement;
import calendar.valueobject.EventId;
import calendar.valueobject.HeureDebut;
import calendar.valueobject.TitreEvenement;

public interface Evenement {

    EventId id();

    TitreEvenement titre();

    DateEvenement date();

    HeureDebut heureDebut();

    DureeEvenement duree();

    String description();

    boolean estDansPeriode(DateEvenement debut, DateEvenement fin);

    boolean conflicteAvec(Evenement autre);

    // Entrées du double dispatch
    boolean conflicteAvecRendezVous(RendezVousPersonnel rdv);

    boolean conflicteAvecReunion(Reunion reunion);

    boolean conflicteAvecPeriodique(EvenementPeriodique periodique);
}
