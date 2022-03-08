package landlordApp;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.SisaltaaTarkistaja;
import kanta.Tietue;

/**
 * -Tietää asukkaan kentät                        
 * -Osaa tarkistaa kenttien oikeellisuuden(Syntax)
 * -Osaa muuttaa asukkaan merkkijonon asukkaan    
 * tiedoiksi                                     
 * -Osaa antaa merkkijonona i:n kentän tiedot     
 * -Osaa laittaa merkkijonon i:neksi kentäksi                                                     
 * @author Nico & Eelis
 * @version 12.3.2020
 */
public class Asukas implements Cloneable, Tietue{
	private int id;
	private int asuntoId;
	
	private String etunimet = "";
	private String sukunimi = "";
	private String puhNro = "";
	private String sPosti = "";
	private int ika;
	
	private static Random rand;
	private static int seuraavaNro = 1;
	
	/**
	 * vertailee asukkaita
	 */
	public static class Vertailija implements Comparator<Asukas> { 
        private int k;  
         
        @SuppressWarnings("javadoc") 
        public Vertailija(int k) { 
            this.k = k; 
        } 
         
        @Override 
        public int compare(Asukas asukas1, Asukas asukas2) { 
            return asukas1.getAvain(k).compareToIgnoreCase(asukas2.getAvain(k)); 
        } 
    } 

	/**
	 * Rekisteroi uuden talon ja kasvattaa talojen lukumäärää yhdellä.
	 * @return id
	 * @example
	 * <pre name="test">
	 * Asukas asukas = new Asukas(1);
	 * asukas.getId() === 0;
	 * asukas.rekisteroi();
	 * Asukas asukas2 = new Asukas();
	 * asukas2.rekisteroi();
	 * int n1 = asukas.getId();
	 * int n2 = asukas2.getId();
	 * n1 === n2-1;
	 * </pre>
	 */
	public int rekisteroi() {
		this.id = seuraavaNro;
		Asukas.seuraavaNro++;
		return id;
	}
	
	
	/**
	 * Alustaa uuden asukkaan tiettyyn asuntoon.
	 * @param asuntoId asunnon id
	 */
	public Asukas(int asuntoId) {
		this.asuntoId = asuntoId;
	}
	
	/**
	 * Alustaa uuden tyhjän asukkaan.
	 */
	public Asukas() {
		
	}
	
	
	/**
	 * @return asukkaan id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
     * asukkaan id setteri
	 * @param value the setted value
     */
    public void setId(int value) {
        this.id = value;
    }
	/**
	 * @return asukkaan asuntoId
	 */
	public int getAsuntoId() {
		return this.asuntoId;
	}
	
	/**
     * asukkaan id setteri
     * @param value the setted value
     */
    public void setAsuntoId(int value) {
        this.asuntoId = value;
    }
	/**
	 * @return asukkaan etunimi
	 */
	public String getAsukaanEtunimi() {
	    return this.etunimet;
	}
	
