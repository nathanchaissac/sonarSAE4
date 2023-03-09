package Tests;

import java.sql.SQLException;
import java.util.List;

import model.ecurie.Ecurie;
import model.ecurie.EcurieManager;

public class TestEcurieManager {

	public static void main(String[] args) throws SQLException {
		List<Ecurie> test = EcurieManager.getInstance().getEcuries();		
		for (int i=0; i <test.size(); i++) {
			System.out.println(test.get(i));
		}
	}

}
