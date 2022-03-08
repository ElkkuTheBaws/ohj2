package landlordApp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; // import the ArrayList class
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Vastuualueet:
 * -Pitää yllä asuntojen rekisteriä, osaa lisätä ja poistaa asunnon
 * -Lukee ja kirjoittaa asunnot tiedostoon
 * -Osaa etsiä ja lajitella
 * Avustajat: 
 * Asunto
 * @author Nico & Eelis
 * @version 12.3.2020
 */
public class Asunnot implements Iterable<Asunto>{
    
    private final ArrayList<Asunto> asunnot = new ArrayList<Asunto>();
    private String tiedostonPerusNimi = "asunnot";
    private boolean muutettu = false;
    
    /**
     * Palauttaa asuntojen lukumäärän
     * @return asuntojen lukumäärä
     */
    public int getLkm() {
        return asunnot.size();
    }
    
    /**
     * Lisää asunnon asuntojen listaan.
     * @param asunto lisättävä asunto
     * @example
	 * <pre name="test">
	 * Asunnot asunnot = new Asunnot();
	 * Asunto asunto1 = new Asunto(1); Asunto asunto2 = new Asunto(1);
	 * asunnot.getLkm() === 0;
	 * asunnot.lisaa(asunto1); asunnot.getLkm() === 1;
	 * asunnot.lisaa(asunto2); asunnot.getLkm() === 2;
	 * asunnot.lisaa(asunto2); asunnot.getLkm() === 3;
	 * asunnot.anna(0) === asunto1;
	 * asunnot.anna(1) === asunto2;
	 * asunnot.anna(2) === asunto2;
	 * asunnot.anna(1) == asunto1 === false;
	 * asunnot.anna(1) == asunto2 === true;
	 * asunnot.anna(3) === asunto1; #THROWS IndexOutOfBoundsException
	 * asunnot.lisaa(asunto1); asunnot.getLkm() === 4;
	 * asunnot.lisaa(asunto1); asunnot.getLkm() === 5;
	 * asunnot.lisaa(asunto1);
	 * </pre>
     */
    public void lisaa(Asunto asunto) {
        asunnot.add(asunto);
        muutettu = true;
    }
    
    
    /**
     * Korvaa tai lisää uuden asunnon tietorakenteeseen, jos asuntoa ei ole jo tallennettu.
     * @param asunto lisättävä asunto
     */
    public void korvaaTaiLisaa(Asunto asunto) {
        int id = asunto.getId();
        for (int i = 0; i < getLkm(); i++) {
            if (anna(i).getId() == id) {
                asunnot.set(i, asunto);
                muutettu = true;
                return;
            }
        }
        lisaa(asunto);
    }
    
