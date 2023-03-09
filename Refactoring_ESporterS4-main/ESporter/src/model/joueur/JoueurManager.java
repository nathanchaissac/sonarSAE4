package model.joueur;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import model.DBconnec;
import model.equipe.Equipe;

public class JoueurManager {
	
	private static JoueurManager instance;
	private List<Joueur> joueurs;
	
	private JoueurManager() {
		this.joueurs = new ArrayList<>();
		try {
			setJoueurs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static synchronized JoueurManager getInstance() {
		if(instance == null) {
			instance = new JoueurManager();
		}
		return instance;
	}
	
	/**
	 * Getter des joueurs
	 * @return Liste de tous les joueurs
	 * @throws SQLException
	 */
	public List<Joueur> setJoueurs() throws SQLException{
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM Joueurs ORDER BY pseudo");
		while(rs.next()) {
			this.joueurs.add(new Joueur(rs.getString("pseudo"), rs.getDate("naissance"), rs.getString("prenom"), rs.getInt("id_equipe"), rs.getInt("id_joueur")));
		}
		rs.close();
		st.close();
		return this.joueurs;
	}
	
	public List<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	/**
	 * Getter des joueurs
	 * @return Tableau de tous les joueurs
	 * @throws SQLException
	 */
	public Joueur[] getTabJoueur() throws SQLException {
		return this.joueurs.toArray(new Joueur[this.joueurs.size()]);
	}
	
	/**
	 * Getter des joueurs pour une équipe
	 * @param equipe Equipe commune
	 * @return Liste des équipes pour un même jeu
	 * @throws SQLException
	 */
	public List<Joueur> getJoueurPourEquipe(Equipe equipe) throws SQLException{
		return this.joueurs.stream().filter((e) -> e.getIdEquipe() == equipe.getId()).collect(Collectors.toList());
	}
	
	/**
	 * Getter des joueurs pour une équipe
	 * @param equipe Equipe commune
	 * @return Tableau des équipes pour un même jeu
	 * @throws SQLException
	 */
	public Joueur[] getTabJoueurPourEquipe(Equipe equipe) throws SQLException {
		List<Joueur> listJoueurs = JoueurManager.getInstance().getJoueurPourEquipe(equipe);
		return listJoueurs.toArray(new Joueur[listJoueurs.size()]);
	}
	
	/**
	 * Ajout d'une joueur dans la base de données
	 * @param pseudo
	 * @param naissance
	 * @param prenom
	 * @param idEquipe
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public void addJoueur(String pseudo,Date naissance,String prenom,int idEquipe) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Joueurs (pseudo,naissance,prenom,id_equipe) values (?,?,?,?)");
		ps.setString(1, pseudo);
		ps.setString(2, naissance.toString());
		ps.setString(3, prenom);
		ps.setInt(4, idEquipe);
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Supprime un joueur de la base de données
	 * @param joueur Joueur à supprimer
	 * @throws SQLException
	 */
	public void removeJoueur(Joueur joueur) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("DELETE FROM Joueurs WHERE Joueurs.id_joueur =?");
		ps.setInt(1, joueur.getIdJoueur());
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Modifie les informations du joueur
	 * @param joueur Nouvelle version du joueur
	 * @param last Ancienne version du joueur
	 * @throws SQLException
	 */
	public void modifJoueur(Joueur joueur, Joueur last) throws SQLException {
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE `Joueurs` SET `pseudo`=?,`naissance`=?,`prenom`=?, `id_equipe`=? WHERE `pseudo`=?");
		st.setString(1, joueur.getPseudo());
		st.setDate(2, formatDate(joueur.getDateNaissance().replaceAll("/", "-")));
		st.setString(3, joueur.getPrenom());
		st.setInt(4, joueur.getIdEquipe());
		st.setString(5, last.getPseudo());
		st.execute();
		st.close();
	}
	
	/**
	 * Changer le format d'une date
	 * @param oldDate Date à modifier
	 * @return Une nouvelle date avec le bon format
	 */
	private Date formatDate(String oldDate) {
		String[] compacted = oldDate.split("-");
		if(compacted.length == 3) {
			return Date.valueOf(compacted[2] + "-" + compacted[1] + "-" + compacted[0]);
		}
		return null;
	}

	/**
	 * Vérifie si l'âge est supérieur ou égal à 16 ans
	 * @param naissance Date de naissance
	 * @return Vrai si l'âge est supérieur ou égal à 16 ans
	 */
	public boolean verifAge(Calendar naissance) {
		Calendar now = Calendar.getInstance();
		int age = now.get(Calendar.YEAR) - naissance.get(Calendar.YEAR);
		if(now.get(Calendar.MONTH) < naissance.get(Calendar.MONTH)) {
			age--;
		}
		else if(now.get(Calendar.MONTH) == naissance.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < naissance.get(Calendar.DAY_OF_MONTH)) {
			age--;
		}
		return age >= 16;
	}

}
