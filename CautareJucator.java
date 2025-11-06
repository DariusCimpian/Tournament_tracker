package proiect2poo.tournamenttracker.utile;

import proiect2poo.tournamenttracker.model.oameni.Jucator;
import java.util.HashMap;

/**
 * Permite identificarea rapidă a unui jucător după id.
 */
public class CautareJucator {
    private HashMap<Integer, Jucator> index = new HashMap<>();

    public void adaugaJucator(Jucator j) {
        index.put(j.getId(), j);
    }

    public Jucator getJucator(int id) {
        return index.get(id);
    }

    public boolean exists(int id) {
        return index.containsKey(id);
    }
}

