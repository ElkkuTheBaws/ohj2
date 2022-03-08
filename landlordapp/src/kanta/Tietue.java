package kanta;


/**
 * Rajapinta tietueelle johon voidaan taulukon avulla rakentaa 
 * "attribuutit".
 * @author niveseka
 * @version 21.4, 2020
 * @example
 */
public interface Tietue {

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import landlordApp.Asukas;
     *   Asukas a = new Asukas();
     *   a.getKenttia() === 5;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Asukas a = new Asukas();
     *   a.ekaKentta() === 2;
     * </pre>
     */
    public abstract int ekaKentta();


    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Asukas a = new Asukas();
     *   a.getKysymys(2) === "ala";
     * </pre>
     */
    public abstract String getKysymys(int k);


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Asukas a = new Asukas();
     *   a.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   a.anna(0) === "2";   
     *   a.anna(1) === "10";   
     *   a.anna(2) === "Kalastus";   
     *   a.anna(3) === "1949";   
     *   a.anna(4) === "22";   
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Asukas a = new Asukas();
     *   a.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   a.aseta(3,"1940")  === null;
     *   a.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   a.aseta(4,"20")    === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Asukas a = new Asukas();
     *   a.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = a.clone();
     *   kopio.toString() === a.toString();
     *   a.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(a.toString()) === false;
     *   kopio instanceof Asukas === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Asukas asukas = new Asukas();
     *   asukas.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   asukas.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public abstract String toString();

}
