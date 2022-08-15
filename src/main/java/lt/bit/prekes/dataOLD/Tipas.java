package lt.bit.prekes.dataOLD;

public class Tipas {
    private int id;
    private String pavadinimas;

    public Tipas(int id, String pavadinimas) {
        this.id = id;
        this.pavadinimas = pavadinimas;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tipas{" +
                "id=" + id +
                ", pavadinimas='" + pavadinimas + '\'' +
                '}';
    }
}
