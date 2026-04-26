package calendar.event;

import calendar.valueobject.DateEvenement;
import calendar.valueobject.DureeEvenement;
import calendar.valueobject.EventId;
import calendar.valueobject.HeureDebut;
import calendar.valueobject.Lieu;
import calendar.valueobject.ListeParticipants;
import calendar.valueobject.TitreEvenement;
import java.time.LocalDateTime;

public record Reunion(
    EventId id,
    TitreEvenement titre,
    DateEvenement date,
    HeureDebut heureDebut,
    DureeEvenement duree,
    Lieu lieu,
    ListeParticipants participants
) implements Evenement {

    @Override
    public String description() {
        return "Réunion : " + titre.valeur()
             + " à " + lieu.valeur()
             + " avec " + String.join(", ", participants.valeur());
    }

    @Override
    public boolean estDansPeriode(DateEvenement debut, DateEvenement fin) {
        return !date.valeur().isBefore(debut.valeur())
            && !date.valeur().isAfter(fin.valeur());
    }

    @Override
    public boolean conflicteAvec(Evenement autre) {
        return autre.conflicteAvecReunion(this);
    }

    @Override
    public boolean conflicteAvecRendezVous(RendezVousPersonnel rdv) {
        return TemporelUtils.seChevauche(
            dateTimeDebut(), dateTimeFin(),
            LocalDateTime.of(rdv.date().valeur(), rdv.heureDebut().valeur()),
            LocalDateTime.of(rdv.date().valeur(), rdv.heureDebut().valeur()).plusMinutes(rdv.duree().minutes())
        );
    }

    @Override
    public boolean conflicteAvecReunion(Reunion autre) {
        return TemporelUtils.seChevauche(
            dateTimeDebut(), dateTimeFin(),
            autre.dateTimeDebut(), autre.dateTimeFin()
        );
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