	/**
	 * Täyttää asukkaan tiedot oletustiedoilla testaamista varten.
	 */
	public void taytaOletus() {
		rand = new Random();
		int randomNro = rand.nextInt(9999999);
		ArrayList<String> randEtunimet = new ArrayList<String>();
		randEtunimet.add("Kallu");
		randEtunimet.add("Matti");
		randEtunimet.add("Teppo");
		randEtunimet.add("Seppo");
		randEtunimet.add("Eetu");
		randEtunimet.add("king_elkku007");
		
		ArrayList<String> randSukunimet = new ArrayList<String>();
		randSukunimet.add("Meikäläinen");
		randSukunimet.add("Kallunen");
		randSukunimet.add("Kääriäinen");
		randSukunimet.add("Mikkonen");
		randSukunimet.add("Ukkonen");
		randSukunimet.add("THESLAYER");
		
		etunimet = randEtunimet.get(rand.nextInt(randEtunimet.size()));
		sukunimi = randSukunimet.get(rand.nextInt(randSukunimet.size()));
		puhNro =  "045 " + String.format("%07d", randomNro);
		sPosti = (etunimet.toLowerCase() + "." + sukunimi.toLowerCase()  + "@gmail.com").replace('ä', 'a').replace('ö', 'o');
		ika = 18 + rand.nextInt(40);
		
	}
	
	
	/**
	 * Metodi tekee asukkaan tiedoista merkkijonon, joka on helposti tallennettavissa esim. tiedostoon
	 */
	@Override
    public String toString() {
	    StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
         
	}
	
	
	/**
	 * Ottaa syötetystä merkkijonosta tiedot ja asettaa ne asukkaalle
	 * @param rivi syötetty merkkijono ( yleensä tiedostosta)
	 * @example
	 * <pre name="test">
	 * Asukas kallu = new Asukas();
	 * kallu.parse("1 | 2 | Kallu | Kallunen | 0400841251 | kallu.kallunen@gmail.com | 20");
	 * kallu.getId() === 1;
	 * kallu.toString().equals("1|2|Kallu|Kallunen|0400841251|kallu.kallunen@gmail.com|20"); 
	 * </pre>
	 */
	public void parse(String rivi) {
	    StringBuffer sb = new StringBuffer(rivi);
	    for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));

	}
	
	/**
	 * Tulostaa asukkaan tiedot tietovirtaan
	 * @param os tietovirta, johon tulostetaan.
	 */
	public void tulosta(PrintStream os) {
		os.println("Id : " + this.id);
		os.println("AsuntoId: " + this.asuntoId);
		os.println(etunimet + " " + sukunimi);
		os.println(puhNro);
		os.println(sPosti);
		os.println("Ikä: " + ika + "v" + "\n");
	}
	
	
	/**
	 * Tulostaa asukkaan tiedot tietovirtaan, tyypiltään outputstream
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		
		Asukas kallu = new Asukas(1);
		kallu.rekisteroi();
		kallu.taytaOletus();
		kallu.tulosta(System.out);
		kallu.parse("2|2|Eelis|Koivusaari|0405219330|eelis.koivusaari@gmail.com|19");
		kallu.tulosta(System.out);
	}
	
	 /** 
     * Antaa k:n kentän sisällön merkkijonona, käytetään vertailussa
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */ 

	public String getAvain(int k) {
	    switch(k) {
        
            case 0: return "" + id;
            case 1: return "" + asuntoId;
            case 2: return "" + etunimet.toUpperCase();
            case 3: return "" + sukunimi.toUpperCase();
            case 4: return "" + puhNro;
            case 5: return "" + sPosti;
            case 6: return "" + ika;
            default: return "Ähäkutti";
        }
	}
	/**
	 * Palauttaa asukkaan kenttien lukumäärn
	 */
    @Override
    public int getKenttia() {
        return 7;
    }

    /**
     * Eka kenttä jota on mielekäs käyttää
     */
    @Override
    public int ekaKentta() {
        return 2;
    }

    /**
     * Palauttaa k:tta asukaan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */

    @Override
    public String getKysymys(int k) {
        switch(k) {
        
        case 0: return "asukas id";
        case 1: return "asunto id";
        case 2: return "etunimet";
        case 3: return "sukunimi";
        case 4: return "puhelinnumero";
        case 5: return "sähköposti";
        case 6: return "ikä";
        default: return "Ähäkutti";
        }
    }

    /** 
     * Antaa k:n kentän sisällön merkkijonona 
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */ 
    @Override
    public String anna(int k) {
        switch(k) {
        
        case 0: return "" + id;
        case 1: return "" + asuntoId;
        case 2: return "" + etunimet;
        case 3: return "" + sukunimi;
        case 4: return "" + puhNro;
        case 5: return "" + sPosti;
        case 6: return "" + ika;
        default: return "Ähäkutti";
        }
    }

    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param s jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Asukas asukas = new Asukas();
     *   asukas.aseta(2,"Ankka Aku") === null;
     *   asukas.aseta(5,"eelis.koivusaari@gmail.com") === null;
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
            setAsuntoId(Mjonot.erota(sb, '§', getAsuntoId()));
            return null;
        case 2:
            tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NIMIKIRJAIMET);
            virhe = tarkistaja.tarkista(tjono);
            if ( virhe != null ) return virhe;
            etunimet = tjono;
            return null;
        case 3:
            tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NIMIKIRJAIMET);
            virhe = tarkistaja.tarkista(tjono);
            if ( virhe != null ) return virhe;
            sukunimi = tjono;
            return null;
        case 4:
            tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NUMEROT + "+- ");
            virhe = tarkistaja.tarkista(tjono);
            if ( virhe != null ) return virhe;
            puhNro = tjono;
            return null;
        case 5:
            sPosti = tjono;
            return null;
        case 6:
            try {
                ika = Mjonot.erotaEx(sb, '§', 0);
            } catch (NumberFormatException e) {
                return "Ikä väärin: " + e.getMessage();
            }
            return null;
        default:
            return "Ähäkutti";
        
        }

    }

    /**
     * Tehdään identtinen klooni asukkaasta
     * @return Object kloonattu asukas
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Asukas asukas = new Asukas();
     *   asukas.parse("1 | 2 | Kallu | Kallunen | 0400841251 | kallu.kallunen@gmail.com | 20");
     *   Asukas kopio = asukas.clone();
     *   kopio.toString() === asukas.toString();
     *   asukas.parse("1 | 2 | Ballu | Dallunen | 0400841251 | Ballu.Dallunen@gmail.com | 250");
     *   kopio.toString().equals(asukas.toString()) === false;
     * </pre>
     */
    @Override
    public Asukas clone() throws CloneNotSupportedException {
        Asukas uusi;
        uusi = (Asukas) super.clone();
        return uusi;
    }
    
    /**
     * Tutkii onko asukkaan tiedot samat kuin parametrina tuodun asukkaan tiedot
     * @param asukas asukas johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Asukas jasen1 = new Asukas();
     *   jasen1.parse("1 | 2 | Kallu | Kallunen | 0400841251 | kallu.kallunen@gmail.com | 20");
     *   Asukas jasen2 = new Asukas();
     *   jasen2.parse("1 | 2 | Kallu | Kallunen | 0400841251 | kallu.kallunen@gmail.com | 20");
     *   Asukas jasen3 = new Asukas();
     *   jasen3.parse("1 | 2 | Ballu | Kallunen | 0400841251 | kallu.kallunen@gmail.com | 20");
     *   
     *   jasen1.equals(jasen2) === true;
     *   jasen2.equals(jasen1) === true;
     *   jasen1.equals(jasen3) === false;
     *   jasen3.equals(jasen2) === false;
     * </pre>
     */
    public boolean equals(Asukas asukas) {
        if ( asukas == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(asukas.anna(k)) ) return false;
        return true;
        
    }


    @Override
    public boolean equals(Object asukas) {
        if ( asukas instanceof Asukas ) return equals((Asukas)asukas);
        return false;
    }


    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return id;
    }

}
