package proiect2poo.tournamenttracker.model.entitati;

import proiect2poo.tournamenttracker.model.Participant;
import proiect2poo.tournamenttracker.model.oameni.Jucator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Turneu {
    private String nume;
    private final List<Participant> participanti;
    private final List<Meci> meciuri;

    public Turneu(String nume) {
        this.nume = nume;
        this.participanti = new ArrayList<>();
        this.meciuri = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public List<Participant> getParticipanti() {
        return participanti;
    }

    public List<Meci> getMeciuri() {
        return meciuri;
    }

    public void adaugaParticipant(Participant p) {
        if (!participanti.contains(p))
            participanti.add(p);
    }

    public void eliminaParticipant(Participant p) {
        participanti.remove(p);
    }

    // SINCRONIZEAZĂ cu jucătorii existenți la generare!
    public void sincronizeazaParticipantiCuJucatori(List<Jucator> jucatoriActuali) {
        participanti.clear();
        participanti.addAll(jucatoriActuali);
    }

    // Generează doar meciuri JUCĂTOR VS JUCĂTOR existenți
    public void genereazaMeciuriJucatorVsJucator() {
        meciuri.clear();
        List<Jucator> jucatori = new ArrayList<>();
        for (Participant p : participanti) {
            if (p instanceof Jucator) {
                jucatori.add((Jucator) p);
            }
        }
        int id = 1;
        for (int i = 0; i < jucatori.size(); i++) {
            for (int j = i + 1; j < jucatori.size(); j++) {
                meciuri.add(new Meci(id++, jucatori.get(i), jucatori.get(j), null));
            }
        }
    }

    public void genereazaRoundRobin() {
        meciuri.clear();
        int id = 1;
        for (int i = 0; i < participanti.size(); i++) {
            for (int j = i + 1; j < participanti.size(); j++) {
                meciuri.add(new Meci(id++, participanti.get(i), participanti.get(j), null));
            }
        }
    }

    public List<Participant> clasament() {
        participanti.sort(Comparator.comparingInt(Participant::getScore).reversed());
        return participanti;
    }
}
