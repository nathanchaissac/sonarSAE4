package model.ecurie;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DBconnec;
import model.users.UserManager;

public class EcurieManager {
	private List<Ecurie> ecuries;
	private static EcurieManager instance;
	
	private EcurieManager() {
		this.ecuries = new ArrayList<>();
		try {
			setEcuries();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized EcurieManager getInstance() {
		if(instance==null) {
			instance=new EcurieManager();
		}
		return instance;
	}
	
	/**
	 * Prend les écurie de la base de données
	 * @return Liste d'écuries
	 * @throws SQLException
	 */
	public Ecurie[] setEcuries() throws SQLException {
		this.ecuries.clear();
		System.out.println("Chargement des écuries");
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT Utilisateurs.*,Ecuries.type FROM Ecuries,Utilisateurs WHERE Utilisateurs.type = 'Ecurie' AND Ecuries.id_utilisateur = Utilisateurs.id_utilisateur");
            while(rs.next()){
				if(rs.getString("Ecuries.type").equals("Associative")){
					this.ecuries.add(new Ecurie(rs.getInt("Utilisateurs.id_utilisateur"),rs.getString("Utilisateurs.identifiant"),rs.getString("Utilisateurs.mail"), TypeEcurie.Associative));
				}else {
					this.ecuries.add(new Ecurie(rs.getInt("Utilisateurs.id_utilisateur"),rs.getString("Utilisateurs.identifiant"),rs.getString("Utilisateurs.mail"), TypeEcurie.Professionnelle));
				}	
            }
		rs.close();
		st.close();
		return this.ecuries.toArray(new Ecurie[this.ecuries.size()]);
	}
	
	public List<Ecurie> getEcuries(){
		return this.ecuries;
	}
	
	/**
	 * Prend les écurie dans le cache
	 * @return Tableau d'écuries
	 * @throws SQLException
	 */
	public Ecurie[] getTabEcuries() throws SQLException {
		return this.ecuries.toArray(new Ecurie[this.ecuries.size()]);
	}
	
	/**
	 * 
	 * @param idUtilisateur
	 * @return L'écurie affilié au joueur
	 */
	public Ecurie getEcurieIdUtilisateur(int idUtilisateur) {
		return this.ecuries.stream().filter(e -> e.getId() == idUtilisateur).findFirst().get();
	}
	

	/**
	 * Ajoute une écurie à la base de données
	 * @param identifiant Permet de se connecter à l'espace écurie
	 * @param mail 
	 * @param typeE 
	 * @param mdp Non crypté, le cryptage est inclu dans la méthode
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public void addDBEcurie(String identifiant,String mail,String typeE,String mdp) throws SQLException, NoSuchAlgorithmException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Utilisateurs (identifiant,mail,password,type) values (?,?,?,'Ecurie')", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, identifiant);
		ps.setString(2, mail);
		ps.setString(3, UserManager.getInstance().cryptPass(mdp));
		int nbLigneInsert = ps.executeUpdate();
			
		int idUser = 0;
		if(nbLigneInsert > 0) {
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				idUser = rs.getInt(1);
				ps.close();
				if(idUser != 0) {
					ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Ecuries (id_utilisateur,type) values (?,?)");
					ps.setInt(1, idUser);
					ps.setString(2,  typeE);
					ps.executeUpdate();
					rs.close();
					ps.close();
				}
			}
		}
	}
	
	/**
	 * Supprime l'écurie de la base de données
	 * @param identifiant Permet d'identifier l'écurie à supprimer
	 * @throws SQLException
	 */
	public void removeEcurie(String identifiant) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("DELETE FROM Utilisateurs WHERE Utilisateurs.identifiant = ?");
		ps.setString(1, identifiant);
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Modifie les informations de l'arbitre directement dans la base de données
	 * @param identifiant Nouvelle valeur
	 * @param mail Nouvelle valeur
	 * @param type Nouvelle valeur
	 * @param idUtilisateur Permet d'identifier l'écurie à modifier
	 * @throws SQLException
	 */
	public void ModifDBEcurie(String identifiant, String mail, String type,int idUtilisateur) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE Utilisateurs u, Ecuries e SET u.identifiant=?, u.mail=?, e.type=? WHERE e.id_utilisateur = u.id_utilisateur AND u.id_utilisateur=?");
		ps.setString(1, identifiant);
		ps.setString(2, mail);
		ps.setString(3, type);
		ps.setInt(4, idUtilisateur);
		ps.execute();
		ps.close();
	}
	
	/**
	 * Modifie les informations de l'arbitre directement dans la base de données
	 * @param identifiant Nouvelle valeur
	 * @param password
	 * @param mail Nouvelle valeur
	 * @param type Nouvelle valeur
	 * @param idUtilisateur Permet d'identifier l'écurie à modifier
	 * @throws SQLException
	 */
	public void ModifDBEcurie(String identifiant, String password, String mail, String type,int idUtilisateur) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE Utilisateurs u, Ecuries e SET u.identifiant=?, u.`password`=?, u.mail=?, e.type=? WHERE e.id_utilisateur = u.id_utilisateur AND u.id_utilisateur=?");
		ps.setString(1, identifiant);
		ps.setString(2, password);
		ps.setString(3, mail);
		ps.setString(4, type);
		ps.setInt(5, idUtilisateur);
		ps.execute();
		ps.close();
	}
	

}
