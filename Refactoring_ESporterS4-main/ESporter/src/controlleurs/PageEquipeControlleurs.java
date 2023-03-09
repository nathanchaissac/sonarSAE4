package controlleurs;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import model.UtilsControlleurs;
import model.equipe.Equipe;
import model.joueur.Joueur;
import model.tournoi.Tournoi;
import model.users.UserManager;
import views.PageAccueil;
import views.PageAjoutJoueur;
import views.PageConnexion;
import views.PageConsultationTournoi;
import views.PageEspaceArbitre;
import views.PageEspaceEquipe;

public class PageEquipeControlleurs extends MouseAdapter {
	private PageEspaceEquipe page;
	private PageAccueil accueil;
	private PageAjoutJoueur ajout;
	
	public PageEquipeControlleurs(PageEspaceEquipe page) {
		this.page = page;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			switch(btn.getText()) {
			case "Déconnexion":
				int result = UtilsControlleurs.dispConfirmDecoDialogBox("Etes-vous sûr de vouloir vous déconnecter ?", this.page);
				if(result == JOptionPane.YES_OPTION) {
					PageConnexion con = new PageConnexion();
					con.setVisible(true);
					this.page.dispose();
				}
				break;
			case "":
				try {
					accueil = new PageAccueil();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				accueil.setVisible(true);
				this.page.dispose();
				break;
			case "Ajouter un joueur":
				ajout = new PageAjoutJoueur(this.page, "Ajouter", (Equipe) UserManager.getInstance().getUser(), null);
				ajout.setVisible(true);
				break;
			case "Gérer les tournois":
				btn.setForeground(Color.LIGHT_GRAY);
				btn.setBackground(SystemColor.controlDkShadow);
				this.page.getJoueurBtn().setForeground(SystemColor.controlDkShadow);
				this.page.getJoueurBtn().setBackground(Color.LIGHT_GRAY);
				this.page.getPanelTournois().setVisible(true);
				this.page.getPanelJoueur().setVisible(false);
				break;
			case "Gérer vos joueurs":
				btn.setBackground(SystemColor.controlDkShadow);
				btn.setForeground(Color.LIGHT_GRAY);
				this.page.getTournoisBtn().setForeground(SystemColor.controlDkShadow);
				this.page.getTournoisBtn().setBackground(Color.LIGHT_GRAY);
				this.page.getPanelTournois().setVisible(false);
				this.page.getPanelJoueur().setVisible(true);
				break;
			}
		}else if(e.getSource() instanceof JList){
			JList lst = (JList) e.getSource();
			if(lst.getSelectedValue() instanceof Joueur) {
				ajout = new PageAjoutJoueur(this.page, "Modification", (Equipe) UserManager.getInstance().getUser(), (Joueur) lst.getSelectedValue());
				ajout.setVisible(true);
			}
			if(lst.getSelectedValue() instanceof Tournoi){
				try {
					PageConsultationTournoi pageConsult = new PageConsultationTournoi((Tournoi) lst.getSelectedValue());
					pageConsult.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
}
