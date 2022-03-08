/**
 * 
 */
package landlordApp;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Vastuualueet:
 * -Huolehtii Talot, Asukkaat ja Asunnot välisestä
 *  yhteistyöstä ja välittää tietoja näiden välillä pyydettäessä.
 * -Lukee ja kirjoittaa landlordin tiedostoon
 *  pyytämällä apua avustajilta
 *  Avustajat:
 *  Talo
 *  Talot
 *  Asukas
 *  Asukkaat
 *  Asunto
 *  Asunnot
 * @author Nico & Eelis
 * @version 12.3.2020
 */
public class Landlord {

	 private Talot talot = new Talot();
	 private Asukkaat asukkaat = new Asukkaat();
	 private Asunnot asunnot = new Asunnot();
	
	/**
	 * Palauttaa talojen lukumäärän
	 * @return talojen lukumäärä
	 */
	public int getTaloja() {
		return talot.getLkm();
	}
	
	
	/**
     * Palauttaa asuntojen lukumäärän
     * @return asuntojen lukumäärä
     */
	public int getAsuntoja() {
	   return asunnot.getLkm();
	}
	
	/**
	 * Palauttaa asuntojen lukumäärän
	 * @return asuntojen lukumäärä
	 */
	public int getAsukkaita() {
		return asukkaat.getLkm();
	}
	
	/**
	 * Lisää talon tietokantaan.
	 * @param talo talo, joka halutaan lisätä tietokantaan.
	 * @example
	 * <pre name="test">
	 * Landlord landlord = new Landlord();
	 * Talo talo1 = new Talo(); Talo talo2 = new Talo();
	 * landlord.getTaloja() === 0;
	 * landlord.lisaa(talo1); landlord.getTaloja() === 1;
	 * landlord.lisaa(talo2); landlord.getTaloja() === 2;
	 * landlord.lisaa(talo2); landlord.getTaloja() === 3;
	 * landlord.annaTalo(0) === talo1;
	 * landlord.annaTalo(1) === talo2;
	 * landlord.annaTalo(2) === talo2;
	 * landlord.annaTalo(1) == talo1 === false;
	 * landlord.annaTalo(1) == talo2 === true;
	 * landlord.annaTalo(3) === talo1; #THROWS IndexOutOfBoundsException
	 * landlord.lisaa(talo1); landlord.getTaloja() === 4;
	 * landlord.lisaa(talo1); landlord.getTaloja() === 5;
	 * landlord.lisaa(talo1);
	 * </pre>
	 */
	public void lisaa(Talo talo){
		talot.lisaa(talo);
	}
	
	/**
	 * Palauttaa talot
	 * @return talot
	 */
	public Talot getTalot() {
	    return this.talot;
	}
	
	/**
     * Lisää Asunnon tietokantaan.
     * @param asunto asunto, joka halutaan lisätä tietokantaan.
     * @example
     * <pre name="test">
     * Landlord landlord = new Landlord();
     * Asunto asunto1 = new Asunto(1); Asunto asunto2 = new Asunto(1);
     * landlord.getAsuntoja() === 0;
     * landlord.lisaa(asunto1); landlord.getAsuntoja() === 1;
     * landlord.lisaa(asunto2); landlord.getAsuntoja() === 2;
     * landlord.lisaa(asunto2); landlord.getAsuntoja() === 3;
     * landlord.annaAsunto(0) === asunto1;
     * landlord.annaAsunto(1) === asunto2;
     * landlord.annaAsunto(2) === asunto2;
     * landlord.annaAsunto(1) == asunto1 === false;
     * landlord.annaAsunto(1) == asunto2 === true;
     * landlord.annaAsunto(3) === asunto1; #THROWS IndexOutOfBoundsException
     * landlord.lisaa(asunto1); landlord.getAsuntoja() === 4;
     * landlord.lisaa(asunto1); landlord.getAsuntoja() === 5;
     * landlord.lisaa(asunto1);
     * </pre>
     */
	public void lisaa(Asunto asunto){
        asunnot.lisaa(asunto);
    }
	
	/**
	 * Lisää asukkaan tietorakenteeseen.
	 * @param asukas lisättävä asukas
	 * @example
	 * <pre name="test">
     * Landlord landlord = new Landlord();
     * Asukas asukas1 = new Asukas(1); Asukas asukas2 = new Asukas(1);
     * landlord.getAsukkaita() === 0;
     * landlord.lisaa(asukas1); landlord.getAsukkaita() === 1;
     * landlord.lisaa(asukas2); landlord.getAsukkaita() === 2;
     * landlord.lisaa(asukas2); landlord.getAsukkaita() === 3;
     * landlord.annaAsukas(0) === asukas1;
     * landlord.annaAsukas(1) === asukas2;
     * landlord.annaAsukas(2) === asukas2;
     * landlord.annaAsukas(1) == asukas1 === false;
     * landlord.annaAsukas(1) == asukas2 === true;
     * landlord.annaAsukas(3) === asukas1; #THROWS IndexOutOfBoundsException
     * landlord.lisaa(asukas1); landlord.getAsukkaita() === 4;
     * landlord.lisaa(asukas1); landlord.getAsukkaita() === 5;
     * landlord.lisaa(asukas1);
	 * </pre>
	 */
	public void lisaa(Asukas asukas){
	    asukkaat.lisaa(asukas);
	}
	
