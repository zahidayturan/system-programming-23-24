import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Aboneler implements Serializable {
    private static final long serialVersionUID = 1L;

    private long lastUpdatedEpochMiliSeconds;
    private List<Integer> abonelerListesi;
    private List<Integer> girisYapanlarListesi;

    public Aboneler(){
        lastUpdatedEpochMiliSeconds = 0;
        abonelerListesi = new ArrayList<Integer>();
        girisYapanlarListesi = new ArrayList<Integer>();
    }

    public long getEpochMiliSeconds() {
        return lastUpdatedEpochMiliSeconds;
    }

    public void setEpochMiliSeconds(long lastUpdatedEpochMiliSeconds) {
        this.lastUpdatedEpochMiliSeconds = lastUpdatedEpochMiliSeconds;
    }

    public List<Integer> getAboneler() {
        return abonelerListesi;
    }

    public void setAboneler(List<Integer> aboneler) {
        abonelerListesi = aboneler;
    }

    public List<Integer> getGirisYapanlarListesi() {
        return girisYapanlarListesi;
    }

    public void setGirisYapanlarListesi(List<Integer> girisYapanlarListesi) {
        this.girisYapanlarListesi = girisYapanlarListesi;
    }
}
