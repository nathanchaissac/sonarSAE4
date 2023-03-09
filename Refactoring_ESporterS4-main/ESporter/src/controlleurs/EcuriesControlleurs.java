
package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.ecurie.Ecurie;
import model.ecurie.EcurieManager;
import model.ecurie.TypeEcurie;
import model.equipe.EquipeManager;
import model.jeux.JeuManager;
import model.users.UserManager;
import model.MajListe;
import model.UtilsControlleurs;
import model.arbitre.ArbitreManager;
import views.PageEspaceArbitre;
import views.PageEspaceEsporter;
import views.PageGestionEcurie;

public class EcuriesControlleurs extends MouseAdapter {

	private PageGestionEcurie page;
	private Ecurie ecurie;
	private PageEspaceEsporter esp;

	public EcuriesControlleurs(PageGestionEcurie page, Ecurie ecurie, PageEspaceEsporter esp) {
		this.page = page;
		this.ecurie = ecurie;
		this.esp = esp;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		switch (btn.getText()) {
		case "Valider":
			String etat;
			if (this.page.getRadioBtnPro().isSelected()) {
				etat = "Professionnelle";
			} else {
				etat = "Associative";
			}
			if (ecurie == null) {
				ajouter(etat);
			} else {
				modifier(etat);
			}
			break;
		case "Annuler":
			this.page.dispose();
			break;
		case "Supprimer":
			supprimer();
			break;
		}
	}

	private void ajouter(String etat) {
		if (areAllFieldsFilled() && !page.getPassField().getText().isEmpty()) {
			if (isMailValid()) {
				try {
					if (!(Ecurie.verifDoublon(page.getIdField().getText()))) {
						EcurieManager.getInstance().addDBEcurie(this.page.getIdField().getText(),
								this.page.getMailField().getText(), etat, this.page.getPassField().getText());
						refreshListAndClose();
					} else {
						UtilsControlleurs.dispErrorDialogBox("Ce nom d'écurie existe déjà, veuillez entrer un autre nom.", this.page);
					}
				} catch (SQLException | NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			} else {
				UtilsControlleurs.dispErrorDialogBox("Veuillez entrer une adresse mail valide (example@ex.com)", this.page);
			}
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}

	private void modifier(String etat){
		try {
			if (areAllFieldsFilled()) {
				if (isMailValid()) {
					Ecurie newEcurie = new Ecurie(ecurie.getId(),
							this.page.getIdField().getText(),
							this.page.getMailField().getText(),
							TypeEcurie.valueOf(etat)
							);
					if(ecurie.getIdentifiant().equals(newEcurie.getIdentifiant()) && ecurie.getMail().equals(newEcurie.getMail())) {
						editEquipe(etat);
					}else if(!ecurie.verifDoublon(newEcurie.getIdentifiant()) || !ecurie.verifDoublonMail(newEcurie.getMail())) {
						editEquipe(etat);
					}else {
						UtilsControlleurs.dispErrorDialogBox("Cet écurie existe déjà, veuillez entrer un autre identifiant/mail.", this.page);
					}
				} else {
					UtilsControlleurs.dispErrorDialogBox("Veuillez entrer une adresse mail valide (example@ex.com)", this.page);
				}
			} else {
				UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
			}
		}catch(SQLException | NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
	}

	private void editEquipe(String etat) throws SQLException, NoSuchAlgorithmException {
		if(this.page.getPassField().getText().isEmpty()) {
			EcurieManager.getInstance().ModifDBEcurie(this.page.getIdField().getText(),this.page.getMailField().getText(), etat, ecurie.getId());
		} else {
			String pass = UserManager.getInstance().cryptPass(this.page.getPassField().getText());
			EcurieManager.getInstance().ModifDBEcurie(this.page.getIdField().getText(), pass,this.page.getMailField().getText(), etat, ecurie.getId());
		}
		refreshListAndClose();
	}

	private void supprimer() {
		try {
			int result = UtilsControlleurs.dispConfirmSupprDialogBox("Etes-vous sûr de vouloir supprimer l'écurie ?", this.page);
			if (result == JOptionPane.YES_OPTION) {
				EcurieManager.getInstance().removeEcurie(this.page.getIdField().getText());
				refreshListAndClose();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	private boolean areAllFieldsFilled() {
		return !(page.getIdField().getText().isEmpty() || page.getMailField().getText().isEmpty());
	}
	
	private boolean isMailValid() {
		return page.getMailField().getText().matches(".+@.+\\.[a-z]+");
	}
	
	private void refreshListAndClose() {
		try {
			MajListe.MAJListGenerique(EcurieManager.getInstance().setEcuries(), esp.getListEcuries());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.page.dispose();
	}
}
