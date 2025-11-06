package proiect2poo.tournamenttracker.model.oameni;

import proiect2poo.tournamenttracker.model.Participant;

/**
 * Jucătorul este și Persoană și Participant la turneu.
 */
public class Jucator extends Persoana implements Participant {
    private int rating;
    private int victorii;
    private int infrangeri;

    public Jucator(int id, String nume, String prenume, int rating) {
        super(id, nume, prenume);
        this.rating = rating;
        this.victorii = 0;
        this.infrangeri = 0;
    }

    public int getVictorii() { return victorii; }
    public void incVictorii() { this.victorii++; }
    public int getInfrangeri() { return infrangeri; }
    public void incInfrangeri() { this.infrangeri++; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    @Override
    public int getScore() { return victorii; }

    @Override
    public void resetScore() { this.victorii = 0; this.infrangeri = 0; }

    @Override
    public String toString() {
        return super.toString() + " (Rating: " + rating + ", Victorii: " + victorii + ")";
    }
}
