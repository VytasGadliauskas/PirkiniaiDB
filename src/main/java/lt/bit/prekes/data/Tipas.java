package lt.bit.prekes.data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tipas")
public class Tipas {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pavadinimas", nullable = false)
    private String pavadinimas;

    @OneToMany(mappedBy = "tipas")
    private List<Preke> prekes = new ArrayList<>();

    public Tipas() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public List<Preke> getPrekes() {
        return prekes;
    }

    public void setPrekes(List<Preke> prekes) {
        this.prekes = prekes;
    }

    @Override
    public String toString() {
        return "Tipas{" +
                "id=" + id +
                ", pavadinimas='" + pavadinimas +'}';
    }
}
