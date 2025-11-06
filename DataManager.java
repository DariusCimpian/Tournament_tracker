package proiect2poo.tournamenttracker.data;

import proiect2poo.tournamenttracker.model.oameni.Jucator;
import proiect2poo.tournamenttracker.model.entitati.Echipa;
import proiect2poo.tournamenttracker.model.entitati.Meci;
import proiect2poo.tournamenttracker.model.Participant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataManager – gestionare simplă a datelor în fișier text.
 * Salvează și încarcă Jucători, Echipe și Meciuri.
 */
public class DataManager implements Saveable {
    private List<Jucator> jucatori = new ArrayList<>();
    private List<Echipa> echipe = new ArrayList<>();
    private List<Meci> meciuri = new ArrayList<>();

    @Override
    public void save(String fileName) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("#JUCATORI");
            for (Jucator j : jucatori) {
                out.printf("%d;%s;%s;%d;%d;%d\n", j.getId(), j.getNume(), j.getPrenume(),
                        j.getRating(), j.getVictorii(), j.getInfrangeri());
            }
            out.println("#ECHIPE");
            for (Echipa e : echipe) {
                out.printf("%s;%d\n", e.getNume(), e.getMembri().size());
                for (Jucator j : e.getMembri()) {
                    out.printf("- %d\n", j.getId());
                }
            }
            out.println("#MECIURI");
            for (Meci m : meciuri) {
                out.printf("%d;%d;%d;%s;%s;%b\n",
                        m.getId(),
                        getIdForParticipant(m.getParticipantA()),
                        getIdForParticipant(m.getParticipantB()),
                        serializeList(m.getSeturiA()),
                        serializeList(m.getSeturiB()),
                        m.isFinalizat()
                );
            }
        } catch (IOException e) {
            System.err.println("Eroare la salvare in fisier: " + e.getMessage());
        }
    }

    @Override
    public void load(String fileName) {

        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            String section = "";
            Echipa echipaCurenta = null;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("#")) {
                    section = line;
                    continue;
                }
                if (section.equals("#JUCATORI")) {
                    String[] split = line.split(";");
                    if (split.length >= 6) {
                        Jucator j = new Jucator(
                                Integer.parseInt(split[0]),
                                split[1],
                                split[2],
                                Integer.parseInt(split[3])
                        );
                        for (int i = 0; i < Integer.parseInt(split[4]); i++) j.incVictorii();
                        for (int i = 0; i < Integer.parseInt(split[5]); i++) j.incInfrangeri();
                        jucatori.add(j);
                    }
                } else if (section.equals("#ECHIPE")) {
                    if (!line.startsWith("-")) {
                        String[] split = line.split(";");
                        echipaCurenta = new Echipa(split[0]);
                        echipe.add(echipaCurenta);
                    } else if (echipaCurenta != null) {
                        int jucatorId = Integer.parseInt(line.substring(2));
                        Jucator j = jucatori.stream()
                                .filter(x -> x.getId() == jucatorId)
                                .findFirst().orElse(null);
                        if (j != null)
                            echipaCurenta.addJucator(j);
                    }
                } else if (section.equals("#MECIURI")) {
                    String[] split = line.split(";");
                    if (split.length >= 6) {
                        int id = Integer.parseInt(split[0]);
                        Participant a = (Participant) findParticipantById(Integer.parseInt(split[1]));
                        Participant b = (Participant) findParticipantById(Integer.parseInt(split[2]));
                        Meci m = new Meci(id, a, b, null);
                        m.setSeturiA(deserializeList(split[3]));
                        m.setSeturiB(deserializeList(split[4]));
                        if (Boolean.parseBoolean(split[5]))
                            m.finalizeaza();
                        meciuri.add(m);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citire fisier: " + e.getMessage());
        }
    }

    // Helper: identificare participant dupa id
    private Participant findParticipantById(int id) {
        for (Jucator j : jucatori)
            if (j.getId() == id) return j;
        for (Echipa e : echipe)
            if (e.getNume().hashCode() == id) return e;
        return null;
    }
    private int getIdForParticipant(Participant p) {
        if (p instanceof Jucator)
            return ((Jucator) p).getId();
        else if (p instanceof Echipa)
            return ((Echipa) p).getNume().hashCode();
        return -1;
    }

    // Serializeaza/deserializeaza scoruri seturi
    private String serializeList(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : list) sb.append(i).append(",");
        return sb.toString();
    }
    private List<Integer> deserializeList(String data) {
        List<Integer> list = new ArrayList<>();
        if (data != null && !data.isEmpty()) {
            String[] arr = data.split(",");
            for (String val : arr)
                if (!val.isEmpty())
                    list.add(Integer.parseInt(val));
        }
        return list;
    }
    public void stergeJucator(Jucator j) {
        jucatori.remove(j);
    }
    public List<Jucator> getJucatori() { return jucatori; }
    public List<Echipa> getEchipe() { return echipe; }
    public List<Meci> getMeciuri() { return meciuri; }
    public void adaugaJucator(Jucator j) { jucatori.add(j); }
    public void adaugaEchipa(Echipa e) { echipe.add(e); }
    public void adaugaMeci(Meci m) { meciuri.add(m); }
}
