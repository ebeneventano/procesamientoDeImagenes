/*
 * Created by JFormDesigner on Tue Mar 24 20:51:31 GFT 2015
 */

package ar.edu.untref.imagenes;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioSeptimoPunto extends JFrame {
    private BufferedImage bmp ;
	private File archivoSeleccionado;
    private BufferedImage imagenActual;


    
	public FormularioSeptimoPunto() {
		initComponents();
	}

	private void button1ActionPerformed(ActionEvent e) {
		buttonCargarImagenActionPerformed(e);
	}

	private void buttonCargarImagenActionPerformed(ActionEvent e) {
		label1.setIcon(new ImageIcon(abrirImagen()));
	}
	
	   public BufferedImage abrirImagen(){
	    	
	    	bmp = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

	        JFileChooser selector=new JFileChooser();
	        selector.setDialogTitle("Seleccione una imagen");
	        
	        int flag=selector.showOpenDialog(null);
	        
	        //Comprobamos que pulse en aceptar
	        if(flag==JFileChooser.APPROVE_OPTION){
	                //Devuelve el fichero seleccionado
	                archivoSeleccionado=selector.getSelectedFile();
	                try {
						bmp = ImageIO.read(archivoSeleccionado);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	        
	        //Asignamos la imagen cargada a la propiedad imageActual
	        imagenActual=bmp;
	        return bmp;
	    }
	private void button2ActionPerformed(ActionEvent e) {
		int azulAcumulado = 0;
		int verdeAcumulado = 0;
		int rojoAcumulado = 0;
		int contadorCantPixel = 0;
		
		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 200; j++) {
				Color color = new Color(bmp.getRGB(i, j));
				azulAcumulado += color.getBlue();
				verdeAcumulado += color.getGreen();
				rojoAcumulado += color.getRed();

				contadorCantPixel++;
			}
		}

		int azulPromedio = azulAcumulado / contadorCantPixel;
		int rojoPromedio = rojoAcumulado / contadorCantPixel;
		int verdePromedio = verdeAcumulado / contadorCantPixel;
		
		labelPromedios.setText("Rojo: " + rojoPromedio + " Verde: " + verdePromedio + " Azul: " + azulPromedio);
		
//		int pixelesSeleccionados = anchoSeleccionado * altoSeleccionado;

	}

	private void label1MouseClicked(MouseEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
		button1 = new JButton();
		button2 = new JButton();
		scrollPane1 = new JScrollPane();
		label1 = new JLabel();
		labelPromedios = new JLabel();

		//======== this ========
		setTitle("Punto 7");
		Container contentPane = getContentPane();

		//---- button1 ----
		button1.setText("Cargar Imagen");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1ActionPerformed(e);
			}
		});

		//---- button2 ----
		button2.setText("Calcular Promedios");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button2ActionPerformed(e);
			}
		});

		//======== scrollPane1 ========
		{

			//---- label1 ----
			label1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					label1MouseClicked(e);
				}
			});
			scrollPane1.setViewportView(label1);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(82, 82, 82)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(71, 71, 71)
							.addComponent(button1)
							.addGap(27, 27, 27)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addComponent(labelPromedios, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(button2))))
					.addContainerGap(83, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addGap(42, 42, 42)
					.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button1)
						.addComponent(button2))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(labelPromedios)
					.addGap(30, 30, 30))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
	private JButton button1;
	private JButton button2;
	private JScrollPane scrollPane1;
	private JLabel label1;
	private JLabel labelPromedios;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
