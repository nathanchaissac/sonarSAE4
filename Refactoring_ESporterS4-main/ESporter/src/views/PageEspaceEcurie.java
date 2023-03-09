package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.PageEcurieControlleurs;
import model.MajListe;
import model.ecurie.Ecurie;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.users.UserManager;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JList;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class PageEspaceEcurie extends JFrame {

	private JPanel contentPane;
	private JList<Equipe> listEquipes;

	public JList getListEquipe() {
		return this.listEquipes;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PageEspaceEcurie() throws SQLException {
		setResizable(false);
		setTitle("Espace Ecurie");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PageEspaceEsporter.class.getResource("img/logoSimple.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logoTopLeft = new JLabel("");
		logoTopLeft.setIcon(new ImageIcon(PageEspaceEcurie.class.getResource("/views/img/logo+ecriture2.png")));
		logoTopLeft.setBounds(10, 5, 241, 82);
		contentPane.add(logoTopLeft);
		
		JButton homeButton = new JButton("");
		homeButton.setBackground(Color.LIGHT_GRAY);
		homeButton.addMouseListener(new PageEcurieControlleurs(this));
		homeButton.setIcon(new ImageIcon(PageEspaceEsporter.class.getResource("img/HomeIcon.png")));
		homeButton.setBounds(615, 28, 41, 41);
		contentPane.add(homeButton);
		
		JButton btnDconnection = new JButton("Déconnexion");
		btnDconnection.addMouseListener(new PageEcurieControlleurs(this));
		btnDconnection.setForeground(SystemColor.controlDkShadow);
		btnDconnection.setFont(new Font("Sora", Font.BOLD, 25));
		btnDconnection.setBackground(Color.LIGHT_GRAY);
		btnDconnection.setBounds(672, 28, 211, 41);
		contentPane.add(btnDconnection);
		
		JLabel navBarreTop = new JLabel("New label");
		navBarreTop.setIcon(new ImageIcon(PageEspaceEsporter.class.getResource("img/bg.png")));
		navBarreTop.setBounds(0, 0, 928, 92);
		contentPane.add(navBarreTop);
		
		JButton btnAjouterUnequipe = new JButton("Ajouter une équipe");
		btnAjouterUnequipe.setForeground(SystemColor.controlDkShadow);
		btnAjouterUnequipe.setFont(new Font("Sora", Font.BOLD, 20));
		btnAjouterUnequipe.setBackground(Color.LIGHT_GRAY);
		btnAjouterUnequipe.setBounds(598, 119, 283, 50);
		btnAjouterUnequipe.addMouseListener(new PageEcurieControlleurs(this));
		contentPane.add(btnAjouterUnequipe);
		
		JLabel lblListeEquipe = new JLabel("Vos équipes :");
		lblListeEquipe.setFont(new Font("Sora", Font.BOLD, 25));
		lblListeEquipe.setBounds(54, 122, 173, 50);
		contentPane.add(lblListeEquipe);
		
		this.listEquipes = new JList();
		listEquipes.setFont(new Font("Sora", Font.BOLD, 20));
		MajListe.MAJListGenerique(EquipeManager.getInstance().equipeParEcurie((Ecurie) UserManager.getInstance().getUser()),this.listEquipes);
		listEquipes.setBackground(new Color(220, 220, 220));
		listEquipes.setBounds(54, 198, 827, 246);
		listEquipes.addMouseListener(new PageEcurieControlleurs(this));
		contentPane.add(listEquipes);
		JScrollPane scrollPane = new JScrollPane(listEquipes);
		scrollPane.setBounds(54, 198, 829, 255);
		contentPane.add(scrollPane);
	}
}
