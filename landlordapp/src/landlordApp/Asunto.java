package landlordApp;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.SisaltaaTarkistaja;
import kanta.Tietue;

/**
 * Vastuualueet:
 * -Tietää asunnolle vaaditut kentät
 * -Osaa tarkastaa tietyn kentän oikeellisuuden
 *  eli syntakstin.
 * -Osaa muuttaa asunto tiedot tiedostosta asunnon
 *  tiedoiksi
 * -Osaa antaa merkkijonona i:n kentän tiedot
 * -Osaa laittaa merkkijonon i:neksi kentäksi
 * @author Nico & Eelis
 * @version 12.3.2020
 *
 */
public class Asunto implements Cloneable, Tietue {
    //Attribuutit
    private int id;
    private int taloid;
    private String asuntoid = "";
    private int neliot;
    private String viimKorjaus = "";
    private double vuokra;
    private String lisatietoja = "";
    
    private static Random rand;
    private static int seuraavaNro = 1;
    
    /**
     * Alustaa uuden asunnon tietylle taloid:lle
     * @param taloId talon id, johon asunto lisätään
     */
    public Asunto(int taloId) {
        this.taloid = taloId;
    }
    
    /**
     * Alustaa tyhjän asunto-olion.
     */
    public Asunto() {
    }
    
    /**
     * Rekisteröi uuden asunnon tietorakenteeseen.
     * @return rekisteröidyn asunnon id.
     * @example
	 * <pre name="test">
	 * Asunto asunto = new Asunto();
	 * asunto.getId() === 0;
	 * asunto.rekisteroi();
	 * Asunto asunto2 = new Asunto();
	 * asunto2.rekisteroi();
	 * int n1 = asunto.getId();
	 * int n2 = asunto2.getId();
	 * n1 === n2-1;
	 * </pre>
     */
    public int rekisteroi() {
        this.id = seuraavaNro;
        Asunto.seuraavaNro++;
        return id;
        
    }
    
    /**
     * @return asunnon taloid
     */
    public int getTaloId() {
    	return this.taloid;
    }
    
    /**
     * Asettaa asunnon taloid:n eli mihin taloon kuuluu
     * @param value asetettava arvo
     */
    public void setTaloId(int value) {
        this.taloid = value;
    }
    
    /**
     * Palauttaa asunnon id:n
     * @return asunnon id
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Asettaa asunnon tunnusnumeron
     * @param value arvo johon asetetaan
     */
    public void setId(int value) {
        this.id = value;
    }
    /**
     * Palauttaa asunnon nimen, eli asuntoid:n
     * @return asunnon id
     */
    public String getAsuntoId() {
       return this.asuntoid;
    }
    
    /**
     * Asettaa asunnon nimen
     * @param value mihin asetetaan
     */
    public void setAsuntoId(String value) {
        this.asuntoid = value;
    }
    /**
     * Täyttää asunnon tiedot oletustiedoilla testaamista varten.
     */
    public void taytaOletus() {
        rand = new Random();
        
        asuntoid = "A"+rand.nextInt(10);
        vuokra = 300 + rand.nextInt(300);
        neliot = 20 + rand.nextInt(40);
        viimKorjaus = String.format("%02d.%02d.%4d", rand.nextInt(28), rand.nextInt(12), 1950 + rand.nextInt(70));
        lisatietoja = "Katto vuotaa xD";
    }
    
    
    @Override
    public String toString() {
        return "" + getId() +"|" +
                taloid + "|" +
                getAsuntoId() + "|" +
                neliot + "|" +
                viimKorjaus + "|" +
                vuokra +  "|" +
                lisatietoja;
                
    }
    
    
    /**
     * Ottaa syötetystä merkkijonosta tiedot ja asettaa ne asunnolle
     * @param rivi syötetty merkkijono ( yleensä tiedostosta)
     * @example
     * <pre name="test">
     * Asunto ab = new Asunto();
     * ab.parse("3|2|A3|28|10.04.1983|389.0|");
     * ab.getId() === 3;
     * ab.toString().equals("3|2|A3|28|10.04.1983|389.0|"); 
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        id = Mjonot.erota(sb, '|', getId());
        taloid = Mjonot.erota(sb, '|', taloid);
        asuntoid = Mjonot.erota(sb, '|', getAsuntoId());
        neliot = Mjonot.erota(sb, '|', neliot);
        viimKorjaus = Mjonot.erota(sb, '|', viimKorjaus);
        vuokra = Mjonot.erota(sb, '|',vuokra);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }
    /**
     * Tulostaa talon tiedot.
     * @param out tietovirta johon tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(id);
        out.println("Talo id: " + taloid);
        out.println(asuntoid);
        out.println("Neliöt: " + neliot);
        out.println("Viimeisin korjaus: " + viimKorjaus);
        out.println(vuokra + "€");
        out.println(lisatietoja + "\n");
        
    }
    
    /**
     * Tulostetaan talon tiedot
     * @param os tietovirta johon tulostetaan.
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Asunto koti = new Asunto(1);
        Asunto koti2 = new Asunto(1);
        
        koti.rekisteroi();
        koti2.rekisteroi();
        
        koti.taytaOletus();
        koti2.taytaOletus();
        
        koti.tulosta(System.out);
        koti2.tulosta(System.out);
        
    }

    @Override
    public int getKenttia() {
        return 7;
    }

    @Override
    public int ekaKentta() {
        return 2;
    }
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */

