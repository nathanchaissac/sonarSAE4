package model.match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DBconnec;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.poule.Poule;
import model.tournoi.Tournoi;
import model.tournoi.TournoiManager;

public class MatchManager {
	private List<Match> listeTournois;
	private static MatchManager instance;
	private List<Match> liste;
	
	public static synchronized MatchManager getInstance() {
		if(instance==null) {
			instance=new MatchManager();
		}
		return instance;
	}

	
	/**
	 * Getter des matchs pour un arbitre
	 * @param id Id de l'arbitre
	 * @return Liste des matchs à arbitrer
	 * @throws SQLException
	 */
	public List<Match> getListeMatchPourArbitre(int id) throws SQLException {
		List<Match> matchs = new ArrayList<>();
		List<Integer> Gset = MatchManager.getInstance().allIdMatchWithWinnerForArbitre(id);
		List<Tournoi> tournois = TournoiManager.getInstance().getListeTournoi();
		tournois.stream().filter((e) -> e.getArbitre() == id).forEach((e) -> {
			if(e.getPoules() != null) {
				for(Poule poule : e.getPoules()) {
					for (Match match : poule.getMatchs()) {
						if(match != null) {
							if(!Gset.contains(match.getId())){
								matchs.add(match);
							}
						
						}
					}
					
				}
			}
			
		});
		return matchs;
	}
	
	/**
	 * Getter des matchs pour une arbitre
	 * @param id Id de l'arbitre
	 * @return Tableau des matchs
	 * @throws SQLException
	 */
	public Match[] getTabMatchPourArbitre(int id) throws SQLException {
		List<Match> listMatchs = getListeMatchPourArbitre(id);
		return listMatchs.toArray(new Match[listMatchs.size()]);
	}
	
	/**
	 * Tableau des matchs non nulle
	 * @param id Id de l'arbitre
	 * @return Tableau des matchs non nulle de l'arbitre
	 * @throws SQLException
	 */
	public Match[] getTabMatchPourArbitreNoNull(int id) throws SQLException {
		List<Match> lstMatch = new ArrayList<Match>();
		Match[] match = MatchManager.getInstance().getTabMatchPourArbitre(id);
		for(int i= 0; match[i]!=null;i++) {
			lstMatch.add(match[i]);
		}
		return lstMatch.toArray(new Match[lstMatch.size()]);
	}
	
	/**
	 * Ajouter un match à une poule
	 * @param match
	 * @param poule
	 * @param equipes
	 * @throws SQLException
	 */
	public void ajouterMatchDePoule(Match match, Poule poule, Equipe[] equipes) throws SQLException {
		
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("SELECT `id_match` FROM `Matchs` WHERE `id_poule`="+poule.getId()+" AND `id_equipe_1`='"+equipes[0].getId()+"' AND `id_equipe_2`='"+equipes[1].getId()+"'");
		ResultSet rs = st.executeQuery();
		PreparedStatement st2 = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO `Matchs`(`id_equipe_1`, `id_equipe_2`, `id_poule`, `id_arbitre`) VALUES ('"+equipes[0].getId()+"','"+equipes[1].getId()+"','"+poule.getId()+"','"+match.getIdArbitre()+"')");
		if(!rs.next()) {
			st2.execute();
			PreparedStatement st3 = DBconnec.getInstance().getConnexion().prepareStatement("SELECT `id_match` FROM `Matchs` WHERE `id_poule`="+poule.getId()+" AND `id_equipe_1`='"+equipes[0].getId()+"' AND `id_equipe_2`='"+equipes[1].getId()+"'");
			ResultSet rs2 = st3.executeQuery();
			if(rs2.next()) {
				match.setId(rs2.getInt("id_match"));
			}
			rs2.close();
			st2.close();
			st3.close();
		}else {
			match.setId(rs.getInt("id_match"));
		}
		rs.close();
		st.close();


		
	}
	
