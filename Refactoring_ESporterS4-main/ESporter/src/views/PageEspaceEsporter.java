package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controlleurs.PageEsporterControlleur;
import model.arbitre.Arbitre;
import model.arbitre.ArbitreManager;
import model.ecurie.Ecurie;
import model.ecurie.EcurieManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;
import model.tournoi.Tournoi;
import model.tournoi.TournoiManager;
import model.MajListe;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.AbstractListModel;

public class PageEspaceEsporter extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panelGererJeux;
	private JPanel panelGereEcuries;
	private JPanel panelGererTournois;
	private JButton btnGererJeux;
	private JButton btnGererLesEcuries;
	private JButton btnGererLesTournois;
	private JButton btnGererLesArbitres;
	private JPanel panelGererArbitres;

	
	public JList<Jeu> listJeux;
	public JList<Ecurie> listEcuries;
	public JList<Arbitre> listArbitre;
	public JList<Tournoi> listTournoi;
	private PageEsporterControlleur controlleur;

	public JList getListEcuries() {
		return this.listEcuries;
	}
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PageEspaceEsporter() throws SQLException {
		this.controlleur = new PageEsporterControlleur(this);
		setResizable(false);
		setTitle("Espace Esporter");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PageEspaceEsporter.class.getResource("img/logoSimple.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logoTopLeft = new JLabel("");
		logoTopLeft.setIcon(new ImageIcon(PageEspaceEsporter.class.getResource("/views/img/logo+ecriture2.png")));
		logoTopLeft.setBounds(10, 5, 241, 82);
		contentPane.add(logoTopLeft);
		
		JButton homeButton = new JButton("");
		homeButton.setBackground(Color.LIGHT_GRAY);
		homeButton.addMouseListener(this.controlleur);
		homeButton.setIcon(new ImageIcon(PageEspaceEsporter.class.getResource("img/HomeIcon.png")));
		homeButton.setBounds(615, 28, 41, 41);
		contentPane.add(homeButton);
		
		JButton btnDconnection = new JButton("Déconnexion");
		btnDconnection.addMouseListener(this.controlleur);
		setDefaultButtonDesign(btnDconnection, 25);
		btnDconnection.setBounds(672, 28, 211, 41);
		contentPane.add(btnDconnection);
		
		JLabel navBarreTop = new JLabel("New label");
		navBarreTop.setIcon(new ImageIcon(PageEspaceEsporter.class.getResource("img/bg.png")));
		navBarreTop.setBounds(0, 0, 928, 92);
		contentPane.add(navBarreTop);
		
		panelGereEcuries = new JPanel();
		panelGereEcuries.setVisible(false);
		panelGereEcuries.setBounds(66, 189, 817, 264);
		contentPane.add(panelGereEcuries);
		panelGereEcuries.setLayout(null);
		
		JLabel lblListEcurie = new JLabel("Liste écuries :");
		lblListEcurie.setBounds(10, 10, 190, 33);
		lblListEcurie.setFont(new Font("Sora", Font.BOLD, 25));
		panelGereEcuries.add(lblListEcurie);
		
		JButton btnAjoutEcurie = new JButton("Ajouter une écurie");
		btnAjoutEcurie.addMouseListener(this.controlleur);
		btnAjoutEcurie.setBounds(549, 10, 258, 41);
		setDefaultButtonDesign(btnAjoutEcurie, 20);
		panelGereEcuries.add(btnAjoutEcurie);
		
		this.listEcuries = new JList();
		this.listEcuries.addMouseListener(this.controlleur);
		
		this.listEcuries.setFont(new Font("Sora", Font.BOLD, 20));
		MajListe.MAJListGenerique(EcurieManager.getInstance().getTabEcuries(),this.listEcuries);
		
		JScrollPane scrollPaneEcurie = new JScrollPane(listEcuries);
		scrollPaneEcurie.setBounds(10, 61, 797, 203);
		panelGereEcuries.add(scrollPaneEcurie);
		this.listEcuries.setBackground(new Color(220, 220, 220));
		this.listEcuries.setBounds(10, 60, 796, 204);
		
		panelGererTournois = new JPanel();
		panelGererTournois.setVisible(false);
		panelGererTournois.setBounds(66, 189, 817, 264);
		contentPane.add(panelGererTournois);
		panelGererTournois.setLayout(null);
		
		JLabel lblListTournois = new JLabel("Liste tournois :");
		lblListTournois.setBounds(10, 10, 190, 33);
		lblListTournois.setFont(new Font("Sora", Font.BOLD, 25));
		panelGererTournois.add(lblListTournois);
		
		this.listTournoi = new JList();
		listTournoi.addMouseListener(this.controlleur);
		listTournoi.setFont(new Font("Sora", Font.BOLD, 20));
		listTournoi.setBounds(10, 60, 796, 204);
		listTournoi.setBackground(new Color(220, 220, 220));
		MajListe.MAJListGenerique(TournoiManager.getInstance().getTabTournoi(),this.listTournoi);
		
		
		JButton btnAjoutTournoi = new JButton("Ajouter un tournoi");
		btnAjoutTournoi.addMouseListener(this.controlleur);
		JScrollPane scrollTournois = new JScrollPane(listTournoi);
		scrollTournois.setBounds(10, 61, 797, 203);
		panelGererTournois.add(scrollTournois);
		btnAjoutTournoi.setBounds(567, 10, 240, 41);
		setDefaultButtonDesign(btnAjoutTournoi, 20);
		panelGererTournois.add(btnAjoutTournoi);
		
		JPanel panel = new JPanel();
		panel.setBounds(65, 115, 818, 64);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		btnGererJeux = new JButton("Gerer les jeux");
		btnGererJeux.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnGererJeux.setForeground(Color.LIGHT_GRAY);
				btnGererJeux.setBackground(SystemColor.controlDkShadow);
				btnGererLesTournois.setForeground(SystemColor.controlDkShadow);
				btnGererLesTournois.setBackground(Color.LIGHT_GRAY);
				btnGererLesEcuries.setForeground(SystemColor.controlDkShadow);
				btnGererLesEcuries.setBackground(Color.LIGHT_GRAY);
				btnGererLesArbitres.setForeground(SystemColor.controlDkShadow);
				btnGererLesArbitres.setBackground(Color.LIGHT_GRAY);
				panelGererJeux.setVisible(true);
				panelGereEcuries.setVisible(false);
				panelGererTournois.setVisible(false);
				panelGererArbitres.setVisible(false);
			}
		});
		setDefaultButtonDesign(btnGererJeux, 15);
		panel.add(btnGererJeux);
		
		btnGererLesEcuries = new JButton("Gerer les écuries");
		btnGererLesEcuries.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnGererJeux.setForeground(SystemColor.controlDkShadow);
				btnGererJeux.setBackground(Color.LIGHT_GRAY);
				btnGererLesTournois.setForeground(SystemColor.controlDkShadow);
				btnGererLesTournois.setBackground(Color.LIGHT_GRAY);
				btnGererLesEcuries.setForeground(Color.LIGHT_GRAY);
				btnGererLesEcuries.setBackground(SystemColor.controlDkShadow);
				btnGererLesArbitres.setForeground(SystemColor.controlDkShadow);
				btnGererLesArbitres.setBackground(Color.LIGHT_GRAY);
				panelGererJeux.setVisible(false);
				panelGereEcuries.setVisible(true);
				panelGererTournois.setVisible(false);
				panelGererArbitres.setVisible(false);
			}
		});
		setDefaultButtonDesign(btnGererLesEcuries, 15);
		panel.add(btnGererLesEcuries);
		
		btnGererLesTournois = new JButton("Gerer les tournois");
		btnGererLesTournois.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnGererJeux.setForeground(SystemColor.controlDkShadow);
				btnGererJeux.setBackground(Color.LIGHT_GRAY);
				btnGererLesTournois.setForeground(Color.LIGHT_GRAY);
				btnGererLesTournois.setBackground(SystemColor.controlDkShadow);
				btnGererLesEcuries.setForeground(SystemColor.controlDkShadow);
				btnGererLesEcuries.setBackground(Color.LIGHT_GRAY);
				btnGererLesArbitres.setForeground(SystemColor.controlDkShadow);
				btnGererLesArbitres.setBackground(Color.LIGHT_GRAY);
				panelGererJeux.setVisible(false);
				panelGereEcuries.setVisible(false);
				panelGererTournois.setVisible(true);
				panelGererArbitres.setVisible(false);
			}
		});
		setDefaultButtonDesign(btnGererLesTournois, 15);
		panel.add(btnGererLesTournois);
		
		btnGererLesArbitres = new JButton("Gerer les arbitres");
		btnGererLesArbitres.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnGererJeux.setForeground(SystemColor.controlDkShadow);
				btnGererJeux.setBackground(Color.LIGHT_GRAY);
				btnGererLesTournois.setForeground(SystemColor.controlDkShadow);
				btnGererLesTournois.setBackground(Color.LIGHT_GRAY);
				btnGererLesEcuries.setForeground(SystemColor.controlDkShadow);
				btnGererLesEcuries.setBackground(Color.LIGHT_GRAY);
				btnGererLesArbitres.setForeground(Color.LIGHT_GRAY);
				btnGererLesArbitres.setBackground(SystemColor.controlDkShadow);
				panelGererJeux.setVisible(false);
				panelGereEcuries.setVisible(false);
				panelGererTournois.setVisible(false);
				panelGererArbitres.setVisible(true);
			}
		});
		setDefaultButtonDesign(btnGererLesArbitres, 15);
		panel.add(btnGererLesArbitres);
		
		panelGererJeux = new JPanel();
		
		panelGererJeux.setBounds(66, 189, 817, 264);
		contentPane.add(panelGererJeux);
		panelGererJeux.setVisible(true);
		panelGererJeux.setLayout(null);
		
		JLabel lblListeJeu = new JLabel("Liste jeux :");
		lblListeJeu.setFont(new Font("Sora", Font.BOLD, 25));
		lblListeJeu.setBounds(10, 10, 249, 33);
		panelGererJeux.add(lblListeJeu);
		
		JButton btnAjoutJeu = new JButton("Ajouter un jeu");
		btnAjoutJeu.addMouseListener(this.controlleur);
		setDefaultButtonDesign(btnAjoutJeu, 20);
		btnAjoutJeu.setBounds(582, 10, 225, 41);
		panelGererJeux.add(btnAjoutJeu);
		
		this.listJeux = new JList();
		listJeux.addMouseListener(this.controlleur);
		listJeux.setFont(new Font("Sora", Font.BOLD, 20));
		listJeux.setBackground(new Color(220, 220, 220));
		MajListe.MAJListGenerique(JeuManager.getInstance().getTabJeu(),this.listJeux);
		
		JScrollPane scrollPaneJeux = new JScrollPane(listJeux);
		scrollPaneJeux.setBounds(10, 61, 797, 203);
		panelGererJeux.add(scrollPaneJeux);
		listJeux.setBounds(10, 60, 796, 204);
		
		
		panelGererArbitres = new JPanel();
		panelGererArbitres.setBounds(66, 189, 817, 264);
		contentPane.add(panelGererArbitres);
		panelGererArbitres.setLayout(null);
		panelGererArbitres.setVisible(false);
		
		JButton btnAjoutArbitre = new JButton("Ajouter un arbitre");
		btnAjoutArbitre.addMouseListener(this.controlleur);
		btnAjoutArbitre.setSize(225, 41);
		btnAjoutArbitre.setLocation(582, 10);
		setDefaultButtonDesign(btnAjoutArbitre, 20);
		panelGererArbitres.add(btnAjoutArbitre);
		
		JLabel lblListArbitre = new JLabel("Liste arbitres :");
		lblListArbitre.setBounds(10, 10, 249, 33);
		lblListArbitre.setFont(new Font("Sora", Font.BOLD, 25));
		panelGererArbitres.add(lblListArbitre);
		
		listArbitre = new JList<Arbitre>();
		MajListe.MAJListGenerique(ArbitreManager.getInstance().getTabArbitres(), this.listArbitre);
		listArbitre.addMouseListener(this.controlleur);
		
		JScrollPane scrollArbite = new JScrollPane(listArbitre);
		scrollArbite.setBounds(10, 61, 797, 203);
		panelGererArbitres.add(scrollArbite);
		listArbitre.setBounds(10, 60, 796, 204);
		listArbitre.setFont(new Font("Sora", Font.BOLD, 20));
		listArbitre.setBackground(new Color(220, 220, 220));
	}
	
	private void setDefaultButtonDesign(JButton btn, int fontSize) {
		btn.setForeground(SystemColor.controlDkShadow);
		btn.setFont(new Font("Sora", Font.BOLD, fontSize));
		btn.setBackground(Color.LIGHT_GRAY);
	}
}
