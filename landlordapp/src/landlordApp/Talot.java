package landlordApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Vastuualueet:
 * -Pitää yllä talojen rekisteriä, osaa lisätä ja poistaa talon
 * -Lukee ja kirjoittaa talot tiedostoon
 * -Osaa etsiä ja lajitella
 * Avustajat: 
 * Talo
 * @author Nico & Eelis
 * @version 12.3.2020
 */
public class Talot implements Iterable<Talo>{
	
	//Attribuutit
	private int lkm;
	private static final int MAX_LKM = 5;
	private Talo[] talot = new Talo[MAX_LKM];
	private String tiedostonPerusNimi = "talot";
	private boolean muutettu = false;
	
	
	/**
	 * Palauttaa talojen lukumäärän
	 * @return talojen lukumäärä
	 */
	public int getLkm() {
		return lkm;
	}
	

	/**
	 * Lisää tietorakenteeseen uuden talon ja päivittää talojen lukumäärän.
	 * Ottaa talon omistukseensa.
	 * @param talo Lisättävän talon viite
	 * @example
	 * <pre name="test">
	 * Talot talot = new Talot();
	 * Talo talo1 = new Talo(); Talo talo2 = new Talo();
	 * talot.getLkm() === 0;
	 * talot.lisaa(talo1); talot.getLkm() === 1;
	 * talot.lisaa(talo2); talot.getLkm() === 2;
	 * talot.lisaa(talo2); talot.getLkm() === 3;
	 * talot.anna(0) === talo1;
	 * talot.anna(1) === talo2;
	 * talot.anna(2) === talo2;
	 * talot.anna(1) == talo1 === false;
	 * talot.anna(1) == talo2 === true;
	 * talot.anna(3) === talo1; #THROWS IndexOutOfBoundsException
	 * talot.lisaa(talo1); talot.getLkm() === 4;
	 * talot.lisaa(talo1); talot.getLkm() === 5;
	 * </pre>
	 */
	public void lisaa(Talo talo) {
		if (lkm >= talot.length) {
			Talo[] uudetTalot = new Talo[talot.length+5];
			for (int i = 0; i < talot.length; i++) {
				uudetTalot[i] = talot[i];
			}
			talot = uudetTalot;
		}
		talot[lkm] = talo;
		lkm++;
		muutettu = true;
	}
	
	
	/**
	 * Korvaa tietorakenteeseen talon tai lisää jos sitä ei jo löydy
	 * @param talo lisättävä talo
	 */
	public void korvaaTaiLisaa(Talo talo) {
	    int id = talo.getId();
	    for (int i = 0; i < getLkm(); i++) {
	        if (anna(i).getId() == id) {
	           talot[i] = talo;
	           muutettu = true;
	           return;
	        }
	    }
	     lisaa(talo);
	 }
	
	/** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien asukkaiden viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä asukkaista 
     * @example 
     * <pre name="test"> 
     *   Talot taloet = new Talot(); 
     *   Talo talo1 = new Talo(); talo1.parse("2|2|Eelis|Koivusaari|0405219330|eelis.koivusaari@gmail.com|19"); 
     *   Talo talo2 = new Talo(); talo2.parse("3|2|Eero|Koivusaari|0405219330|Eero.koivusaari@gmail.com|15"); 
     *   Talo talo3 = new Talo(); talo3.parse("4|4|Nico|Kääriäinen|0405219330|the.gamer@gmail.com|19"); 
     *   Talo talo4 = new Talo(); talo4.parse("5|1|Naku|Ankka|0405219330|a.ku@gmail.com|28"); 
     *   Talo talo5 = new Talo(); talo5.parse("3|1|Beef|Turkay|0405219330|beed.turkay@gmail.com|33"); 
     *   taloet.lisaa(talo1); taloet.lisaa(talo2); taloet.lisaa(talo3); taloet.lisaa(talo4); taloet.lisaa(talo5);
     *   List<Talo> loytyneet;  
     *   loytyneet = (List<Talo>)taloet.etsi("*n*",2);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == talo4 === true;  
     *   loytyneet.get(1) == talo3 === true;  
     *     
     *   loytyneet = (List<Talo>)taloet.etsi("*e*",2);  
     *   loytyneet.size() === 3;  
     *   loytyneet.get(0) == talo5 === true;  
     *   loytyneet.get(1) == talo1 === true; 
     *     
     *   loytyneet = (List<Talo>)taloet.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre> 
     */ 
     public Collection<Talo> etsi(String hakuehto, int k){
         String ehto ="*";
         if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
         int hk = k;
         if(hk < 0) hk = 0;
         List<Talo> loytyneet = new ArrayList<Talo>();
         for(Talo talo : this) {
             if(WildChars.onkoSamat(talo.anna(hk), ehto)) loytyneet.add(talo);
         }
         Collections.sort(loytyneet, new Talo.Vertailija(hk));
         return loytyneet;
     }
	
