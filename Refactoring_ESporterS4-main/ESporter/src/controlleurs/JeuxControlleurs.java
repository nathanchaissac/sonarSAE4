package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.MajListe;
import model.UtilsControlleurs;
import model.arbitre.ArbitreManager;
import views.PageEspaceArbitre;
import views.PageEspaceEsporter;
import views.PageGestionJeu;

public class JeuxControlleurs extends MouseAdapter{
	
	private Jeu jeu;
	private PageEspaceEsporter esp;
	private PageGestionJeu page;
	
	public JeuxControlleurs(Jeu jeu, PageEspaceEsporter esp, PageGestionJeu page) {
		this.jeu = jeu;
		this.esp = esp;
		this.page = page;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		switch(btn.getText()) {
		case "Supprimer":
			supprimer();
			break;
		case "Valider" :
			if(jeu == null) {
				ajouter();
			} else {
				modifier();
			}
			break;
		case "Annuler":
			this.page.dispose();
			break;
		}
	}

	private void supprimer() {
		try {
			int result = UtilsControlleurs.dispConfirmSupprDialogBox("Etes-vous sûr de vouloir supprimer le jeu?", this.page);
			if(result == JOptionPane.YES_OPTION) {
				JeuManager.getInstance().removeJeu(jeu);
				refreshListAndClose();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void modifier() {
		if(areAllFieldsFilled()) {
			Jeu newJeu = createJeu();
			try {
				if(jeu.getAcronyme().equals(newJeu.getAcronyme()) && jeu.getNom().equals(newJeu.getNom())) {
					this.page.dispose();
				}else if(!newJeu.verifDoublonAcronymeJeu() || !newJeu.verifDoublonNomJeu()) {
					JeuManager.getInstance().modifJeu(newJeu, jeu);
					refreshListAndClose();
				} else {
					UtilsControlleurs.dispErrorDialogBox("Ce jeu existe déjà, veuillez entrer un autre nom/acronyme.", this.page);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}

	private void ajouter() {
		if(areAllFieldsFilled()) {
			Jeu NvJeu = createJeu();
			try {
				if(!NvJeu.verifDoublon()) {
					JeuManager.getInstance().addDBJeu(NvJeu);
					refreshListAndClose();
				} else {
					UtilsControlleurs.dispErrorDialogBox("Ce jeu existe déjà, veuillez entrer un autre nom/acronyme.", this.page);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}
	
	private boolean areAllFieldsFilled() {
		return !(this.page.getAcronymeField().getText().equals("") || this.page.getEditeurField().getText().equals("") || this.page.getNomJeuField().getText().equals(""));
	}
	
	private void refreshListAndClose() {
		try {
			MajListe.MAJListGenerique(JeuManager.getInstance().setJeux(),esp.listJeux);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.page.dispose();
	}
	
	private Jeu createJeu() {
		return new Jeu(this.page.getNomJeuField().getText(), this.page.getEditeurField().getText(), this.page.getAcronymeField().getText());
	}

}
