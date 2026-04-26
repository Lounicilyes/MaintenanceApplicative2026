package calendar;

import calendar.event.Evenement;
import calendar.valueobject.DateEvenement;
import calendar.valueobject.EventId;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {

    private final List<Evenement> evenements = new ArrayList<>();

    public void ajouter(Evenement evenement) {
        evenements.add(evenement);
    }

    public void supprimer(EventId id) {
        evenements.removeIf(e -> e.id().equals(id));
    }

    public List<Evenement> tousLesEvenements() {
        return List.copyOf(evenements);
    }

    public List<Evenement> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        return evenements.stream()
                         .filter(e -> e.estDansPeriode(debut, fin))
                         .toList();
    }

    public boolean conflit(Evenement e1, Evenement e2) {
        return e1.conflicteAvec(e2);
    }
}
