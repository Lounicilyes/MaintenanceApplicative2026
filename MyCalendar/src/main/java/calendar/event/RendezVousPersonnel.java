package calendar.event;

import calendar.valueobject.DateEvenement;
import calendar.valueobject.DureeEvenement;
import calendar.valueobject.EventId;
import calendar.valueobject.HeureDebut;
import calendar.valueobject.TitreEvenement;
import java.time.LocalDateTime;

public record RendezVousPersonnel(
    EventId id,
    TitreEvenement titre,
    DateEvenement date,
    HeureDebut heureDebut,
    DureeEvenement duree
) implements Evenement {

    @Override
    public String description() {
        return "RDV : " + titre.valeur() + " le " + date.valeur() + " à " + heureDebut.valeur();
    }

    @Override
    public boolean estDansPeriode(DateEvenement debut, DateEvenement fin) {
        return !date.valeur().isBefore(debut.valeur())
            && !date.valeur().isAfter(fin.valeur());
    }

    @Override
    public boolean conflicteAvec(Evenement autre) {
        return autre.conflicteAvecRendezVous(this);
    }

    @Override
    public boolean conflicteAvecRendezVous(RendezVousPersonnel rdv) {
        return TemporelUtils.seChevauche(dateTimeDebut(), dateTimeFin(),
            LocalDateTime.of(rdv.date().valeur(), rdv.heureDebut().valeur()),
            LocalDateTime.of(rdv.date().valeur(), rdv.heureDebut().valeur()).plusMinutes(rdv.duree().minutes()));
    }

    @Override
    public boolean conflicteAvecReunion(Reunion reunion) {
        return TemporelUtils.seChevauche(dateTimeDebut(), dateTimeFin(),
            LocalDateTime.of(reunion.date().valeur(), reunion.heureDebut().valeur()),
            LocalDateTime.of(reunion.date().valeur(), reunion.heureDebut().valeur()).plusMinutes(reunion.duree().minutes()));
    }

    @Override
    public boolean conflicteAvecPeriodique(EvenementPeriodique periodique) {
        return periodique.conflicteAvecNonPeriodique(this);
    }

    LocalDateTime dateTimeDebut() {
        return LocalDateTime.of(date.valeur(), heureDebut.valeur());
    }

    LocalDateTime dateTimeFin() {
        return dateTimeDebut().plusMinutes(duree.minutes());
    }
}
