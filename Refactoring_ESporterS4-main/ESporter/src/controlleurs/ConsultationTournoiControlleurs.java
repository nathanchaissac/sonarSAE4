package controlleurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import views.PageConsultationTournoi;

public class ConsultationTournoiControlleurs extends MouseAdapter {
	
	private PageConsultationTournoi page;
	
	public ConsultationTournoiControlleurs(PageConsultationTournoi page) {
		this.page = page;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			if(btn.getText().equalsIgnoreCase("Rejoindre")) {
				
			}else if(btn.getText().equalsIgnoreCase("Annuler")) {
				this.page.dispose();
			}
		}
	}

}