    /**
     * Poistaa valitun asunnon
     * @param asunto poistettava 
     * @return tosi jos löytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Asunnot harrasteet = new Asunnot();
     *  Asunto pitsi21 = new Asunto(); pitsi21.rekisteroi(); pitsi21.setTaloId(2);
     *  Asunto pitsi11 = new Asunto(); pitsi11.rekisteroi(); pitsi11.setTaloId(1);
     *  Asunto pitsi22 = new Asunto(); pitsi22.rekisteroi(); pitsi22.setTaloId(2);
     *  Asunto pitsi12 = new Asunto(); pitsi12.rekisteroi(); pitsi12.setTaloId(1);
     *  Asunto pitsi23 = new Asunto(); pitsi23.rekisteroi(); pitsi23.setTaloId(2);
     *  harrasteet.lisaa(pitsi21);
     *  harrasteet.lisaa(pitsi11);
     *  harrasteet.lisaa(pitsi22);
     *  harrasteet.lisaa(pitsi12);
     *  harrasteet.poista(pitsi23) === false ; harrasteet.getLkm() === 4;
     *  harrasteet.poista(pitsi11) === true;   harrasteet.getLkm() === 3;
     *  List<Asunto> h = harrasteet.annaAsunnot(1);
     *  h.size() === 1; 
     *  h.get(0) === pitsi12;
     * </pre>
     */
    public boolean poista(Asunto asunto) {
        boolean ret = asunnot.remove(asunto);
        if (ret) muutettu = true;
        return ret;
    }

    
    /**
     * Poistaa kaikki tietyn tietyn jäsenen harrastukset
     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
     * @return montako poistettiin 
     * @example
     * <pre name="test">
     *  Asunnot harrasteet = new Asunnot();
     *  Asunto pitsi21 = new Asunto(); pitsi21.rekisteroi(); pitsi21.setTaloId(2);
     *  Asunto pitsi11 = new Asunto(); pitsi11.rekisteroi(); pitsi11.setTaloId(1);
     *  Asunto pitsi22 = new Asunto(); pitsi22.rekisteroi(); pitsi22.setTaloId(2);
     *  Asunto pitsi12 = new Asunto(); pitsi12.rekisteroi(); pitsi12.setTaloId(1);
     *  Asunto pitsi23 = new Asunto(); pitsi23.rekisteroi(); pitsi23.setTaloId(2);
     *  harrasteet.lisaa(pitsi21);
     *  harrasteet.lisaa(pitsi11);
     *  harrasteet.lisaa(pitsi22);
     *  harrasteet.lisaa(pitsi12);
     *  harrasteet.lisaa(pitsi23);
     *  harrasteet.poistaTalonAsunnot(2) === 3;  harrasteet.getLkm() === 2;
     *  harrasteet.poistaTalonAsunnot(3) === 0;  harrasteet.getLkm() === 2;
     *  List<Asunto> h = harrasteet.annaAsunnot(2);
     *  h.size() === 0; 
     *  h = harrasteet.annaAsunnot(1);
     *  h.get(0) === pitsi11;
     *  h.get(1) === pitsi12;
     * </pre>
     */
    public int poistaTalonAsunnot(int tunnusNro) {
        int n = 0;
        for (Iterator<Asunto> it = asunnot.iterator(); it.hasNext();) {
            Asunto har = it.next();
            if ( har.getTaloId() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }

    /**
     * Palauttaa paikassa i olevan asunnon
     * @param i asunnon indeksi
     * @return asunto
     * @throws IndexOutOfBoundsException jos paikassa i ei asuntoa
     */
    public Asunto anna(int i) throws IndexOutOfBoundsException {
        if(i < 0 || asunnot.size() <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return asunnot.get(i);
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
     *  Asunnot asunnot = new Asunnot();
     *  Asunto pitsi21 = new Asunto(); pitsi21.taytaOletus();
     *  Asunto pitsi11 = new Asunto(); pitsi11.taytaOletus();
     *  Asunto pitsi22 = new Asunto(); pitsi22.taytaOletus(); 
     *  Asunto pitsi12 = new Asunto(); pitsi12.taytaOletus();
     *  Asunto pitsi23 = new Asunto(); pitsi23.taytaOletus(); 
     *  String tiedNimi = "testiasunnot";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  asunnot.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  asunnot.lisaa(pitsi21);
     *  asunnot.lisaa(pitsi11);
     *  asunnot.lisaa(pitsi22);
     *  asunnot.lisaa(pitsi12);
     *  asunnot.lisaa(pitsi23);
     *  asunnot.tallenna();
     *  asunnot = new Asunnot();
     *  asunnot.lueTiedostosta(tiedNimi);
     *  Iterator<Asunto> i = asunnot.iterator();
     *  i.next().toString() === pitsi21.toString();
     *  i.next().toString() === pitsi11.toString();
     *  i.next().toString() === pitsi22.toString();
     *  i.next().toString() === pitsi12.toString();
     *  i.next().toString() === pitsi23.toString();
     *  i.hasNext() === false;
     *  asunnot.lisaa(pitsi23);
     *  asunnot.tallenna();
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
				 Asunto asunto = new Asunto();
				 asunto.parse(rivi);
				 lisaa(asunto);
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
			 for ( Asunto asunto : this) {
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
	     * Iteraattori kaikkien asukkaiden läpikäymiseen
	     * @return asukasiteraattori
	     * 
	     * @example
	     * <pre name="test">
	     * #PACKAGEIMPORT
	     * #import java.util.*;
	     * 
	     *  Asunnot asunnot = new Asunnot();
	     *  Asunto pitsi21 = new Asunto(2); asunnot.lisaa(pitsi21);
	     *  Asunto pitsi11 = new Asunto(1); asunnot.lisaa(pitsi11);
	     *  Asunto pitsi22 = new Asunto(2); asunnot.lisaa(pitsi22);
	     *  Asunto pitsi12 = new Asunto(1); asunnot.lisaa(pitsi12);
	     *  Asunto pitsi23 = new Asunto(2); asunnot.lisaa(pitsi23);
	     * 
	     *  Iterator<Asunto> i2=asunnot.iterator();
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
	     *  for ( Asunto as:asunnot ) { 
	     *    as.getTaloId() === jnrot[n]; n++;  
	     *  }
	     *  
	     *  n === 5;
	     *  
	     * </pre>
	     */
	 @Override
	 public Iterator<Asunto> iterator(){
		 return asunnot.iterator();
	 }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
    	Asunnot asunnot = new Asunnot();
		
		Asunto testi1 = new Asunto(1);
		testi1.taytaOletus();
		Asunto testi2 = new Asunto(1);
		testi2.taytaOletus();
		
		asunnot.lisaa(testi1);
		asunnot.lisaa(testi2);
		
		try {
			asunnot.tallenna();
		}catch (SailoException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			asunnot.lueTiedostosta();
		}catch(SailoException e) {
			System.err.println(e.getMessage());
		}
		
		
		System.out.println("=============== Asunnot testi ===============");
		
		for (Asunto as : asunnot) {
			

			as.tulosta(System.out);
		}
    }

    /**
     * Palauttaa listan asunnoista tietyllä id:llä
     * @param id id
     * @return lista jossa löydetyt asunnot
     */
    public List<Asunto> annaAsunnot(int id) {
        List<Asunto> loydetyt = new ArrayList<Asunto>();
        for (Asunto as : asunnot)
            if(as.getTaloId() == id) loydetyt.add(as);
        
        return loydetyt;
    }

}
