package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.EquipeControlleurs;
import model.Const;
import model.ecurie.Ecurie;
import model.equipe.Equipe;
import model.jeux.JeuManager;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.sql.SQLException;

import javax.swing.JTextField;

public class PageGestionEquipe extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textMail;
	private JTextField textMDP;
	private Equipe equipe;
	private JComboBox comboJeux;
	private Ecurie ecurie;
	private JLabel lblMDP;
	
	public JTextField getMailField() {
		return this.textMail;
	}
	
	public JTextField getPassField() {
		return this.textMDP;
	}
	
	public Equipe getEquipe() {
		return this.equipe;
	}
	
	public JComboBox getComboBoxJeu() {
		return this.comboJeux;
	}
	
	public Ecurie getEcurie() {
		return this.ecurie;
	}
	
	public JLabel getPassText() {
		return this.lblMDP;
	}
	
	
	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public PageGestionEquipe(String title,Equipe equipe,PageEspaceEcurie esp,Ecurie ecurie) throws SQLException {
		if(equipe != null) {
			this.equipe = equipe;
		}
		if(ecurie != null) {
			this.ecurie = ecurie;
		}
		setResizable(false);
		setBounds(100, 100, 421, 409);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		comboJeux = new JComboBox(JeuManager.getInstance().getTabJeu());
		comboJeux.setFont(new Font("Sora", Font.BOLD, 15));
		comboJeux.getSelectedItem();
		comboJeux.setBounds(32, 119, 341, 28);
		contentPanel.add(comboJeux);
		{
			JLabel titreAjoutEcurie = new JLabel((title+" équipe"));
			titreAjoutEcurie.setForeground(Color.WHITE);
			titreAjoutEcurie.setFont(new Font("Sora", Font.BOLD, 30));
			titreAjoutEcurie.setBounds(20, 29, 327, 47);
			contentPanel.add(titreAjoutEcurie);
		}
		{
			JLabel lblMail = new JLabel("Mail :");
			lblMail.setForeground(Color.WHITE);
			lblMail.setFont(new Font("Sora", Font.BOLD, 17));
			lblMail.setBounds(32, 157, 252, 23);
			contentPanel.add(lblMail);
		}
		
		textMail = new JTextField();
		textMail.setFont(new Font("Sora", Font.BOLD, 15));
		textMail.setBounds(32, 178, 341, 28);
		contentPanel.add(textMail);
		textMail.setColumns(10);
		
		lblMDP = new JLabel("Mots de passe :");
		lblMDP.setForeground(Color.WHITE);
		lblMDP.setFont(new Font("Sora", Font.BOLD, 17));
		lblMDP.setBounds(32, 218, 252, 23);
		contentPanel.add(lblMDP);
		
		textMDP = new JTextField();
		textMDP.setFont(new Font("Sora", Font.BOLD, 15));
		textMDP.setColumns(10);
		textMDP.setBounds(32, 239, 341, 28);
		contentPanel.add(textMDP);
		{
			JLabel lblJeu = new JLabel("Jeu :");
			lblJeu.setForeground(Color.WHITE);
			lblJeu.setFont(new Font("Sora", Font.BOLD, 17));
			lblJeu.setBounds(32, 98, 252, 23);
			contentPanel.add(lblJeu);
		}
		{
			JLabel bg = new JLabel("");
			bg.setIcon(new ImageIcon(PageGestionEquipe.class.getResource("/views/img/bg.png")));
			bg.setBounds(0, 0, 407, 335);
			contentPanel.add(bg);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(-1, 334, 676, 38);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Valider");
				okButton.setBounds(228, 10, 76, 21);
				okButton.setBackground(Const.BUTTON_COLOR);
				okButton.setForeground(Color.WHITE);
				okButton.setActionCommand("OK");
				okButton.addMouseListener(new EquipeControlleurs(this, esp));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.setBounds(314, 10, 81, 21);
				cancelButton.setBackground(Color.WHITE);
				cancelButton.setForeground(Const.BUTTON_COLOR);
				cancelButton.setActionCommand("Cancel");
				cancelButton.addMouseListener(new EquipeControlleurs(this, esp));
				buttonPane.add(cancelButton);
			}
			if(equipe != null) {
				JButton btnSupprimer1 = new JButton("Supprimer");
				btnSupprimer1.setForeground(Color.WHITE);
				btnSupprimer1.setBackground(Const.DELETE_BUTTON_COLOR);
				btnSupprimer1.setBounds(10, 10, 106, 21);
				btnSupprimer1.addMouseListener(new EquipeControlleurs(this,esp));
				buttonPane.add(btnSupprimer1);
			}
			
		}
	}
}
