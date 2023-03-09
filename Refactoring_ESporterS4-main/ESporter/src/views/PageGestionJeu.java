package views;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.JeuxControlleurs;
import model.Const;
import model.jeux.Jeu;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;

public class PageGestionJeu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNomJeu;
	private JTextField textFieldAcronyme;
	private JTextField textFieldNomEditeur;
	
	public JTextField getNomJeuField() {
		return this.textFieldNomJeu;
	}
	
	public JTextField getAcronymeField() {
		return this.textFieldAcronyme;
	}
	
	public JTextField getEditeurField() {
		return this.textFieldNomEditeur;
	}
	
	/**
	 * Create the dialog.
	 */
	public PageGestionJeu(String title,Jeu jeu, PageEspaceEsporter esp) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PageGestionJeu.class.getResource("img/logoSimple.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel titreAjoutJeu = new JLabel(title);
			titreAjoutJeu.setForeground(new Color(255, 255, 255));
			titreAjoutJeu.setFont(new Font("Sora", Font.BOLD, 25));
			titreAjoutJeu.setBounds(10, 10, 202, 47);
			contentPanel.add(titreAjoutJeu);
		}
		
		JLabel lblNomJeu = new JLabel("Nom du jeu :");
		lblNomJeu.setForeground(new Color(255, 255, 255));
		lblNomJeu.setFont(new Font("Sora", Font.BOLD, 15));
		lblNomJeu.setBounds(10, 67, 143, 20);
		contentPanel.add(lblNomJeu);
		
		JLabel lblAcronyme = new JLabel("Acronyme :");
		lblAcronyme.setForeground(new Color(255, 255, 255));
		lblAcronyme.setFont(new Font("Sora", Font.BOLD, 15));
		lblAcronyme.setBounds(228, 67, 143, 20);
		contentPanel.add(lblAcronyme);
		
		JLabel lblNomEditeur = new JLabel("Nom éditeur :");
		lblNomEditeur.setForeground(new Color(255, 255, 255));
		lblNomEditeur.setFont(new Font("Sora", Font.BOLD, 15));
		lblNomEditeur.setBounds(10, 150, 143, 20);
		contentPanel.add(lblNomEditeur);
		
		textFieldNomJeu = new JTextField();
		textFieldNomJeu.setBorder(null);
		if( jeu  != null) {
			textFieldNomJeu.setText(jeu.getNom());
		}
		textFieldNomJeu.setBounds(10, 90, 189, 23);
		contentPanel.add(textFieldNomJeu);
		textFieldNomJeu.setColumns(10);
		
		textFieldAcronyme = new JTextField();
		textFieldAcronyme.setBorder(null);
		if( jeu  != null) {
			textFieldAcronyme.setText(jeu.getAcronyme());
		}
		textFieldAcronyme.setColumns(10);
		textFieldAcronyme.setBounds(228, 90, 143, 23);
		contentPanel.add(textFieldAcronyme);
		
		textFieldNomEditeur = new JTextField();
		textFieldNomEditeur.setBorder(null);
		if( jeu  != null) {
			textFieldNomEditeur.setText(jeu.getEditeur());
		}
		textFieldNomEditeur.setColumns(10);
		textFieldNomEditeur.setBounds(10, 174, 177, 23);
		contentPanel.add(textFieldNomEditeur);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(PageGestionJeu.class.getResource("img/bg.png")));
		background.setBounds(0, 0, 436, 232);
		contentPanel.add(background);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 231, 436, 32);
			contentPanel.add(buttonPane);
			{
				
				
				if(jeu != null) {
					JButton btnSupprimer1 = new JButton("Supprimer");
					btnSupprimer1.setBounds(10, 5, 95, 21);
					btnSupprimer1.setForeground(Color.WHITE);
					btnSupprimer1.setBackground(Const.DELETE_BUTTON_COLOR);
					buttonPane.add(btnSupprimer1);
					btnSupprimer1.addMouseListener(new JeuxControlleurs(jeu, esp,  this));
				}
				
				JButton ajoutButton = new JButton("Valider");
				ajoutButton.setBounds(256, 5, 84, 21);
				ajoutButton.addMouseListener(new JeuxControlleurs(jeu, esp, this));
				buttonPane.setLayout(null);
				ajoutButton.setForeground(Color.WHITE);
				ajoutButton.setBackground(Const.BUTTON_COLOR);
				ajoutButton.setActionCommand("OK");
				buttonPane.add(ajoutButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(347, 5, 84, 21);
				cancelButton.addMouseListener(new JeuxControlleurs(jeu, esp, this));
				cancelButton.setForeground(Const.BUTTON_COLOR);
				cancelButton.setBackground(Color.WHITE);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
