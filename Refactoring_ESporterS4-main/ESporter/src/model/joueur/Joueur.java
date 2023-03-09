package model.joueur;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.equipe.Equipe;
import model.equipe.EquipeManager;

public class Joueur{
	
	private String pseudo;
	private Date naissance;
	private String prenom;
	private int idEquipe;
	private int idJoueur;

	/**
	 * Constructeur d'un joueur
	 * @param pseudo Pseudo du joueur
	 * @param naissance Date de naissance du joueur
	 * @param prenom Nom du joueur
	 * @param id_Equipe Id de l'équipe du joueur
	 */
	public Joueur(String pseudo, Date naissance, String prenom, int id_Equipe, int id) {
		this.pseudo = pseudo;
		this.naissance = naissance;
		this.prenom = prenom;
		this.idEquipe = id_Equipe;
		this.idJoueur = id;
	}
	
	/**
	 * Getter du pseudo
	 * @return Pseudo du joueur
	 */
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setIdJoueur(int id) {
		this.idJoueur = id;
	}
	
	public int getIdJoueur() {
		return this.idJoueur;
	}
	
	/**
	 * Getter de la date de naissance
	 * @return Date de naissance du joueur
	 */
	public String getDateNaissance() {
		Date d = this.naissance;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(d);
	}
	
	public int getDateDay() {
		String[] str = getDateNaissance().split("/");
		return Integer.valueOf(str[0]);
	}
	
	public int getDateMonth() {
		String[] str = getDateNaissance().split("/");
		return Integer.valueOf(str[1])-1;
	}
	
	public int getDateYear() {
		String[] str = getDateNaissance().split("/");
		return Integer.valueOf(str[2]);
	}
	
	/**
	 * Getter du nom
	 * @return Nom du joueur
	 */
	public String getPrenom() {
		return this.prenom;
	}
	
	/**
	 * Getter de l'id équipe
	 * @return Id équipe du joueur
	 */
	public int getIdEquipe() {
		return this.idEquipe;
	}
	
	@Override
	public String toString() {
		return this.pseudo + " - " + this.prenom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		return Objects.equals(pseudo, other.pseudo) && Objects.equals(naissance, other.naissance) && Objects.equals(prenom, other.prenom);
	}
	
	/**
	 * Verifie l'existance du joueur dans l'quipe
	 * @param equipe
	 * @param pseudo
	 * @return Vrai si un joueur à déjà se pseudo dans l'équipe
	 */
	public static boolean verifDoublon(Equipe equipe, String pseudo) {
		List<Joueur> listJoueurs = new ArrayList<Joueur>();
		try {
			listJoueurs = JoueurManager.getInstance().getJoueurPourEquipe(equipe);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean res = false;
		for(Joueur j : listJoueurs) {
			if(j.getPseudo().equals(pseudo)) {
				res = true;
			}
		}
		return res;
	}

}
