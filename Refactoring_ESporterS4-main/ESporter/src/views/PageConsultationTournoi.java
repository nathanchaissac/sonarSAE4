package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Const;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.tournoi.ImportanceTournoi;
import model.tournoi.Tournoi;
import model.tournoi.TournoiManager;
import model.users.UserManager;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class PageConsultationTournoi extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public PageConsultationTournoi(Tournoi tournoi) throws SQLException {
		setBounds(100, 100, 450, 228);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbltype = new JLabel("Type : "+tournoi.getImportance().getNom());
		lbltype.setForeground(Color.WHITE);
		lbltype.setFont(new Font("Sora", Font.ITALIC, 14));
		lbltype.setBounds(10, 99, 405, 26);
		contentPanel.add(lbltype);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 416, 69);
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel nomTournoi = new JLabel(tournoi.getNom());
		nomTournoi.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nomTournoi);
		nomTournoi.setForeground(Const.BUTTON_COLOR);
		nomTournoi.setBackground(Color.WHITE);
		nomTournoi.setFont(new Font("Dialog", Font.BOLD, 30));
		{
			
			JLabel date1 = new JLabel(new SimpleDateFormat("dd/MM/yyyy").format(tournoi.getDate()));
			date1.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(date1);
			date1.setForeground(Const.BUTTON_COLOR);
			date1.setFont(new Font("Dialog", Font.BOLD, 15));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 155, 436, 36);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			JButton btnQuitter = new JButton("Quitter");
			btnQuitter.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						TournoiManager.getInstance().removeEquipeTournoi(tournoi, (Equipe) UserManager.getInstance().getUser());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
			});
			btnQuitter.setForeground(Color.white);
			btnQuitter.setBackground(Const.BUTTON_COLOR);
			btnQuitter.setActionCommand("OK");
			btnQuitter.setBounds(243, 8, 92, 21);
			buttonPane.add(btnQuitter);
			JButton btnRejoindre = new JButton("Rejoindre");
			btnRejoindre.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						TournoiManager.getInstance().addEquipeAuTournois(tournoi,(Equipe) UserManager.getInstance().getUser());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
			});
			btnRejoindre.setForeground(Color.white);
			btnRejoindre.setBackground(Const.BUTTON_COLOR);
			btnRejoindre.setActionCommand("OK");
			btnRejoindre.setBounds(243, 8, 92, 21);
			buttonPane.add(btnRejoindre);
			{
				System.out.println(TournoiManager.getInstance().verifInscription(tournoi.getId(), UserManager.getInstance().getUser().getId()));
				if(!TournoiManager.getInstance().verifInscription(tournoi.getId(), UserManager.getInstance().getUser().getId())) {
					btnRejoindre.setVisible(true);
					btnQuitter.setVisible(false);
				}else {
					btnRejoindre.setVisible(false);
					btnQuitter.setVisible(true);
				}
				
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setForeground(Const.BUTTON_COLOR);
				cancelButton.setBackground(Color.WHITE);
				cancelButton.setActionCommand("Cancel");
				cancelButton.setBounds(345, 8, 81, 21);
				buttonPane.add(cancelButton);
			}
		}
		{
			JLabel lblLieu = new JLabel("Lieu : "+tournoi.getLieu());
			lblLieu.setForeground(Color.WHITE);
			lblLieu.setFont(new Font("Sora", Font.ITALIC, 14));
			lblLieu.setBounds(10, 79, 175, 26);
			contentPanel.add(lblLieu);
		}
		{
			JLabel lblFinIsncr = new JLabel();
			lblFinIsncr.setForeground(Color.WHITE);
			lblFinIsncr.setText("Fin Inscription : "+new SimpleDateFormat("dd/MM/yyyy").format(tournoi.getDateLimite()));
			lblFinIsncr.setFont(new Font("Dialog", Font.ITALIC, 14));
			lblFinIsncr.setBounds(10, 119, 405, 26);
			contentPanel.add(lblFinIsncr);
		}
		
		JLabel bg = new JLabel();
		bg.setIcon(new ImageIcon(PageConsultationTournoi.class.getResource("/views/img/bg.png")));
		bg.setBounds(0, 0, 436, 156);
		contentPanel.add(bg);
	}
}
