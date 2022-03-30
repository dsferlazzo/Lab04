package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	/**
	 * Data una matricola, ottengo lo studente
	 */
	
	public Studente getStudente(int matStudente) {
		final String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola = ?";

		Studente s = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matStudente);
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String CDS = rs.getString("CDS");
				
				s = new Studente(matricola, cognome, nome, CDS);
				
				
			}
			
			conn.close();
			return s;
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}
	
	

}
