/*
 * Created by JFormDesigner on Fri Mar 20 20:14:54 GFT 2015
 */

package com.untref.tp0;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioTercerPunto extends JFrame {
    private BufferedImage bmp ;

	public FormularioTercerPunto() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
		buttonCrearImagen = new JButton();
		scrollPane1 = new JScrollPane();
		imagenLabel = new JLabel();
		buttonGuardar = new JButton();

		//======== this ========
		setTitle("Punto 3 - Imagen Binaria con Cuadrado en el medio");
		Container contentPane = getContentPane();

		//---- buttonCrearImagen ----
		buttonCrearImagen.setText("Crear Imagen");
		buttonCrearImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCrearImagenConCuadradoCentrado(evt);
            }
        });

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(imagenLabel);
		}

		//---- buttonGuardar ----
		buttonGuardar.setText("Guardar");
		buttonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonGuardarImagen(evt);
            }
        });

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(75, 75, 75)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(buttonCrearImagen)
							.addGap(18, 18, 18)
							.addComponent(buttonGuardar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(28, 28, 28)
					.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(buttonCrearImagen)
						.addComponent(buttonGuardar))
					.addContainerGap(47, Short.MAX_VALUE))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

    private void jButtonCrearImagenConCuadradoCentrado(ActionEvent evt) {
        imagenLabel.setIcon(new ImageIcon(crearImagen()));

    }
    
    private void jButtonGuardarImagen(ActionEvent evt) {
        guardarImagen();
    }
    
	private BufferedImage crearImagen() {
    	bmp = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_BINARY);

        for(int i = 0; i < 200 ; i++){
        	for(int j = 0; j < 200; j++){
        		bmp.setRGB(j, i, 255);
        	}
        }
        
        bmp.setRGB(99, 99, -1);
        bmp.setRGB(99, 100, -1);
        bmp.setRGB(99, 101, -1);
        bmp.setRGB(100, 99, -1);
        bmp.setRGB(100, 100, -1);
        bmp.setRGB(100, 101, -1);
        bmp.setRGB(101, 99, -1);
        bmp.setRGB(101, 100, -1);
        bmp.setRGB(101, 101, -1);


        
        return bmp;
	}
	
    public void guardarImagen(){
    	File outputfile = new File("C:/imagenes/punto3.jpg");
    	try {
			ImageIO.write(bmp, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
	private JButton buttonCrearImagen;
	private JScrollPane scrollPane1;
	private JLabel imagenLabel;
	private JButton buttonGuardar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
