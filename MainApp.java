package proiect2poo.tournamenttracker.ui;

import proiect2poo.tournamenttracker.model.Participant;
import proiect2poo.tournamenttracker.model.entitati.Turneu;
import proiect2poo.tournamenttracker.model.oameni.Jucator;
import proiect2poo.tournamenttracker.model.entitati.Echipa;
import proiect2poo.tournamenttracker.model.entitati.Meci;
import proiect2poo.tournamenttracker.service.TournamentService;
import proiect2poo.tournamenttracker.data.DataManager;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TournamentService ts = new TournamentService();
        DataManager dataManager = new DataManager();
        Turneu turneu = new Turneu("Cupa Generala");
        String numeFisier = "turneu_date.txt";

        while (true) {
            System.out.println("\n-- MENIU --");
            System.out.println("1. Adauga jucator");
            System.out.println("2. Adauga echipa");
            System.out.println("3. Adauga jucator in echipa");
            System.out.println("4. Genereaza meciuri Jucator vs Jucator");
            System.out.println("5. Afiseaza toate meciurile");
            System.out.println("6. Inregistreaza scoruri la meciuri");
            System.out.println("7. Afiseaza clasament");
            System.out.println("8. Salveaza totul in " + numeFisier);
            System.out.println("9. Incarca totul din " + numeFisier);
            System.out.println("0. Iesire");
            System.out.print("Alege optiunea: ");
            String opt = sc.nextLine().trim();

            if (opt.equals("1")) {
                System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                System.out.print("Nume: "); String nume = sc.nextLine();
                System.out.print("Prenume: "); String prenume = sc.nextLine();
                System.out.print("Rating: "); int rating = Integer.parseInt(sc.nextLine());
                Jucator j = new Jucator(id, nume, prenume, rating);
                turneu.adaugaParticipant(j);
                dataManager.adaugaJucator(j);
                System.out.println("Jucator adaugat!");

            } else if (opt.equals("2")) {
                System.out.print("Nume echipa: "); String numeE = sc.nextLine();
                Echipa echipa = new Echipa(numeE);
                turneu.adaugaParticipant(echipa);
                dataManager.adaugaEchipa(echipa);
                System.out.println("Echipa adaugata!");

            } else if (opt.equals("3")) {
                System.out.print("Nume echipa: "); String numeE = sc.nextLine();
                Echipa echipa = null;
                for (Participant p : turneu.getParticipanti())
                    if (p instanceof Echipa && p.getNume().equals(numeE)) { echipa = (Echipa) p; break; }
                if (echipa == null) { System.out.println("Echipa nu exista!"); continue; }

                System.out.print("ID Jucator: "); int idJ = Integer.parseInt(sc.nextLine());
                Jucator jucator = null;
                for (Participant p : turneu.getParticipanti())
                    if (p instanceof Jucator && ((Jucator)p).getId() == idJ) { jucator = (Jucator)p; break; }
                if (jucator == null) { System.out.println("Jucatorul nu exista!"); continue; }

                echipa.addJucator(jucator);
                System.out.println("Jucator adaugat in echipa!");

            } else if (opt.equals("4")) {
                turneu.genereazaMeciuriJucatorVsJucator();
                dataManager.getMeciuri().clear();
                for (Meci m : turneu.getMeciuri())
                    dataManager.adaugaMeci(m);
                System.out.println("Meciuri Jucator vs Jucator generate!");

            } else if (opt.equals("5")) {
                int idx = 1;
                for (Meci m : turneu.getMeciuri()) {
                    Jucator a = (Jucator) m.getParticipantA();
                    Jucator b = (Jucator) m.getParticipantB();
                    System.out.print(idx + ". " + a.getNume() + " " + a.getPrenume());
                    System.out.print(" vs ");
                    System.out.println(b.getNume() + " " + b.getPrenume());
                    idx++;
                }
                if (turneu.getMeciuri().isEmpty()) System.out.println("Nu exista meciuri generate!");

            } else if (opt.equals("6")) {
                int idx = 1;
                for (Meci m : turneu.getMeciuri()) {
                    if (m.isFinalizat()) continue;
                    Jucator a = (Jucator) m.getParticipantA();
                    Jucator b = (Jucator) m.getParticipantB();
                    System.out.println("Meci " + idx + ": " + a.getNume() + " " + a.getPrenume()
                            + " vs " + b.getNume() + " " + b.getPrenume());
                    System.out.print("Scor " + a.getNume() + " (seturi castigate): ");
                    int scorA = Integer.parseInt(sc.nextLine());
                    System.out.print("Scor " + b.getNume() + " (seturi castigate): ");
                    int scorB = Integer.parseInt(sc.nextLine());
                    m.adaugaSet(scorA, scorB);
                    m.finalizeaza();
                    ts.updateStatsAfterMatch(m);
                    dataManager.adaugaMeci(m);
                    idx++;
                }
                System.out.println("Scorurile au fost inregistrate!");

            } else if (opt.equals("7")) {
                System.out.println("\n-- CLASAMENT --");
                for (Participant p : turneu.clasament()) {
                    if (p instanceof Jucator) {
                        Jucator j = (Jucator) p;
                        String echipa = "Individual";
                        for (Participant pp : turneu.getParticipanti())
                            if (pp instanceof Echipa && ((Echipa)pp).getMembri().contains(j))
                                echipa = ((Echipa)pp).getNume();
                        System.out.println(j.getNume() + " " + j.getPrenume() + " (" + echipa + ") | puncte: " + j.getScore());
                    }
                }

            } else if (opt.equals("8")) {
                dataManager.save(numeFisier);
                System.out.println("Datele au fost salvate in " + numeFisier);

            } else if (opt.equals("9")) {
                dataManager.load(numeFisier);
                // REPOPULARE cu totul in Turneu:
                turneu.getParticipanti().clear();
                turneu.getMeciuri().clear();
                for (Jucator j : dataManager.getJucatori())
                    turneu.adaugaParticipant(j);
                for (Echipa e : dataManager.getEchipe())
                    turneu.adaugaParticipant(e);
                for (Meci m : dataManager.getMeciuri())
                    turneu.getMeciuri().add(m);

                System.out.println("Datele au fost incarcate din " + numeFisier);

            } else if (opt.equals("0")) {
                System.out.println("La revedere!");
                break;

            } else {
                System.out.println("Optiune invalida!");
            }
        }
    }
}