    @Override
    public String getKysymys(int k) {
        switch(k) {
            case 0: return "id";
            case 1: return "taloid";
            case 2: return "Asunnon tunnus";
            case 3: return "Neliöt";
            case 4: return "Viimeisin korjaus";
            case 5: return "Vuokra";
            case 6: return "Lisätietoja";
            default: return "???";
        }
    }

    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Asunto asu = new Asunto();
     *   asu.parse("3|2|A3|28|10.04.1983|389.0|");
     *   asu.anna(0) === "3";   
     *   asu.anna(1) === "2";   
     *   asu.anna(2) === "A3";   
     *   asu.anna(3) === "28";
     *   
     * </pre>
     */

    @Override
    public String anna(int k) {
        switch(k) {
        
            case 0: return "" + id;
            case 1: return "" + taloid;
            case 2: return "" + asuntoid;
            case 3: return "" + neliot;
            case 4: return "" + viimKorjaus;
            case 5: return "" + vuokra;
            case 6: return "" + lisatietoja;
            default: return "???";
            }
    }
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param s jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Asunto as = new Asunto();
     *   as.aseta(3,"533") === null;
     *   as.aseta(4,"19.5.1995") === null;
     *   as.aseta(4, "kissa") == null === false;
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        String tjono = s.trim();
        StringBuffer sb = new StringBuffer(tjono);
        SisaltaaTarkistaja tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NIMIKIRJAIMET);
        String virhe = "";
        switch ( k ) {
        case 0: 
            setId(Mjonot.erota(sb, '§', getId()));
            return null;
        case 1:
            setTaloId(Mjonot.erota(sb, '§', getTaloId()));
            return null;
        case 2:
            setAsuntoId(Mjonot.erota(sb, '§', getAsuntoId()));
            return null;
        case 3:
            tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NUMEROT);
            virhe = tarkistaja.tarkista(tjono);
            if(virhe != null) return virhe;
            neliot =  Mjonot.erota(sb, '§', neliot);
            return null;
        case 4:
            tarkistaja = new SisaltaaTarkistaja("1234567890 .:v");
            virhe = tarkistaja.tarkista(tjono);
            if (virhe != null) return virhe;
            viimKorjaus = tjono;
            return null;
        case 5:
            tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NUMEROT);
            virhe = tarkistaja.tarkista(tjono);
            if(virhe != null) return virhe;
            vuokra =  Mjonot.erota(sb, '§', vuokra);
            return null;
        case 6:
            lisatietoja = tjono;
            return null;
        default:
            return "Ähäkutti";
        
        }
    }

    @Override
    public Asunto clone() throws CloneNotSupportedException {
        Asunto uusi;
        uusi = (Asunto) super.clone();
        return uusi;
    }
    
    /**
     * Tutkii onko asukkaan tiedot samat kuin parametrina tuodun asukkaan tiedot
     * @param asunto asunot johon verrataab
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Asunto jasen1 = new Asunto();
     *   jasen1.parse("3|2|A3|28|10.04.1983|389.0|");
     *   Asunto jasen2 = new Asunto();
     *   jasen2.parse("3|2|A3|28|10.04.1983|389.0|");
     *   Asunto jasen3 = new Asunto();
     *   jasen3.parse("3|5|A3|28|10.04.1983|389.0|");
     *   
     *   jasen1.equals(jasen2) === true;
     *   jasen2.equals(jasen1) === true;
     *   jasen1.equals(jasen3) === false;
     *   jasen3.equals(jasen2) === false;
     * </pre>
     */
    public boolean equals(Asunto asunto) {
        if ( asunto == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(asunto.anna(k)) ) return false;
        return true;
        
    }


    @Override
    public boolean equals(Object asunto) {
        if ( asunto instanceof Asunto ) return equals((Asunto)asunto);
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return id;
    }
}
