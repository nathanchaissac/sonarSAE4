package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import model.UtilsControlleurs;
import model.ecurie.Ecurie;
import model.equipe.Equipe;
import model.users.UserManager;
import views.PageAccueil;
import views.PageConnexion;
import views.PageEspaceArbitre;
import views.PageEspaceEcurie;
import views.PageGestionEquipe;

public class PageEcurieControlleurs extends MouseAdapter {
	private PageEspaceEcurie page;
	private PageAccueil accueil;
	private PageGestionEquipe ajout;

	public PageEcurieControlleurs(PageEspaceEcurie page) {
		this.page = page;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			switch (btn.getText()) {
			case "Déconnexion":
				int result = UtilsControlleurs.dispConfirmDecoDialogBox("Etes-vous sûr de vouloir vous déconnecter ?",
						this.page);
				if (result == JOptionPane.YES_OPTION) {
					PageConnexion con = new PageConnexion();
					con.setVisible(true);
					this.page.dispose();
				}
				break;
			case "":
				try {
					accueil = new PageAccueil();
					accueil.setVisible(true);
					this.page.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case "Ajouter une équipe":
				try {
					ajout = new PageGestionEquipe("Ajouter ", null, this.page,
							(Ecurie) UserManager.getInstance().getUser());
					ajout.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			}
		} else {
			JList lst = (JList) e.getSource();
			try {
				ajout = new PageGestionEquipe("Modifier ", (Equipe) lst.getSelectedValue(), page,
						(Ecurie) UserManager.getInstance().getUser());
				ajout.getPassField().setVisible(false);
				ajout.getMailField().setText(((Equipe) lst.getSelectedValue()).getMail());
				ajout.getComboBoxJeu().setSelectedItem(((Equipe) lst.getSelectedValue()).getJeu());
				ajout.getPassText().setVisible(false);
				ajout.setVisible(true);
				ajout.getComboBoxJeu().setEnabled(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}