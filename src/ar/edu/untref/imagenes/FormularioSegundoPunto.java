/*
 * Created by JFormDesigner on Fri Mar 20 20:14:54 GFT 2015
 */

package ar.edu.untref.imagenes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioSegundoPunto extends JFrame {
    private BufferedImage bmp ;

	public FormularioSegundoPunto() {
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
		setTitle("Punto 2 - Imagen Binaria con Circulo en el medio");
		Container contentPane = getContentPane();

		//---- buttonCrearImagen ----
		buttonCrearImagen.setText("Crear Imagen");
		buttonCrearImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCrearImagenConCirculoCentrado(evt);
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
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
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

    private void jButtonCrearImagenConCirculoCentrado(ActionEvent evt) {
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
        
//        bmp.setRGB(100, 99, -1);
//        bmp.setRGB(100, 100, -1);
//        bmp.setRGB(100, 101, -1);
//        bmp.setRGB(99, 100, -1);
//        bmp.setRGB(101, 100, -1);
        
        Graphics2D g = bmp.createGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(100, 100, 30, 30);
        
        return bmp;
	}
	
    public void guardarImagen(){
    	File outputfile = new File("C:/imagenes/punto2.jpg");
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
