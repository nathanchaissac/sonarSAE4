package Tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.match.MatchManager;
import model.match.Match;
import model.users.UserManager;

public class TestAux {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		List<Match> lstMatch = new ArrayList<Match>();
		Match[] match = MatchManager.getInstance().getTabMatchPourArbitre(UserManager.getInstance().getUser().getId());
		for(int i= 0; match[i]!=null;i++) {
			lstMatch.add(match[i]);
		}
		System.out.println(match.length);
		match = lstMatch.toArray(new Match[lstMatch.size()]);
		System.out.println(match.length);
		for(int i= 0;i< match.length;i++) {
			System.out.println(match[i]);
			System.out.println(match[i].getIdArbitre());
		}
	}

}
