package ohtu.verkkokauppa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Kauppa {

//    private Varasto varasto;
//    private Pankki pankki;
    private Ostoskori ostoskori;
//    private Viitegeneraattori viitegeneraattori;
    private VarastoIO varasto;
    private PankkiIO pankki;
    private ViitegeneraattoriIO viitegeneraattori;
    private String kaupanTili;

    public Kauppa(IO v, IO p, IO vg) {
        varasto = (VarastoIO) v;
        pankki = (PankkiIO) p;
        viitegeneraattori = (ViitegeneraattoriIO) vg;
        kaupanTili = "33333-44455";
    }

    public void aloitaAsiointi() {
        System.out.println("asioidaan");
        ostoskori = new Ostoskori();
    }

    public void poistaKorista(int id) {
        Tuote t = varasto.haeTuote(id);
        varasto.palautaVarastoon(t);
    }

    public void lisaaKoriin(int id) {
        if (varasto.saldo(id) > 0) {
            Tuote t = varasto.haeTuote(id);
            ostoskori.lisaa(t);
            varasto.otaVarastosta(t);
        }
    }

    public boolean tilimaksu(String nimi, String tiliNumero) {
        int viite = viitegeneraattori.uusi();
        int summa = ostoskori.hinta();
        System.out.println("summa: " + summa);
        return pankki.tilisiirto(nimi, viite, tiliNumero, kaupanTili, summa);
    }

}
