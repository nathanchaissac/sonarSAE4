package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;

import model.arbitre.Arbitre;
import model.ecurie.Ecurie;
import model.equipe.Equipe;
import model.users.ESporter;
import model.users.UserManager;
import views.PageAccueil;
import views.PageEspaceArbitre;
import views.PageEspaceEcurie;
import views.PageEspaceEquipe;
import views.PageEspaceEsporter;

public class AccueilControlleurs extends MouseAdapter  {
	//commentaire pour push
	private PageAccueil thispage;
	
	public AccueilControlleurs(PageAccueil acc) {
		this.thispage = acc;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		switch (btn.getText()) {
		case "Mon espace":
			if(UserManager.getInstance().getUser() instanceof Arbitre) {
				creationPageEspaceArbitre();
			} else if (UserManager.getInstance().getUser() instanceof ESporter) {
				creationPageEspaceEsporter();
			} else if (UserManager.getInstance().getUser() instanceof Ecurie) {
				creationPageEcurie();
			} else if(UserManager.getInstance().getUser() instanceof Equipe) {
				creationPageEspaceEquipe();
			}
		}
	}

	private void creationPageEspaceEquipe() {
		try {
			PageEspaceEquipe equipe = new PageEspaceEquipe();
			equipe.setVisible(true);
			this.thispage.dispose();
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void creationPageEcurie() {
		try {
			PageEspaceEcurie ecurie = new PageEspaceEcurie();
			ecurie.setVisible(true);
			this.thispage.dispose();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void creationPageEspaceEsporter() {
		try {
			PageEspaceEsporter esporter = new PageEspaceEsporter();
			esporter.setVisible(true);
			this.thispage.dispose();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void creationPageEspaceArbitre() {
		PageEspaceArbitre arbitre;
		try {
			arbitre = new PageEspaceArbitre();
			arbitre.setVisible(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		this.thispage.dispose();
	}
}