	/**
	 * Palauttaa talot taulukon paikassa i olevan Talon viitteen
	 * @param i paikka
	 * @return Halutun talon viite
	 * @throws IndexOutOfBoundsException Laiton indeksi
	 */
	public Talo anna(int i) throws IndexOutOfBoundsException {
		if(i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return talot[i];
	}
    /**
     * @param id id jota etsitään
     * @return palauttaa kohdan missä kyseisen id omaava asukas on listassa
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) 
            if (id == talot[i].getId()) return i; 
        return -1; 

     }
    
      /** 
     * Etsii asukkaan id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return asukas jolla etsittävä id tai null 
     * <pre name="test"> 
     * Talot talot = new Talot(); 
     * Talo aku1 = new Talo(), aku2 = new Talo(), aku3 = new Talo(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getId(); 
     * talot.lisaa(aku1); talot.lisaa(aku2); talot.lisaa(aku3); 
     * talot.annaId(id1  ) == aku1 === true; 
     * talot.annaId(id1+1) == aku2 === true; 
     * talot.annaId(id1+2) == aku3 === true; 
     * </pre> 
     */ 

    public Talo annaId(int id) {
        for (Talo talo : this) { 
            if (id == talo.getId()) return talo; 
        } 
        return null; 

    }
    /** 
     * Poistaa jäsenen jolla on valittu tunnusnumero  
     * @param id poistettavan jäsenen tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Talot talot = new Talot(); 
     * Talo aku1 = new Talo(), aku2 = new Talo(), aku3 = new Talo(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getId(); 
     * talot.lisaa(aku1); talot.lisaa(aku2); talot.lisaa(aku3); 
     * talot.poista(id1+1) === 1; 
     * talot.annaId(id1+1) === null; talot.getLkm() === 2; 
     * talot.poista(id1) === 1; talot.getLkm() === 1; 
     * talot.poista(id1+3) === 0; talot.getLkm() === 1; 
     * </pre> 
     *  
     */ 

	public int poista(int id) {
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            talot[i] = talot[i + 1]; 
        talot[lkm] = null; 
        muutettu = true; 
        return 1;

    }

    
	/**
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Talot talot = new Talot();
     * Talo aku1 = new Talo(), aku2 = new Talo();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * talot.lisaa(aku1); 
     * talot.lisaa(aku2); 
     * talot.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Talo talo:talot)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+talo.getId());           
     * 
     * String tulos = " " + aku1.getId() + " " + aku2.getId() + " " + aku1.getId();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Talo>  i=talot.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Talo talo = i.next();
     *   ids.append(" "+talo.getId());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Talo>  i=talot.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class TalotIterator implements Iterator<Talo> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Talo next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Talo> iterator() {
        return new TalotIterator();
    }
	
    
    /**
	  * @return tiedoston nimi ilman tarkenninta
	  */
	 public String getTiedostonPerusNimi() {
		 return tiedostonPerusNimi;
	 }
	 
	 /**
	  * @return tiedoston nimi
	  */
	 public String getTiedostonNimi() {
		 return tiedostonPerusNimi + ".dat";
	 }
	 
	 /**
	  * Asettaa tiedostolle nimen
	  * @param tied tiedostonnimi
	  */
	 public void setTiedostonPerusNimi(String tied) {
		 this.tiedostonPerusNimi = tied;
	 }
	 
	 /**
	  * @return varmuuskopion nimi
	  */
	 public String getBakNimi() {
		 return tiedostonPerusNimi + ".bak";
	 }
   
   /**
	  * Lukee tiedostosta asukkaiden tiedot ja luo oliot niiden perusteella.
	  * @param tied luettavan tiedoston nimi
	  * @throws SailoException jos tulee ongelmia
	  * @example
    * <pre name="test">
    * #THROWS SailoException 
    * #import java.io.File;
    * #import java.util.Iterator;
    *  Talot talot = new Talot();
    *  Talo pitsi21 = new Talo(); pitsi21.taytaOletus();
    *  Talo pitsi11 = new Talo(); pitsi11.taytaOletus();
    *  Talo pitsi22 = new Talo(); pitsi22.taytaOletus(); 
    *  Talo pitsi12 = new Talo(); pitsi12.taytaOletus();
    *  Talo pitsi23 = new Talo(); pitsi23.taytaOletus(); 
    *  String tiedNimi = "testitalot";
    *  File ftied = new File(tiedNimi+".dat");
    *  ftied.delete();
    *  talot.lueTiedostosta(tiedNimi); #THROWS SailoException
    *  talot.lisaa(pitsi21);
    *  talot.lisaa(pitsi11);
    *  talot.lisaa(pitsi22);
    *  talot.lisaa(pitsi12);
    *  talot.lisaa(pitsi23);
    *  talot.tallenna();
    *  talot = new Talot();
    *  talot.lueTiedostosta(tiedNimi);
    *  Iterator<Talo> i = talot.iterator();
    *  i.next().toString() === pitsi21.toString();
    *  i.next().toString() === pitsi11.toString();
    *  i.next().toString() === pitsi22.toString();
    *  i.next().toString() === pitsi12.toString();
    *  i.next().toString() === pitsi23.toString();
    *  i.hasNext() === false;
    *  talot.lisaa(pitsi23);
    *  talot.tallenna();
    *  ftied.delete() === true;
    *  File fbak = new File(tiedNimi+".bak");
    *  fbak.delete() === true;
    * </pre>
    */
	 public void lueTiedostosta(String tied) throws SailoException {
		setTiedostonPerusNimi(tied);
		 String rivi;
		 try (Scanner fi = new Scanner(new FileInputStream(new File(getTiedostonNimi())))){
			 while ( fi.hasNext()) {
				 rivi = fi.nextLine().trim();
				 if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
				 Talo talo = new Talo();
				 talo.parse(rivi);
				 lisaa(talo);
			 }
			 muutettu = false;
		}catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
		}
	 }
	 