	/**
	 * Palauttaa listan asukkaista, jotka kuuluvat tiettyyn asuntoon.
	 * @param asunto asunto, jonka asukkaat halutaan
	 * @return lista löydetyistä asukkaista
	 */
	public List<Asukas> annaAsukkaat(Asunto asunto){
	    return asukkaat.annaAsukkaat(asunto.getId());
	}
	
	
	/**
     * Palauttaa listan asukkaista, jotka kuuluvat tiettyyn asuntoon.
	 * @param talo mistä asukkaat etsitään
     * @return lista löydetyistä asunnoista
     */
    public List<Asunto> annaAsunnot(Talo talo){
        return asunnot.annaAsunnot(talo.getId());
    }
	/**
	 * Palauttaa paikassa i olevan Talon viitteen
	 * @param i paikka
	 * @return talo paikassa i
	 * @throws IndexOutOfBoundsException jos i on väärin.
	 */
	public Talo annaTalo(int i) throws IndexOutOfBoundsException {
		return talot.anna(i);
	}
	
	
	/**
     * Palauttaa paikassa i olevan Asunnon viitteen
     * @param i paikka
     * @return Asunto paikassa i
     * @throws IndexOutOfBoundsException jos i on väärin.
     */
	public Asunto annaAsunto(int i) throws IndexOutOfBoundsException {
        return asunnot.anna(i);
    }
	
	/**
	 * Palauttaa paikassa i olevan asukkaan viitteen
	 * @param i paikka
	 * @return asukas paikassa i
	 * @throws IndexOutOfBoundsException jos i on väärin.
	 */
	public Asukas annaAsukas(int i) throws IndexOutOfBoundsException {
		return asukkaat.anna(i);
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Landlord landlord = new Landlord();
		
		Talo talo1 = new Talo();
		Talo talo2 = new Talo();
		
		talo1.rekisteroi();
		talo2.rekisteroi();
		talo1.taytaOletus();
		talo2.taytaOletus();
		
		Asunto asunto1 = new Asunto(talo1.getId());
		asunto1.rekisteroi();
		asunto1.taytaOletus();
		
		Asukas asukas1 = new Asukas(asunto1.getId());
		asukas1.rekisteroi();
		asukas1.taytaOletus();
		
		landlord.lisaa(talo1);
		landlord.lisaa(talo2);
		landlord.lisaa(asunto1);


        landlord.lisaa(asukas1);

		System.out.println("=============Landlordin testi=============");
		
		for (int i = 0; i < landlord.getTaloja(); i++) {
			Talo talo = landlord.annaTalo(i);
			System.out.println("Talo paikassa: " + i);
			talo.tulosta(System.out);
		}
		
		for(int i = 0; i < landlord.getAsuntoja(); i++) {
			Asunto asunto = landlord.annaAsunto(i);
			System.out.println("Asunto paikassa: " + i);
			asunto.tulosta(System.out);
		}
		
		for(int i = 0; i < landlord.getAsukkaita(); i++) {
			Asukas asukas = landlord.annaAsukas(i);
			System.out.println("Asukas paikassa: " + i);
			asukas.tulosta(System.out);
		}

	}


    /**
     * @throws SailoException  tulostaa virheen jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException{
        String virhe = "";
        try {
            talot.tallenna();
        } catch(SailoException e) {
            virhe = e.getMessage();
        }
        try {
            asukkaat.tallenna();
        } catch(SailoException e) {
            virhe += e.getMessage();
        }
        try {
            asunnot.tallenna();
        } catch(SailoException e) {
            virhe += e.getMessage();
        }
        
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    
    /**
     * Poistaa talon tietorakenteesta ja lisäksi talon asunnot ja niiden talojen asukkaat.
     * @param talo poistettava talo
     * @return poistettujen määrä
     */
    public int poista(Talo talo) {
        if (talo == null) return 0;
        int ret = talot.poista(talo.getId());
        asunnot.poistaTalonAsunnot(talo.getId());
        Collection<Asunto> poistettavat = asunnot.annaAsunnot(talo.getId());
        for (Asunto as : poistettavat) {
            asukkaat.poistaAsunnonAsukkaat(as.getId());
       }
        asunnot.poistaTalonAsunnot(talo.getId());
        return ret;
    }
    
    /**
     * Poistaa asunnon tietorakenteesta ja lisäksi asunnon asukkaat.
     * @param asunto poistettava
     * @return poistettujen määrä
     */
    public int poistaAsunto(Asunto asunto) {
        if (asunto == null) return 0;
        asunnot.poista(asunto);
        asukkaat.poistaAsunnonAsukkaat(asunto.getId());
        return 0;
        
    }
    
