package Tests;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;

public class TestJeuManager {
	
	public static void main(String[] args) throws SQLException, ParseException {
		//Jeu jeu1 = new Jeu("Minecraft", "Microsoft", "MC");
		//JeuManager.getInstance().addDBJeu(jeu1);
		//JeuManager.getInstance().removeJeu(jeu1);
		//Jeu jeu1modif = new Jeu("Minecraft", "Microsoft", "MCC");
		//JeuManager.getInstance().modifJeu(jeu1modif, jeu1);
		
		ArrayList<Jeu> listJeu = (ArrayList<Jeu>) JeuManager.getInstance().getJeux();
		System.out.println(listJeu);
		Arbitre[] tab = ArbitreManager.getInstance().getTabArbitresParJeu(listJeu.get(2));
		for(Arbitre a: tab) {
			System.out.println(a);
		}
		ArrayList<Arbitre> listArbitre = (ArrayList<Arbitre>) ArbitreManager.getInstance().getArbitresPourJeu(listJeu.get(0));
		System.out.println(listArbitre);
	}
}
