package model.match;

import java.sql.SQLException;

import model.equipe.Equipe;
import model.poule.Poule;
import model.poule.Phases;

public class Match {
	
	private Equipe[] equipe;
	private int idArbitre;
	private Equipe gagnant;
	private int id;
	private Poule poule;

	/**
	 * Constructeur d'un match
	 * @param idArbitre Id de l'arbitre du match
	 * @param poule Poule dans laquelle se déroule le match
	 */
	public Match(int idArbitre, Poule poule) {
		this.equipe = new Equipe[2];
		this.idArbitre = idArbitre;
		this.poule = poule;
		this.gagnant = null;
	}
	
	/**
	 * Getter de la poule
	 * @return Poule dans lequel est le match
	 */
	public Poule getPouleMatch() {
		return this.poule;
	}
	
	/**
	 * Ajouter une équipe au match
	 * @param equipe Equipe à ajouter
	 * @return Vrai si l'équipe à pu être ajoutée
	 * @throws SQLException
	 */
	public boolean ajouterEquipe(Equipe equipe) throws SQLException {
		boolean ajouter = false;
		int index = 0;
		while(!ajouter && index < 2) {
			if(this.equipe[index] == null) {
				this.equipe[index] = equipe;
				ajouter = true;
				if(this.equipe[1] != null) {
					MatchManager.getInstance().ajouterMatchDePoule(this, this.getPouleMatch(), getEquipes());
				}
			}
			index ++;
		}
		return ajouter;
	}
	
	/**
	 * Setter de l'id du match
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter de l'id du match
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Etablir le gagnant du match
	 * @param equipe
	 */
	public void setGagnant(Equipe equipe) {
		boolean contain = false;
		for(int i = 0; i < this.equipe.length; i ++) {
			if(!contain) {
				if(this.equipe[i].equals(equipe)) {
					contain = true;
					this.gagnant = equipe;
					if(this.poule.getPhase().equals(Phases.DEMI_FINAL)) {
						try {
							this.getPouleMatch().getTournoi().createPouleFinal();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * Getter du gagnant du match
	 * @return
	 */
	public Equipe getGagnant() {
		return this.gagnant;
	}

	/**
	 * Getter de l'id de l'arbitre
	 * @return
	 */
	public int getIdArbitre() {
		return this.idArbitre;
	}

	/**
	 * Getter des équipes du match
	 * @return
	 */
	public Equipe[] getEquipes() {
		return this.equipe;
	}
	
	@Override
	public String toString() {
		return "["+this.equipe[0].getIdentifiant() + " VS " + this.equipe[1].getIdentifiant() + "] "+this.getId();
	}

}
