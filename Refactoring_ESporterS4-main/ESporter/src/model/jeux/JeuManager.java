package model.jeux;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.DBconnec;

public class JeuManager {

	private List<Jeu> jeux;
	private static JeuManager instance;
	
	private JeuManager() {
		this.jeux = new ArrayList<>();
		try {
			setJeux();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized JeuManager getInstance() {
		if(instance==null) {
			instance=new JeuManager();
		}
		return instance;
	}
	
	/**
	 * Getter des jeux
	 * @return Liste des jeux
	 * @throws SQLException
	 */
	public Jeu[] setJeux() throws SQLException {
		System.out.println("Chargement des jeux");
		this.jeux.clear();
		Statement st = DBconnec.getInstance().getConnexion().createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM Jeux ORDER BY nom");
		while(rs.next()){
			this.jeux.add(new Jeu(rs.getString("nom"),rs.getString("editeur"),rs.getString("acronyme_jeu")));
			this.jeux.get(this.jeux.size()-1).setId(rs.getInt("id_jeu"));
		}
		rs.close();
		st.close();
		return this.jeux.toArray(new Jeu[this.jeux.size()]);
	}
	
	public List<Jeu> getJeux(){
		return this.jeux;
	}
	
	/**
	 * Getter des jeux
	 * @return Tableau des jeux
	 * @throws SQLException
	 */
	public Jeu[] getTabJeu() throws SQLException {
		return this.jeux.toArray(new Jeu[this.jeux.size()]);
	}
	
	/**
	 * Getter des acronymes
	 * @return Tableau des acronymes des jeux
	 * @throws SQLException
	 */
	public String[] tabNomAccronyme() throws SQLException {
		String[] result = new String[this.jeux.size()];
		for(int i = 0; i < this.jeux.size(); i++) {
			result[i] = this.jeux.get(i).getAcronyme();
		}
		return result;
	}
	
	/**
	 * Ajoute un jeu à la base de données
	 * @param j Jeu à ajouter
	 * @throws SQLException
	 */
	public void addDBJeu(Jeu j) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("INSERT INTO Jeux (nom,editeur,acronyme_jeu) values (?,?,?)");
		ps.setString(1, j.getNom());
		ps.setString(2, j.getEditeur());
		ps.setString(3, j.getAcronyme());
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Supprime un jeu de la base de données
	 * @param game Jeu à supprimer
	 * @throws SQLException
	 */
	public void removeJeu(Jeu game) throws SQLException {
		PreparedStatement ps = DBconnec.getInstance().getConnexion().prepareStatement("DELETE FROM Jeux WHERE Jeux.id_jeu =?");
		ps.setInt(1, game.getId());
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * Modifier les informations du jeu
	 * @param game Nouvelle valeur du jeu
	 * @param last Ancienne valeur du jeu
	 * @throws SQLException
	 */
	public void modifJeu(Jeu game, Jeu last) throws SQLException {
		PreparedStatement st = DBconnec.getInstance().getConnexion().prepareStatement("UPDATE `Jeux` SET `nom`=?,`editeur`=?,`acronyme_jeu`=? WHERE `nom`=?");
		st.setString(1, game.getNom());
		st.setString(2, game.getEditeur());
		st.setString(3, game.getAcronyme());
		st.setString(4, last.getNom());
		st.executeUpdate();
		st.close();
	}
	
	/**
	 * 
	 * @return Dictionnaire des jeux <Jeu, Id>
	 * @throws SQLException
	 */
	public Map<Jeu, Integer> dicoJeu() throws SQLException {
		Map<Jeu, Integer> dico = new HashMap<Jeu, Integer>();
		for(Jeu jeu : this.jeux) {
			dico.put(jeu, jeu.getId());
		}
		return dico;
	}
	
	/**
	 * 
	 * @return Dictionnaire des jeux <Id, Jeu>
	 * @throws SQLException
	 */
	public Map<Integer, Jeu> dicoJeuInv() throws SQLException {
		Map<Integer, Jeu> dico = new HashMap<Integer, Jeu>();
		for(Jeu jeu : this.jeux) {
			dico.put(jeu.getId(), jeu);
		}
		return dico;
	}
	
	/**
	 * Retrouve un jeu grâce à son acronyme
	 * @param acro Permet de retrouver le jeu
	 * @return ID du jeu
	 * @throws SQLException
	 */
	public int getIdJeu(String acro) throws SQLException {
		int id = 0;
		for(Jeu j : this.jeux) {
			if(j.getAcronyme().equals(acro)) {
				id = getIdJeu(j);
			}
		}
		return id;
	}
	
	/**
	 * Getter de l'id du jeu
	 * @param j Jeu à retrouver
	 * @return Id du jeu
	 * @throws SQLException
	 */
	public int getIdJeu(Jeu j) throws SQLException {
		return  dicoJeu().get(j);
	}
	
	/**
	 * Getter du jeu
	 * @param id Id du jeu à retrouver (String)
	 * @return Jeu
	 * @throws SQLException
	 */
	public Jeu getJeuParId(String id) throws SQLException {
		return dicoJeuInv().get(Integer.parseInt(id));
	}
	
	/**
	 * Getter du jeu
	 * @param id Id du jeu à retrouver (int)
	 * @return Jeu
	 * @throws SQLException
	 */
	public Jeu getJeuParIdInt(int id) throws SQLException {
		return dicoJeuInv().get(id);
	}
}
