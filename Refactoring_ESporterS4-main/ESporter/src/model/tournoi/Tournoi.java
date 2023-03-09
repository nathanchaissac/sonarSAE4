package model.tournoi;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.equipe.Equipe;
import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.match.MatchManager;
import model.poule.Poule;
import model.poule.Phases;
import model.poule.PouleManager;
import model.users.UserManager;

public class Tournoi {
	
	private int idTournoi;
	private String nomTournoi;
	private Date dateTournoi;
	private String lieuTournoi;
	private Date dateLimiteInscription;
	private ImportanceTournoi importanceTournoi;
	private int id_jeu;
	private int id_arbitre;
	private List<Equipe> listeEquipe;
	private Poule[] poule;

	/**
	 * Constructeur d'un tournoi
	 * @param nom Nom du tournoi
	 * @param date Date du tournoi
	 * @param lieu Lieu du tournoi
	 * @param dateLimite Date limite d'inscription
	 * @param importance Importance du tournoi
	 * @param id_jeu Id du jeu du tournoi
	 * @param id_arbitre Id de l'arbitre du tournoi
	 */
	public Tournoi(String nom, Date date, String lieu, Date dateLimite, ImportanceTournoi importance, int id_jeu, int id_arbitre) {
		this.nomTournoi = nom;
		this.dateTournoi = date;
		this.lieuTournoi = lieu;
		this.dateLimiteInscription = dateLimite;
		this.importanceTournoi = importance;
		this.listeEquipe = new ArrayList<Equipe>();
		this.id_jeu = id_jeu;
		this.id_arbitre = id_arbitre;
	}
	
	/**
	 * Créer les poules du tournoi
	 * @throws SQLException
	 */
	public void createPoules() throws SQLException {
		if(new Date(System.currentTimeMillis()).after(this.dateTournoi)) {
			if(this.listeEquipe.size() >= 15) {
				this.poule = new Poule[5];
				for(int i = 0; i < 5; i++) {
					this.poule[i] = new Poule(i, (i < 4) ? Phases.DEMI_FINAL : Phases.FINAL, this);
				}
				List<Poule> poules = Arrays.asList(this.poule).stream().filter((e) -> e.getPhase().equals(Phases.DEMI_FINAL)).collect(Collectors.toList());
				int equipeIndex = 0;
				for(Poule poule : poules) {
					for(int i = equipeIndex ; i < this.listeEquipe.size(); i++) {
						if(poule.ajouterEquipe(this.listeEquipe.get(equipeIndex))) {
							equipeIndex++;
						}
					}
					poule.showPoule();
				}
				createPouleFinal();
			}
		}
	}
	
