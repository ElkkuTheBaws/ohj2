package landlord;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import landlordApp.Landlord;
import landlordApp.SailoException;
import landlordApp.Talo;
import landlordApp.Asukas;
import landlordApp.Asunto;

/**
 * @author Nico & Eelis
 * @version 13.3.2020
 *
 */

public class LandlordGUIController implements Initializable {

	  @FXML
	  private ListChooser<Asukas> listResidents;

	  @FXML
	  private GridPane gridAsunto;
	  
	  @FXML
	  private GridPane gridTalo;

	  @FXML
	  private Accordion taloAccordion;
	  
	  @FXML
	  private TextField hakuehto;
	  
	  @Override
	  public void initialize(URL url, ResourceBundle bundle) {
	      alusta();
	  }

	  /**
	   * Lisää asunnon
	   */
    @FXML
    void clickAddApartment() {
    	uusiAsunto();
    }
    
    /**
     * Näyttää tietoja
     */
    @FXML
    void clickAbout() {
        Dialogs.showMessageDialog("Morjesta totanoi niih.");
    }
    
    /**
     * Tallentaa muutokset
     */
    @FXML
    void clickSave() {
        tallenna();
    }
    /*
     * Avaa uuden ikkunan jossa voi lisätä taloja
     */
    @FXML
    void clickAddHouse() {
    	uusiTalo();
    }

    /*
     * Lisää uuden asukkaan
     */
    @FXML
    void clickAddResident() {
        uusiAsukas();
    }
    
    /**
     * Hakee
     */
    @FXML
    void handleHaku() {
        hae(0);
    }
    /*
     * Avaa ikkunan, jossa varmistetaan halutaanko poistaa asunto
     */
    @FXML
    void clickDeleteApartment() {
        poistaAsunto();
    }

    /*
     * Poistaa valitun asukkaan.
     */
    @FXML
    void clickDeleteResident() {
    	poistaAsukas();
    }
    
    /*
     * Poistuu sovelluksesta.
     */
    @FXML
    void clickExit() {
    	poistu();
    }
    
    /*
     * Avaa uuden ikkunan, jossa tiedot valitusta asukkaasta
     */
    @FXML
    void clickResidentInfo() {
    	muokkaaAsukasta(kentta);
    }
    
    /*
     * Poistaa valitun talon.
     */
    @FXML
    void clickDeleteSelectedHouse() {
        poistaTalo();
    }
    
    //====================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyviä koodinpätkiä

   private Landlord landlord;
   private TitledPane valittuTaloRuutu; // Valitun talon TitledPane
   private Talo valittuTalo; // Valittu talo-olio.
   private Asunto valittuAsunto; // Valittu asunto-olio.
   private Asukas valittuAsukas; //Valittu asukas-olio.
   private TextField edits[];
   private TextField edits2[];
   private int kentta = 0;
   private static Talo aputalo = new Talo();
   private static Asunto apuasunto = new Asunto();
    
    /**
     * Asetetaan controlleriin landlordviite.
     * @param landlord mihin viitataan
     */
	public void setLandlord(Landlord landlord) {
		this.landlord = landlord;
		listResidents.clear(); //Väliaikainen
	}
	
