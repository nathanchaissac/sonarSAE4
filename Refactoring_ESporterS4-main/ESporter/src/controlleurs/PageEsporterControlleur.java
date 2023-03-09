package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import model.MajListe;
import model.UtilsControlleurs;
import model.arbitre.Arbitre;
import model.ecurie.Ecurie;
import model.jeux.Jeu;
import model.tournoi.Tournoi;
import model.tournoi.TournoiManager;
import views.PageAccueil;
import views.PageConnexion;
import views.PageEspaceArbitre;
import views.PageEspaceEsporter;
import views.PageGererTournoi;
import views.PageGestionArbitre;
import views.PageGestionEcurie;
import views.PageGestionJeu;

public class PageEsporterControlleur extends MouseAdapter {
	private PageEspaceEsporter page;
	private PageAccueil accueil;

	public PageEsporterControlleur(PageEspaceEsporter page) {
		this.page = page;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			switch (btn.getText()) {
			case "Ajouter un tournoi":
				PageGererTournoi ajoutTournois;
				try {
					ajoutTournois = new PageGererTournoi("Ajouter Tournoi", null, this.page);
					ajoutTournois.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case "Ajouter un jeu":
				PageGestionJeu ajoutJeu = new PageGestionJeu("Ajouter un jeu", null, this.page);
				ajoutJeu.setVisible(true);
				break;
			case "Ajouter un arbitre":
				try {
					PageGestionArbitre ajoutArbitre = new PageGestionArbitre("Ajouter un arbitre", null, this.page);
					ajoutArbitre.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case "Ajouter une écurie":
				PageGestionEcurie ajoutEcurie = new PageGestionEcurie("Ajouter une Ecurie", null, this.page);
				ajoutEcurie.setVisible(true);
				break;
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
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				this.page.dispose();
				break;
			}
		} else if (e.getSource() instanceof JList) {
			JList lst = (JList) e.getSource();
			if (lst.getSelectedValue() instanceof Tournoi) {
				int result = UtilsControlleurs
						.dispConfirmSupprDialogBox("Etes-vous sûr de vouloir supprimer le tournoi ?", this.page);
				if (result == JOptionPane.YES_OPTION) {
					try {
						TournoiManager.getInstance().removeTournoi((Tournoi) lst.getSelectedValue());
						MajListe.MAJListGenerique(TournoiManager.getInstance().getTabTournoi(), page.listTournoi);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (lst.getSelectedValue() instanceof Ecurie) {
				PageGestionEcurie modifEcurie = new PageGestionEcurie("Modification de l'écurie :",
						(Ecurie) this.page.listEcuries.getSelectedValue(), this.page);
				modifEcurie.setVisible(true);
			}
			if (lst.getSelectedValue() instanceof Jeu) {
				PageGestionJeu modifJeu = new PageGestionJeu("Modification du jeu :",
						(Jeu) this.page.listJeux.getSelectedValue(), this.page);
				modifJeu.setVisible(true);
			}
			if (lst.getSelectedValue() instanceof Arbitre) {
				PageGestionArbitre modifArbitre;
				try {
					modifArbitre = new PageGestionArbitre("Modification de l'arbitre :",
							this.page.listArbitre.getSelectedValue(), this.page);
					modifArbitre.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		}

	}

}
