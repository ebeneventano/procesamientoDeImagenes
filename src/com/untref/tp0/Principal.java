package com.untref.tp0;
import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Principal extends JFrame {
    
    public static void main(String args[]) {

    	EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new FormularioPrincipal().setVisible(true);
            }
        });
    }
}
