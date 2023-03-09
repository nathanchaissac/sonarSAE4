package model.arbitre;

import java.sql.SQLException;
import java.util.Objects;

import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.users.Users;

public class Arbitre extends Users{
	
	private String nom;
	private String jeu;

	/**
	 * Constructeur d'un arbitre
	 * @param id
	 * @param identifiant
	 * @param mail
	 * @param nom
	 * @param jeu
	 */
	public Arbitre(int id, String identifiant, String mail, String nom, String jeu) {
		super(id, identifiant, mail);
		this.nom = nom;
		this.jeu = jeu;
	}	
	
	/**
	 * Getter jeu 
	 * @return Id du jeu de l'arbitre
	 */
	public int getIdJeu() {
		return Integer.parseInt(this.jeu);
	}
	
	/**
	 * Getter du nom
	 * @return nom de l'arbitre
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Getter de l'i
	 * @return Id arbitre
	 */
	public int getArbitre() {
		return this.getId();
	}
	
	/**
	 * Verifie si un arbitre est dans la base de données
	 * @param identifiant
	 * @return Vrai si un arbitre à déjà cet identifiant
	 * @throws SQLException
	 */
	public static boolean verifDoublon(String identifiant) throws SQLException {
		for(Arbitre a : ArbitreManager.getInstance().getListArbitres()) {
			if(a.getIdentifiant().equals(identifiant)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifie si l'adresse mail de l'arbitre est dans la base de données
	 * @param mail
	 * @return Vrai si un arbitre à déjà ce mail
	 * @throws SQLException
	 */
	public static boolean verifDoublonMail(String mail) throws SQLException {
		for(Arbitre a : ArbitreManager.getInstance().getListArbitres()) {
			if(a.getMail().equals(mail)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String s = "";
		try {
			s = this.getIdentifiant() + " - " + super.getMail() + " (" + JeuManager.getInstance().getJeuParId(this.jeu).getNom() + ")";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arbitre other = (Arbitre) obj;
		return Objects.equals(other.jeu, jeu) && Objects.equals(other.nom, nom) && Objects.equals(this.getMail(), other.getMail()) && Objects.equals(this.getIdentifiant(), other.getIdentifiant());
	} 

}
