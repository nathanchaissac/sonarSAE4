package Tests;

import java.sql.SQLException;
import java.util.List;

import model.ecurie.Ecurie;
import model.ecurie.EcurieManager;
import model.equipe.Equipe;
import model.equipe.EquipeManager;

public class TestEquipeManager {

	public static void main(String[] args) throws SQLException {
		List<Ecurie> listE = EcurieManager.getInstance().getEcuries();
		System.out.println(listE);
		System.out.println(EquipeManager.getInstance().equipeParEcurie(listE.get(0)));
		Equipe e[] = EquipeManager.getInstance().equipeParEcurie(listE.get(0));
		for(Equipe eq : e) {
			System.out.println(eq);
		}
	}

}
