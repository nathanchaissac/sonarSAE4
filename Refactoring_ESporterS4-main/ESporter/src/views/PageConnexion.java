	package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controlleurs.ConnexionControlleurs;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;

public class PageConnexion extends JFrame {

	private JPanel pageConnexion;
	private JTextField inputIdentifiants;
	private JPasswordField inputPassword;
	private JLabel messageErreur;
	
	public JTextField getIdentifiantField() {
		return this.inputIdentifiants;
	}
	
	public JPasswordField getPassField() {
		return this.inputPassword;
	}
	
	public JLabel getErrorMessage() {
		return this.messageErreur;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PageConnexion frame = new PageConnexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PageConnexion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/logoSimple.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 500);
		pageConnexion = new JPanel();
		pageConnexion.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pageConnexion);
		pageConnexion.setLayout(null);
		
		JLabel illustration = new JLabel("");
		illustration.setIcon(new ImageIcon(getClass().getResource("img/illustartionConnectionPage.png")));
		illustration.setBounds(0, 115, 541, 348);
		pageConnexion.add(illustration);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(PageConnexion.class.getResource("/views/img/logo+ecriture2.png")));
		logo.setBounds(53, 54, 240, 78);
		pageConnexion.add(logo);
		
		JPanel panelConnexion = new JPanel();
		panelConnexion.setBorder(null);
		panelConnexion.setBackground(new Color(192, 192, 192));
		panelConnexion.setForeground(Color.LIGHT_GRAY);
		panelConnexion.setBounds(518, 54, 301, 321);
		pageConnexion.add(panelConnexion);
		panelConnexion.setLayout(null);
		
		inputIdentifiants = new JTextField();
		inputIdentifiants.setBorder(null);
		inputIdentifiants.setFont(new Font("Tahoma", Font.PLAIN, 18));
		inputIdentifiants.setBounds(42, 120, 210, 26);
		panelConnexion.add(inputIdentifiants);
		inputIdentifiants.setColumns(10);
		
		JLabel lblMdp = new JLabel("Mot de passe :");
		lblMdp.setForeground(new Color(25, 25, 112));
		lblMdp.setFont(new Font("Sora", Font.PLAIN, 18));
		lblMdp.setBounds(42, 167, 131, 26);
		panelConnexion.add(lblMdp);
		
		JLabel lblIdentifiant = new JLabel("Identifiant :");
		lblIdentifiant.setForeground(new Color(25, 25, 112));
		lblIdentifiant.setFont(new Font("Sora", Font.PLAIN, 18));
		lblIdentifiant.setBounds(42, 93, 131, 26);
		panelConnexion.add(lblIdentifiant);
		
		JLabel lblConnexion = new JLabel("Connexion");
		lblConnexion.setForeground(new Color(25, 25, 112));
		lblConnexion.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnexion.setFont(new Font("Sora", Font.BOLD, 35));
		lblConnexion.setBounds(42, 10, 215, 73);
		panelConnexion.add(lblConnexion);
		
		JButton btnConnection = new JButton("connexion");
		btnConnection.setBackground(new Color(25, 25, 112));
		btnConnection.setFont(new Font("Sora", Font.BOLD, 20));
		btnConnection.setForeground(UIManager.getColor("Button.shadow"));
		btnConnection.setBounds(67, 246, 173, 39);
		btnConnection.addActionListener(new ConnexionControlleurs(this));
		panelConnexion.add(btnConnection);
		
		inputPassword = new JPasswordField();
		inputPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					btnConnection.doClick();
		        }
			}
		});
		inputPassword.setBorder(null);
		inputPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		inputPassword.setBounds(42, 192, 210, 26);
		panelConnexion.add(inputPassword);
		
		messageErreur = new JLabel("identifiant ou mot de passe incorrect");
		messageErreur.setVisible(false);
		messageErreur.setHorizontalAlignment(SwingConstants.CENTER);
		messageErreur.setForeground(Color.RED);
		messageErreur.setBounds(42, 73, 210, 13);
		panelConnexion.add(messageErreur);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(getClass().getResource("img/bg.png")));
		background.setBounds(0, 0, 928, 463);
		pageConnexion.add(background);
	}
}
