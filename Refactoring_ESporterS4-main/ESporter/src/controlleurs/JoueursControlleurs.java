package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.MajListe;
import model.UtilsControlleurs;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.joueur.Joueur;
import model.joueur.JoueurManager;
import views.PageAjoutJoueur;
import views.PageEspaceArbitre;
import views.PageEspaceEquipe;

public class JoueursControlleurs extends MouseAdapter {

	private PageAjoutJoueur page;
	private Equipe equipe;
	private PageEspaceEquipe esp;

	public JoueursControlleurs(PageEspaceEquipe esp, PageAjoutJoueur page, Equipe equipe) {
		this.page = page;
		this.equipe = equipe;
		this.esp = esp;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		if (this.page.getJoueur() == null) {
			if (btn.getText().equals("Valider")) {
				ajouter();
			} else {
				this.page.dispose();
			}
		} else {
			if (btn.getText().equals("Valider")) {
				modifier();
			}
			if (btn.getText().equals("Supprimer")) {
				supprimer();
			}
			if (btn.getText().equals("Annuler")) {
				this.page.dispose();
			}
		}
	}

	private void ajouter() {
		Calendar dateNaissance = (Calendar) this.page.getDateField().getModel().getValue();
		if (areAllFieldsFilled() && !this.page.getDateField().isValid()) {
			if (!JoueurManager.getInstance().verifAge(dateNaissance)) {
				UtilsControlleurs.dispErrorDialogBox("Le joueur doit avoir au moins 18 ans.", this.page);
			} else {
				if (!Joueur.verifDoublon(equipe, this.page.getPseudoField().getText())) {
					try {
						int idEquipe = EquipeManager.getInstance().getIdEquipe(this.equipe.getId());
						if (idEquipe != -1) {
							JoueurManager.getInstance().addJoueur(this.page.getPseudoField().getText(),
									new java.sql.Date(dateNaissance.getTimeInMillis()),
									this.page.getPrenomField().getText(), idEquipe);
							refreshListAndClose();
						}
					} catch (NoSuchAlgorithmException | SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					UtilsControlleurs.dispErrorDialogBox("Ce pseudo de joueur existe déjà, veuillez entrer un autre pseudo.", this.page);
				}
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}

	}

	private void supprimer() {
		try {
			int result = UtilsControlleurs.dispConfirmSupprDialogBox("Etes-vous sûr de vouloir supprimer le joueur ?", this.page);
			if (result == JOptionPane.YES_OPTION) {
				JoueurManager.getInstance().removeJoueur(this.page.getJoueur());
				refreshListAndClose();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void modifier() {
		Calendar dateNaissance = (Calendar) this.page.getDateField().getModel().getValue();
		if (areAllFieldsFilled()) {
			if (!JoueurManager.getInstance().verifAge(dateNaissance)) {
				UtilsControlleurs.dispErrorDialogBox("Le joueur doit avoir au moins 18 ans.", this.page);
			} else {
				try {
					int idEquipe = EquipeManager.getInstance().getIdEquipe(this.equipe.getId());
					if (idEquipe != -1) {
						Joueur newJoueur = new Joueur(this.page.getPseudoField().getText(),
								new java.sql.Date(dateNaissance.getTimeInMillis()),
								this.page.getPrenomField().getText(), idEquipe, 0);
						Joueur lastJoueur = this.page.getJoueur();
						if (newJoueur.getPseudo().equals(lastJoueur.getPseudo())) {
							if(!newJoueur.getPrenom().equals(lastJoueur.getPrenom()) || !newJoueur.getDateNaissance().equals(lastJoueur.getDateNaissance())) {
								JoueurManager.getInstance().modifJoueur(newJoueur, lastJoueur);
								refreshListAndClose();
							}
							this.page.dispose();
							
						} else if (!Joueur.verifDoublon(equipe, newJoueur.getPseudo())) {
							JoueurManager.getInstance().modifJoueur(newJoueur, lastJoueur);
							refreshListAndClose();
						} else {
							UtilsControlleurs.dispErrorDialogBox("Ce joueur existe déjà, veuillez entrer un autre pseudo.", this.page);
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}

	private boolean areAllFieldsFilled() {
		return !(this.page.getPseudoField().getText().isEmpty() || this.page.getPrenomField().getText().isEmpty());
	}

	private void refreshListAndClose() {
		try {
			JoueurManager.getInstance().setJoueurs();
			MajListe.MAJListGenerique(JoueurManager.getInstance().getTabJoueurPourEquipe(equipe),
					this.esp.getListJoueurs());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.page.dispose();
	}

}
