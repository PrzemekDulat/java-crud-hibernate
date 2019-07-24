import javax.persistence.*;

@Entity
public class Mecz {

    //ID
    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMecz;

    @OneToOne
    @JoinColumn(name = "idDruzyna1")
    private Druzyna idDruzyna1;

    @OneToOne
    @JoinColumn(name = "idDruzyna2")
    private Druzyna idDruzyna2;

    @OneToOne
    @JoinColumn(name = "idWynik")
    private Wynik idWyniku;

    @OneToOne
    @JoinColumn(name = "idTermin")
    private Termin idTerminu;

    public Druzyna getIdDruzyna1() {
        return idDruzyna1;
    }

    public void setIdDruzyna1(Druzyna idDruzyna1) {
        this.idDruzyna1 = idDruzyna1;
    }

    public Druzyna getIdDruzyna2() {
        return idDruzyna2;
    }

    public void setIdDruzyna2(Druzyna idDruzyna2) {
        this.idDruzyna2 = idDruzyna2;
    }

    public Wynik getIdWyniku() {
        return idWyniku;
    }

    public void setIdWyniku(Wynik idWyniku) {
        this.idWyniku = idWyniku;
    }

    public Termin getIdTerminu() {
        return idTerminu;
    }

    public void setIdTerminu(Termin idTerminu) {
        this.idTerminu = idTerminu;
    }

    public int getIdMecz() {
        return idMecz;
    }

    public void setIdMecz(int idMecz) {
        this.idMecz = idMecz;
    }

}
