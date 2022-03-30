package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/**
	 * Ottengo tutti i corsi salvati nel Db
	 **/
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);		
			//RICONTROLLARE MANAGEMENT EXCEPTION
			
		}
	}
	
	
	/**
	 * Dato un codice insegnamento, ottengo il corso
	 **/
	public Corso getCorso(String codCorso) {
		
		Corso c =  null;
		final String sql = "SELECT "
				+ "FROM corso "
				+ "WHERE codins = ?";		//RICONTROLLARE IL FUNZIONAMENTO DELLA QUERY
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codCorso);
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
			}
			
			conn.close();
			return c;
			
		} catch(SQLException e )
		{
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);	
		}
		
	
		// TODO
	}

	/**
	 * Dato un corso, ottengo tutti gli studenti iscritti ad esso
	 **/
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		// TODO
		List<Studente> studenti = new ArrayList<Studente>();
		final String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins= ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String CDS = rs.getString("CDS");
				
				Studente s = new Studente(matricola, cognome, nome, CDS);
				studenti.add(s);
			}
			
			conn.close();
			return studenti;
			
		} catch(SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}
	/**
	 * Dato uno studente, il metodo cerca tutti i corsi a cui è iscritto
	 * @param s studente di cui dobbiamo cercare i corsi
	 * @return
	 */
	public List<Corso> getCorsiByStudente(Studente s)
	{
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND i.matricola=?";

		List<Corso> corsi = new LinkedList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				corsi.add(c);
			}
			
			conn.close();
			return corsi;
			
		} catch(SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	/**
	 * Dato uno studente ed un corso, il metodo ritorna true se lo studente è iscritto al corso,
	 * altrimenti, ritorna false	
	 * @param studente
	 * @param corso
	 * @return
	 */
	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso)
	{
		final String sql = "SELECT * "
				+ "FROM iscrizione "
				+ "WHERE codins=? AND matricola=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			Map<Integer, String> map = new TreeMap<Integer,String>();
			while(rs.next())
			{
				map.put(rs.getInt("matricola"), rs.getString("codins"));
			}
			if(map.size()>=1)
				return true;
			else return false;
			
			
		} catch(SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	/**
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 **/
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		if(this.isStudenteIscrittoACorso(studente, corso))
		{
			System.out.println("Studente gia iscritto a corso");
			return false;
		}
		
		final String sql = "INSERT INTO iscrizione (matricola, codins) "
				+ "VALUES (?,?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());

			int res = st.executeUpdate();
			conn.close();
			
			return (res==1);
			
			
		} catch(SQLException e )
		{
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

}
