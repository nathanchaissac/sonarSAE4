package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlleurs.PopupControlleurs;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.Toolkit;

public class PopUpValidation extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public PopUpValidation(String title, String desc, String requete, JFrame prespage) {
		setResizable(false);
		setType(Type.POPUP);
		setTitle("Pop-up Confirmation");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PopUpValidation.class.getResource("img/logoSimple.png")));
		setBounds(100, 100, 350, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel popUpTitle = new JLabel(title);
		popUpTitle.setHorizontalAlignment(SwingConstants.CENTER);
		popUpTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		popUpTitle.setBounds(10, 10, 316, 41);
		contentPanel.add(popUpTitle);
		
		JTextPane descPopUp = new JTextPane();
		descPopUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descPopUp.setText(desc);
		descPopUp.setEditable(false);
		descPopUp.setBounds(10, 50, 316, 72);
		contentPanel.add(descPopUp);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new PopupControlleurs("logout", this));
				okButton.setForeground(new Color(0, 100, 0));
				okButton.setBackground(new Color(34, 139, 34));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new PopupControlleurs("logout", this));
				cancelButton.setForeground(new Color(128, 0, 0));
				cancelButton.setBackground(new Color(255, 0, 0));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
