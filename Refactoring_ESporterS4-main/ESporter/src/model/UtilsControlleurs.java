package model;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import views.PageEspaceArbitre;

public class UtilsControlleurs {
	
	/**
	 * Affiche une boîte de dialogue pour valider une suppression
	 * @param message Message de la boîte de dialogue affichée
	 * @param page Page sur laquelle la boîte de dialogue est lancée
	 * @return retourne l'option cliquée
	 */
	public static int dispConfirmSupprDialogBox(String message, Object page) {
		return JOptionPane.showConfirmDialog((Component) page, message, "Supprimer", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, new ImageIcon(PageEspaceArbitre.class.getResource("img/poubelle.png")));
	}
	
	/**
	 * Affiche une boîte de dialogue pour valider une déconnexion
	 * @param message Message de la boîte de dialogue affichée
	 * @param page Page sur laquelle la boîte de dialogue est lancée
	 * @return retourne l'option cliquée
	 */
	public static int dispConfirmDecoDialogBox(String message, Object page) {
		return JOptionPane.showConfirmDialog((Component) page, message, "Déconnexion", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, new ImageIcon(PageEspaceArbitre.class.getResource("img/poubelle.png")));
	}
	
	/**
	 * Affiche une boîte de dialogue pour informer d'une erreur
	 * @param message Message de la boîte de dialogue affichée
	 * @param o Page sur laquelle la boîte de dialogue est lancée
	 */
	public static void dispErrorDialogBox(String message, Object page) {
		JOptionPane.showMessageDialog((Component) page, message, "Erreur", 0,
				new ImageIcon(PageEspaceArbitre.class.getResource("img/erreurIcon.png")));
	}
}
