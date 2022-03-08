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
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * -Pitää yllä asukkaiden rekisteriä, osaa lisätä ja
 * poistaa asukkaan                                
 * -Lukee ja kirjoittaa asukkaat tiedostoon         
 * -Osaa etsiä ja lajitella                         
 * @author Nico & Eelis
 * @version 12.3.2020
 *
 */
public class Asukkaat implements Iterable<Asukas>{
	 private boolean muutettu = false;
	 private String tiedostonPerusNimi = "asukkaat";
	 private final ArrayList<Asukas> asukkaat = new ArrayList<Asukas>();
	 
	 
	 /**
	  * @return asukkaiden lukumäärä
	  */
	 public int getLkm() {
		 return asukkaat.size();
	 }
	 
	 /**
	  * Lisää asukkaiden listaan uuden asukkaan.
	  * @param asukas lisättävä asukas
	  * @example
	  * <pre name="test">
	  * Asukkaat asukkaat = new Asukkaat();
	  * Asukas asukas1 = new Asukas(1); Asukas asukas2 = new Asukas(1);
	  * asukkaat.getLkm() === 0;
	  * asukkaat.lisaa(asukas1); asukkaat.getLkm() === 1;
	  * asukkaat.lisaa(asukas2); asukkaat.getLkm() === 2;
	  * asukkaat.lisaa(asukas2); asukkaat.getLkm() === 3;
	  * asukkaat.anna(0) === asukas1;
	  * asukkaat.anna(1) === asukas2;
	  * asukkaat.anna(2) === asukas2;
	  * asukkaat.anna(1) == asukas1 === false;
	  * asukkaat.anna(1) == asukas2 === true;
	  * asukkaat.anna(3) === asukas1; #THROWS IndexOutOfBoundsException
	  * asukkaat.lisaa(asukas1); asukkaat.getLkm() === 4;
	  * asukkaat.lisaa(asukas1); asukkaat.getLkm() === 5;
	  * asukkaat.lisaa(asukas1);
	  * </pre>
	  */
	 public void lisaa(Asukas asukas) {
		 this.asukkaat.add(asukas);
		 muutettu = true;
	 }
	 
	 /**
	  * Korvaa asukkaan tietorakenteessa. Ottaa asukkaan omistukseensa.
	  * Etsitään samalla id:llä oleva asukas, jos ei ole niin lisätään uutena.
	  * @param asukas lisättävä asukas
	  * <pre name="test">
	  * #THROWS SailoException,CloneNotSupportedException
	  * #PACKAGEIMPORT
	  * Asukkaat asukkaat = new Asukkaat();
	  * Asukas aku1 = new Asukas(), aku2 = new Asukas();
	  * aku1.rekisteroi(); aku2.rekisteroi();
	  * asukkaat.getLkm() === 0;
	  * asukkaat.korvaaTaiLisaa(aku1); asukkaat.getLkm() === 1;
	  * asukkaat.korvaaTaiLisaa(aku2); asukkaat.getLkm() === 2;
	  * Asukas aku3 = aku1.clone();
	  * aku3.aseta(3,"kkk");
	  * Iterator<Asukas> it = asukkaat.iterator();
	  * it.next() == aku1 === true;
	  * asukkaat.korvaaTaiLisaa(aku3); asukkaat.getLkm() === 2;
	  * it = asukkaat.iterator();
	  * Asukas j0 = it.next();
	  * j0 === aku3;
	  * j0 == aku3 === true;
	  * j0 == aku1 === false;
      * </pre>
	  */
	 public void korvaaTaiLisaa(Asukas asukas) {
	     int id = asukas.getId();
	     for (int i = 0; i < getLkm();i++) {
	         if (anna(i).getId() == id) {
	             asukkaat.set(i, asukas);
	             muutettu = true;
	             return;
	         }
	     }
	     lisaa(asukas);
	 }
	 