	/**
	 * Créer la poule final
	 * @throws SQLException
	 */
	public void createPouleFinal() throws SQLException {
		int nbPouleFini = 0;
		for(Poule poule : this.poule) {
			if(MatchManager.getInstance().allMatchFinishForPoule(poule)) {
				nbPouleFini++;
			}
		}
		
		if(nbPouleFini == 4) {
			System.out.println("PASSAGE A POULE FINALE");
			Poule poule = Arrays.asList(this.poule).stream().filter((e) -> e.getPhase().equals(Phases.FINAL)).findFirst().get();
			System.out.println(poule.getId());
			List<Poule> poules = Arrays.asList(this.poule).stream().filter((e) -> e.getPhase().equals(Phases.DEMI_FINAL)).collect(Collectors.toList());
			poules.stream().forEach((e) -> {
				try {
					poule.ajouterEquipe(PouleManager.getInstance().getQualified(e));
					System.out.println(PouleManager.getInstance().getQualified(e));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});
		}
	}
	
	/**
	 * Getter du gagnant
	 * @return Gagnant du tournoi
	 * @throws SQLException
	 */
	public Equipe winner() throws SQLException {
		Poule poule = Arrays.asList(this.poule).stream().filter((e) -> e.getPhase().equals(Phases.FINAL)).findFirst().get();
		return PouleManager.getInstance().getQualified(poule);

	}
	
	/**
	 * Getter des poules
	 * @return Tableau des poules du tournoi
	 */
	public Poule[] getPoules() {
		return this.poule;
	}
	
	/**
	 * Getter de l'id
	 * @return Id tournoi
	 */
	public int getId() {
		return this.idTournoi;
	}
	
	/**
	 * Setter de l'id du tournoi
	 * @param id Nouvelle id
	 */
	public void setIdTournoi(int id) {
		this.idTournoi = id;
	}
	
	/**
	 * Getter du nom
	 * @return nom du tournoi
	 */
	public String getNom() {
		return this.nomTournoi;
	}
	
	/**
	 * Getter de la date
	 * @return Date de début
	 */
	public Date getDate() {
		return this.dateTournoi;
	}
	
	/**
	 * Getter du lieu
	 * @return Lieu du tournoi
	 */
	public String getLieu() {
		return this.lieuTournoi;
	}
	
	/**
	 * Getter date limite
	 * @return Date limite d'inscription
	 */
	public Date getDateLimite() {
		return this.dateLimiteInscription;
	}
	
	/**
	 * Getter ID du jeu
	 * @return String de l'id du jeu
	 */
	public String getJeu() {
		return ""+this.id_jeu;
	}
	
	/**
	 * Getter du jeu
	 * @return Jeu du tournoi
	 * @throws SQLException
	 */
	public Jeu getJeuAvecId() throws SQLException {
		return JeuManager.getInstance().getJeuParIdInt(this.id_jeu);
	}
	
	/**
	 * Getter de l'id arbitre
	 * @return Id arbitre du tournoi
	 */
	public int getArbitre() {
		return this.id_arbitre;
	}
	
	/**
	 * Getter de l'importance du tournois
	 * @return Importance du tournois
	 */
	public ImportanceTournoi getImportance() {
		return this.importanceTournoi;
	}
	
	/**
	 * Ajouter une équipe au tournois
	 * @param equipe Equipe à ajouter
	 */
	public void ajouterEquipe(Equipe equipe) {
		this.listeEquipe.add(equipe);
	}
	
	/**
	 * Indice de l'équipe dans la liste
	 * @param equipe
	 * @return Indice de l'équipe dans la liste
	 */
	public int rechercherEquipe(Equipe equipe) {
		int indice = -1;
		for(int i = 0; i < this.listeEquipe.size(); i++) {
			if(this.listeEquipe.get(i).equals(equipe)) {
				indice = i;
			}
		}
		return indice;
	}
	
	/**
	 * Supprime une équipe du tournois
	 * @param equipe Equipe à supprimer
	 */
	public void supprimerEquipe(Equipe equipe) {
		this.listeEquipe.remove(equipe);
	}
	
	/**
	 * Vérifie la présence du équipe au tournois
	 * @param equipe Equipe à vérifier
	 * @return Vrai si l'équipe est présente
	 * @throws SQLException
	 */
	public boolean verifDoublonEquipe(Equipe equipe) throws SQLException {
		for(Tournoi t:TournoiManager.getInstance().getListeTournoi()) {
			if(this.equals(t)) {
				if(t.rechercherEquipe(equipe) > -1) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Getter de la liste des équipes
	 * @return Liste des équipes inscrite
	 */
	public List<Equipe> getListeEquipe(){
		return this.listeEquipe;
	}
	
	/**
	 * Vérifie l'existance du tournois
	 * @return Vrai si le tournois existe
	 * @throws SQLException
	 */
	public boolean verifDoublon() throws SQLException {
		for(Tournoi t : TournoiManager.getInstance().getListeTournoi()) {
			if(t.equals(this)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		try {
			if(TournoiManager.getInstance().verifInscription(this.idTournoi, UserManager.getInstance().getUser().getId())) {
				return this.nomTournoi + " - " + new SimpleDateFormat("dd/MM/yyyy").format(this.dateTournoi) + " (" + this.importanceTournoi.getNom() + ") déjà inscrit ";
			}else {
				return this.nomTournoi +  " - " +new SimpleDateFormat("dd/MM/yyyy").format(this.dateTournoi)+ " (" + this.importanceTournoi.getNom() + ")";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
	}
	@Override
	public boolean equals(Object o) {
		if(o!=null) {
			if(o instanceof Tournoi) {
				Tournoi t = (Tournoi) o;
				if(this.nomTournoi.equals(t.nomTournoi)) {
					if(this.dateTournoi.equals(t.dateTournoi)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
