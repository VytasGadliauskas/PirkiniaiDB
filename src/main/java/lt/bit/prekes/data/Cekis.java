package lt.bit.prekes.data;

import java.util.Date;

public class Cekis {
    private int id;
    private Date data;
    private String pavadinimas;
    private String aprasymas;

    public Cekis(int id, Date data, String pavadinimas) {
        this.id = id;
        this.data = data;
        this.pavadinimas = pavadinimas;
    }

    public Cekis(int id, Date data, String pavadinimas, String aprasymas) {
        this.id = id;
        this.data = data;
        this.pavadinimas = pavadinimas;
        this.aprasymas = aprasymas;
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    @Override
    public String toString() {
        return "Cekis{" +
                "id=" + id +
                ", data=" + data +
                ", pavadinimas='" + pavadinimas + '\'' +
                ", aprasymas='" + aprasymas + '\'' +
                '}';
    }
}
