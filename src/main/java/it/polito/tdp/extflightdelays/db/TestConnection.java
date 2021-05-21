package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;

import it.polito.tdp.extflightdelays.model.Model;

public class TestConnection {

	public static void main(String[] args) {
		
		Model m = new Model();
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Test PASSED");
			

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}
		
	m.creaGrafo(200);
	//System.out.println(dao.getAdiacenze(null));
	}

}
