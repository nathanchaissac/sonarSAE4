package model.equipe;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.DBconnec;
import model.ecurie.Ecurie;
import model.ecurie.EcurieManager;
import model.jeux.Jeu;
import model.users.UserManager;

public class EquipeManager {
	
	private static EquipeManager instance;
	private List<Equipe> equipes;
	
	private EquipeManager() {
		this.equipes = new ArrayList<>();
		try {
			setEquipes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized EquipeManager getInstance() {
		if(instance == null) {
			instance = new EquipeManager();
		}
		return instance;
	}
	
	/**
	 * Prend les Equipe de la base de données
	 * @return La list des équipes
	 * @throws SQLException
	 */
	public Equipe[] setEquipes() throws SQLException {
		System.out.println("Chargement des équipes");
		this.equipes.clear();
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT u.mail, u.identifiant,u.id_utilisateur, j.nom, j.id_jeu, j.editeur, j.acronyme_jeu, e.id_equipe FROM Utilisateurs u, Jeux j, Equipes e WHERE u.id_utilisateur = e.id_utilisateur AND e.id_jeu = j.id_jeu");
		while(rs.next()){
			
			Equipe equipe = new Equipe(rs.getInt("u.id_utilisateur"),rs.getString("u.identifiant"),rs.getString("u.mail"), 0, rs.getInt("e.id_equipe"));
			this.equipes.add(equipe);
			this.equipes.get(this.equipes.size()-1).setJeu(new Jeu(rs.getString("nom"),rs.getString("editeur"),rs.getString("acronyme_jeu")));
		}
		rs.close();
		st.close();
		return  this.equipes.toArray(new Equipe[this.equipes.size()]);
	}
	
	/**
	 * Getter liste des équipes
	 * @return List des équipes
	 */
	public List<Equipe> getEquipes(){
		return this.equipes;
	}
	
	/**
	 * Classement général des équipes par ordre décroissant
	 * @param jeu Trie par jeu des équipes
	 * @return Un tableau du classement des équipe pour un jeu
	 * @throws SQLException
	 */
	public Equipe[] classementEquipePourJeu(Jeu jeu) throws SQLException {
		return this.equipes.stream().filter((e) -> e.getJeu().equals(jeu)).sorted((a, b) -> a.compareTo(b)).toArray(Equipe[]::new);
	}
	
	/**
	 * Prend les équipe dans le cache
	 * @return Un tableau des équipes
	 * @throws SQLException
	 */
	public Equipe[] tabEquipe() throws SQLException {
		return this.equipes.toArray(new Equipe[this.equipes.size()]);
	}

	/**
	 * Ajoute uné équipe à la base de données
	 * @param e Ecurie affilié à l'équipe
 	 * @param psswd Mot de passe pour le compte équipe
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public void addEquipe(Equipe e,String psswd) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Utilisateurs (identifiant,mail,type,password) values (?,?, 'Equipe', ?", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, e.getIdentifiant());
		ps.setString(2, e.getMail());
		ps.setString(3, UserManager.getInstance().cryptPass(psswd));
		int nbLigneInsert = ps.executeUpdate();
		int idUser = 0;
		if(nbLigneInsert > 0) {
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				idUser = rs.getInt(1);
				ps.close();
				if(idUser != 0) {
					ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Equipes (id_utilisateur, id_jeu,id_ecurie) values (?,?,?)");
					ps.setInt(1, idUser);
					ps.setInt(2, e.getJeu().getId());
					ps.setInt(3, UserManager.getInstance().getUser().getId());
					ps.executeUpdate();
					rs.close();
					ps.close();
				}
			}
		}
	}
	
	/**
	 * Supprime une équipe de la base de données
	 * @param e Equipe à supprimer
	 * @throws SQLException
	 */
	public void removeEquipe(Equipe e) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("DELETE FROM Utilisateurs WHERE id_utilisateur =?");
		ps.setInt(1, e.getId());
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Prend les équipe de la base de données
	 * @param j Jeu commun
	 * @return Tableau des équipes affilié au même jeu
	 * @throws SQLException
	 */
	public Equipe[] equipeParJeu(Jeu j) throws SQLException {
		return this.equipes.stream().filter((e) -> e.getJeu().equals(j)).toArray(Equipe[]::new);
	}
	
	/**
	 * Prend les équipe du cache
	 * @param ecurie 
	 * @return Tableau des équipe appartenant à la même écurie
	 * @throws SQLException
	 */
	public Equipe[] equipeParEcurie(Ecurie ecurie) throws SQLException {
		List<Equipe> listEquipes = setEquipeParEcurie(ecurie);
		Equipe[] tabEquipes = new Equipe[listEquipes.size()];
		return listEquipes.toArray(tabEquipes);
	}
	
	/**
	 * Prend les informations dans la base de données
	 * @param ecurie 
	 * @return Liste des équipes d'une même écurie
	 * @throws SQLException
	 */
	public List<Equipe> setEquipeParEcurie(Ecurie ecurie) throws SQLException {
		ArrayList<Equipe> resultListe = new ArrayList<Equipe>();
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("SELECT * FROM Equipes e, Ecuries ec, Jeux j, Utilisateurs u WHERE ec.id_utilisateur = e.id_ecurie AND e.id_jeu = j.id_jeu AND u.id_utilisateur = e.id_utilisateur AND ec.id_utilisateur =?");
		ps.setInt(1, ecurie.getId());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Equipe equipe = new Equipe(rs.getInt("id_utilisateur"),rs.getString("identifiant"),rs.getString("mail"), 0, rs.getInt("e.id_equipe"));
			Jeu game = new Jeu(rs.getString("nom"),rs.getString("editeur"),rs.getString("acronyme_jeu"));
			equipe.setJeu(game);
			resultListe.add(equipe);
		}
		rs.close();
		ps.close();
		return resultListe;
	}
	
	/**
	 * Trouve l'id équipe grâce à l'id utilisateur
	 * @param idUser Id utilisateur de l'équipe
	 * @return Id de l'équipe
	 * @throws SQLException
	 */
	public int getIdEquipe(int idUser) throws SQLException {
        Statement st = DBconnec.getInstance().getConnexion().createStatement();
        PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("SELECT id_equipe FROM Equipes WHERE id_utilisateur=?");
        ps.setInt(1, idUser);
        ResultSet rs = ps.executeQuery();
        int idEquipe = -1;
        if(rs.next()) {
        	idEquipe = rs.getInt("id_equipe");
        }
		rs.close();
		st.close();
        return idEquipe;
    }
	
	/**
	 * Modifie le mail dans la base de données
	 * @param mail Nouvelle valeur
	 * @param before Equipe à modifier 
	 * @throws SQLException
	 */
	public void modifEquipe(String mail, Equipe before) throws SQLException {
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE `Utilisateurs` SET `mail`=? WHERE id_utilisateur=?");
		st.setString(1, mail);
		st.setInt(2, before.getId());
		st.execute();
		st.close();
	}

	public Equipe getEquipeId(int idEquipe) {
		Equipe result = null;
		for(Equipe e : this.equipes) {
			if(e.getIdEquipe() == idEquipe) {
				result = e;
			}
		}
		return result;
	}
}
