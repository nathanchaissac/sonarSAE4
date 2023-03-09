package model.arbitre;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.DBconnec;
import model.equipe.Equipe;
import model.jeux.Jeu;
import model.match.MatchManager;
import model.match.Match;
import model.users.UserManager;

public class ArbitreManager {
	
	private static ArbitreManager instance;
	private List<Arbitre> arbitres;
	
	private ArbitreManager() {
		this.arbitres = new ArrayList<>();
		try {
			setArbitres();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ArbitreManager getInstance() {
		if(instance == null) {
			instance = new ArbitreManager();
		}
		return instance;
	}
	
	/**
	 * Setter liste d'arbitre
	 * @return Tableau des arbitres
	 * @throws SQLException
	 */
	public Arbitre[] setArbitres() throws SQLException{
		this.arbitres.clear();
		System.out.println("Chargement des arbitres");
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT a.`id_arbitre`, a.id_jeu, a.`nom`, u.mail, u.identifiant FROM `Arbitres` a, `Utilisateurs` u WHERE a.id_utilisateur = u.id_utilisateur ORDER BY nom");
		while(rs.next()) {
			this.arbitres.add(new Arbitre(rs.getInt("id_arbitre"), rs.getString("identifiant"), rs.getString("mail"), rs.getString("nom"), rs.getString("id_jeu")));
		}
		rs.close();
		st.close();
		return this.arbitres.toArray(new Arbitre[this.arbitres.size()]);
	}
	
	/**
	 * Getter des arbitres
	 * @return Liste des arbitres
	 */
	public List<Arbitre> getListArbitres(){
		return this.arbitres;
	}
	
	/**
	 * Getter arbitre d'un jeu
	 * @param jeu
	 * @return Liste des arbitre d'un même jeu
	 * @throws SQLException
	 */
	public List<Arbitre> getArbitresPourJeu(Jeu jeu) throws SQLException{
		return this.arbitres.stream().filter((e) -> e.getIdJeu() == jeu.getId()).collect(Collectors.toList());
	}
	
	/**
	 * Getter des arbitres
	 * @return Tableau des arbitres
	 * @throws SQLException
	 */
	public Arbitre[] getTabArbitres() throws SQLException {
		return this.arbitres.toArray(new Arbitre[this.arbitres.size()]);
	}
	
	/**
	 * Getter des arbitres
	 * @param jeu
	 * @return Tableau d'arbitres d'un même jeu
	 * @throws SQLException
	 */
	public Arbitre[] getTabArbitresParJeu(Jeu jeu) throws SQLException {
		List<Arbitre> listArbitres = ArbitreManager.getInstance().getArbitresPourJeu(jeu);
		return listArbitres.toArray(new Arbitre[listArbitres.size()]);
	}
	
	/**
	 * Ajoute un arbitre à la base de données
	 * @param identifiant
	 * @param mail
	 * @param mdp
	 * @param prenom
	 * @param id_jeu
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public void addArbitre(String identifiant, String mail, String mdp, String prenom, int id_jeu) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Utilisateurs (identifiant,mail,password,type) values (?,?,?,'Arbitre')", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, identifiant);
		ps.setString(2, mail);
		ps.setString(3, UserManager.getInstance().cryptPass(mdp));
		int nbLigneInser = ps.executeUpdate();
		
		int idUser = 0;
		if(nbLigneInser > 0) {
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				idUser = rs.getInt(1);
				ps.close();
				if(idUser != 0) {
					ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Arbitres (id_utilisateur,nom, id_jeu) values (?,?,?)");
					ps.setInt(1, idUser);
					ps.setString(2,  prenom);
					ps.setInt(3, id_jeu);
					ps.executeUpdate();
					rs.close();
					ps.close();
				}
			}
		}	
	}
	
	/**
	 * Supprime un arbitre de la base de données
	 * @param Identifiant
	 * @throws SQLException
	 */
	public void removeArbitre(String identifiant) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("DELETE FROM Utilisateurs WHERE Utilisateurs.identifiant = ?");
		ps.setString(1, identifiant);
		ps.execute();
		ps.close();
	}
	
	/**
	 * Modifie un arbitre de la base de données
	 * @param identifiant Permet d'identifier l'arbitre à modifier
	 * @param password
	 * @param mail
	 * @param idArbitre
	 * @param prenom
	 * @param idJeu
	 * @throws SQLException
	 */
	public void ModifDBArbitre(String identifiant, String password, String mail, int idArbitre, String prenom, int idJeu) throws SQLException {
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE `Utilisateurs` u, `Arbitres` a SET u.`identifiant`=?, u.`password`=? ,u.`mail`=? , a.nom=?, a.id_jeu=? WHERE u.id_utilisateur = a.id_utilisateur AND a.id_arbitre =? ");
		st.setString(1, identifiant);
		st.setString(2, password);
		st.setString(3, mail);
		st.setString(4, prenom);
		st.setInt(5, idJeu);
		st.setInt(6, idArbitre);
		st.execute();
		st.close();
	}
	
	
	/**
	 * Modification de l'arbitre dans la base de données
	 * @param identifiant
	 * @param mail
	 * @param idArbitre Arbitre à modifier
	 * @param prenom
	 * @param idJeu
	 * @throws SQLException
	 */
	public void ModifDBArbitre(String identifiant, String mail, int idArbitre, String prenom, int idJeu) throws SQLException {
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE `Utilisateurs` u, `Arbitres` a SET u.`identifiant`=?, u.`mail`=? , a.nom=?, a.id_jeu=? WHERE u.id_utilisateur = a.id_utilisateur AND a.id_arbitre =? ");
		st.setString(1, identifiant);
		st.setString(2, mail);
		st.setString(3, prenom);
		st.setInt(4, idJeu);
		st.setInt(5, idArbitre);
		st.execute();
		st.close();
	}

	/**
	 * Permet à un arbitre de definir un gagnant
	 * @param match Match arbitré
	 * @param equipe Equipe gagnante
	 * @throws SQLException
	 */
	public void ajoutGagnant(Match match, Equipe equipe) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE Matchs set id_equipe_gagnante = ? WHERE id_match = ?");
		ps.setInt(1, equipe.getId());
		ps.setInt(2, match.getId());
		ps.execute();
		ps.close();
		MatchManager.getInstance().ajoutPointsMatchFini(match, equipe);
		match.setGagnant(equipe);
	}
	
	/**
	 * 
	 * @param id Id utilisateur
	 * @return Id arbitre
	 * @throws SQLException
	 */
	public int getIDArbitresParID(int id) throws SQLException{
		int IDAbrt = 0;
		
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("SELECT a.id_arbitre FROM Arbitres a WHERE a.id_utilisateur = ? ORDER BY nom");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			IDAbrt = rs.getInt("id_arbitre");
		}
		rs.close();
		rs.close();
		return IDAbrt;
	}
}
