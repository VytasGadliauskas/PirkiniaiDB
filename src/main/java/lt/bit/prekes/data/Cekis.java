package lt.bit.prekes.data;

import jakarta.persistence.*;
import lt.bit.prekes.data.Preke;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cekis")
public class Cekis {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "parduotuve", nullable = false)
    private String parduotuve;

    @Column(name = "aprasymas")
    private String aprasymas;
    @jakarta.persistence.OneToMany(mappedBy = "cekis")
    private List<Preke> prekes = new ArrayList<>();

    public Cekis() {
    }

    public Cekis(Date data, String parduotuve, String aprasymas) {
        this.data = data;
        this.parduotuve = parduotuve;
        this.aprasymas = aprasymas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setParduotuve(String parduotuve) {
        this.parduotuve = parduotuve;
    }

    public String getParduotuve() {
        return parduotuve;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public List<Preke> getPrekes() {
        return prekes;
    }

    public void setPrekes(List<Preke> prekes) {
        this.prekes = prekes;
    }

    @Override
    public String toString() {
        return "Cekis{" +
                "id=" + id +
                ", data=" + data +
                ", parduotuve='" + parduotuve + ", aprasymas='" + aprasymas + '}';
    }
}
