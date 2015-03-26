package ar.edu.untref.imagenes.tps.main;

import java.awt.EventQueue;

import ar.edu.untref.imagenes.tps.forms.PrincipalForm;

public class MainPrincipal {

	public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new PrincipalForm().setVisible(true);
            }
        });
	}

}
