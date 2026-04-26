package calendar.event;

import calendar.valueobject.DateEvenement;
import calendar.valueobject.DureeEvenement;
import calendar.valueobject.EventId;
import calendar.valueobject.FrequenceJours;
import calendar.valueobject.HeureDebut;
import calendar.valueobject.TitreEvenement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

public record EvenementPeriodique(
    EventId id,
    TitreEvenement titre,
    DateEvenement date,
    HeureDebut heureDebut,
    DureeEvenement duree,
    FrequenceJours frequence
) implements Evenement {

    @Override
    public String description() {
        return "Événement périodique : " + titre.valeur()
             + " tous les " + frequence.valeur() + " jours";
    }

    @Override
    public boolean estDansPeriode(DateEvenement debut, DateEvenement fin) {
        return Stream.iterate(date.valeur(), d -> d.plusDays(frequence.valeur()))
                     .takeWhile(d -> !d.isAfter(fin.valeur()))
                     .anyMatch(d -> !d.isBefore(debut.valeur()));
    }

    @Override
    public boolean conflicteAvec(Evenement autre) {
        return autre.conflicteAvecPeriodique(this);
    }

    @Override
    public boolean conflicteAvecRendezVous(RendezVousPersonnel rdv) {
        return conflicteAvecNonPeriodique(rdv);
    }

    @Override
    public boolean conflicteAvecReunion(Reunion reunion) {
        return conflicteAvecNonPeriodique(reunion);
    }

    @Override
    public boolean conflicteAvecPeriodique(EvenementPeriodique autre) {
        // Un périodique conflicte avec un autre si une de ses occurrences
        // tombe le même jour qu'une occurrence de l'autre ET qu'elles se chevauchent
        return Stream.iterate(date.valeur(), d -> d.plusDays(frequence.valeur()))
                     .limit(3650) // horizon ~10 ans
                     .anyMatch(monJour -> {
                         LocalTime monDebut = heureDebut.valeur();
                         LocalTime maFin = monDebut.plusMinutes(duree.minutes());
                         return Stream.iterate(autre.date().valeur(), d -> d.plusDays(autre.frequence().valeur()))
                                      .takeWhile(d -> !d.isAfter(monJour))
                                      .anyMatch(autreJour -> autreJour.equals(monJour)
                                          && TemporelUtils.seChevaucheHoraire(
                                              monDebut, maFin,
                                              autre.heureDebut().valeur(),
                                              autre.heureDebut().valeur().plusMinutes(autre.duree().minutes())));
                     });
    }

    public boolean conflicteAvecNonPeriodique(Evenement autre) {
        LocalDate autreDate = autre.date().valeur();
        LocalTime autreDebut = autre.heureDebut().valeur();
        LocalTime autreFin = autreDebut.plusMinutes(autre.duree().minutes());
        LocalTime monDebut = heureDebut.valeur();
        LocalTime maFin = monDebut.plusMinutes(duree.minutes());
        return Stream.iterate(date.valeur(), d -> d.plusDays(frequence.valeur()))
                     .takeWhile(d -> !d.isAfter(autreDate))
                     .anyMatch(d -> d.equals(autreDate)
                                 && TemporelUtils.seChevaucheHoraire(monDebut, maFin, autreDebut, autreFin));
    }
}
