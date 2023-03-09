package views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePicker;

import controlleurs.JoueursControlleurs;
import model.Const;
import model.equipe.Equipe;
import model.joueur.Joueur;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class PageAjoutJoueur extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField prenomField;
	private JTextField pseudoField;
	private JDatePicker dateField;
	private Joueur joueur;
	
	public JTextField getPrenomField() {
		return this.prenomField;
	}
	
	public JTextField getPseudoField() {
		return this.pseudoField;
	}
	
	public JDatePicker getDateField() {
		return this.dateField;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	/**
	 * Create the dialog.
	 */
	public PageAjoutJoueur(PageEspaceEquipe esp, String title,Equipe equipe, Joueur joueur) {
		this.joueur = joueur;
		setResizable(false);
		setBounds(100, 100, 409, 382);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel titreAjoutEcurie = new JLabel(title+" Joueur");
			titreAjoutEcurie.setForeground(Color.WHITE);
			titreAjoutEcurie.setFont(new Font("Sora", Font.BOLD, 30));
			titreAjoutEcurie.setBounds(35, 24, 327, 47);
			contentPanel.add(titreAjoutEcurie);
		}
		{
			JLabel lblDate = new JLabel("Date de naissance :");
			lblDate.setForeground(Color.WHITE);
			lblDate.setFont(new Font("Sora", Font.BOLD, 17));
			lblDate.setBounds(35, 217, 252, 23);
			contentPanel.add(lblDate);
		}
		{
			dateField = new JDatePicker();
			dateField.setFont(new Font("Sora", Font.BOLD, 15));
			dateField.setBounds(35, 239, 254, 28);
			if(this.joueur!=null) {
				dateField.getModel().setYear(this.joueur.getDateYear()); 
				dateField.getModel().setMonth(this.joueur.getDateMonth()); 
				dateField.getModel().setDay(this.joueur.getDateDay()); 
				dateField.getModel().setSelected(true);
			}
			contentPanel.add(dateField);
		}
		{
			JLabel lblPseudo = new JLabel("Pseudo :");
			lblPseudo.setForeground(Color.WHITE);
			lblPseudo.setFont(new Font("Sora", Font.BOLD, 17));
			lblPseudo.setBounds(35, 93, 254, 23);
			contentPanel.add(lblPseudo);
		}
		{
			prenomField = new JTextField();
			prenomField.setFont(new Font("Sora", Font.BOLD, 15));
			prenomField.setColumns(10);
			prenomField.setBounds(35, 180, 254, 28);
			if(joueur != null) prenomField.setText(joueur.getPrenom());
			contentPanel.add(prenomField);
		}
		{
			pseudoField = new JTextField();
			pseudoField.setFont(new Font("Sora", Font.BOLD, 15));
			pseudoField.setColumns(10);
			pseudoField.setBounds(35, 118, 254, 26);
			if(joueur != null) pseudoField.setText(joueur.getPseudo());
			contentPanel.add(pseudoField);
		}
		{
			JLabel lblPrenom = new JLabel("Prénom :");
			lblPrenom.setForeground(Color.WHITE);
			lblPrenom.setFont(new Font("Sora", Font.BOLD, 17));
			lblPrenom.setBounds(35, 155, 252, 23);
			contentPanel.add(lblPrenom);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 306, 393, 39);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			
			JButton cancelButton = new JButton("Annuler");
			cancelButton.setBackground(Color.WHITE);
			cancelButton.setForeground(Const.BUTTON_COLOR);
			cancelButton.setActionCommand("Cancel");
			cancelButton.setBounds(302, 10, 81, 21);
			cancelButton.addMouseListener(new JoueursControlleurs(esp, this, equipe));
			buttonPane.add(cancelButton);
			
			JButton okButton = new JButton("Valider");
			okButton.setForeground(Color.WHITE);
			okButton.setBackground(Const.BUTTON_COLOR);
			okButton.setActionCommand("OK");
			okButton.setBounds(216, 10, 76, 21);
			okButton.addMouseListener(new JoueursControlleurs(esp, this, equipe));
			buttonPane.add(okButton);
			if(joueur != null) {
				JButton btnSupprimer = new JButton("Supprimer");
				btnSupprimer.setForeground(Color.WHITE);
				btnSupprimer.setBackground(Const.DELETE_BUTTON_COLOR);
				btnSupprimer.setActionCommand("Cancel");
				btnSupprimer.setBounds(10, 10, 94, 21);
				btnSupprimer.addMouseListener(new JoueursControlleurs(esp, this, equipe));
				buttonPane.add(btnSupprimer);
			}
			
			{
				JLabel bg = new JLabel("");
				bg.setIcon(new ImageIcon(PageAjoutJoueur.class.getResource("/views/img/bg.png")));
				bg.setBounds(0, 0, 393, 309);
				contentPanel.add(bg);
			}
		}
	}
}
