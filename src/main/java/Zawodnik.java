import javax.persistence.*;

@Entity
public class Zawodnik {

    public Zawodnik(String imie, String nick, String nazwisko, Druzyna druzyna) {
        this.imie = imie;
        this.nick = nick;
        this.nazwisko = nazwisko;
        this.druzyna = druzyna;
    }

    @Id    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idZawodnik;

    private String imie;

    private String nick;

    private String nazwisko;



    @Column(name = "Zdjecie", unique = false, nullable = true, length = 100000)
    private byte[] fotka;

    @ManyToOne
    @JoinColumn(name = "idDruzyna")
    private Druzyna druzyna;

    public Druzyna getDruzyna() {
        return druzyna;
    }

    public void setDruzyna(Druzyna druzyna) {
        this.druzyna = druzyna;
    }

    public int getIdZawodnik() {
        return idZawodnik;
    }

    public byte[] getFotka() {
        return fotka;
    }

    public void setFotka(byte[] fotka) {
        this.fotka = fotka;
    }

    public void setIdZawodnik(int idZawodnik) {
        this.idZawodnik = idZawodnik;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Zawodnik (){}

}
