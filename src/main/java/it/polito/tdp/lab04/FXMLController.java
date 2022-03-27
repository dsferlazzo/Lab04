package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private ComboBox<String> cmbCorsi;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    void handleCercaCorsi(ActionEvent event) {

    }

    @FXML
    void handleCercaIscrittiCorso(ActionEvent event) {
    	
    	if(cmbCorsi.getValue().equals(""))		//GESTISCO COMBOBOX VUOTA
    	{
    		txtArea.appendText("Corso non inserito");
    		return;
    	}
    	
    	List<Corso> corsi = model.getCorsi();
    	Corso corsoScelto= null;
    	for(Corso c:corsi)
    	{
    		if(c.getNome().equals(cmbCorsi.getValue()))
    		{
    			corsoScelto=c;
    			break;
    		}
    	}
    	
    	List<Studente> studenti = this.model.getStudentiByCorso(corsoScelto);
    	
    	if(studenti.size()==0)
    	{
    		txtArea.appendText("Corso senza iscritti");
    		return;
    	}
    	
    	for(Studente s:studenti)
    	{
    		txtArea.appendText(s.toString() + "\n");
    	}
    	
    	

    }

    @FXML
    void handleCompletamento(ActionEvent event) {
    	
    	txtNome.clear();
    	txtCognome.clear();
    	String matricola = txtMatricola.getText();
    	
    	try {
    		int numMatricola = Integer.parseInt(matricola);
    		Studente s = this.model.getStudente(numMatricola);
    		
    		if(s==null)
    		{
    			txtArea.appendText("Studente non registrato");
    			return;
    		}
    		
    		txtNome.setText(s.getNome());
    		txtCognome.setText(s.getCognome());
    		
    		return;
    		
    	} catch (Exception e)
    	{
    		txtArea.appendText("Inserire un numero matricola valido");
    		e.printStackTrace();
    	}
    	
    	
    	

    }

    @FXML
    void handleIscrivi(ActionEvent event) {

    }

    @FXML
    void handleReset(ActionEvent event) {
    	txtArea.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	//IMPLEMENTARE EVENTUALE RESET DELLA COMBO BOX

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	List<Corso> corsi = model.getCorsi();
    	cmbCorsi.getItems().clear();
    	cmbCorsi.getItems().add("");
    	
    	for(Corso c : corsi)
    	{
    		cmbCorsi.getItems().add(c.getNome());
    	}
    	
    }

    @FXML
    void initialize() {
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";

    }

}
