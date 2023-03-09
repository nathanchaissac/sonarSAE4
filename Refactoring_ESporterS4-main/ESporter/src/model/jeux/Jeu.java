package model.jeux;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.DBconnec;

public class Jeu {
	
	private int idJeu;
	private String nomJeu;
	private String editeur;
	private String acronymeJeu;

	/**
	 * Constructeur d'un jeu
	 * @param nom Nom du jeu
	 * @param edit Editeur du jeu
	 * @param acronyme Acronyme du jeu
	 */
	public Jeu(String nom, String edit, String acronyme) {
		this.nomJeu = nom;
		this.editeur = edit;
		this.acronymeJeu = acronyme;
		
	}
	
	/**
	 * Getter du nom du jeu
	 * @return Nom du jeu
	 */
	public String getNom() {
		return this.nomJeu;
	}
	
	/**
	 * Getter de l'éditeur du jeu
	 * @return Nom de l'éditeur
	 */
	public String getEditeur() {
		return this.editeur;
	}
	
	/**
	 * Getter de l'acronyme du jeu
	 * @return Nom de l'acronyme
	 */
	public String getAcronyme() {
		return this.acronymeJeu;
	}
	
	/**
	 * Getter de l'id du jeu
	 * @return Id du jeu
	 */
	public int getId() {
		return this.idJeu;
	}
	
	/**
	 * Setter de l'id du jeu
	 * @param id Nouvelle valleur
	 */
	public void setId(int id) {
		this.idJeu = id;
	}
	
	/**
	 * Verifier l'existance du jeu dans la base de données
	 * @return Vrai si L'acronyme et le nom son identique
	 * @throws SQLException
	 */
	public boolean verifDoublon() throws SQLException {
		for(Jeu j : JeuManager.getInstance().getJeux()) {
			if(j.nomJeu.equals(this.nomJeu)) {
				return true;
			}else if(j.acronymeJeu.equals(this.acronymeJeu)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifier l'existance d'un jeu avec le même nom dans la base de données
	 * @return Vrai si le nom est identique
	 * @throws SQLException
	 */
	public boolean verifDoublonNomJeu() throws SQLException{
		for(Jeu j : JeuManager.getInstance().getJeux()) {
			if(j.nomJeu.equals(this.nomJeu)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifier l'existance d'un jeu avec le même acronyme dans la base de données
	 * @return Vrai si l'acronyme est identique
	 * @throws SQLException
	 */
	public boolean verifDoublonAcronymeJeu() throws SQLException{
		for(Jeu j : JeuManager.getInstance().getJeux()) {
			if(j.acronymeJeu.equals(this.acronymeJeu)) {
				return true;
			}
		}
		return false;
	}
		
	@Override
	public String toString() {
		return this.getNom()+" ( "+this.getEditeur()+" )";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(acronymeJeu, editeur, nomJeu);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jeu other = (Jeu) obj;
		return Objects.equals(acronymeJeu, other.acronymeJeu) && Objects.equals(editeur, other.editeur)
				&& Objects.equals(nomJeu, other.nomJeu);
	} 
}
