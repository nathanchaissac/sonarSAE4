package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.jeux.JeuManager;
import model.users.UserManager;
import model.MajListe;
import model.UtilsControlleurs;
import views.PageEspaceArbitre;
import views.PageEspaceEsporter;
import views.PageGestionArbitre;

public class ArbitreControlleurs extends MouseAdapter{
	private PageGestionArbitre page;
	private Arbitre arbitre;
	private PageEspaceEsporter esp;
	
	public ArbitreControlleurs(PageGestionArbitre page, Arbitre arbitre, PageEspaceEsporter esp) {
		this.page = page;
		this.arbitre = arbitre;
		this.esp = esp;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn.getText().equals("Supprimer")) {
			supprimer();
		}
		if(btn.getText().equals("Valider")) {
			if(arbitre == null) {
				ajouter();
			} else {
				modifier();
			}
		}
		if(btn.getText().equals("Annuler")) {
			this.page.dispose();
		}
	}

	private void modifier() {
		try {
			if(areAllFieldsFilled()) {
				if (isMailValid()) {
					Arbitre newArbitre = new Arbitre(arbitre.getId(),
							this.page.getIdField().getText(),
							this.page.getMailField().getText(),
							this.page.getNomField().getText(),
							String.valueOf(JeuManager.getInstance().getIdJeu(this.page.getComboBoxJeu().getSelectedItem().toString()))							
							);
					if(arbitre.getIdentifiant().equals(newArbitre.getIdentifiant()) && arbitre.getMail().equals(newArbitre.getMail())){
						editArbitre();
					}else if(!arbitre.verifDoublon(newArbitre.getIdentifiant()) || !arbitre.verifDoublonMail(newArbitre.getMail())) {
						editArbitre();
					}else {
						UtilsControlleurs.dispErrorDialogBox("Cet Arbitre existe déjà, veuillez entrer un autre identifiant/mail.", this.page);
					}					
				} else {
					UtilsControlleurs.dispErrorDialogBox("Veuillez entrer une adresse mail valide (example@ex.com)", this.page);
				}
			} else {
				UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
			}				
		} catch (SQLException | NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
	}

	private void editArbitre() throws SQLException, NoSuchAlgorithmException {
		if(this.page.getPassField().getText().isEmpty()) {
			ArbitreManager.getInstance().ModifDBArbitre(this.page.getIdField().getText(), this.page.getMailField().getText(), arbitre.getId(), this.page.getNomField().getText(), JeuManager.getInstance().getIdJeu(this.page.getComboBoxJeu().getSelectedItem().toString()));
		} else {
			String pass = UserManager.getInstance().cryptPass(this.page.getPassField().getText());
			ArbitreManager.getInstance().ModifDBArbitre(this.page.getIdField().getText(), pass, this.page.getMailField().getText(), arbitre.getId(), this.page.getNomField().getText(), JeuManager.getInstance().getIdJeu(this.page.getComboBoxJeu().getSelectedItem().toString()));	
		}
		refreshListAndClose();
	}

	private void ajouter() {
		if(areAllFieldsFilled() && !this.page.getPassField().getText().isEmpty()) {
			if (isMailValid()) {
				try {
					if(!(Arbitre.verifDoublon(page.getIdField().getText()))) {
						ArbitreManager.getInstance().addArbitre(this.page.getIdField().getText(), this.page.getMailField().getText(), this.page.getPassField().getText(), this.page.getNomField().getText(), JeuManager.getInstance().getIdJeu(this.page.getComboBoxJeu().getSelectedItem().toString()));
						refreshListAndClose();
					} else {
						UtilsControlleurs.dispErrorDialogBox("Cet identifiant d'arbitre existe déjà, veuillez entrer un autre identifiant.", this.page);
					}
				} catch (NoSuchAlgorithmException | SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				UtilsControlleurs.dispErrorDialogBox("Veuillez entrer une adresse mail valide (example@ex.com)",this.page);
			}
			
		} else {
			UtilsControlleurs.dispErrorDialogBox("Veuillez remplir tous les champs avant de valider.", this.page);
		}
	}
	
	private void refreshListAndClose() {
		try {
			MajListe.MAJListGenerique(ArbitreManager.getInstance().setArbitres(),esp.listArbitre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.page.dispose();
	}
	
	private boolean areAllFieldsFilled() {
		return !(this.page.getIdField().getText().isEmpty() || this.page.getMailField().getText().isEmpty() || this.page.getNomField().getText().isEmpty());
	}
	
	private boolean isMailValid() {
		return page.getMailField().getText().matches(".+@.+\\.[a-z]+");
	}
	
	private void supprimer() {
		try {
			int result = UtilsControlleurs.dispConfirmSupprDialogBox("Etes-vous sûr de vouloir supprimer l'arbitre ?", this.page);
			if(result == JOptionPane.YES_OPTION) {
				ArbitreManager.getInstance().removeArbitre(arbitre.getIdentifiant());
				refreshListAndClose();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
