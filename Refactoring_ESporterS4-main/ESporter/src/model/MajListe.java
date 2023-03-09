package model;

import javax.swing.*;

public class MajListe {

    /**
     * Met à jour une liste générique
     * @param liste Liste générique
     * @param listObjet JList à mettre à jour
     */
    public static void MAJListGenerique(Object[] liste, JList listObjet) {
        listObjet.setModel(new AbstractListModel() {
            public int getSize() {
                return liste.length;
            }
            public Object getElementAt(int index) {
                return liste[index];
            }
        });
    }
}
