package proiect2poo.tournamenttracker.data;

public interface Saveable {
    void save(String fileName);
    void load(String fileName);
}
