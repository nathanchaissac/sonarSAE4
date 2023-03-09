package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePicker;

import controlleurs.TournoiControlleurs;
import model.Const;
import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.tournoi.ImportanceTournoi;
import model.tournoi.Tournoi;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class PageGererTournoi extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNom;
	private JTextField textLieu;
	private JComboBox<Arbitre> comboBoxArbitre;
	private JComboBox<Jeu> comboBoxJeux;
	private JDatePicker datePickerFin;
	private JDatePicker datePicker;
	private JComboBox<ImportanceTournoi> comboBoxImportance;
	private Arbitre[] tabArbitre;
	
	public JTextField getNomField() {
		return this.textFieldNom;
	}
	
	public JTextField getLieuField() {
		return this.textLieu;
	}
	
	public JComboBox<Arbitre> getComboBoxArbitres(){
		return this.comboBoxArbitre;
	}
	
	public JComboBox<Jeu> getComboBoxJeu(){
		return this.comboBoxJeux;
	}
	
	public JComboBox<ImportanceTournoi> getComboBoxImportanceTournoi(){
		return this.comboBoxImportance;
	}
	
	public JDatePicker getPickerDateFin() {
		return this.datePickerFin;
	}
	
	public JDatePicker getPickerDateTournois() {
		return this.datePicker;
	}
	
	
	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */
	public PageGererTournoi(String title, Tournoi trn, PageEspaceEsporter esp) throws SQLException {
		setResizable(false);
		setBounds(100, 100, 579, 409);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		TournoiControlleurs tc = new TournoiControlleurs(this, trn, esp);
		Jeu[] tabJeu = JeuManager.getInstance().getTabJeu();
		this.tabArbitre = null;
		
		if (trn != null) {
			JButton btnNewButton = new JButton("Supprimer le tournoi");
			btnNewButton.addMouseListener(new TournoiControlleurs(this, trn, esp));
			btnNewButton.setBackground(Color.RED);
			btnNewButton.setForeground(Color.WHITE);
			btnNewButton.setFont(new Font("Sora", Font.BOLD, 20));
			btnNewButton.setBounds(275, 269, 257, 35);
			contentPanel.add(btnNewButton);
		}

		JLabel lblAbitre = new JLabel("Arbitre");
		lblAbitre.setForeground(Color.WHITE);
		lblAbitre.setFont(new Font("Dialog", Font.BOLD, 20));
		lblAbitre.setBackground(Color.WHITE);
		lblAbitre.setBounds(21, 290, 72, 21);
		contentPanel.add(lblAbitre);

		
		{
			JLabel lblJeu = new JLabel("Jeu");
			lblJeu.setForeground(Color.WHITE);
			lblJeu.setFont(new Font("Dialog", Font.BOLD, 20));
			lblJeu.setBackground(Color.WHITE);
			lblJeu.setBounds(21, 251, 50, 21);
			contentPanel.add(lblJeu);
		}
		comboBoxJeux = new JComboBox(JeuManager.getInstance().getTabJeu());
		if (comboBoxJeux != null) {
			comboBoxJeux.addItemListener(tc);
		}
		comboBoxArbitre = new JComboBox(ArbitreManager.getInstance().getTabArbitresParJeu((Jeu) this.comboBoxJeux.getSelectedItem()));
		comboBoxArbitre.addItemListener(tc);
		comboBoxArbitre.setBounds(91, 290, 228, 21);
		contentPanel.add(comboBoxArbitre);
		
		comboBoxJeux.setBounds(61, 251, 258, 21);
		contentPanel.add(comboBoxJeux);
		{
			JLabel lblImportance = new JLabel("Importance");
			lblImportance.setForeground(Color.WHITE);
			lblImportance.setFont(new Font("Dialog", Font.BOLD, 20));
			lblImportance.setBackground(Color.WHITE);
			lblImportance.setBounds(21, 209, 109, 21);
			contentPanel.add(lblImportance);
		}
		

		this.comboBoxImportance = new JComboBox(ImportanceTournoi.values());
		comboBoxImportance.setBounds(136, 209, 183, 21);
		contentPanel.add(comboBoxImportance);

		{
			JLabel lblDate = new JLabel("Date fin inscription : ");
			lblDate.setForeground(Color.WHITE);
			lblDate.setFont(new Font("Dialog", Font.BOLD, 20));
			lblDate.setBackground(Color.WHITE);
			lblDate.setBounds(21, 112, 209, 21);
			contentPanel.add(lblDate);
		}
		{
			DateFormat date = new SimpleDateFormat("YYYY-MM-DD");
		}
		{
			JLabel lblLieu = new JLabel("Lieu");
			lblLieu.setForeground(Color.WHITE);
			lblLieu.setFont(new Font("Sora", Font.BOLD, 20));
			lblLieu.setBackground(Color.WHITE);
			lblLieu.setBounds(290, 66, 50, 21);
			contentPanel.add(lblLieu);
		}
		{
			textLieu = new JTextField();
			textLieu.setBorder(null);
			textLieu.setColumns(10);
			textLieu.setBounds(350, 66, 182, 21);
			contentPanel.add(textLieu);
		}

		JLabel lblNom = new JLabel("Nom");
		lblNom.setForeground(Color.WHITE);
		lblNom.setBackground(Color.WHITE);
		lblNom.setFont(new Font("Sora", Font.BOLD, 20));
		lblNom.setBounds(21, 66, 55, 21);
		contentPanel.add(lblNom);

		textFieldNom = new JTextField();
		textFieldNom.setBorder(null);
		textFieldNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldNom.setBounds(86, 67, 182, 21);
		contentPanel.add(textFieldNom);
		textFieldNom.setColumns(10);

		JLabel lblNewLabel1 = new JLabel(title);
		lblNewLabel1.setForeground(new Color(255, 255, 255));
		lblNewLabel1.setFont(new Font("Sora", Font.BOLD, 25));
		lblNewLabel1.setBounds(10, 10, 446, 43);
		contentPanel.add(lblNewLabel1);

		JLabel lblDateTournoi = new JLabel("Date tournoi : ");
		lblDateTournoi.setForeground(Color.WHITE);
		lblDateTournoi.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDateTournoi.setBackground(Color.WHITE);
		lblDateTournoi.setBounds(291, 112, 209, 21);
		contentPanel.add(lblDateTournoi);
		
		this.datePickerFin = new JDatePicker();
		datePickerFin.setBounds(21, 143, 150, 21);
		contentPanel.add(datePickerFin);
		datePickerFin.getModel().setSelected(true);
		
		this.datePicker = new JDatePicker();
		datePicker.setBounds(291, 143, 150, 21);
		contentPanel.add(datePicker);
		datePicker.getModel().setSelected(true);
		{
			JLabel backgroundImage = new JLabel("");
			backgroundImage.setIcon(new ImageIcon(PageGererTournoi.class.getResource("img/bg.png")));
			backgroundImage.setBounds(0, 0, 565, 341);
			contentPanel.add(backgroundImage);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton ajoutButton = new JButton("Valider");
			ajoutButton.setForeground(Color.WHITE);
			ajoutButton.setPreferredSize(Const.BUTTON_SIZE);
			ajoutButton.setBackground(Const.BUTTON_COLOR);
			ajoutButton.setActionCommand("OK");
			ajoutButton.addMouseListener(tc);
			buttonPane.add(ajoutButton);

			JButton cancelButton = new JButton("Annuler");
			cancelButton.addMouseListener(tc);
			cancelButton.setPreferredSize(Const.BUTTON_SIZE);
			cancelButton.setForeground(Const.BUTTON_COLOR);
			cancelButton.setBackground(Color.WHITE);
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}
}
