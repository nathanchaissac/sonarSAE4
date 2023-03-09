package model;

import java.sql.*;

public class DBconnec {

	private Connection connx;
	private static DBconnec instance;
	
	public static synchronized DBconnec getInstance() {
		if(instance == null) {
			instance = new DBconnec();
		}
		return instance;
	}

	/**
	 * Constructeur de la classe DBconnec
	 */
	private DBconnec() {
		String log = "";
		String mdp = "";
		String connectString = "";
		try {
			this.connx = DriverManager.getConnection(connectString,log,mdp);
			System.out.println("Connexion réussie");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Getter de la connexion
	 * @return La connexion
	 */
	public Connection getConnexion() {
		return this.connx;
	}
	/**
	 * Fermeture de la connexion
	 * @throws SQLException
	 */
	public void endConnec() throws SQLException {
		this.connx.close();
	}
}
