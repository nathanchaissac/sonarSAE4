package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.arbitre.ArbitreManager;
import model.match.MatchManager;
import model.match.Match;
import model.users.UserManager;
import model.MajListe;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class PageEspaceArbitre extends JFrame {

	private JPanel contentPane;
	private JList listMatch;

	public JList getlistMatch() {
		return this.listMatch;
	}
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PageEspaceArbitre() throws SQLException {
		System.out.println(UserManager.getInstance().getUser().getId());
		setResizable(false);
		setTitle("Espace Arbitre");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PageEspaceArbitre.class.getResource("img/logoSimple.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 942, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDconnection = new JButton("Déconnexion");
		btnDconnection.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(contentPane,"êtes-vous sûr de vous déconnecter ?", "Déconnection",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, new ImageIcon(PageEspaceArbitre.class.getResource("img/logOut.png")));
				if(result == JOptionPane.YES_OPTION) {
					PageConnexion con = new PageConnexion();
					con.setVisible(true);
					dispose();
				} else {}
			}
		});
		
		JButton homeButton = new JButton("");
		homeButton.setBackground(Color.LIGHT_GRAY);
		homeButton.addMouseListener(new MouseAdapter() {
			private PageAccueil accueil;

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					accueil = new PageAccueil();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				accueil.setVisible(true);
				dispose();
			}
		});
		homeButton.setIcon(new ImageIcon(PageEspaceArbitre.class.getResource("img/HomeIcon.png")));
		homeButton.setBounds(615, 28, 41, 41);
		contentPane.add(homeButton);
		btnDconnection.setForeground(SystemColor.controlDkShadow);
		btnDconnection.setFont(new Font("Sora", Font.BOLD, 25));
		btnDconnection.setBackground(Color.LIGHT_GRAY);
		btnDconnection.setBounds(672, 28, 211, 41);
		contentPane.add(btnDconnection);
		
		JLabel logoTopLeft = new JLabel("New label");
		logoTopLeft.setIcon(new ImageIcon(PageEspaceArbitre.class.getResource("img/logo+ecriture2.png")));
		logoTopLeft.setBounds(10, 5, 241, 82);
		contentPane.add(logoTopLeft);
		
		JLabel navBarreTop = new JLabel("New label");
		navBarreTop.setIcon(new ImageIcon(PageEspaceArbitre.class.getResource("img/bg.png")));
		navBarreTop.setBounds(0, 0, 928, 92);
		contentPane.add(navBarreTop);
		listMatch = new JList();
		MajListe.MAJListGenerique(MatchManager.getInstance().getTabMatchPourArbitre(ArbitreManager.getInstance().getIDArbitresParID(UserManager.getInstance().getUser().getId())),this.listMatch);
		Match[] mtch = MatchManager.getInstance().getTabMatchPourArbitre(ArbitreManager.getInstance().getIDArbitresParID(UserManager.getInstance().getUser().getId()));
		System.out.println(mtch.length);

		
		listMatch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PopUpScore entry = new PopUpScore(PageEspaceArbitre.this,(Match) listMatch.getSelectedValue());
				entry.setVisible(true);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(listMatch);
		scrollPane.setBounds(54, 191, 829, 272);
		contentPane.add(scrollPane);
		listMatch.setFont(new Font("Sora", Font.BOLD, 22));
		listMatch.setBounds(54, 206, 829, 247);
		
		JLabel lblNewLabel = new JLabel(" Liste des matchs a arbitrer :");
		lblNewLabel.setFont(new Font("Sora", Font.BOLD, 25));
		lblNewLabel.setBounds(54, 129, 683, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel2 = new JLabel(" Cliquer sur le match dans la liste pour y selectionner le vainqueur");
		lblNewLabel2.setFont(new Font("Sora", Font.PLAIN, 15));
		lblNewLabel2.setBounds(54, 166, 489, 30);
		contentPane.add(lblNewLabel2);
	}
}
