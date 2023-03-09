package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.PageEquipeControlleurs;
import model.MajListe;
import model.equipe.Equipe;
import model.joueur.Joueur;
import model.joueur.JoueurManager;
import model.tournoi.Tournoi;
import model.tournoi.TournoiManager;
import model.users.UserManager;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class PageEspaceEquipe extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panelGereTournois;
	private JButton btnGererTournois;
	private JButton btnGererJoueur;
	private JPanel panelGererJoueurs;
	private JList<Tournoi> listTournois;
	private JList<Joueur> listJoueurs;

	public JList getListJoueurs() {
		return listJoueurs;
	}
	
	public JPanel getPanelTournois() {
		return this.panelGereTournois;
	}
	
	public JButton getTournoisBtn() {
		return this.btnGererTournois;
	}
	
	public JButton getJoueurBtn() {
		return this.btnGererJoueur;
	}
	
	public JPanel getPanelJoueur() {
		return this.panelGererJoueurs;
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PageEspaceEquipe() throws SQLException {
		setResizable(false);
		setTitle("Espace Equipe");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PageEspaceEquipe.class.getResource("img/logoSimple.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel logoTopLeft = new JLabel("");
		logoTopLeft.setIcon(new ImageIcon(PageEspaceEquipe.class.getResource("/views/img/logo+ecriture2.png")));
		logoTopLeft.setBounds(10, 5, 241, 82);
		contentPane.add(logoTopLeft);

		JButton homeButton = new JButton("");
		homeButton.setBackground(Color.LIGHT_GRAY);
		homeButton.addMouseListener(new PageEquipeControlleurs(this));
		homeButton.setIcon(new ImageIcon(PageEspaceEquipe.class.getResource("img/HomeIcon.png")));
		homeButton.setBounds(615, 28, 41, 41);
		contentPane.add(homeButton);

		JButton btnDconnection = new JButton("Déconnexion");
		btnDconnection.addMouseListener(new PageEquipeControlleurs(this));
		setDefaultButtonDesign(btnDconnection, 25);
		btnDconnection.setBounds(672, 28, 211, 41);
		contentPane.add(btnDconnection);

		JLabel navBarreTop = new JLabel("New label");
		navBarreTop.setIcon(new ImageIcon(PageEspaceEquipe.class.getResource("img/bg.png")));
		navBarreTop.setBounds(0, 0, 928, 92);
		contentPane.add(navBarreTop);

		panelGereTournois = new JPanel();
		panelGereTournois.setVisible(true);
		panelGereTournois.setBounds(66, 189, 817, 264);
		contentPane.add(panelGereTournois);
		panelGereTournois.setLayout(null);

		JLabel lblListTournois = new JLabel("Liste tournois :");
		lblListTournois.setBounds(10, 10, 190, 33);
		lblListTournois.setFont(new Font("Sora", Font.BOLD, 25));
		panelGereTournois.add(lblListTournois);

		this.listTournois = new JList<Tournoi>();
		this.listTournois.setFont(new Font("Sora", Font.BOLD, 20));
		this.listTournois.addMouseListener(new PageEquipeControlleurs(this));
		MajListe.MAJListGenerique(TournoiManager.getInstance().getTabTournoiParJeu(((Equipe) UserManager.getInstance().getUser()).getJeu(), UserManager.getInstance().getUser().getId()),this.listTournois);

		JScrollPane scrollPaneTournois = new JScrollPane(listTournois);
		scrollPaneTournois.setBounds(10, 60, 796, 204);
		panelGereTournois.add(scrollPaneTournois);
		this.listTournois.setBackground(new Color(220, 220, 220));
		this.listTournois.setBounds(10, 60, 796, 204);

		JPanel panel = new JPanel();
		panel.setBounds(65, 115, 818, 64);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		btnGererTournois = new JButton("Gérer les tournois");
		btnGererTournois.addMouseListener(new PageEquipeControlleurs(this));
		setDefaultButtonDesign(btnGererTournois, 15);
		panel.add(btnGererTournois);

		btnGererJoueur = new JButton("Gérer vos joueurs");
		btnGererJoueur.addMouseListener(new PageEquipeControlleurs(this));
		setDefaultButtonDesign(btnGererJoueur, 15);
		panel.add(btnGererJoueur);

		panelGererJoueurs = new JPanel();
		panelGererJoueurs.setBounds(66, 189, 817, 264);
		contentPane.add(panelGererJoueurs);
		panelGererJoueurs.setLayout(null);
		panelGererJoueurs.setVisible(false);

		JButton btnAjoutJoueur = new JButton("Ajouter un joueur");
		btnAjoutJoueur.setSize(225, 41);
		btnAjoutJoueur.setLocation(582, 10);
		setDefaultButtonDesign(btnAjoutJoueur, 20);
		btnAjoutJoueur.addMouseListener(new PageEquipeControlleurs(this));
		panelGererJoueurs.add(btnAjoutJoueur);

		JLabel lblListJoueur = new JLabel("Liste joueurs :");
		lblListJoueur.setBounds(10, 10, 249, 33);
		lblListJoueur.setFont(new Font("Sora", Font.BOLD, 25));
		panelGererJoueurs.add(lblListJoueur);

		listJoueurs = new JList<Joueur>();
		listJoueurs.setBounds(10, 60, 796, 204);
		listJoueurs.addMouseListener(new PageEquipeControlleurs(this));
		MajListe.MAJListGenerique(JoueurManager.getInstance().getTabJoueurPourEquipe((Equipe) UserManager.getInstance().getUser()),this.listJoueurs);

		JScrollPane scrollPaneJoueurs = new JScrollPane(listJoueurs);
		scrollPaneJoueurs.setBounds(10, 61, 797, 203);
		panelGererJoueurs.add(scrollPaneJoueurs);
		listJoueurs.setFont(new Font("Sora", Font.BOLD, 20));
		listJoueurs.setBackground(new Color(220, 220, 220));
	}

	private void setDefaultButtonDesign(JButton btn, int fontSize) {
		btn.setForeground(SystemColor.controlDkShadow);
		btn.setFont(new Font("Sora", Font.BOLD, fontSize));
		btn.setBackground(Color.LIGHT_GRAY);
	}
	
}
