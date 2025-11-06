package proiect2poo.tournamenttracker.utile;

import proiect2poo.tournamenttracker.model.Participant;
import java.util.HashMap;

/**
 * Mapare rapidă a scorurilor pentru clasament la un turneu.
 */
public class Rankings {
    private HashMap<Participant, Integer> ranks = new HashMap<>();

    public void setRank(Participant p, int scor) {
        ranks.put(p, scor);
    }

    public int getRank(Participant p) {
        return ranks.getOrDefault(p, 0);
    }
}