	 /**
	  * Lukee tiedoston oletusnimellä
	  * @throws SailoException jos tulee poikkeus
	  */
	 public void lueTiedostosta() throws SailoException{
		 lueTiedostosta(getTiedostonPerusNimi());
	 }
	 
	 /**
	  * Tallettaa tiedostoon tämänhetkiset oliot.
	  * @throws SailoException jos tulee ongelmia
	  */
	 public void tallenna() throws SailoException{
		 if (!muutettu) return;
		 
		 File fbak = new File(getBakNimi());
		 File ftied = new File(getTiedostonNimi());
		 fbak.delete();
		 ftied.renameTo(fbak);
		 
		 try(PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))){
			 for ( Talo asunto : this) {
				 fo.println(asunto.toString());
			 }
		 }catch (FileNotFoundException e) {
			 throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
			 
		 }catch (IOException e) {
			 throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
		 }
		 muutettu = false;
	 }
	 
	 /**
	     * Palauttaa listan taloista tietyllä id:llä
	     * @param id id
	     * @return lista jossa löydetyt talot
	     */
	    public List<Talo> annaTalot(int id) {
	        List<Talo> loydetyt = new ArrayList<Talo>();
	        for (Talo ta : talot)
	            if(ta.getId() == id) loydetyt.add(ta);
	        
	        return loydetyt;
	    }
    
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Talot talot = new Talot();
		
		Talo testi1 = new Talo();
		testi1.taytaOletus();
		testi1.rekisteroi();
		Talo testi2 = new Talo();
		testi2.taytaOletus();
		testi2.rekisteroi();
		
		talot.lisaa(testi1);
		talot.lisaa(testi2);
		
		try {
			talot.tallenna();
		}catch (SailoException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			talot.lueTiedostosta();
		}catch(SailoException e) {
			System.err.println(e.getMessage());
		}
		
		
		System.out.println("=============== Talot testi ===============");
		
		for (Talo as : talot) {
			

			as.tulosta(System.out);
		}
	}
}
