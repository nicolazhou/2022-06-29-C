/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.AlbumBilancio;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	Album a1 = this.cmbA1.getValue();
    	
    	
    	for(AlbumBilancio ab : this.model.getAdiacenze(a1)) {
    		
    		
    		this.txtResult.appendText(ab + "\n");
    		
    	}
    	
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	Album a1 = this.cmbA1.getValue();
    	
    	if(a1 == null) {
    		
    		this.txtResult.appendText("Scegli A1!");
    		return;
    		
    	}

    	
    	Album a2 = this.cmbA2.getValue();
    	
    	if(a2 == null) {
    		
    		this.txtResult.appendText("Scegli A2!");
    		return;
    		
    	}
    	
    	
    	String input = this.txtX.getText();
    	
    	double x = 0.0;
    	
    	try {
    		
    		x = Double.parseDouble(input);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.appendText("Inserisci un valore numerico a X!");
    		return;
    		
    	}

    	List<Album> soluzione = this.model.trovaPercorso(x, a1, a2);
    	
    	for(Album a : soluzione) {
    		
    		this.txtResult.appendText(a + "\n");	
    		
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
  
    	String input = this.txtN.getText();
    	
    	Double n = 0.0;
    	
    	try {
    		
    		n = Double.parseDouble(input);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.setText("Inserisci un valore numerico a n!");
    		return;
    		
    	}
    	

    	this.model.creaGrafo(n);
    	
    	
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# Vertici: " + this.model.getNNodes() + "\n");
    	this.txtResult.appendText("# Archi: " + this.model.getNArchi() + "\n");
        
    	this.cmbA1.getItems().clear();
    	this.cmbA1.getItems().addAll(this.model.getVertici());
    	
    	this.cmbA2.getItems().clear();
    	this.cmbA2.getItems().addAll(this.model.getVertici());
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
