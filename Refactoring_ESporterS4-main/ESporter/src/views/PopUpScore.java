package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Const;
import model.arbitre.ArbitreManager;
import model.equipe.Equipe;
import model.match.MatchManager;
import model.match.Match;
import model.users.UserManager;
import model.MajListe;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class PopUpScore extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public PopUpScore(PageEspaceArbitre esp, Match match) {
		setResizable(false);
		setTitle("Insertion Score");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PopUpScore.class.getResource("img/logoSimple.png")));
		setBounds(100, 100, 568, 177);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			
			JLabel TitreMatch = new JLabel("Match de " + match.getEquipes()[0].getIdentifiant() + " vs " + match.getEquipes()[1].getIdentifiant());
			TitreMatch.setFont(new Font("Sora", Font.BOLD, 17));
			TitreMatch.setForeground(Color.WHITE);
			TitreMatch.setBounds(10, 10, 451, 31);
			contentPanel.add(TitreMatch);
			
		}
		
		JLabel labelSelectionVainqueur = new JLabel("Selectionnez le vainqueur :");
		labelSelectionVainqueur.setForeground(Color.WHITE);
		labelSelectionVainqueur.setFont(new Font("Dialog", Font.PLAIN, 15));
		labelSelectionVainqueur.setBounds(10, 59, 180, 27);
		contentPanel.add(labelSelectionVainqueur);
		
		JComboBox selectionVainqueur = new JComboBox(match.getEquipes());
		selectionVainqueur.setFont(new Font("Sora", Font.BOLD, 15));
		selectionVainqueur.setBounds(200, 62, 337, 25);
		contentPanel.add(selectionVainqueur);
		{
			JPanel panel = new JPanel();
			panel.setBounds(0, 104, 554, 36);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				JButton okButton = new JButton("Valider");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							ArbitreManager.getInstance().ajoutGagnant(match,((Equipe) selectionVainqueur.getSelectedItem()));
							MajListe.MAJListGenerique(MatchManager.getInstance().getTabMatchPourArbitre(ArbitreManager.getInstance().getIDArbitresParID(UserManager.getInstance().getUser().getId())),esp.getlistMatch());
							dispose();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				okButton.setBounds(378, 8, 80, 21);
				panel.add(okButton);
				okButton.setForeground(Color.WHITE);
				okButton.setBackground(Const.BUTTON_COLOR);
				okButton.setActionCommand("OK");
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setBounds(464, 8, 80, 21);
				panel.add(cancelButton);
				
				cancelButton.setForeground(Const.BUTTON_COLOR);
				cancelButton.setBackground(Color.WHITE);
				cancelButton.setActionCommand("Cancel");
			}
		}
		
		JLabel bgButtonBar = new JLabel("");
		bgButtonBar.setBounds(0, 0, 567, 104);
		contentPanel.add(bgButtonBar);
		bgButtonBar.setIcon(new ImageIcon(PopUpScore.class.getResource("img/bg.png")));
	}
}
