package Tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.match.Match;
import model.match.MatchManager;
import model.users.UserManager;

public class TestArbitre {
	
	public static void main(String[] args) throws SQLException {
		List<Jeu> jeux = JeuManager.getInstance().getJeux();
		List<Arbitre> arbitres = ArbitreManager.getInstance().getListArbitres();
		
		System.out.println(jeux);
		System.out.println(arbitres);
		
		List<Arbitre> arbitreLol = ArbitreManager.getInstance().getArbitresPourJeu(jeux.get(1));
		
		System.out.println(arbitreLol);
	}

}
