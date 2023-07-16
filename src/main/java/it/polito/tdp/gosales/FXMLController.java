package it.polito.tdp.gosales;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.alg.util.Pair;

import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<String> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	
    	 String riv = cmbRivenditore.getValue() ;
     	
     	if(riv==null) {
     		txtResult.appendText("Inserire un rivenditore.\n");
     		return ;
     	}
     	
     	Retailers r=new Retailers(0, null, null, null);
     	
     	for(Retailers i : this.model.getVertici()) {
     		if(i.getName().equals(riv)){
     			r=i;
     		}
     	}
     	
     	
       	this.txtResult.appendText("La componente connessa di "+riv+" ha dimensione "+this.model.getComponente(r).size()+".\n");
       	

       		txtResult.appendText("Il peso totale degli archi della componente connessa è "+(int)this.model.getPesoTot(r)+".\n");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	cmbRivenditore.getItems().clear();
    	
       String nazione = cmbNazione.getValue() ;
    	
    	if(nazione==null) {
    		txtResult.setText("Inserire una nazione.\n");
    		return ;
    	}
    	
     	 Integer anno = cmbAnno.getValue() ;
    	
    	if(anno==null) {
    		txtResult.setText("Inserire un anno.\n");
    		return ;
    	}
    	
       String tm = txtNProdotti.getText() ;
    	
    	if(tm.equals("")) {
    		txtResult.setText("Inserire i prodotti in comune.\n");
    		return ;
    	}
    	
    	int m = 0 ;

    	try {
	    	m = Integer.parseInt(tm) ;
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un valore numerico.\n");
    		return ;
    	}
    	
//   	creazione grafo
   	this.model.creaGrafo(nazione, anno , m);
   	
   	
//	stampa grafo
	this.txtResult.setText("Grafo creato.\n");
	this.txtResult.appendText("#Vertici: " + this.model.nVertici() + "\n");
	this.txtResult.appendText("#Archi: " + this.model.nArchi() + "\n\n");
   	
   	txtVertici.clear();
   	txtArchi.clear();
   	
   	for(String i : this.model.getVerticiName()) {
   	txtVertici.appendText(i+"\n");
   	}
   	
   	for(String i : this.model.stampaArchi()) {
    	txtArchi.appendText(i+"\n");
   	}
   	
   	List<String> venditori=new LinkedList<>();
   	
   	for(Retailers i : this.model.getVertici()) {
   		venditori.add(i.getName());
   	}
   	
   	Collections.sort(venditori);
   	
   	cmbRivenditore.setDisable(false);
   	
   	cmbRivenditore.getItems().addAll(venditori);
   	
   	btnAnalizzaComponente.setDisable(false);
   	
	   	
  
    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	List<Integer> anni = new LinkedList<>();
    	anni.add(2015);
    	anni.add(2016);
    	anni.add(2017);
    	anni.add(2018);
    	cmbAnno.getItems().addAll(anni);
    	
    	List<String> nazioni = new LinkedList<>();
    	nazioni.addAll(this.model.getNazioni());
    	cmbNazione.getItems().addAll(nazioni);
    }

}
