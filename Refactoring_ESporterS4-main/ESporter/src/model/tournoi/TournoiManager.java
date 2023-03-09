package model.tournoi;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.DBconnec;
import model.arbitre.Arbitre;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.users.UserManager;


public class TournoiManager {
	
	private List<Tournoi> listeTournois;
	private static TournoiManager instance;
	
	public static synchronized TournoiManager getInstance() {
		if(instance==null) {
			instance=new TournoiManager();
		}
		return instance;
	}
	
	/**
	 * Getter des tournois
	 * @return Listes des tournois
	 * @throws SQLException
	 */
	public List<Tournoi> getListeTournoi() throws SQLException {
		List<Tournoi> liste = new ArrayList<Tournoi>();
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT t.id_tournois, t.nom, t.dateTournois, t.lieu, t.importance, t.dateLimite, j.acronyme_jeu, t.id_jeu, t.id_arbitre FROM Tournois t, Jeux j WHERE j.id_jeu = t.id_jeu");
		Statement st2 = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs2 =st2.executeQuery("SELECT id_equipe, id_tournois FROM Tournois_equipes");
		while(rs.next()){
			Tournoi tournoi = new Tournoi(rs.getString("nom"), rs.getDate("dateTournois"), rs.getString("lieu"), rs.getDate("dateLimite"), ImportanceTournoi.valueOf(rs.getString("importance")), rs.getInt("id_jeu"),rs.getInt("id_arbitre"));
			tournoi.setIdTournoi(rs.getInt("id_tournois"));
			while(rs2.next()) {
				if(rs2.getInt("id_tournois") == tournoi.getId()) {
					tournoi.ajouterEquipe(EquipeManager.getInstance().getEquipeId(rs2.getInt("id_equipe")));
				}
			}
			tournoi.createPoules();
			liste.add(tournoi);
		}
		rs.close();
		rs2.close();
		st.close();
		st2.close();
		return liste;
	}
	
	/**
	 * Tableau des tournois
	 * @return Tableau des tournois
	 * @throws SQLException
	 */
	public Tournoi[] getTabTournoi() throws SQLException {
		List<Tournoi> listTournois = getListeTournoi();
		return listTournois.toArray(new Tournoi[listTournois.size()]);
	}
	
	/**
	 * Ajoute un tournois à la base de données
	 * @param t Tournoi à ajouter
	 * @throws SQLException
	 */
	public void addDBTournoi(Tournoi t) throws SQLException {
		if(!t.verifDoublon()) {
			int idArbitre = t.getArbitre();
			Statement st = DBconnec.getInstance().getConnexion().createStatement();
			st.executeUpdate("INSERT INTO Tournois (nom, dateTournois, lieu, importance, dateLimite, id_jeu, id_arbitre) values ('" + t.getNom() + "','"+t.getDate()+"','"+t.getLieu()+"','"+t.getImportance().toString()+"','"+t.getDateLimite()+ "'," + t.getJeu() + "," + idArbitre + ")");
			st.close();
		}
	}
	
	/**
	 * Supprimer un tournois de la base de données
	 * @param tournoi Tournoi à supprimer
	 * @throws SQLException
	 */
	public void removeTournoi(Tournoi tournoi) throws SQLException {
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		st.execute("DELETE FROM `Tournois` WHERE `id_tournois`="+tournoi.getId());
		System.out.println(tournoi.getId());
		st.close();
	}
	
