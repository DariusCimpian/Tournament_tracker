package proiect2poo.tournamenttracker.service;

import proiect2poo.tournamenttracker.model.entitati.Meci;
import proiect2poo.tournamenttracker.model.Participant;
import proiect2poo.tournamenttracker.model.oameni.Jucator;
import proiect2poo.tournamenttracker.model.entitati.Echipa;
import java.util.List;

/**
 * Serviciu pentru actualizare scoruri după meci (și Echipe, și Jucători).
 */
public class TournamentService {
    public void updateStatsAfterMatch(Meci m) {
        if (!m.isFinalizat()) return;
        Object a = m.getParticipantA();
        Object b = m.getParticipantB();

        int victoriiA = 0, victoriiB = 0;
        List<Integer> setsA = m.getSeturiA();
        List<Integer> setsB = m.getSeturiB();
        for (int i = 0; i < setsA.size() && i < setsB.size(); i++) {
            if (setsA.get(i) > setsB.get(i)) victoriiA++;
            else if (setsB.get(i) > setsA.get(i)) victoriiB++;
        }

        // Pentru Jucător vs Jucător
        if (a instanceof Jucator && b instanceof Jucator) {
            Jucator ja = (Jucator) a;
            Jucator jb = (Jucator) b;

            if (victoriiA > victoriiB) { ja.incVictorii(); jb.incInfrangeri(); }
            else if (victoriiB > victoriiA) { jb.incVictorii(); ja.incInfrangeri(); }
        }
        // Pentru Echipă vs Echipă
        else if (a instanceof Echipa && b instanceof Echipa) {
            Echipa ea = (Echipa) a;
            Echipa eb = (Echipa) b;
            if (victoriiA > victoriiB) { ea.adaugaPuncte(2); }
            else if (victoriiB > victoriiA) { eb.adaugaPuncte(2); }
        }
        // Pentru Jucător vs Echipă (sau invers, rareori folosit)
        else if (a instanceof Jucator && b instanceof Echipa) {
            Jucator ja = (Jucator) a;
            Echipa eb = (Echipa) b;
            if (victoriiA > victoriiB) { ja.incVictorii(); }
            else if (victoriiB > victoriiA) { eb.adaugaPuncte(2); }
        }
        else if (a instanceof Echipa && b instanceof Jucator) {
            Echipa ea = (Echipa) a;
            Jucator jb = (Jucator) b;
            if (victoriiA > victoriiB) { ea.adaugaPuncte(2); }
            else if (victoriiB > victoriiA) { jb.incVictorii(); }
        }
    }
}


