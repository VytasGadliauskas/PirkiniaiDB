package lt.bit.prekes.dataOLD;

public class Preke {
    private int id;
    private int cekis_id;
    private String preke;
    private double kiekis;
    private double kaina;
    private int tipas_id;

    public Preke(int id, int cekis_id, String preke, double kiekis, double kaina, int tipas_id) {
        this.id = id;
        this.cekis_id = cekis_id;
        this.preke = preke;
        this.kiekis = kiekis;
        this.kaina = kaina;
        this.tipas_id = tipas_id;
    }

    public Preke(int cekis_id, String preke, double kiekis, double kaina, int tipas_id) {
        this.cekis_id = cekis_id;
        this.preke = preke;
        this.kiekis = kiekis;
        this.kaina = kaina;
        this.tipas_id = tipas_id;
    }

    public int getId() {
        return id;
    }

    public int getCekis_id() {
        return cekis_id;
    }

    public void setCekis_id(int cekis_id) {
        this.cekis_id = cekis_id;
    }

    public String getPreke() {
        return preke;
    }

    public void setPreke(String preke) {
        this.preke = preke;
    }

    public double getKiekis() {
        return kiekis;
    }

    public void setKiekis(double kiekis) {
        this.kiekis = kiekis;
    }

    public double getKaina() {
        return kaina;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public int getTipas_id() {
        return tipas_id;
    }

    public void setTipas_id(int tipas_id) {
        this.tipas_id = tipas_id;
    }

    @Override
    public String toString() {
        return "Prekes{" +
                "id=" + id +
                ", cekis_id=" + cekis_id +
                ", preke='" + preke + '\'' +
                ", kiekis=" + kiekis +
                ", kaina=" + kaina +
                ", tipas_id=" + tipas_id +
                '}';
    }
}