	/**
	 * Ajouter une équipe au tournoi
	 * @param tournoi
	 * @param equipe Equipe inscrite
	 * @throws SQLException
	 */
	public void addEquipeAuTournois(Tournoi tournoi,Equipe equipe) throws SQLException {
		Statement st2 = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs =st2.executeQuery("SELECT * FROM Tournois_equipes WHERE "+ tournoi.getId() +" = id_tournois AND " + EquipeManager.getInstance().getIdEquipe(equipe.getId())+" = id_equipe;" );
		if(!rs.next()) {
			if(!LocalDate.now().isAfter(tournoi.getDateLimite().toLocalDate())) {
				Statement st = DBconnec.getInstance().getConnexion().createStatement();
				st.executeUpdate("INSERT INTO Tournois_equipes (id_tournois, id_equipe, inscription, points) values (" + tournoi.getId() + ","+ EquipeManager.getInstance().getIdEquipe(equipe.getId()) +",'"+ LocalDate.now() +"',"+ 0 +")");
				st.close();
			}
		}
		rs.close();
		st2.close();
	}
		
	/**
	 * Supprimer une équipes du tournois
	 * @param tournoi
	 * @param equipe
	 * @throws SQLException
	 */
	public void removeEquipeTournoi(Tournoi tournoi,Equipe equipe) throws SQLException {
		if(!LocalDate.now().isAfter(tournoi.getDate().toLocalDate())) {
			Statement st = DBconnec.getInstance().getConnexion().createStatement();
			st.execute("DELETE FROM Tournois_equipes WHERE id_tournois = "+tournoi.getId()+" AND id_equipe ="+EquipeManager.getInstance().getIdEquipe(equipe.getId()));
			st.close();
		}
	}
	
	/**
	 * Getter des tournois 
	 * @param j Jeu commun
	 * @param idEquipe 
	 * @return Tableau des tournois pour un même jeu
	 * @throws SQLException
	 */
	public Tournoi[] getTabTournoiParJeu(Jeu j, int idEquipe) throws SQLException {
		List<Tournoi> liste = new ArrayList<Tournoi>();
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("SELECT DISTINCT(t.id_tournois), t.nom, t.dateTournois, t.lieu, t.importance, t.dateLimite, t.id_jeu, t.id_arbitre "
				+ "FROM Tournois t "
				+ "WHERE t.id_jeu = ? AND t.dateLimite >= NOW() OR ? in "
				+ "(SELECT te.id_equipe FROM Tournois_equipes te , Equipes e , Utilisateurs u "
				+ "WHERE te.id_tournois = t.id_tournois AND te.id_equipe = e.id_equipe AND u.id_utilisateur = e.id_utilisateur)");
		st.setInt(1, j.getId());
		st.setInt(2, idEquipe);
		ResultSet rs = st.executeQuery();
		Statement st2 = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs2 =st2.executeQuery("SELECT id_equipe, id_tournois FROM Tournois_equipes");
		while(rs.next()){
			Tournoi tournoi = new Tournoi(rs.getString("nom"), rs.getDate("dateTournois"), rs.getString("lieu"), rs.getDate("dateLimite"), ImportanceTournoi.valueOf(rs.getString("importance")), rs.getInt("id_jeu"),rs.getInt("id_arbitre"));
			tournoi.setIdTournoi(rs.getInt("id_tournois"));
			while(rs2.next()) {
				if(rs2.getInt("id_tournois") == tournoi.getId()) {
					tournoi.ajouterEquipe(EquipeManager.getInstance().getEquipeId(rs2.getInt("id_equipe")));
				}
			}
			liste.add(tournoi);
		}
		return liste.toArray(new Tournoi[liste.size()]);
		
	}
	
	/**
	 * Vérifie si une équipe est déjà inscrite au tournoi
	 * @param idTn Id Tournoi
	 * @param id Id équipe
	 * @return Vrai si l'équipe est inscrite
	 * @throws SQLException
	 */
	public boolean verifInscription(int idTn,int id) throws SQLException {
		
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs =st.executeQuery("SELECT te.id_tournois FROM Tournois_equipes as te,Equipes WHERE te.id_equipe = Equipes.id_equipe AND Equipes.id_utilisateur = "+ id);
		boolean rep = false;
		while(rs.next()) {
			if(rep == false) {
				if(rs.getInt(1) == idTn) {
					rep = true;
				}else{
					rep = false;
				}
			}
		}
		rs.close();
		st.close();
		return rep;
	}
}
