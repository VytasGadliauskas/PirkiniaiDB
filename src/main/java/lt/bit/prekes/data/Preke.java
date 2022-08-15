package lt.bit.prekes.data;

import jakarta.persistence.*;

@Entity(name="Prekes")
@Table(name = "Prekes")
public class Preke {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "preke", nullable = false)
    private String preke;

    @Column(name = "kiekis", nullable = false)
    private double kiekis;

    @Column(name = "kaina", nullable = false)
    private double kaina;

    @ManyToOne
    @JoinColumn(name = "cekis_id")
    private Cekis cekis;

    @ManyToOne
    @JoinColumn(name = "tipas_id")
    private Tipas tipas;

    public Preke() {
    }

    public Preke(Cekis cekis, String prekep, Double kiekis, Double kaina, Tipas tipas) {
        this.cekis = cekis;
        this.preke = prekep;
        this.kiekis = kiekis;
        this.kaina = kaina;
        this.tipas = tipas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPreke(String preke) {
        this.preke = preke;
    }

    public String getPreke() {
        return preke;
    }

    public void setKiekis(double kiekis) {
        this.kiekis = kiekis;
    }

    public double getKiekis() {
        return kiekis;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public double getKaina() {
        return kaina;
    }

    public Cekis getCekis() {
        return cekis;
    }

    public void setCekis(Cekis cekis) {
        this.cekis = cekis;
    }

    public Tipas getTipas() {
        return tipas;
    }

    public void setTipas(Tipas tipas) {
        this.tipas = tipas;
    }

    @Override
    public String toString() {
        return "Preke{" +
                "id=" + id +
                ", cekis_id=" + cekis.getId() +
                ", preke='" + preke +
                ", kiekis=" + kiekis +
                ", kaina=" + kaina +
                ", tipas_id=" + tipas.getId() +
                '}';
    }
}
