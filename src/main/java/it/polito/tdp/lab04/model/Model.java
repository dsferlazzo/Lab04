package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;

	public Model() {
		this.corsoDAO = new CorsoDAO();
		this.studenteDAO = new StudenteDAO();
	}
	
	public List<Corso> getCorsi(){
		return corsoDAO.getTuttiICorsi();
	}
	
	public Studente getStudente(int matricola) {
		return studenteDAO.getStudente(matricola);
	}
	
	public List<Studente> getStudentiByCorso(Corso corso){
		return corsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	
	public Corso getCorsoByNome(String nome) {
		List<Corso> corsi = this.getCorsi();		//OTTENGO IL CORSO DAL CODINS
    	for(Corso c:corsi)
    	{
    		if(c.getNome().equals(nome))
    		{
    			return c;
    		}
    	}	
		return null;
	}
	
	public List<Corso> getCorsiByStudente(Studente s){
		return corsoDAO.getCorsiByStudente(s);
	}
	
	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		return corsoDAO.isStudenteIscrittoACorso(studente, corso);
	}
	
	public boolean InscriviStudenteACorso(Studente studente, Corso corso) {
		return corsoDAO.inscriviStudenteACorso(studente, corso);
	}
	
	

}