    /**
     * Poistaa asukkaan tietorakenteesta
     * @param asukas poistettava
     * @return poistettujen määrä
     */
    public int poistaAsukas(Asukas asukas) {
        if (asukas == null) return 0;
        int ret = asukkaat.poista(asukas.getId());
        return ret;
    }
    
    /**
     * Etsii asukkaiden nimien mukaan talot
     * @param hakuehto hakuehto
     * @param k mitä kenttää etsitään
     * @return löydetyt
     * @example
     * <pre name="test">
     * Landlord landlord = new Landlord();
     * Talo eka = new Talo();
     * eka.rekisteroi();
     * landlord.korvaaTaiLisaa(eka);
     * eka.aseta(1, "Kurvikallio");
     * Collection <Talo> loydetyt = landlord.etsi("Kurvikallio", 1);
     * loydetyt.size() === 1;
     * </pre>
     */
    public Collection<Talo> etsi(String hakuehto, int k) {
        Collection<Talo> loydetytTalot = talot.etsi(hakuehto, k);
        return loydetytTalot;
    }
    
    /**
     * Korvaa tai lisää asukkaan tietorakenteeseen, jos ei löydy.
     * @param asukas lisättävä asukas
     */
    public void korvaaTaiLisaa(Asukas asukas) {
        asukkaat.korvaaTaiLisaa(asukas);
    }
    
    /**
     * Korvaa tai lisää asunnon tietorakenteeseen, jos ei löydy.
     * @param asunto lisättävä asunto
     */
    public void korvaaTaiLisaa(Asunto asunto) {
        asunnot.korvaaTaiLisaa(asunto);
    }
    
    /**
     * Korvaa tai lisää talon tietorakenteeseen, jos ei jo löydy.
     * @param talo lisättävä talo
     */
    public void korvaaTaiLisaa(Talo talo) {
        talot.korvaaTaiLisaa(talo);
    }


    /**
     * lukee tiedostosta oliot.
     * @param nimi tiedoston nimi
     * @throws SailoException  tulostaa virheen jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Landlord landlord = new Landlord();
     *  
     *  Talo talo1 = new Talo(); talo1.taytaOletus(); talo1.rekisteroi();
     *  Asunto aku1 = new Asunto(talo1.getId()); aku1.taytaOletus(); aku1.rekisteroi();
     *  Asunto aku2 = new Asunto(talo1.getId()); aku2.taytaOletus(); aku2.rekisteroi();
     *  Asukas pitsi21 = new Asukas(aku2.getId()); pitsi21.taytaOletus();
     *  Asukas pitsi11 = new Asukas(aku1.getId()); pitsi11.taytaOletus();
     *  Asukas pitsi22 = new Asukas(aku2.getId()); pitsi22.taytaOletus(); 
     *  Asukas pitsi12 = new Asukas(aku1.getId()); pitsi12.taytaOletus(); 
     *  Asukas pitsi23 = new Asukas(aku2.getId()); pitsi23.taytaOletus();
     *   
     *  String hakemisto = "testikelmit";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/asunnot.dat");
     *  File fhtied = new File(hakemisto+"/asukkaat.dat");
     *  File fttied = new File(hakemisto+"/talot.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  fttied.delete();
     *  landlord.lueTiedostosta(hakemisto); #THROWS SailoException
     *  landlord.lisaa(talo1);
     *  landlord.lisaa(aku1);
     *  landlord.lisaa(aku2);
     *  landlord.lisaa(pitsi21);
     *  landlord.lisaa(pitsi11);
     *  landlord.lisaa(pitsi22);
     *  landlord.lisaa(pitsi12);
     *  landlord.lisaa(pitsi23);
     *  landlord.tallenna();
     *  landlord = new Landlord();
     *  landlord.lueTiedostosta(hakemisto);
     *  landlord.lisaa(talo1);
     *  landlord.lisaa(aku2);
     *  landlord.lisaa(pitsi23);
     *  landlord.tallenna();
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     *  fttied.delete() === true;
     *  File fbak = new File(hakemisto+"/asukkaat.bak");
     *  File fhbak = new File(hakemisto+"/asunnot.bak");
     *  File ftbak = new File(hakemisto+"/talot.bak");
     *  fbak.delete() === true;
     *  fhbak.delete() === true;
     *  ftbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException{
        talot = new Talot();
        asukkaat = new Asukkaat();
        asunnot = new Asunnot();
        
        setTiedosto(nimi);
        talot.lueTiedostosta();
        asukkaat.lueTiedostosta();
        asunnot.lueTiedostosta();
        
    }
    
    /**
     * Asettaa luettavan tiedoston
     * @param nimi tiedoston nimi
     */
    public void setTiedosto(String nimi) {
    	File dir = new File(nimi);
    	dir.mkdirs();
    	String hakemistonNimi = "";
    	if( !nimi.isEmpty()) hakemistonNimi = nimi +"/";
    	asukkaat.setTiedostonPerusNimi(hakemistonNimi + "asukkaat");
    	asunnot.setTiedostonPerusNimi(hakemistonNimi + "asunnot");
    	talot.setTiedostonPerusNimi(hakemistonNimi + "talot");
    }

}
