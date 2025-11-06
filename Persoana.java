package proiect2poo.tournamenttracker.model.oameni;
/**
 * Clasă abstractă pentru persoane (jucători, arbitri, etc).
 */
public abstract class Persoana {
    protected int id;
    protected String nume;
    protected String prenume;

    public Persoana(int id, String nume, String prenume) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public void setId(int id) { this.id = id; }
    public void setNume(String nume) { this.nume = nume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    @Override
    public String toString() {
        return id + " " + nume + " " + prenume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Persoana p = (Persoana) obj;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
