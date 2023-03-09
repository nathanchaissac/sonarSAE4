package model.equipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ecurie.Ecurie;
import model.ecurie.EcurieManager;
import model.jeux.Jeu;
import model.tournoi.Tournoi;
import model.users.Users;

public class Equipe extends Users implements Comparable<Equipe>{
	
	private Jeu jeu;
	private int points;
	private int idEquipe;

	/**
	 * Constructeur d'une équipe
	 * @param id
	 * @param nom
	 * @param mail
	 * @param point
	 */
	public Equipe(int id, String nom, String mail,int point, int idEquipe) {
		super(id, nom, mail);
		this.points = point;
		this.idEquipe = idEquipe;
		
	}
	
	public int getIdEquipe() {
		return this.idEquipe;
	}
	
	/**
	 * Setter du jeu affilié à l'équipe
	 * @param game Jeu affilié
	 */
	public void setJeu(Jeu game) {
		this.jeu = game;
	}
	
	/**
	 * Setter du nombre de point
	 * @param point Nouveau solde de point
	 */
	public void setPoint(int point) {
		this.points = point;
	}
	
	/**
	 * Getter du jeu
	 * @return Jeu affilié à l'équipe
	 */
	public Jeu getJeu() {
		return this.jeu;
	}
	
	/**
	 * Getter du nombre de points
	 * @return Solde des points
	 */
	public int getPoints() {
		return this.points;			
	}

	@Override
	public String toString() {
		return this.getIdentifiant() + " (" + this.getMail() + ") Points : "+this.getPoints();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o!=null) {
			if(o instanceof Equipe) {
				Equipe t = (Equipe) o;
				if(this.getId()== t.getId()) {
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Vérifie si une équipe est déjà enregister dans la base de données
	 * @param idEcurie 
	 * @param jeu
	 * @return Vrai si une équipe de l'écurie est déjà affilié à ce jeu
	 */
	public static boolean verifDoublon(int idEcurie, Jeu jeu) {
		List<Equipe> listeEquipes = new ArrayList<Equipe>();
		try {
			listeEquipes = EquipeManager.getInstance().setEquipeParEcurie(EcurieManager.getInstance().getEcurieIdUtilisateur(idEcurie));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Equipe l : listeEquipes) {
			if(l.jeu.equals(jeu)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Equipe equipe) {
		if(equipe.getPoints() > this.points) return -1;
		if(equipe.getPoints() == this.points) return 0;
		return 1;
	}
}
