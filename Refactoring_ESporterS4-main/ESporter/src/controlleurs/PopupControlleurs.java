package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;

import views.PageConnexion;
import views.PageEspaceArbitre;

public class PopupControlleurs extends MouseAdapter{
	
	private String request;
	private JDialog popup;
	
	public PopupControlleurs(String request, JDialog popup) {
		this.request = request;
		this.popup = popup;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		if(this.popup != null) {
			if(btn.getText().equals("Cancel") && this.request.equals("logout")) {
				PageEspaceArbitre arbitre;
				try {
					arbitre = new PageEspaceArbitre();
					arbitre.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else if(btn.getText().equals("OK") && this.request.equals("logout")) {
				PageConnexion con = new PageConnexion();
				con.setVisible(true);
			}
		}
		this.popup.dispose();
	}

}
