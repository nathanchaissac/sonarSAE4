package model.poule;


import java.sql.SQLException;

import model.equipe.Equipe;
import model.match.Match;
import model.tournoi.Tournoi;

public class Poule {
	
	private Equipe[] equipes;
	private int numeroPoule;
	private Phases phase;
	private Tournoi tournoi;
	private Match[] matchs;
	private int idPoule;

	/**
	 * Constructeur d'une poule
	 * @param numeroPoule Numéro de la poule
	 * @param phase Phase de la poule
	 * @param tournoi Tournoi dans lequel se déroule la poule
	 */
	public Poule(int numeroPoule, Phases phase, Tournoi tournoi) {
		this.equipes = new Equipe[4];
		this.numeroPoule = numeroPoule;
		this.phase = phase;
		this.tournoi = tournoi;
		this.matchs = new Match[6];
		this.idPoule = 0;
		try {
			PouleManager.getInstance().createPouleForTournoi(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter des matchs
	 * @return Tableau des matchs de la poule
	 */
	public Match[] getMatchs() {
		return this.matchs;
	}
	
	/**
	 * Getter de l'id
	 * @return Id de la poule
	 */
	public int getId() {
		return this.idPoule;
	}
	
	/**
	 * Setter de l'id
	 * @param pouleId Nouvelle id de la poule
	 */
	public void setIdPoule(int pouleId) {
		this.idPoule = pouleId;
	}
	
	/**
	 * Getter de la phase
	 * @return La phase de poule
	 */
	public Phases getPhase() {
		return this.phase;
	}
	
	/**
	 * Getter du tournoi
	 * @return Tournoi affilié à la poule
	 */
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	/**
	 * Getter du numéro de poule
	 * @return Numéro de poule
	 */
	public int getNumeroPoule() {
		return this.numeroPoule;
	}
	
	/**
	 * Getter des équipes
	 * @return Tableau des équipes dans la poule
	 */
	public Equipe[] getEquipes() {
		return this.equipes;
	}
	
	/**
	 * Ajouter une équipe à la poule
	 * @param equipe Equipe à ajouter
	 * @return Vrai si l'équipe à pu être ajouter à la poule
	 */
	public boolean ajouterEquipe(Equipe equipe) {
		boolean ajouter = false;
		int index = 0;
		while(!ajouter && index < 4) {
			if(this.equipes[index] == null) {
				this.equipes[index] = equipe;
				ajouter = true;
				try {
					PouleManager.getInstance().ajouterEquipeALaPoule(this, equipe);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			index++;
		}
		if(poulePleine()) {
			createAllMatchs();
		}
		return ajouter;
	}
	
	private void createAllMatchs() {
		int index = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = i+1; j < 4; j++) {
				Match match = new Match(this.tournoi.getArbitre(), this);
				try {
					match.ajouterEquipe(this.equipes[i]);
					match.ajouterEquipe(this.equipes[j]);
				}catch(SQLException e) {
					e.printStackTrace();
				}
				this.matchs[index] = match;
				index++;
			}
		}
	}
	
	/**
	 * Vérifie si une poule est pleine
	 * @return Vrai si la poule est pleine
	 */
	public boolean poulePleine() {
		return this.equipes[3] != null;
	}
	
	public void showPoule() {
		StringBuilder str = new StringBuilder();
		str.append("[Numéro: " + this.numeroPoule + " [ ");
		for(int i = 0; i < this.equipes.length; i++) {
			if(this.equipes[i] != null) {
				str.append(this.equipes[i].getIdentifiant() + ", ");
			}
		}
		str.append("]]");
		System.out.println(str.toString());
	}
	
	@Override
	public String toString() {
		return "[Numéro: " + this.numeroPoule + ", phase: " + this.phase + "]";
	}
	
	public void showMatchsPoule() {
		StringBuilder str = new StringBuilder();
		
		for(Match match : this.matchs) {
			str.append(match.toString() + "\n");
		}
		System.out.println(str.toString());
	}
}
