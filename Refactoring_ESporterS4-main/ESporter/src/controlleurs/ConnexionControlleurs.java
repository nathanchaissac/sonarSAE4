package controlleurs;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;

import model.users.UserManager;
import views.PageAccueil;
import views.PageConnexion;

public class ConnexionControlleurs implements ActionListener{
	private PageConnexion conx ;
	
	public ConnexionControlleurs(PageConnexion conx) {
		this.conx = conx;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		try {
			if(conx.getIdentifiantField().getText().isEmpty() || conx.getPassField().getPassword().length == 0) {
				this.conx.getErrorMessage().setVisible(true);
			}else {
				if(UserManager.getInstance().tryConnect(this.conx.getIdentifiantField().getText(), this.conx.getPassField().getText())) {
					PageAccueil accueil = new PageAccueil();
					accueil.setVisible(true);
					this.conx.dispose();
				}else {
					this.conx.getErrorMessage().setVisible(true);
				}
			}
		} catch (SQLException | NoSuchAlgorithmException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
	}

}
