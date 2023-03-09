package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.ArbitreControlleurs;
import model.Const;
import model.arbitre.Arbitre;
import model.jeux.JeuManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class PageGestionArbitre extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textId;
	private JTextField textMail;
	private JTextField textNom;
	private JTextField passwordField;
	private JComboBox<String> comboBox;
	
	public JTextField getIdField() {
		return this.textId;
	}
	
	public JTextField getMailField() {
		return this.textMail;
	}
	
	public JTextField getNomField() {
		return this.textNom;
	}
	
	public JTextField getPassField() {
		return this.passwordField;
	}
	
	public JComboBox<String> getComboBoxJeu(){
		return this.comboBox;
	}

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public PageGestionArbitre(String title, Arbitre arbitre, PageEspaceEsporter esp) throws SQLException {
		setResizable(false);
		setBounds(100, 100, 541, 373);
		getContentPane().setLayout(new BorderLayout());
		setBounds(100, 100, 450, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
				JLabel lblJeu = new JLabel("Jeu :");
				lblJeu.setForeground(Color.WHITE);
				lblJeu.setFont(new Font("Sora", Font.BOLD, 15));
				lblJeu.setBounds(214, 187, 161, 20);
				contentPanel.add(lblJeu);
		}
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBorder(null);
		passwordField.setBounds(10, 187, 189, 23);
		contentPanel.add(passwordField);
			
		JLabel lblMdp = new JLabel("Mot de passe :");
		lblMdp.setForeground(Color.WHITE);
		lblMdp.setFont(new Font("Sora", Font.BOLD, 15));
		lblMdp.setBounds(10, 164, 161, 20);
		contentPanel.add(lblMdp);
			
		JLabel lblNom = new JLabel("Nom Arbitre :");
		lblNom.setForeground(Color.WHITE);
		lblNom.setFont(new Font("Sora", Font.BOLD, 15));
		lblNom.setBounds(219, 52, 143, 20);
		contentPanel.add(lblNom);
		comboBox = new JComboBox<String>(JeuManager.getInstance().tabNomAccronyme());
		comboBox.setBounds(254, 188, 108, 22);
		if(arbitre != null) comboBox.setSelectedItem(arbitre.getIdJeu());
		contentPanel.add(comboBox);	
		textNom = new JTextField();
		textNom.setColumns(10);
		textNom.setBorder(null);
		textNom.setText(arbitre != null ? arbitre.getNom() : null);
		textNom.setBounds(219, 74, 189, 23);
		contentPanel.add(textNom);
			
			JLabel titreAjoutEcurie = new JLabel(title);
			titreAjoutEcurie.setForeground(Color.WHITE);
			titreAjoutEcurie.setFont(new Font("Sora", Font.BOLD, 25));
			titreAjoutEcurie.setBounds(10, 0, 327, 47);
			contentPanel.add(titreAjoutEcurie);
			
			JLabel lblIdentifiantAbitre = new JLabel("Identifiant Arbitre :");
			lblIdentifiantAbitre.setForeground(Color.WHITE);
			lblIdentifiantAbitre.setFont(new Font("Sora", Font.BOLD, 15));
			lblIdentifiantAbitre.setBounds(10, 51, 161, 20);
			contentPanel.add(lblIdentifiantAbitre);
		
			textId = new JTextField();
			textId.setColumns(10);
			textId.setBorder(null);
			textId.setBounds(10, 74, 189, 23);
			textId.setText(arbitre != null ? arbitre.getIdentifiant() : null);
			contentPanel.add(textId);
				
			textMail = new JTextField();
			textMail.setColumns(10);
			textMail.setBorder(null);
			textMail.setText(arbitre != null ? arbitre.getMail() : null);
			textMail.setBounds(10, 131, 352, 23);
			contentPanel.add(textMail);
						
			JLabel lblEmail = new JLabel("Adresse Email :");
			lblEmail.setForeground(Color.WHITE);
			lblEmail.setFont(new Font("Sora", Font.BOLD, 15));
			lblEmail.setBounds(10, 107, 143, 20);
			contentPanel.add(lblEmail);
			
			
			
			JLabel background = new JLabel("");
			background.setIcon(new ImageIcon(PageGestionJeu.class.getResource("img/bg.png")));
			background.setBounds(0, 0, 436, 232);
			contentPanel.add(background);
			
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 232, 436, 31);
			contentPanel.add(buttonPane);
			if(arbitre != null) {
				JButton deleteButton = new JButton("Supprimer");
				deleteButton.setBounds(10, 5, 101, 21);
				deleteButton.setForeground(Color.WHITE);
				deleteButton.setBackground(Const.DELETE_BUTTON_COLOR);
				deleteButton.setActionCommand("Cancel");
				deleteButton.addMouseListener(new ArbitreControlleurs(this, arbitre, esp));
				buttonPane.add(deleteButton);
			}
				

			{
				JButton okButton = new JButton("Valider");
				okButton.setBounds(244, 5, 86, 21);
				okButton.setForeground(Color.WHITE);
				okButton.setBackground(Const.BUTTON_COLOR);
				okButton.setActionCommand("OK");
				okButton.addMouseListener(new ArbitreControlleurs(this, arbitre, esp));
				buttonPane.setLayout(null);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(340, 5, 86, 21);
				cancelButton.setForeground(Const.BUTTON_COLOR);
				cancelButton.setBackground(Color.WHITE);
				cancelButton.setActionCommand("Cancel");
				cancelButton.addMouseListener(new ArbitreControlleurs(this, arbitre, esp));
				buttonPane.add(cancelButton);
			}
		}
	}
}
