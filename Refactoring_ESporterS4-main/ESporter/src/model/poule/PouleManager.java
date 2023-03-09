package model.poule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DBconnec;
import model.equipe.Equipe;

public class PouleManager {
	
	private static PouleManager instance;
	
	public synchronized static PouleManager getInstance() {
		if(instance == null) {
			instance = new PouleManager();
		}
		return instance;
	}
	
	/**
	 * Creer les poules pour un tournoi
	 * @param poule
	 * @throws SQLException
	 */
	public void createPouleForTournoi(Poule poule) throws SQLException {
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT `numero`, `id_tournois` FROM `Poules` WHERE `numero`='"+poule.getNumeroPoule()+"' AND `id_tournois`="+poule.getTournoi().getId());
		if(!rs.next()) {
			st.execute("INSERT INTO `Poules`(`numero`, `phase`, `id_tournois`) VALUES ('"+poule.getNumeroPoule()+"','"+poule.getPhase()+"','"+poule.getTournoi().getId()+"')");
			poule.setIdPoule(idPouleByPoule(poule));
		}else {
			poule.setIdPoule(idPouleByPoule(poule));
			
		}
		rs.close();
		st.close();
	}
	
	/**
	 * Getter d'une poule
	 * @param poule
	 * @return Id de la poule
	 * @throws SQLException
	 */
	private int idPouleByPoule(Poule poule) throws SQLException {
		if(poule.getId() == 0) {
			Statement st = DBconnec.getInstance().getConnexion().createStatement();
			ResultSet rs = st.executeQuery("SELECT `id_poule` FROM `Poules` WHERE `numero`='"+poule.getNumeroPoule()+"' AND `id_tournois`="+poule.getTournoi().getId());
			if(rs.next()) {
				poule.setIdPoule(rs.getInt("id_poule"));
			}
			rs.close();
			st.close();
		}
		return poule.getId();
	}
	
	/**
	 * Ajouter une équipe à la poule
	 * @param poule 
	 * @param equipe Equipe à ajouter
	 * @throws SQLException
	 */
	public void ajouterEquipeALaPoule(Poule poule, Equipe equipe) throws SQLException {
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT id_poule FROM Poules WHERE numero='"+poule.getNumeroPoule()+"' AND id_tournois="+poule.getTournoi().getId());
		if(!rs.next()) {
			st.execute("INSERT INTO `Poules_equipes`(`id_equipe`, `id_poule`) VALUES ('"+equipe.getId()+"','"+poule.getId()+"')");
		}
		rs.close();
		st.close();
	}
	
	/**
	 * 
	 * @param poule
	 * @return Equipe avec le plus de point dans la poule
	 * @throws SQLException
	 */
	public Equipe getQualified(Poule poule) throws SQLException {
		Equipe first = poule.getEquipes()[0];
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("SELECT te.id_equipe, te.points FROM `Tournois_equipes` te, Equipes e, Poules_equipes pe WHERE te.id_tournois = ? AND te.id_equipe = e.id_equipe AND e.id_utilisateur = pe.id_equipe AND pe.id_poule = ? ORDER BY te.points desc LIMIT 1;");
		ps.setInt(1, poule.getTournoi().getId());
		ps.setInt(2, poule.getId());
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			for(Equipe equipe : poule.getEquipes()) {
				if(equipe.getId() == rs.getInt("id_equipe")) {
					first = equipe;
				}
			}
		}
		rs.close();
		ps.close();
		return first;
	}

}
