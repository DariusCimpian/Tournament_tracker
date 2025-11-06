package proiect2poo.tournamenttracker.model.entitati;

import proiect2poo.tournamenttracker.model.Participant;
import proiect2poo.tournamenttracker.model.oameni.Jucator;
import java.util.ArrayList;
import java.util.List;
/**
 * Echipa – participant la turnee pe echipe.
 */
public class Echipa implements Participant {
    private String nume;
    private List<Jucator> membri;
    private int puncte;

    public Echipa(String nume) {
        this.nume = nume;
        this.membri = new ArrayList<>();
        this.puncte = 0;
    }

    public void addJucator(Jucator j) { membri.add(j); }
    public void removeJucator(Jucator j) { membri.remove(j); }
    public List<Jucator> getMembri() { return membri; }
    @Override
    public String getNume() { return nume; }
    @Override
    public int getScore() { return puncte; }
    public void adaugaPuncte(int p) { puncte += p; }
    @Override
    public void resetScore() { puncte = 0; }
    @Override
    public String toString() {
        return "Echipa " + nume + " (" + membri.size() + " membri)";
    }
}