	 /**
	  * Palauttaa paikassa i olevan asukkaan viitteen
	  * @param i indeksi arraylistissä
	  * @return etsitty asukas
	  * @throws IndexOutOfBoundsException jos indeksi on virheellinen, eli liian suuri tai pieni.
	  */
	 public Asukas anna(int i) throws IndexOutOfBoundsException {
		 if(i < 0 || asukkaat.size() <= i)
	            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
	        return asukkaat.get(i);
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
	     * Iteraattori kaikkien asukkaiden läpikäymiseen
	     * @return asukasiteraattori
	     * 
	     * @example
	     * <pre name="test">
	     * #PACKAGEIMPORT
	     * #import java.util.*;
	     * 
	     *  Asukkaat asukkaat = new Asukkaat();
	     *  Asukas pitsi21 = new Asukas(2); asukkaat.lisaa(pitsi21);
	     *  Asukas pitsi11 = new Asukas(1); asukkaat.lisaa(pitsi11);
	     *  Asukas pitsi22 = new Asukas(2); asukkaat.lisaa(pitsi22);
	     *  Asukas pitsi12 = new Asukas(1); asukkaat.lisaa(pitsi12);
	     *  Asukas pitsi23 = new Asukas(2); asukkaat.lisaa(pitsi23);
	     * 
	     *  Iterator<Asukas> i2=asukkaat.iterator();
	     *  i2.next() === pitsi21;
	     *  i2.next() === pitsi11;
	     *  i2.next() === pitsi22;
	     *  i2.next() === pitsi12;
	     *  i2.next() === pitsi23;
	     *  i2.next() === pitsi12;  #THROWS NoSuchElementException  
	     *  
	     *  int n = 0;
	     *  int jnrot[] = {2,1,2,1,2};
	     *  
	     *  for ( Asukas as:asukkaat ) { 
	     *    as.getAsuntoId() === jnrot[n]; n++;  
	     *  }
	     *  
	     *  n === 5;
	     *  
	     * </pre>
	     */
	 @Override
	 public Iterator<Asukas> iterator(){
		 return asukkaat.iterator();
	 }
	 
	 /**
	  * Palauttaa listan asukkaista, joilla on täsmäävä asuntoId.
	  * @param id asunnon id
	  * @return lista löydetyistä asukkaista
	  * @example
	  * #import java.util.List;
	  * <pre name="test">
	  * Asukkaat asukkaat = new Asukkaat();
	  * Asukas asukas1 = new Asukas(1);
	  * Asukas asukas2 = new Asukas(2);
	  * Asukas asukas3 = new Asukas(1);
	  * asukkaat.lisaa(asukas1);
	  * asukkaat.lisaa(asukas2);
	  * asukkaat.lisaa(asukas3);
	  * List<Asukas> loydetyt1 = asukkaat.annaAsukkaat(1);
	  * loydetyt1.size() === 2;
	  * List<Asukas> loydetyt2 = asukkaat.annaAsukkaat(2);
	  * loydetyt2.size() === 1;
	  * loydetyt1.get(0) === asukas1;
	  * </pre>
	  */
	 public List<Asukas> annaAsukkaat(int id){
	        List<Asukas> loydetyt = new ArrayList<Asukas>();
	        for (Asukas as : asukkaat)
	            if(as.getAsuntoId() == id) loydetyt.add(as);
	        
	        return loydetyt;
	        
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
      *  Asukkaat asukkaat = new Asukkaat();
      *  Asukas pitsi21 = new Asukas(); pitsi21.taytaOletus();
      *  Asukas pitsi11 = new Asukas(); pitsi11.taytaOletus();
      *  Asukas pitsi22 = new Asukas(); pitsi22.taytaOletus(); 
      *  Asukas pitsi12 = new Asukas(); pitsi12.taytaOletus();
      *  Asukas pitsi23 = new Asukas(); pitsi23.taytaOletus(); 
      *  String tiedNimi = "testiasukkaat";
      *  File ftied = new File(tiedNimi+".dat");
      *  ftied.delete();
      *  asukkaat.lueTiedostosta(tiedNimi); #THROWS SailoException
      *  asukkaat.lisaa(pitsi21);
      *  asukkaat.lisaa(pitsi11);
      *  asukkaat.lisaa(pitsi22);
      *  asukkaat.lisaa(pitsi12);
      *  asukkaat.lisaa(pitsi23);
      *  asukkaat.tallenna();
      *  asukkaat = new Asukkaat();
      *  asukkaat.lueTiedostosta(tiedNimi);
      *  Iterator<Asukas> i = asukkaat.iterator();
      *  i.next().toString() === pitsi21.toString();
      *  i.next().toString() === pitsi11.toString();
      *  i.next().toString() === pitsi22.toString();
      *  i.next().toString() === pitsi12.toString();
      *  i.next().toString() === pitsi23.toString();
      *  i.hasNext() === false;
      *  asukkaat.lisaa(pitsi23);
      *  asukkaat.tallenna();
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
				 Asukas asukas = new Asukas();
				 asukas.parse(rivi);
				 lisaa(asukas);
			 }
			 muutettu = false;
		}catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
		}
	 }
	 
	    /** 
	     * Palauttaa "taulukossa" hakuehtoon vastaavien asukkaiden viitteet 
	     * @param hakuehto hakuehto 
	     * @param k etsittävän kentän indeksi  
	     * @return tietorakenteen löytyneistä asukkaista 
	     * @example 
	     * <pre name="test"> 
	     * #THROWS SailoException  
	     *   Asukkaat jasenet = new Asukkaat(); 
	     *   Asukas jasen1 = new Asukas(); jasen1.parse("2|2|Eelis|Koivusaari|0405219330|eelis.koivusaari@gmail.com|19"); 
	     *   Asukas jasen2 = new Asukas(); jasen2.parse("3|2|Eero|Koivusaari|0405219330|Eero.koivusaari@gmail.com|15"); 
	     *   Asukas jasen3 = new Asukas(); jasen3.parse("4|4|Nico|Kääriäinen|0405219330|the.gamer@gmail.com|19"); 
	     *   Asukas jasen4 = new Asukas(); jasen4.parse("5|1|Naku|Ankka|0405219330|a.ku@gmail.com|28"); 
	     *   Asukas jasen5 = new Asukas(); jasen5.parse("3|1|Beef|Turkay|0405219330|beed.turkay@gmail.com|33"); 
	     *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
	     *   List<Asukas> loytyneet;  
	     *   loytyneet = (List<Asukas>)jasenet.etsi("*n*",2);  
	     *   loytyneet.size() === 2;  
	     *   loytyneet.get(0) == jasen4 === true;  
	     *   loytyneet.get(1) == jasen3 === true;  
	     *     
	     *   loytyneet = (List<Asukas>)jasenet.etsi("*e*",2);  
	     *   loytyneet.size() === 3;  
	     *   loytyneet.get(0) == jasen5 === true;  
	     *   loytyneet.get(1) == jasen1 === true; 
	     *     
	     *   loytyneet = (List<Asukas>)jasenet.etsi(null,-1);  
	     *   loytyneet.size() === 5;  
	     * </pre> 
	     */ 
	     public Collection<Asukas> etsi(String hakuehto, int k){
	         String ehto ="*";
	         if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
	         int hk = k;
	         if(hk < 0) hk = 0;
	         List<Asukas> loytyneet = new ArrayList<Asukas>();
	         for(Asukas asukas : this) {
	             if(WildChars.onkoSamat(asukas.anna(hk), ehto)) loytyneet.add(asukas);
	         }
	         Collections.sort(loytyneet, new Asukas.Vertailija(hk));
	         return loytyneet;
	     }
	     /** 
	     * Poistaa asukkaan jolla on valittu tunnusnumero  
	     * @param id poistettavan asukkaan tunnusnumero 
	     * @return 1 jos poistettiin, 0 jos ei löydy 
	     * @example 
	     * <pre name="test"> 
	     * Asukkaat asukkaat = new Asukkaat(); 
	     * Asukas aku1 = new Asukas(), aku2 = new Asukas(), aku3 = new Asukas(); 
	     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
	     * int id1 = aku1.getId(); 
	     * asukkaat.lisaa(aku1); asukkaat.lisaa(aku2); asukkaat.lisaa(aku3); 
	     * asukkaat.poista(id1+1) === 1; 
	     * asukkaat.annaId(id1+1) === null; asukkaat.getLkm() === 2; 
	     * asukkaat.poista(id1) === 1; asukkaat.getLkm() === 1; 
	     * asukkaat.poista(id1+3) === 0; asukkaat.getLkm() === 1; 
	     * </pre> 
	     */ 
	 public int poista (int id) {
	        int ind = etsiId(id);
	        if (ind < 0) return 0;
	        asukkaat.remove(ind);
	        muutettu =  true;
	        return 1;
	    }
	 
	    /**
	     * Poistaa kaikki tietyn tietyn jäsenen harrastukset
	     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
	     * @return montako poistettiin 
	     * @example
	     * <pre name="test">
	     *  Asukkaat harrasteet = new Asukkaat();
	     *  Asukas pitsi21 = new Asukas(); pitsi21.rekisteroi(); pitsi21.setAsuntoId(2);
	     *  Asukas pitsi11 = new Asukas(); pitsi11.rekisteroi(); pitsi11.setAsuntoId(1);
	     *  Asukas pitsi22 = new Asukas(); pitsi22.rekisteroi(); pitsi22.setAsuntoId(2);
	     *  Asukas pitsi12 = new Asukas(); pitsi12.rekisteroi(); pitsi12.setAsuntoId(1);
	     *  Asukas pitsi23 = new Asukas(); pitsi23.rekisteroi(); pitsi23.setAsuntoId(2);
	     *  harrasteet.lisaa(pitsi21);
	     *  harrasteet.lisaa(pitsi11);
	     *  harrasteet.lisaa(pitsi22);
	     *  harrasteet.lisaa(pitsi12);
	     *  harrasteet.lisaa(pitsi23);
	     *  harrasteet.poistaAsunnonAsukkaat(2) === 3;  harrasteet.getLkm() === 2;
	     *  harrasteet.poistaAsunnonAsukkaat(3) === 0;  harrasteet.getLkm() === 2;
	     *  List<Asukas> h = harrasteet.annaAsukkaat(2);
	     *  h.size() === 0; 
	     *  h = harrasteet.annaAsukkaat(1);
	     *  h.get(0) === pitsi11;
	     *  h.get(1) === pitsi12;
	     * </pre>
	     */
	    public int poistaAsunnonAsukkaat(int tunnusNro) {
	        int n = 0;
	        for (Iterator<Asukas> it = asukkaat.iterator(); it.hasNext();) {
	            Asukas har = it.next();
	            if ( har.getAsuntoId() == tunnusNro ) {
	                it.remove();
	                n++;
	            }
	        }
	        if (n > 0) muutettu = true;
	        return n;
	    }
	 /**
	 * @param id id jota etsitään
	 * @return palauttaa kohdan missä kyseisen id omaava asukas on listassa
	 */
	public int etsiId(int id) {
	     for(int i = 0; i < asukkaat.size(); i++) 
	             if(id == asukkaat.get(i).getId()) return i;
	     return -1;
	 }
	
	  /** 
     * Etsii asukkaan id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return asukas jolla etsittävä id tai null 
     * <pre name="test"> 
     * Asukkaat jasenet = new Asukkaat(); 
     * Asukas aku1 = new Asukas(), aku2 = new Asukas(), aku3 = new Asukas(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getId(); 
     * jasenet.lisaa(aku1); jasenet.lisaa(aku2); jasenet.lisaa(aku3); 
     * jasenet.annaId(id1  ) == aku1 === true; 
     * jasenet.annaId(id1+1) == aku2 === true; 
     * jasenet.annaId(id1+2) == aku3 === true; 
     * </pre> 
     */ 
	public Asukas annaId(int id) {
	    for( Asukas asukas : this)
	        if( id == asukas.getId()) return asukas;
	    
	    return null;
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
			 for ( Asukas asukas : this) {
				 fo.println(asukas.toString());
			 }
		 }catch (FileNotFoundException e) {
			 throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
			 
		 }catch (IOException e) {
			 throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
		 }
		 muutettu = false;
	 }
	 
	 
	 /**
	  * @param args ei käytössä
	  */
	public static void main(String[] args) {
		Asukkaat asukkaat = new Asukkaat();
		
		Asukas testi1 = new Asukas(1);
		testi1.taytaOletus();
		Asukas testi2 = new Asukas(1);
		testi2.taytaOletus();
		
		asukkaat.lisaa(testi1);
		asukkaat.lisaa(testi2);
		
		try {
			asukkaat.tallenna();
		}catch (SailoException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			asukkaat.lueTiedostosta();
		}catch(SailoException e) {
			System.err.println(e.getMessage());
		}
		
		
		System.out.println("=============== Asukkaat testi ===============");
		
		for (Asukas as : asukkaat) {
			

			as.tulosta(System.out);
		}
		
	}
}
