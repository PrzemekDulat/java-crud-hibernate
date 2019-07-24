import javax.persistence.*;

@Entity
public class Wynik {

    public Wynik(int wygraneRundyDruzyny1, int wygraneRundyDruzyny2)
    {
        this.wygraneRundyDruzyny1 = wygraneRundyDruzyny1;
        this.wygraneRundyDruzyny2 = wygraneRundyDruzyny2;
    }

    @Id    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWynik;

    private int wygraneRundyDruzyny1;

    private int wygraneRundyDruzyny2;






    public int getIdWynik() {
        return idWynik;
    }

    public void setIdWynik(int idWynik) {
        this.idWynik = idWynik;
    }

    public int getWygraneRundyDruzyny1() {
        return wygraneRundyDruzyny1;
    }

    public void setWygraneRundyDruzyny1(int wygraneRundyDruzyny1) {
        this.wygraneRundyDruzyny1 = wygraneRundyDruzyny1;
    }

    public int getWygraneRundyDruzyny2() {
        return wygraneRundyDruzyny2;
    }

    public void setWygraneRundyDruzyny2(int wygraneRundyDruzyny2) {
        this.wygraneRundyDruzyny2 = wygraneRundyDruzyny2;
    }

    public Wynik(){}
}
