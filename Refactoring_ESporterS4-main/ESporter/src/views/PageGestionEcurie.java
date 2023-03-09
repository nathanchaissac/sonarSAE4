package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.EcuriesControlleurs;
import model.Const;
import model.ecurie.Ecurie;
import model.ecurie.TypeEcurie;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class PageGestionEcurie extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldIdentifiant;
	private JTextField textFieldMDP;
	private JTextField textFieldMail;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton radioButtonPro;
	
	public JTextField getIdField() {
		return this.textFieldIdentifiant;
	}
	
	public JTextField getPassField() {
		return this.textFieldMDP;
	}
	
	public JTextField getMailField() {
		return this.textFieldMail;
	}
	
	public JRadioButton getRadioBtnPro() {
		return this.radioButtonPro;
	}

	/**
	 * Create the dialog.
	 */
	public PageGestionEcurie(String title, Ecurie ecurie, PageEspaceEsporter esp) {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel titreAjoutEcurie = new JLabel(title);
		titreAjoutEcurie.setForeground(Color.WHITE);
		titreAjoutEcurie.setFont(new Font("Sora", Font.BOLD, 25));
		titreAjoutEcurie.setBounds(10, 0, 327, 47);
		contentPanel.add(titreAjoutEcurie);
		
		JLabel lblIdEcurie = new JLabel("Identifiant Ecurie :");
		lblIdEcurie.setForeground(Color.WHITE);
		lblIdEcurie.setFont(new Font("Sora", Font.BOLD, 15));
		lblIdEcurie.setBounds(10, 56, 143, 20);
		contentPanel.add(lblIdEcurie);
		
		JLabel lblEcurieDeType = new JLabel("Ecurie de type :");
		lblEcurieDeType.setForeground(Color.WHITE);
		lblEcurieDeType.setFont(new Font("Sora", Font.BOLD, 15));
		lblEcurieDeType.setBounds(10, 179, 143, 20);
		contentPanel.add(lblEcurieDeType);
		
		JRadioButton rdbtnAssociative = new JRadioButton("Associative");
		if(	ecurie == null) {
			rdbtnAssociative.setSelected(false);
		}else if (ecurie.getType() == TypeEcurie.Associative) {
			rdbtnAssociative.setSelected(true);
		}else {
			rdbtnAssociative.setSelected(false);
		}
		rdbtnAssociative.setFont(new Font("Sora", Font.BOLD, 10));
		buttonGroup.add(rdbtnAssociative);
		rdbtnAssociative.setBounds(259, 179, 103, 21);
		contentPanel.add(rdbtnAssociative);
		radioButtonPro = new JRadioButton("Professionnelle");
		if (ecurie == null) {
			radioButtonPro.setSelected(true);
		}else if (ecurie.getType() == TypeEcurie.Professionnelle) {
			radioButtonPro.setSelected(true);
		}else {
			radioButtonPro.setSelected(false);
		}
		
		radioButtonPro.setFont(new Font("Sora", Font.BOLD, 10));
		buttonGroup.add(radioButtonPro);
		radioButtonPro.setBounds(141, 179, 116, 21);
		contentPanel.add(radioButtonPro);
		
		textFieldIdentifiant = new JTextField();
		if( ecurie  != null) {
			textFieldIdentifiant.setText(ecurie.getIdentifiant());
		}
		textFieldIdentifiant.setBorder(null);
		textFieldIdentifiant.setColumns(10);
		textFieldIdentifiant.setBounds(10, 79, 189, 23);
		contentPanel.add(textFieldIdentifiant);
		textFieldMDP = new JTextField();
		textFieldMDP.setBorder(null);
		textFieldMDP.setColumns(10);
		textFieldMDP.setBounds(219, 79, 118, 23);
		contentPanel.add(textFieldMDP);
		JLabel lblMdpEcurie = new JLabel("Mot de passe :");
		lblMdpEcurie.setForeground(Color.WHITE);
		lblMdpEcurie.setFont(new Font("Sora", Font.BOLD, 15));
		lblMdpEcurie.setBounds(219, 56, 143, 20);
		contentPanel.add(lblMdpEcurie);
		textFieldMail = new JTextField();
		if( ecurie  != null) {
			textFieldMail.setText(ecurie.getMail());
		}
		textFieldMail.setBorder(null);
		textFieldMail.setColumns(10);
		textFieldMail.setBounds(10, 136, 352, 23);
		contentPanel.add(textFieldMail);
		
		JLabel lblEmail = new JLabel("Adresse Email :");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Sora", Font.BOLD, 15));
		lblEmail.setBounds(10, 112, 143, 20);
		contentPanel.add(lblEmail);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(PageGestionEcurie.class.getResource("img/bg.png")));
		background.setBounds(0, 0, 436, 232);
		contentPanel.add(background);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 231, 436, 32);
			contentPanel.add(buttonPane);
			
			
			if (ecurie != null) {
				JButton btnSupprimer_1 = new JButton("Supprimer");
				btnSupprimer_1.setBounds(10, 5, 95, 21);
				btnSupprimer_1.setForeground(Color.WHITE);
				btnSupprimer_1.setBackground(Const.DELETE_BUTTON_COLOR);
				buttonPane.add(btnSupprimer_1);
				btnSupprimer_1.addMouseListener(new EcuriesControlleurs(this, ecurie, esp));
			}
			JButton ajoutButton = new JButton("Valider");
			ajoutButton.setBounds(267, 5, 78, 21);
			ajoutButton.addMouseListener(new EcuriesControlleurs(this, ecurie, esp));
			buttonPane.setLayout(null);
			ajoutButton.setForeground(Color.WHITE);
			ajoutButton.setBackground(Const.BUTTON_COLOR);
			ajoutButton.setActionCommand("OK");
			buttonPane.add(ajoutButton);
			
			JButton cancelButton = new JButton("Annuler");
			cancelButton.setBounds(353, 5, 78, 21);
			cancelButton.addMouseListener(new EcuriesControlleurs(this, ecurie, esp));
			cancelButton.setForeground(Const.BUTTON_COLOR);
			cancelButton.setBackground(Color.WHITE);
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}
}
