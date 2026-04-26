import calendar.CalendarManager;
import calendar.event.Evenement;
import calendar.event.EvenementPeriodique;
import calendar.event.RendezVousPersonnel;
import calendar.event.Reunion;
import calendar.valueobject.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        Scanner scanner = new Scanner(System.in);
        String utilisateur = null;
        boolean continuer = true;

        List<String> utilisateurs = new ArrayList<>();
        List<String> motsDePasses = new ArrayList<>();

        while (true) {

            if (utilisateur == null) {
                System.out.println("  _____         _                   _                __  __");
                System.out.println(" / ____|       | |                 | |              |  \\/  |");
                System.out.println("| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
                System.out.println("| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
                System.out.println("| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
                System.out.println(" \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
                System.out.println("                                                                                   __/ |");
                System.out.println("                                                                                  |___/");

                System.out.println("1 - Se connecter");
                System.out.println("2 - Créer un compte");
                System.out.print("Choix : ");

                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Nom d'utilisateur: ");
                        String nomConnexion = scanner.nextLine();
                        System.out.print("Mot de passe: ");
                        String mdpConnexion = scanner.nextLine();
                        for (int i = 0; i < utilisateurs.size(); i++) {
                            if (utilisateurs.get(i).equals(nomConnexion)
                                    && motsDePasses.get(i).equals(mdpConnexion)) {
                                utilisateur = nomConnexion;
                            }
                        }
                        if (utilisateur == null) {
                            System.out.println("Identifiants incorrects.");
                        }
                        break;

                    case "2":
                        System.out.print("Nom d'utilisateur: ");
                        String nouveauNom = scanner.nextLine();
                        System.out.print("Mot de passe: ");
                        String nouveauMdp = scanner.nextLine();
                        System.out.print("Répéter mot de passe: ");
                        if (scanner.nextLine().equals(nouveauMdp)) {
                            utilisateurs.add(nouveauNom);
                            motsDePasses.add(nouveauMdp);
                            utilisateur = nouveauNom;
                            System.out.println("Compte créé !");
                        } else {
                            System.out.println("Les mots de passes ne correspondent pas...");
                        }
                        break;
                }
            }

            while (continuer && utilisateur != null) {
                System.out.println("\nBonjour, " + utilisateur);
                System.out.println("=== Menu Gestionnaire d'Événements ===");
                System.out.println("1 - Voir les événements");
                System.out.println("2 - Ajouter un rendez-vous perso");
                System.out.println("3 - Ajouter une réunion");
                System.out.println("4 - Ajouter un évènement périodique");
                System.out.println("5 - Supprimer un événement");
                System.out.println("6 - Se déconnecter");
                System.out.print("Votre choix : ");

                String choix = scanner.nextLine();

                switch (choix) {
                    case "1":
                        System.out.println("\n=== Menu de visualisation d'Événements ===");
                        System.out.println("1 - Afficher TOUS les événements");
                        System.out.println("2 - Afficher les événements d'un MOIS précis");
                        System.out.println("3 - Afficher les événements d'une SEMAINE précise");
                        System.out.println("4 - Afficher les événements d'un JOUR précis");
                        System.out.println("5 - Retour");
                        System.out.print("Votre choix : ");

                        choix = scanner.nextLine();

                        switch (choix) {
                            case "1":
                                afficherListe(calendar.tousLesEvenements());
                                break;

                            case "2":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeMois = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le mois (1-12) : ");
                                int mois = Integer.parseInt(scanner.nextLine());
                                DateEvenement debutMois = new DateEvenement(LocalDate.of(anneeMois, mois, 1));
                                DateEvenement finMois = new DateEvenement(LocalDate.of(anneeMois, mois, 1).plusMonths(1).minusDays(1));
                                afficherListe(calendar.eventsDansPeriode(debutMois, finMois));
                                break;

                            case "3":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeSemaine = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le numéro de semaine (1-52) : ");
                                int semaine = Integer.parseInt(scanner.nextLine());
                                LocalDate lundi = LocalDate.now()
                                        .withYear(anneeSemaine)
                                        .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                                        .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1);
                                DateEvenement debutSemaine = new DateEvenement(lundi);
                                DateEvenement finSemaine = new DateEvenement(lundi.plusDays(6));
                                afficherListe(calendar.eventsDansPeriode(debutSemaine, finSemaine));
                                break;

                            case "4":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeJour = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le mois (1-12) : ");
                                int moisJour = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le jour (1-31) : ");
                                int jour = Integer.parseInt(scanner.nextLine());
                                DateEvenement leJour = new DateEvenement(LocalDate.of(anneeJour, moisJour, jour));
                                afficherListe(calendar.eventsDansPeriode(leJour, leJour));
                                break;
                        }
                        break;

                    case "2":
                        System.out.print("Titre de l'événement : ");
                        String titre = scanner.nextLine();
                        System.out.print("Année (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute = Integer.parseInt(scanner.nextLine());
                        System.out.print("Durée (en minutes) : ");
                        int duree = Integer.parseInt(scanner.nextLine());

                        calendar.ajouter(new RendezVousPersonnel(
                            EventId.nouveau(),
                            new TitreEvenement(titre),
                            new DateEvenement(LocalDate.of(annee, moisRdv, jourRdv)),
                            new HeureDebut(LocalTime.of(heure, minute)),
                            new DureeEvenement(duree)
                        ));
                        System.out.println("Événement ajouté.");
                        break;

                    case "3":
                        System.out.print("Titre de l'événement : ");
                        String titre2 = scanner.nextLine();
                        System.out.print("Année (AAAA) : ");
                        int annee2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Durée (en minutes) : ");
                        int duree2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Lieu : ");
                        String lieu = scanner.nextLine();

                        List<String> participantsList = new ArrayList<>();
                        participantsList.add(utilisateur);
                        System.out.print("Ajouter un participant ? (oui / non) : ");
                        while (scanner.nextLine().equals("oui")) {
                            System.out.print("Nom du participant : ");
                            participantsList.add(scanner.nextLine());
                            System.out.print("Ajouter un autre participant ? (oui / non) : ");
                        }

                        calendar.ajouter(new Reunion(
                            EventId.nouveau(),
                            new TitreEvenement(titre2),
                            new DateEvenement(LocalDate.of(annee2, moisRdv2, jourRdv2)),
                            new HeureDebut(LocalTime.of(heure2, minute2)),
                            new DureeEvenement(duree2),
                            new Lieu(lieu),
                            new ListeParticipants(participantsList)
                        ));
                        System.out.println("Événement ajouté.");
                        break;

                    case "4":
                        System.out.print("Titre de l'événement : ");
                        String titre3 = scanner.nextLine();
                        System.out.print("Année (AAAA) : ");
                        int annee3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Durée (en minutes, 0 si non définie) : ");
                        int duree3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Fréquence (en jours) : ");
                        int frequence = Integer.parseInt(scanner.nextLine());

                        calendar.ajouter(new EvenementPeriodique(
                            EventId.nouveau(),
                            new TitreEvenement(titre3),
                            new DateEvenement(LocalDate.of(annee3, moisRdv3, jourRdv3)),
                            new HeureDebut(LocalTime.of(heure3, minute3)),
                            new DureeEvenement(duree3),
                            new FrequenceJours(frequence)
                        ));
                        System.out.println("Événement ajouté.");
                        break;

                    case "5":
                        List<Evenement> tous = calendar.tousLesEvenements();
                        if (tous.isEmpty()) {
                            System.out.println("Aucun événement à supprimer.");
                        } else {
                            System.out.println("Événements disponibles :");
                            for (int i = 0; i < tous.size(); i++) {
                                System.out.println((i + 1) + " - " + tous.get(i).description());
                            }
                            System.out.print("Numéro à supprimer (0 pour annuler) : ");
                            int num = Integer.parseInt(scanner.nextLine());
                            if (num > 0 && num <= tous.size()) {
                                calendar.supprimer(tous.get(num - 1).id());
                                System.out.println("Événement supprimé.");
                            }
                        }
                        break;

                    default:
                        System.out.println("Déconnexion !");
                        utilisateur = null;
                        System.out.print("Voulez-vous continuer ? (oui/non) : ");
                        continuer = scanner.nextLine().trim().equalsIgnoreCase("oui");
                }
            }
        }
    }

    private static void afficherListe(List<Evenement> evenements) {
        if (evenements.isEmpty()) {
            System.out.println("Aucun événement trouvé pour cette période.");
        } else {
            System.out.println("Événements trouvés : ");
            for (Evenement e : evenements) {
                System.out.println("- " + e.description());
            }
        }
    }
}
