package model.ecurie;

import java.sql.SQLException;
import java.util.Objects;

import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.users.Users;

public class Ecurie extends Users {
	
	private TypeEcurie type;

	/**
	 * Constructeur d'une écurie
	 * @param id
	 * @param identifiant
	 * @param mail
	 * @param type
	 */
	public Ecurie(int id, String identifiant, String mail, TypeEcurie type) {
		super(id, identifiant, mail);
		this.type = type;
	}
	
	/**
	 * Getter type d'écurie
	 * @return Type de l'écurie
	 */
	public TypeEcurie getType() {
		return this.type;
	}
	
	/**
	 * Veririfie si l'écurie est déjà dans la base de données
	 * @param identifiant Identifiant de l'écurie
	 * @return Vrai si une écurie possède déjà ce nom
	 * @throws SQLException
	 */
	public static boolean verifDoublon(String identifiant) throws SQLException {
		for(Ecurie e : EcurieManager.getInstance().getEcuries()) {
			if(e.getIdentifiant().equals(identifiant)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifie si l'adresse mail de l'écurie est dans la base de données
	 * @param mail
	 * @return Vrai si une écurie à déjà ce mail
	 * @throws SQLException
	 */
	public static boolean verifDoublonMail(String mail) throws SQLException {
		for(Ecurie a : EcurieManager.getInstance().getEcuries()) {
			if(a.getMail().equals(mail)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return  super.getIdentifiant()+" (" + this.type.toString()+") - "+this.getMail();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ecurie other = (Ecurie) obj;
		return Objects.equals(other.type, type) && Objects.equals(this.getMail(), other.getMail()) && Objects.equals(this.getIdentifiant(), other.getIdentifiant());
	} 
}