	/**
	 * Vérifie si tous les matchs de la poule sont fini
	 * @param p Poule à vérifier
	 * @return Vrai si tous les matchs sont fini
	 * @throws SQLException
	 */
	public boolean allMatchFinishForPoule(Poule p) throws SQLException {
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT count(Matchs.id_match) as nbMatchFini FROM `Matchs` WHERE `id_poule`= "+p.getId()+" AND `id_equipe_gagnante` IS NOT NULL;");
		int nbMatch = 0;
		if(rs.next()) {
			nbMatch = rs.getInt("nbMatchFini");
		}
		rs.close();
		st.close();
		return nbMatch == 6;
	}
	
	/**
	 * Getter de la liste des id de matchs
	 * @param idArbitre
	 * @return La liste des matchs qui on un gagnat 
	 * @throws SQLException
	 */
	public List<Integer> allIdMatchWithWinnerForArbitre(int idArbitre) throws SQLException {
		List<Integer> idList = new ArrayList<Integer>();
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT Matchs.id_match FROM Matchs WHERE Matchs.id_equipe_gagnante IS NOT NULL AND Matchs.id_arbitre = "+idArbitre);
		while(rs.next()) {
			idList.add(rs.getInt("Matchs.id_match"));
		}
		return idList;
		
	}
	
	/**
	 * Vérifie si tous les tournois de demi final sont fini
	 * @param idTournoi
	 * @return Vrai si toutes les demi finales sont fini
	 * @throws SQLException
	 */
	public boolean isAllDemiFinalMatchFinishedForTournoi(int idTournoi) throws SQLException {
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(DISTINCT(Matchs.id_match)) FROM Poules_equipes ,Poules ,Matchs"
				+ "      WHERE Poules.id_tournois =" + idTournoi
				+ "    AND Poules.phase = 'DEMI_FINAL'"
				+ "    AND Poules.id_poule =Poules_equipes.id_poule"
				+ "    AND Poules_equipes.id_poule = Matchs.id_poule"
				+ "    AND Matchs.id_equipe_gagnante IS NOT NULL");
		int nbMatch = 0;
		if(rs.next()) {
			nbMatch = rs.getInt(1);
		}
		rs.close();
		st.close();
		return nbMatch == 24;
	}
	
	/**
	 * Ajoute les points d'un match aux équipes
	 * @param match
	 * @param equipeGagnante
	 * @throws SQLException
	 */
	public void ajoutPointsMatchFini(Match match, Equipe equipeGagnante) throws SQLException {
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM Matchs m WHERE m.id_match="+match.getId());
		int idGagnant = 0, idEquipe2 = 0, idEquipe1 = 0;
		while(rs.next()) {
			idGagnant = rs.getInt(1);
			idEquipe1 = rs.getInt(2);
			idEquipe2 = rs.getInt(3);
		}
		rs.close();
		System.out.println(idGagnant);
		System.out.println(idEquipe1);
		System.out.println(idEquipe2);
		//+3 points pour l'equipe gagnante et +1 pour la perdante
		if(idGagnant == idEquipe1) {
			System.out.println(1);
			st.executeUpdate("UPDATE Tournois_equipes SET points=points+3 WHERE id_equipe=" + EquipeManager.getInstance().getIdEquipe(idEquipe1));
			st.executeUpdate("UPDATE Tournois_equipes SET points=points+1 WHERE id_equipe=" + EquipeManager.getInstance().getIdEquipe(idEquipe2));
		} else {
			System.out.println(2);
			st.executeUpdate("UPDATE Tournois_equipes SET points=points+3 WHERE id_equipe=" + EquipeManager.getInstance().getIdEquipe(idEquipe1));
			st.executeUpdate("UPDATE Tournois_equipes SET points=points+1 WHERE id_equipe=" + EquipeManager.getInstance().getIdEquipe(idEquipe2));
		}
		st.close();
	}
}
