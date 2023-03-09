package views;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlleurs.AccueilControlleurs;
import model.DBconnec;
import model.equipe.Equipe;
import model.equipe.EquipeManager;
import model.jeux.Jeu;
import model.jeux.JeuManager;

import java.awt.Toolkit;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class PageAccueil extends JFrame {
	private JPanel contentPane;
	private JList listClassement;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PageAccueil() throws SQLException {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PageAccueil.class.getResource("img/logoSimple.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 942, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//closing connection when window closed 
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	try {
					DBconnec.getInstance().endConnec();
					System.out.println("Connexion fermée");
				} catch (SQLException e) {
					e.printStackTrace();
				}
		        System.exit(0);
		    }
		});
		
		JComboBox comboJeu = new JComboBox();
		comboJeu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					System.out.println(( (Jeu) comboJeu.getSelectedItem()).getId());
					MAJListEquipe(EquipeManager.getInstance().classementEquipePourJeu((Jeu) comboJeu.getSelectedItem()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboJeu.setModel(new DefaultComboBoxModel(JeuManager.getInstance().getTabJeu()));
		comboJeu.setFont(new Font("Sora", Font.BOLD, 20));
		comboJeu.setBounds(250, 132, 483, 37);
		comboJeu.setForeground(new Color(169, 169, 169));
		contentPane.add(comboJeu);
		
		JButton btnNewButton = new JButton("Mon espace");
		btnNewButton.addMouseListener(new AccueilControlleurs(this));
		JLabel lblNewLabel3 = new JLabel("Classement :");
		lblNewLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel3.setForeground(new Color(169, 169, 169));
		lblNewLabel3.setFont(new Font("Sora", Font.BOLD, 30));
		lblNewLabel3.setBounds(32, 126, 211, 48);
		contentPane.add(lblNewLabel3);
		
		btnNewButton.setForeground(new Color(105, 105, 105));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setFont(new Font("Sora", Font.BOLD, 25));
		btnNewButton.setBounds(672, 28, 211, 41);
		contentPane.add(btnNewButton);
		
		JLabel lblLogoEsporter = new JLabel("New label");
		lblLogoEsporter.setIcon(new ImageIcon(getClass().getResource("img/logo+ecriture2.png")));
		lblLogoEsporter.setBounds(10, 5, 241, 82);
		contentPane.add(lblLogoEsporter);
		
		JLabel lblTopBarre = new JLabel("New label");
		lblTopBarre.setIcon(new ImageIcon(getClass().getResource("img/bg.png")));
		lblTopBarre.setBounds(0, 0, 928, 92);
		contentPane.add(lblTopBarre);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("img/bg.png")));
		lblNewLabel_2.setBounds(32, 126, 858, 48);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("N°");
		lblNewLabel_5_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_1_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_5_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_5_1_1.setBounds(32, 194, 189, 38);
		contentPane.add(lblNewLabel_5_1_1);
		
		JLabel lblNewLabel_5 = new JLabel("TEAM NAME");
		lblNewLabel_5.setForeground(new Color(169, 169, 169));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_5.setBounds(222, 195, 435, 37);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("POINT.S");
		lblNewLabel_5_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_5_1.setBounds(654, 196, 229, 36);
		contentPane.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon(getClass().getResource("img/bg.png")));
		lblNewLabel_4.setBounds(32, 195, 851, 37);
		contentPane.add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 232, 851, 226);
		contentPane.add(scrollPane);
		
		listClassement = new JList();
		listClassement.setFont(new Font("Sora", Font.BOLD, 20));
		scrollPane.setViewportView(listClassement);
		MAJListEquipe(EquipeManager.getInstance().classementEquipePourJeu((Jeu) comboJeu.getSelectedItem()));
		
	}
	public void MAJListEquipe(Equipe[] equipe) {
		this.listClassement.setModel(new AbstractListModel() {
			public int getSize() {
				return equipe.length;
			}
			public Object getElementAt(int index) {
				return equipe[index];
			}
		});
	}
}
