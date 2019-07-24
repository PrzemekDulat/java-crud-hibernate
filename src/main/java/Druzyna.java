import javax.persistence.*;
import java.util.List;

@Entity
public class Druzyna {


    public Druzyna(String nazwa, int ilosc_zawodnikow, int ilosc_wygranych) {
        this.nazwa = nazwa;
        this.ilosc_zawodnikow = ilosc_zawodnikow;
        this.ilosc_wygranych = ilosc_wygranych;
    }

    @Id    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDruzyny;

    private String nazwa;

    private int ilosc_zawodnikow;

    private int ilosc_wygranych;

    @OneToMany(mappedBy = "druzyna")
    private List<Zawodnik> zawodnikList;


    public List<Zawodnik> getZawodnikList() {
        return zawodnikList;
    }

    public void setZawodnikList(List<Zawodnik> zawodnikList) {
        this.zawodnikList = zawodnikList;
    }

    public int getIdDruzyny() {
        return idDruzyny;
    }

    public void setIdDruzyny(int idDruzyny) {
        this.idDruzyny = idDruzyny;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc_zawodnikow() {
        return ilosc_zawodnikow;
    }

    public void setIlosc_zawodnikow(int ilosc_zawodnikow) {
        this.ilosc_zawodnikow = ilosc_zawodnikow;
    }

    public int getIlosc_wygranych() {
        return ilosc_wygranych;
    }

    public void setIlosc_wygranych(int ilosc_wygranych) {
        this.ilosc_wygranych = ilosc_wygranych;
    }

    public Druzyna(){}
}
