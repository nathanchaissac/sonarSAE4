package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.MajListe;
import model.UtilsControlleurs;
import model.ecurie.Ecurie;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.jeux.Jeu;
import model.users.UserManager;
import views.PageEspaceArbitre;
import views.PageEspaceEcurie;
import views.PageGestionEquipe;

public class EquipeControlleurs extends MouseAdapter{
	private PageGestionEquipe page;
	private PageEspaceEcurie esp;
	private Equipe equipe;
	
	public EquipeControlleurs(PageGestionEquipe page , PageEspaceEcurie esp) {
		this.esp = esp;
		this.page = page;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn.getText().equals("Annuler")) {
			page.dispose();
		} else if (btn.getText().equals("Valider")) {
			if(this.page.getEquipe() == null) {
				ajouter();
			} else {
				modifier();
			}
		} else if (btn.getText().equals("Supprimer")){			
			supprimer();
		}
	}
	
	private void supprimer() {
		try {
			int result = UtilsControlleurs.dispConfirmSupprDialogBox("Etes-vous sûr de vouloir supprimer cette équipe ?", this.page);
			if(result == JOptionPane.YES_OPTION) {
				EquipeManager.getInstance().removeEquipe(page.getEquipe());
				refreshListAndClose();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void modifier() {
		if(!this.page.getMailField().getText().isEmpty()) {
			if(isMailValid()) {
				try {
					EquipeManager.getInstance().modifEquipe(page.getMailField().getText(), page.getEquipe());
					refreshListAndClose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				UtilsControlleurs.dispErrorDialogBox("Veuillez entrer une adresse mail valide (example@ex.com)", this.page);
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}
	
	private void ajouter() {
		if(areAllFieldsFilled()) {
			if(isMailValid()) {
				if(!Equipe.verifDoublon(UserManager.getInstance().getUser().getId(), (Jeu) this.page.getComboBoxJeu().getSelectedItem())) {
					try {
						Jeu jeu = (Jeu) this.page.getComboBoxJeu().getSelectedItem();
						String nomEquipe = this.page.getEcurie().getIdentifiant()+jeu.getAcronyme();
						this.equipe = new Equipe(0, nomEquipe, this.page.getMailField().getText(), 0, 0);
						this.equipe.setJeu(jeu);
						EquipeManager.getInstance().addEquipe(this.equipe, this.page.getPassField().getText());
						refreshListAndClose();
					} catch (NoSuchAlgorithmException | SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					UtilsControlleurs.dispErrorDialogBox("Ce jeu est déjà utilisé par une équipe de l'écurie, veuillez utiliser un autre jeu.", this.page);
				}
			} else {
				UtilsControlleurs.dispErrorDialogBox("Veuillez entrer une adresse mail valide (example@ex.com)", this.page);
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}
	
	
	private boolean areAllFieldsFilled() {
		return !(this.page.getMailField().getText().isEmpty() || this.page.getPassField().getText().isEmpty());
	}
	
	private boolean isMailValid() {
		return page.getMailField().getText().matches(".+@.+\\.[a-z]+");
	}
	
	private void refreshListAndClose() {
		try {
			MajListe.MAJListGenerique(EquipeManager.getInstance().equipeParEcurie((Ecurie) UserManager.getInstance().getUser()), esp.getListEquipe());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.page.dispose();
	}
}
