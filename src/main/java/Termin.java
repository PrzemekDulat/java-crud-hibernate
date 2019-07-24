import javax.persistence.*;
import java.util.List;

@Entity
public class Termin {

    public Termin(String data)
    {
        this.data = data;
    }

    @Id    //@GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTermin;

    private String data;

    public int getIdTermin() {
        return idTermin;
    }

    public void setIdTermin(int idTermin) {
        this.idTermin = idTermin;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Termin(){}

}
