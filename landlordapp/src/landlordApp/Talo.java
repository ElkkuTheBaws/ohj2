package landlordApp;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.SisaltaaTarkistaja;
import kanta.Tietue;

/**
 * Vastuualueet:
 * -Tietää talolle vaaditut kentät
 * -Osaa tarkastaa tietyn kentän oikeellisuuden
 *  eli syntakstin.
 * -Osaa muuttaa talo tiedot tiedostosta talon
 *  tiedoiksi
 * -Osaa antaa merkkijonona i:n kentän tiedot
 * -Osaa laittaa merkkijonon i:neksi kentäksi
 * @author Nico & Eelis
 * @version 12.3.2020
 *
 */
public class Talo implements Cloneable, Tietue{
    
    /** Vertailee taloja */
    public static class Vertailija implements Comparator<Talo> { 
        private int k;  
         
        @SuppressWarnings("javadoc") 
        public Vertailija(int k) { 
            this.k = k; 
        } 
         
        @Override 
        public int compare(Talo asukas1, Talo asukas2) { 
            return asukas1.getAvain(k).compareToIgnoreCase(asukas2.getAvain(k)); 
        } 
    } 
	
	//Attribuutit
	private int id;
	private String osoite = "";
	private String paikkakunta = "";
	private int postinro;
	private int rakennusvuosi;
	
	private static Random rand;
	private static int seuraavaNro = 1;
	
	
	/**
	 * Rekisteroi uuden talon ja kasvattaa talojen lukumäärää yhdellä.
	 * @return id
	 * @example
	 * <pre name="test">
	 * Talo talo = new Talo();
	 * talo.getId() === 0;
	 * talo.rekisteroi();
	 * Talo talo2 = new Talo();
	 * talo2.rekisteroi();
	 * int n1 = talo.getId();
	 * int n2 = talo2.getId();
	 * n1 === n2-1;
	 * </pre>
	 */
	public int rekisteroi() {
		this.id = seuraavaNro;
		Talo.seuraavaNro++;
		return id;
		
	}
	
	
	/**
	 * Palauttaa talon id:n
	 * @return talon id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Palauttaa avaimen
	 * @param k kenttä
	 * @return avain
	 */
	public String getAvain(int k) {
	        switch(k) {
	        case 0: return "" + id;
	        case 1: return "" + osoite.toUpperCase();
	        case 2: return "" + paikkakunta.toUpperCase();
	        case 3: return "" + postinro;
	        case 4: return "" + rakennusvuosi;
	        default: return "???";
	        }
	    }
	
	
	/**
	 * Täyttää talon tiedot oletustiedoilla testaamista varten.
	 */
	public void taytaOletus() {
		rand = new Random();
		
		osoite = "Taitoniekantie 9b";
		paikkakunta = "Jyväskylä";
		postinro = 40740 + rand.nextInt(100);
		rakennusvuosi = 1975;
	}
	
	
	  @Override
	    public String toString() {
	        return "" + getId() +"|" +
	                osoite + "|" +
	                paikkakunta + "|" +
	                postinro + "|" +
	                rakennusvuosi;
	                
	    }
	    
	  /**
	     * Ottaa syötetystä merkkijonosta tiedot ja asettaa ne talolle
	     * @param rivi syötetty merkkijono ( yleensä tiedostosta)
	     * @example
	     * <pre name="test">
	     * Talo ta = new Talo();
	     * ta.parse("2|Taitoniekantie 9b|Jyväskylä|40773|1975");
	     * ta.getId() === 2;
	     * ta.toString().equals("2|Taitoniekantie 9b|Jyväskylä|40773|1975"); 
	     * </pre>
	     */
	    public void parse(String rivi) {
	        StringBuffer sb = new StringBuffer(rivi);
	        id = Mjonot.erota(sb, '|', getId());
	        osoite = Mjonot.erota(sb, '|', osoite);
	        paikkakunta = Mjonot.erota(sb, '|', paikkakunta);
	        postinro = Mjonot.erota(sb, '|', postinro);
	        rakennusvuosi = Mjonot.erota(sb, '|', rakennusvuosi);
	    }
	/**
	 * Tulostaa talon tiedot.
	 * @param out tietovirta johon tulostetaan.
	 */
	public void tulosta(PrintStream out) {
		out.println(id);
		out.println(osoite + " " + postinro + " " + paikkakunta);
		out.println(rakennusvuosi + "\n");
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
		Talo koti = new Talo();
		Talo koti2 = new Talo();
		
		koti.rekisteroi();
		koti2.rekisteroi();
		
		koti.taytaOletus();
		koti2.taytaOletus();
		
		koti.tulosta(System.out);
		koti2.tulosta(System.out);
		
	}

/**
 * Palauttaa osoitteen
 * @return osoite
 */
	public String getOsoite() {
		
		return osoite;
	}


/**
 * Palauttaa kenttien lukumäärän.
 */
@Override
public int getKenttia() {
    return 5;
}


/**
 * Palauttaa ensimmäisen kentän tunnuksen, jota halutaan pystyä muokkaamaan
 */
@Override
public int ekaKentta() {
    return 1;
}


/**
 * Palauttaa "täyttöohje" kysymyksen kentästä
 * @param k kenttä
 */
@Override
public String getKysymys(int k) {
    switch(k) {
    case 0: return "id";
    case 1: return "Osoite";
    case 2: return "Paikkakunta";
    case 3: return "Postinro";
    case 4: return "Rakennusvuosi";
    default: return "???";
    }
}

/**
 * Palauttaa stringinä kentän sisällön
 * @param k kenttä
 */
@Override
public String anna(int k) {
    switch(k) {
    case 0: return "" + id;
    case 1: return "" + osoite;
    case 2: return "" + paikkakunta;
    case 3: return "" + postinro;
    case 4: return "" + rakennusvuosi;
    default: return "???";
    }
}


/**
 * Asettaa tiedot talolle ja ilmoittaa virheistä, jos ei onnistu.
 * @param k kenttä
 * @param s string, jota yritetään asettaa.
 * @example
 * <pre name="test">
 * Talo uusi = new Talo();
 * uusi.aseta(1,"Taitoniekantie 9") === null;
 * uusi.aseta(4, "abcd") == null === false;
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
        osoite = tjono;
        return null;
    case 2:
        paikkakunta = tjono;
        return null;
    case 3:
        tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NUMEROT);
        virhe = tarkistaja.tarkista(tjono);
        if ( virhe != null ) return virhe;
        postinro =  Mjonot.erota(sb, '§', postinro);
        return null;
    case 4:
        tarkistaja = new SisaltaaTarkistaja(SisaltaaTarkistaja.NUMEROT);
        virhe = tarkistaja.tarkista(tjono);
        if ( virhe != null ) return virhe;
        rakennusvuosi = Mjonot.erota(sb, '§', rakennusvuosi);
        return null;
    default:
        return "???";
    
    }
}

/**
 * Asettaa id:n
 * @param id id
 */
private void setId(int id) {
    this.id = id;
    
}

/**
 * Kloonaa tietueen
 */
@Override
public Tietue clone() throws CloneNotSupportedException {
    Talo uusi;
    uusi = (Talo) super.clone();
    return uusi;
}
	
	
}