	/**
	 * Alustaa tietorakenteen kentät
	 */
	protected void alusta() {
	    listResidents.clear();
	    listResidents.addSelectionListener(e -> naytaAsukas());
	    
	    edits = LandlordTietueDialogController.luoKentat(gridAsunto, apuasunto);
	    for (TextField edit: edits)
	        if (edit != null) {
	            edit.setEditable(false);
	            edit.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaaAsuntoa(getFieldId(e.getSource(),0)); });
	            edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
	        }
	    
	    edits2 = LandlordTietueDialogController.luoKentat(gridTalo, aputalo);
	    for (TextField edit: edits2)
	        if (edit != null) {
	            edit.setEditable(false);
	            edit.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaaTaloa(getFieldId(e.getSource(),0)); });
	            edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
	        }
	}
	
	/**
	 * Palautetaan komponentin id:stä saatava luku
	 * @param obj tutkittava komponentti
	 * @param oletus mikä arvo jos id ei ole kunnollinen
	 * @return komponentin id lukuna
	 */
	public static int getFieldId(Object obj, int oletus) {
	    if ( !(obj instanceof Node)) return oletus;
	    Node node = (Node)obj;
	    return Mjonot.erotaInt(node.getId().substring(1),oletus);
	}
	
	
	/**
	 * Lukee tiedostot ja luo käyttöliittymään oliot.
	 * @return virhe
	 */
	public String lueTiedostosta(){
	   
	    try {
	        landlord.lueTiedostosta("./src/data");
	        
	        for(Talo x : landlord.getTalot()) {
	            x.rekisteroi();
	            TitledPane tp = new TitledPane(x.getOsoite(), new ListChooser<Asunto>());
	            tp.setUserData(x);
	            taloAccordion.getPanes().add(tp);
	            tp.setOnMouseClicked( e -> naytaTalo(tp));
	            valittuTalo = x;
	            for(Asunto asu :landlord.annaAsunnot(x)){
	            	asu.rekisteroi();
	                @SuppressWarnings("unchecked")
					ListChooser<Asunto> valittu = (ListChooser<Asunto>) tp.getContent(); // Koska valituntaloruudun sisältö ei voi olla muu kuin listchooser
	                valittu.add(asu.getAsuntoId(),asu);
	                valittu.setOnMouseClicked(e -> naytaAsunto(valittu));
	               // valittuAsunto = asu;
	                for (Asukas asukas : landlord.annaAsukkaat(asu)) {
	                	asukas.rekisteroi();
	                }
	            }
	        }
	        
	        return null;
	    }catch(SailoException e) {
	        String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
	    }
	    
	    
	}
	
	/**
	 * Hakee taloyhtiön tiedot
	 * @param tnr numero joka aktivoidaan haun jälkeen
	 */
	protected void hae(int tnr) {
	    int tnro = tnr;
	    int index = 0;
	    if (tnro <= 0) {
	        Talo kohdalla = valittuTalo;
	        if (kohdalla != null) tnro = kohdalla.getId();
	    }
	    
	    String ehto = hakuehto.getText();
	    if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
	    
	    taloAccordion.getPanes().clear();
	    
	    Collection<Talo> talot;
	    talot = landlord.etsi(ehto, aputalo.ekaKentta() ); // Sukunimen kenttä
	    int i = 0;
	    for(Talo x : talot) {
            TitledPane tp = new TitledPane(x.getOsoite(), new ListChooser<Asunto>());
            tp.setUserData(x);
            if (x.getId() == tnro) index = i;
            taloAccordion.getPanes().add(tp);
            tp.setOnMouseClicked( e -> naytaTalo(tp));
            valittuTalo = x;
            for(Asunto asu :landlord.annaAsunnot(x)){
                @SuppressWarnings("unchecked")
                ListChooser<Asunto> valittu = (ListChooser<Asunto>) tp.getContent(); // Koska valituntaloruudun sisältö ei voi olla muu kuin listchooser
                valittu.add(asu.getAsuntoId(),asu);
                valittu.setOnMouseClicked(e -> naytaAsunto(valittu));
            }
            i++;
	    }
	    if (taloAccordion.getPanes().size() > 0) {
	        taloAccordion.getPanes().get(index).setExpanded(true);
	        valittuTaloRuutu = taloAccordion.getPanes().get(index);
	        valittuTalo = (Talo)valittuTaloRuutu.getUserData();
	    }
	    
	}
	
	/**
	 * Poistaa valitun talon sekä sen kaikki asunnot ja asukkaat
	 */
	private void poistaTalo() {
        if (valittuTalo == null || taloAccordion.getPanes().size() == 0) return;
	    if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko talo " + valittuTalo.getOsoite() + "?", "Kyllä", "Ei"))
	        return;
	    landlord.poista(valittuTalo);
	    hae(0);
	}
	
	/**
	 * Poistaa valitun asunnon sekä sen kaikki asukkaat
	 */
	private void poistaAsunto() {
	    @SuppressWarnings("unchecked")
        ListChooser<Asunto> valittu = (ListChooser<Asunto>)valittuTaloRuutu.getContent();
	    if (valittuAsunto == null || taloAccordion.getPanes().size() == 0 || valittu.getSelectedIndex() < 0) return;
	    if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko asunto " + valittuAsunto.getAsuntoId() + " talosta " + valittuTalo.getOsoite() + "?","Kyllä" , "Ei"))
	        return;
	    landlord.poistaAsunto(valittuAsunto);
	    paivita();
	}
	
	/**
	 * Poistaa valitun asukkaan.
	 */
	private void poistaAsukas() {
	    @SuppressWarnings("unchecked")
        ListChooser<Asunto> valittu = (ListChooser<Asunto>)valittuTaloRuutu.getContent();
        if(valittuAsunto == null || (valittu.getSelectedIndex() < 0)) return;
	    if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko asukas " + valittuAsukas.getAsukaanEtunimi() + " asunnosta " + valittuAsunto.getAsuntoId() + "?", "Kyllä", "Ei"))
	        return;
	    landlord.poistaAsukas(valittuAsukas);
	    paivita();
	}
	
	/**
	 * Päivittää kaikki ruudut näkyviin.
	 */
	private void paivita() {
	    int valittuRuutuInd = taloAccordion.getPanes().indexOf(valittuTaloRuutu);

	    taloAccordion.getPanes().clear();
        
        for(Talo x : landlord.getTalot()) {
            TitledPane tp = new TitledPane(x.getOsoite(), new ListChooser<Asunto>());
            tp.setUserData(x);
            taloAccordion.getPanes().add(tp);
            tp.setOnMouseClicked( e -> naytaTalo(tp));
            valittuTalo = x;
            for(Asunto asu :landlord.annaAsunnot(x)){
                @SuppressWarnings("unchecked")
                ListChooser<Asunto> valittu = (ListChooser<Asunto>) tp.getContent(); // Koska valituntaloruudun sisältö ei voi olla muu kuin listchooser
                valittu.add(asu.getAsuntoId(),asu);
                valittu.setOnMouseClicked(e -> naytaAsunto(valittu));
            }
        }
        LandlordTietueDialogController.naytaTietue(edits2, valittuTalo);
        if (landlord.annaAsukkaat(valittuAsunto).size() == 0) listResidents.clear();
        if (taloAccordion.getPanes().size() == 0) {
            for (TextField tx : edits) {
                if (tx != null)
                    tx.setText("");
            }
            for (TextField tx : edits2) {
                if (tx != null)
                    tx.setText("");
            }
            listResidents.clear();
            return;
        }
        if (valittuAsunto == null) {
            for (TextField tx : edits) {
                if (tx != null)
                    tx.setText("");
            }
        }
        if (valittuRuutuInd < 0 || valittuRuutuInd > taloAccordion.getPanes().size()-1)
            valittuTaloRuutu = taloAccordion.getPanes().get(0);
        else valittuTaloRuutu = taloAccordion.getPanes().get(valittuRuutuInd);
        valittuTaloRuutu.setExpanded(true);
	}
	
	
	/**
	 * Tallentaa muutokset tiedostoihin
	 * @return virhe
	 */
	private String tallenna() {
	    try {
	        landlord.tallenna();
	        return null;
	    }catch(SailoException ex) {
	        Dialogs.showMessageDialog("Tallennuksesssa ongelmia" + ex.getMessage());
	        return ex.getMessage();
	    }
	}
	
	/**
	 * Poistuu sovelluksesta.
	 */
	private void poistu() {
	    
	    if (!Dialogs.showQuestionDialog("Sulje sovellus", "Oletko varma, että haluat poistua?", "Kyllä", "Ei"))
	        return;
	    if (!Dialogs.showQuestionDialog("Tallennus", "Tallennetaanko muutokset?", "Kyllä", "Ei"))
	        Platform.exit();
	    tallenna();
	    Platform.exit();
	}
	
	
	/**
	 * Lisää talojen listaan uuden talon ja asettaa lisätyn talon valituksi.
	 */
	private void uusiTalo() {
		Talo uusi = new Talo();
		uusi = LandlordTietueDialogController.kysyTietue(null, uusi, 0);
		if (uusi == null || uusi.getOsoite().length() == 0) return;
		uusi.rekisteroi();
		landlord.lisaa(uusi);

		TitledPane tp = new TitledPane(uusi.getOsoite(), new ListChooser<Asunto>());
		tp.setUserData(uusi);
		taloAccordion.getPanes().add(tp);
		tp.setOnMouseClicked( e -> naytaTalo(tp));
		valittuTalo = uusi;
		hae(0);
	}
	
	/**
	 * Lisää asukkaiden listaan uuden asukkaan ja asettaa asukkaan valituksi
	 * @throws SailoException dwawd
	 */
	@SuppressWarnings("unchecked")
    private void  uusiAsukas(){
	    ListChooser<Asunto> valittu = (ListChooser<Asunto>)valittuTaloRuutu.getContent();
	    if(valittuAsunto == null || (valittu.getSelectedIndex() < 0)) return;
	    Asukas uusi = new Asukas(valittuAsunto.getId());
	    uusi = LandlordTietueDialogController.kysyTietue(null, uusi, 0);
	    if ( uusi == null || uusi.getAsukaanEtunimi().length() == 0) return;
	    uusi.rekisteroi();
	    landlord.lisaa(uusi);

	    listResidents.add(uusi.getAsukaanEtunimi(), uusi);
	    listResidents.setOnMouseClicked(e -> naytaAsukas());
	    valittuAsukas = uusi;
	    naytaAsunto((ListChooser<Asunto>)valittuTaloRuutu.getContent());
	    
	}
	
	/**
	 * Avaa muokkausruudun asukkaalle
	 * @param k valittava muokkaustekstikenttä
	 */
	@SuppressWarnings("unchecked")
    private void muokkaaAsukasta(int k) {
	    ListChooser<Asunto> valittu = (ListChooser<Asunto>)valittuTaloRuutu.getContent();
	    if(valittuAsunto == null || (valittu.getSelectedIndex() < 0)) return; 
	    
	    try {
	        Asukas asukas;
	        asukas = LandlordTietueDialogController.kysyTietue(null, valittuAsukas.clone(), k);
	        if (asukas == null) return;
	        landlord.korvaaTaiLisaa(asukas);
	        valittuAsukas = asukas;
	        naytaAsunto((ListChooser<Asunto>)valittuTaloRuutu.getContent());
	    } catch (CloneNotSupportedException e) {
	        //
	        }
	    }
	       
	/**
	 * Avaa muokkausruudun asunnolle
	 * @param k valittava muokkaustekstikenttä
	 */
	   private void muokkaaAsuntoa(int k) {
	       @SuppressWarnings("unchecked")
        ListChooser<Asunto> valittu = (ListChooser<Asunto>)valittuTaloRuutu.getContent();
	        if (valittuAsunto == null || taloAccordion.getPanes().size() == 0 || valittu.getSelectedIndex() < 0) return;
	       
	       try {
	       Asunto asunto;
	       asunto = LandlordTietueDialogController.kysyTietue(null,  valittuAsunto.clone(), k);
	       if ( asunto == null) return;
	       landlord.korvaaTaiLisaa(asunto);
	       @SuppressWarnings("unchecked")
        ListChooser<Asunto> valittuListChooser = (ListChooser<Asunto>)valittuTaloRuutu.getContent();
	       paivitaAsunto(valittuListChooser);
	       valittuAsunto = asunto;
	       LandlordTietueDialogController.naytaTietue(edits, asunto);
	       paivita();
	       } catch (CloneNotSupportedException e) {
	           //
	       }
	   }
	   
	   /**
	    * Avaa muokkausruudun talolle
	    * @param k valittava muokkaustekstikenttä
	    */
	   private void muokkaaTaloa(int k) {
	        if (valittuTalo == null || taloAccordion.getPanes().size() == 0) return;
           
           try {
           Talo talo;
           talo = (Talo) LandlordTietueDialogController.kysyTietue(null,  valittuTalo.clone(), k);
           if ( talo == null) return;
           landlord.korvaaTaiLisaa(talo);
           valittuTalo = talo;
           LandlordTietueDialogController.naytaTietue(edits2, talo);
           hae(0);
           } catch (CloneNotSupportedException e) {
               //
           }
       }
	/**
	 * Näyttää ruudulla valitun asukkaan tiedot
	 * @param valittu listchooser, missä asukkaat ovat.Eli siis valitun asunnun lista asukkaista. 
	 */
	private void naytaAsukas() {
	    valittuAsukas = listResidents.getSelectedObject();
    }
	
	/**
	 * Päivittää asuntojen tiedot.
	 * @param valittu valitun talon lista asunnoista
	 */
	private void paivitaAsunto(ListChooser<Asunto> valittu) {
	    valittu.clear();
	    int index = valittu.getSelectedIndex();
	    for(Asunto asu : landlord.annaAsunnot(valittuTalo)){
            valittu.add(asu.getAsuntoId(),asu);
            valittu.setOnMouseClicked(e -> naytaAsunto(valittu));
	    }
	    valittu.setSelectedIndex(index);
	}

    /**
     * Lisää asuntojen listaan uuden asunnon ja asettaa lisätyn asunnon valituksi.
     */
    private void uusiAsunto() {
        if(valittuTalo == null || taloAccordion.getPanes().size() == 0) return;
        
        Asunto uusi = new Asunto(valittuTalo.getId());
        uusi = LandlordTietueDialogController.kysyTietue(null, uusi, 0);
        if ( uusi == null || uusi.getAsuntoId().length() == 0) return;
        uusi.rekisteroi();
        landlord.lisaa(uusi);
		@SuppressWarnings("unchecked")
        ListChooser<Asunto> valittu = (ListChooser<Asunto>) valittuTaloRuutu.getContent(); // Koska valituntaloruudun sisältö ei voi olla muu kuin listchooser
        valittu.add(uusi.getAsuntoId(),uusi);
        valittu.setOnMouseClicked(e -> naytaAsunto(valittu));
        naytaAsunto(valittu);
        valittuAsunto = uusi;
        
    }

    
	/**
	 * Näyttää ruudulla valitun talon tiedot.
	 * @param valittu valittu titledpane, jossa talon nimi.
	 */
	public void naytaTalo(TitledPane valittu) {
		valittuTaloRuutu = valittu;
		valittuTalo = (Talo)valittuTaloRuutu.getUserData();
		
		LandlordTietueDialogController.naytaTietue(edits2, valittuTalo);
	}
	
   /**
	* Näyttää ruudulla valitun asunnon tiedot.
	* @param valittu valittu listchooser, jossa asunnot ovat. Eli siis valitun talon lista asunnoista
	*/
    public void naytaAsunto(ListChooser<Asunto> valittu) {
        valittuAsunto = valittu.getSelectedObject();
        Asunto asuntoKohdalla = valittuAsunto;
        int index = listResidents.getSelectedIndex();
        
        if (asuntoKohdalla == null) return;
        
        listResidents.clear();
        for(Asukas x : landlord.annaAsukkaat(asuntoKohdalla)) {
            listResidents.add(x.getAsukaanEtunimi(),x);
            listResidents.setOnMouseClicked(e -> naytaAsukas());
            valittuAsukas = x;
        }
        
        listResidents.setSelectedIndex(index);
        LandlordTietueDialogController.naytaTietue(edits, asuntoKohdalla);
        
    }
}
