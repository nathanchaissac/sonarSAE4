package Tests;

import java.sql.SQLException;
import java.text.ParseException;

import model.tournoi.TournoiManager;

public class TestTournoiManager {

	public static void main(String[] args) throws SQLException, ParseException {
		
		TournoiManager.getInstance().getTabTournoi();
		
	}

}
