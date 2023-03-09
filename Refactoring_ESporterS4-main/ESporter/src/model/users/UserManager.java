package model.users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DBconnec;
import model.arbitre.Arbitre;
import model.ecurie.Ecurie;
import model.ecurie.TypeEcurie;
import model.equipe.Equipe;
import model.jeux.Jeu;

public class UserManager {
	
	private Users user;
	private static UserManager instance;
	
	private UserManager() {}
	
	public static synchronized UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	/**
	 * Système d'autentification
	 * @param login
	 * @param pass
	 * @return Vrai si la connexion à réussi
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean tryConnect(String login, String pass) throws SQLException, NoSuchAlgorithmException{
		Connection conx = DBconnec.getInstance().getConnexion();
		
		boolean success = false;
		
		Statement state = conx.createStatement();
		
		ResultSet rs = state.executeQuery("SELECT `id_utilisateur`, `identifiant`, `type`, `mail` FROM `Utilisateurs` WHERE `identifiant`='" + login + "' AND `password`='" + cryptPass(pass) + "'");
		
		if(rs.next()) {
			success = true;
			switch(typesUser.valueOf(rs.getString("type"))) {
				case Arbitre:
					this.user = new Arbitre(rs.getInt("id_utilisateur"), rs.getString("identifiant"), rs.getString("mail"), prenomArbitre(rs.getInt("id_utilisateur")), jeuArbitre(rs.getInt("id_utilisateur")));
					break;
				case ESporter:
					this.user = new ESporter(rs.getInt("id_utilisateur"), rs.getString("identifiant"), rs.getString("mail"));
					break;
				case Equipe:
					
					PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("SELECT * FROM Jeux J, Utilisateurs U, Equipes E WHERE E.id_utilisateur =?");
					ps.setInt(1, rs.getInt("id_utilisateur"));
					ResultSet rsEquipe = ps.executeQuery();
					Equipe e = null;
					if(rsEquipe.next()) {
						e = new Equipe(rs.getInt("id_utilisateur"), rs.getString("identifiant"), rs.getString("mail"), 0, rsEquipe.getInt("id_equipe"));

						Jeu game = new Jeu(rsEquipe.getString("nom"),rsEquipe.getString("editeur"),rsEquipe.getString("acronyme_jeu"));
						game.setId(rsEquipe.getInt("id_jeu"));
						e.setJeu(game);
						
					}
					this.user = e;
					break;
				case Ecurie:
					this.user = new Ecurie(rs.getInt("id_utilisateur"), rs.getString("identifiant"), rs.getString("mail"), typeEcurie(rs.getInt("id_utilisateur")));
					break;
				default:
					success = false;
			}
		}
		return success;
		
		
	}
	
	/**
	 * Getter de l'utilisateur
	 * @return Utilisateur courrant
	 */
	public Users getUser() {
		return this.user;
	}
	
	/**
	 * Type de l'écurie
	 * @param id 
	 * @return Type de l'écurie
	 */
	private TypeEcurie typeEcurie(int id) {
		try {
			Connection conx = DBconnec.getInstance().getConnexion();
			Statement state = conx.createStatement();
			ResultSet rs = state.executeQuery("SELECT `type` FROM `Ecuries` WHERE `id_utilisateur` =" + id);

			
			if(rs.next()) {
				return TypeEcurie.valueOf(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Getter du jeu pour l'arbitre
	 * @param id Id arbitre
	 * @return Nom du jeu
	 */
	private String jeuArbitre(int id) {
		try {
			Connection conx = DBconnec.getInstance().getConnexion();
			Statement state = conx.createStatement();
			ResultSet rs = state.executeQuery("SELECT `nom` FROM `Arbitres` WHERE `id_utilisateur` = " + id);
			
			if(rs.next()) {
				return rs.getString("nom");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Getter du prénom de l'arbitre
	 * @param id Id arbitre
	 * @return Prenom de l'arbitre
	 */
	private String prenomArbitre(int id) {
		try {
			Connection conx = DBconnec.getInstance().getConnexion();
			Statement state = conx.createStatement();
			ResultSet rs = state.executeQuery("SELECT `nom` FROM `Arbitres` WHERE `id_utilisateur` = " + id);
			
			if(rs.next()) {
				return rs.getString("nom");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Crypter un mot de passe
	 * @param pass Mot de passe en claire
	 * @return Mot de passe crypté
	 * @throws NoSuchAlgorithmException
	 */
	public String cryptPass(String pass) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
	}
	
	
	
}
