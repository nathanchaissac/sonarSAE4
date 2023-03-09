package model.users;

public abstract class Users {
	
	private String identifiant;
	private String mail;
	private int id;

	/**
	 * Constructeur d'un utilisateur
	 * @param id Id de l'utilisateur
	 * @param identifiant Nom de l'utilisateur
	 * @param mail Mail de l'utilisateur
	 */
	public Users(int id, String identifiant, String mail) {
		this.id = id;
		this.identifiant = identifiant;
		this.mail = mail;
	}
	
	/**
	 * Getter de l'id
	 * @return Id utilisateur
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter du mail
	 * @return Mail de l'utilisateur
	 */
	public String getMail() {
		return this.mail;
	}
	
	/**
	 * Getter identifiant 
	 * @return Identifiant de l'utilisateur
	 */
	public String getIdentifiant() {
		return this.identifiant;
	}
	
	@Override
	public String toString() {
		return this.mail + " - " + this.identifiant.toUpperCase();
	}
}
