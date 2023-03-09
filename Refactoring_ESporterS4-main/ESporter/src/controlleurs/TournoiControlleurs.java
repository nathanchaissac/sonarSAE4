package controlleurs;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.MajListe;
import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.jeux.Jeu;
import model.tournoi.ImportanceTournoi;
import model.tournoi.Tournoi;
import model.tournoi.TournoiManager;
import model.users.Users;
import views.PageEspaceArbitre;
import views.PageEspaceEsporter;
import views.PageGererTournoi;

public class TournoiControlleurs extends MouseAdapter implements ItemListener {

	private PageGererTournoi page;
	private PageEspaceEsporter esp;

	public TournoiControlleurs(PageGererTournoi page, Tournoi trn, PageEspaceEsporter esp) {
		this.page = page;
		this.esp = esp;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			switch (btn.getText()) {
			case "Valider":
				try {
					Calendar selectedDateFin = (Calendar) this.page.getPickerDateFin().getModel().getValue();
					Calendar selectedDate = (Calendar) this.page.getPickerDateTournois().getModel().getValue();
					if (this.page.getComboBoxArbitres().getSelectedItem() == null) {
						JOptionPane.showMessageDialog(this.page,
								"Aucun arbitre choisie (Créer s'en un pour le jeu en question )", "Erreur", 0,
								new ImageIcon(PageEspaceEsporter.class.getResource("img/erreurIcon.png")));
					} else if (this.page.getNomField().getText().equals("")) {
						JOptionPane.showMessageDialog(this.page, "Veuillez donner un nom au tournoi", "Erreur", 0,
								new ImageIcon(PageEspaceEsporter.class.getResource("img/erreurIcon.png")));
					} else if (this.page.getLieuField().getText().equals("")) {
						JOptionPane.showMessageDialog(this.page, "Veuillez donner un lieu au tournoi", "Erreur", 0,
								new ImageIcon(PageEspaceEsporter.class.getResource("img/erreurIcon.png")));
					} else {
						Tournoi trn = new Tournoi(this.page.getNomField().getText(),
								new Date(selectedDate.getTimeInMillis()), this.page.getLieuField().getText(),
								new Date(selectedDateFin.getTimeInMillis()),
								(ImportanceTournoi) this.page.getComboBoxImportanceTournoi().getSelectedItem(),
								((Jeu) this.page.getComboBoxJeu().getSelectedItem()).getId(),
								((Users) this.page.getComboBoxArbitres().getSelectedItem()).getId());
						TournoiManager.getInstance().addDBTournoi(trn);
						MajListe.MAJListGenerique(TournoiManager.getInstance().getTabTournoi(), esp.listTournoi);
						this.page.dispose();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "Annuler":
				this.page.dispose();
			}

		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			Arbitre[] values = ArbitreManager.getInstance()
					.getTabArbitresParJeu((Jeu) page.getComboBoxJeu().getSelectedItem());
			page.getComboBoxArbitres().setModel(new DefaultComboBoxModel<>(values));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
