package proiect2poo.tournamenttracker.utile;


import proiect2poo.tournamenttracker.model.entitati.Turneu;
import java.util.Comparator;

public class ComparatorTurneu implements Comparator<Turneu> {
    @Override
    public int compare(Turneu t1, Turneu t2) {
        return t1.getNume().compareTo(t2.getNume());
    }
}
