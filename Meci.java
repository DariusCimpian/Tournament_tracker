package proiect2poo.tournamenttracker.model.entitati;

import proiect2poo.tournamenttracker.model.Participant;
import java.util.ArrayList;
import java.util.List;

public class Meci {
    private int id;
    private Participant participantA;
    private Participant participantB;
    private List<Integer> seturiA;
    private List<Integer> seturiB;
    private boolean finalizat;

    public Meci(int id, Participant a, Participant b, Object data) {
        this.id = id;
        this.participantA = a;
        this.participantB = b;
        this.seturiA = new ArrayList<>();
        this.seturiB = new ArrayList<>();
        this.finalizat = false;
    }

    public int getId() { return id; }
    public Participant getParticipantA() { return participantA; }
    public Participant getParticipantB() { return participantB; }
    public List<Integer> getSeturiA() { return seturiA; }
    public List<Integer> getSeturiB() { return seturiB; }

    // Setters pentru DataManager!
    public void setSeturiA(List<Integer> s) { this.seturiA = s; }
    public void setSeturiB(List<Integer> s) { this.seturiB = s; }

    public boolean isFinalizat() { return finalizat; }
    public void adaugaSet(int scorA, int scorB) {
        seturiA.add(scorA);
        seturiB.add(scorB);
    }
    public void finalizeaza() { this.finalizat = true; }

    @Override
    public String toString() {
        return "Meci #" + id + ": " + participantA.getNume() + " vs " + participantB.getNume();
    }
}
