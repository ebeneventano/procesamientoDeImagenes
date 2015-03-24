/*
 * Created by JFormDesigner on Fri Mar 20 20:14:54 GFT 2015
 */

package ar.edu.untref.imagenes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

import ar.edu.untref.imagenes.utils.ColorProvider;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioSextoPunto extends JFrame {

	private static final long serialVersionUID = -5624241489547478016L;

	private BufferedImage bmp;
	private JButton buttonCrearImagen;
	private JButton buttonCalcularPromedio;
	
	private JScrollPane scrollPane1;
	private JLabel imagenLabel;
	private int primerPuntoX = -1;
	private int primerPuntoY = -1;
	private int segundoPuntoX = -1;
	private int segundoPuntoY = -1;

	public FormularioSextoPunto() {
		initComponents();
	}

	private void initComponents() {
		buttonCrearImagen = new JButton();
		buttonCalcularPromedio = new JButton();
		
		scrollPane1 = new JScrollPane();
		imagenLabel = new JLabel();

		// ======== this ========
		setTitle("Punto 7");
		Container contentPane = getContentPane();

		// ---- buttonCrearImagen ----
		buttonCrearImagen.setText("Crear Imagen");
		buttonCrearImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCrearImagenConCirculoCentrado(evt);
			}
		});
		
		buttonCalcularPromedio.setText("Calcular Promedio Grises");
		buttonCalcularPromedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCalcularPromedio(evt);
			}
		});

		//======== scrollPane1 ========
		{
			imagenLabel.setSize(200,200);
			//---- imagenLabel ----
			imagenLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					imagenLabelMouseClicked(e);
				}
			});
			scrollPane1.setViewportView(imagenLabel);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout
				.setHorizontalGroup(contentPaneLayout
						.createParallelGroup()
						.addGroup(
								contentPaneLayout
										.createSequentialGroup()
										.addGroup(
												contentPaneLayout
														.createParallelGroup()
														.addGroup(
																contentPaneLayout
																		.createSequentialGroup()
																		.addGap(124,
																				124,
																				124)
																		.addComponent(
																				buttonCrearImagen)
																				.addComponent(
																						buttonCalcularPromedio))
														.addGroup(
																contentPaneLayout
																		.createSequentialGroup()
																		.addGap(75,
																				75,
																				75)
																		.addComponent(
																				scrollPane1,
																				GroupLayout.PREFERRED_SIZE,
																				203,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(74, Short.MAX_VALUE)));
		contentPaneLayout
				.setVerticalGroup(contentPaneLayout
						.createParallelGroup()
						.addGroup(
								contentPaneLayout
										.createSequentialGroup()
										.addGap(28, 28, 28)
										.addComponent(scrollPane1,
												GroupLayout.PREFERRED_SIZE,
												203, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(buttonCrearImagen)
										.addComponent(buttonCalcularPromedio)
										.addContainerGap(47, Short.MAX_VALUE)));
		pack();
		setLocationRelativeTo(getOwner());
	}

	private void jButtonCrearImagenConCirculoCentrado(ActionEvent evt) {
		primerPuntoX = -1;
		primerPuntoY = -1;
		segundoPuntoX = -1;
		segundoPuntoY = -1;
		
		imagenLabel.setIcon(new ImageIcon(crearImagen()));

	}

	private void jButtonCalcularPromedio(ActionEvent evt) {
		calcularPromedioEscalaDeGrises();
	}
	private BufferedImage crearImagen() {
		bmp = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 200; j++) {
				bmp.setRGB(i, j, ColorProvider.getIntRgbColorScale(0, 0xFF00FF00, j));
			}
		}

		return bmp;
	}


	private void imagenLabelMouseClicked(MouseEvent e) {
		if(primerPuntoX == -1 || segundoPuntoX == -1){
			if(primerPuntoX == -1 && primerPuntoY == -1){
				primerPuntoX =(int)e.getPoint().getX();
				primerPuntoY = (int) e.getPoint().getY();
	
				Graphics2D g = bmp.createGraphics();
				g.setColor(Color.BLACK);
				g.fillOval(primerPuntoX, primerPuntoY, 4, 4);
			} else {
				segundoPuntoX =(int)e.getPoint().getX();
				segundoPuntoY = (int) e.getPoint().getY();
				
				Graphics2D g = bmp.createGraphics();
				g.setColor(Color.BLACK);
				g.fillOval(segundoPuntoX, segundoPuntoY, 4, 4);
			}
		}
		
		if(primerPuntoX != -1 && segundoPuntoX != -1){
			int tercerPuntoX = primerPuntoX;
			int tercerPuntoY = segundoPuntoY;
			int cuartoPuntoX = segundoPuntoX;
			int cuartoPuntoY = primerPuntoY;
			
			Graphics2D g = bmp.createGraphics();
		    g.setColor(Color.BLACK);
		    g.drawLine(primerPuntoX, primerPuntoY, tercerPuntoX, tercerPuntoY);
		    g.drawLine(tercerPuntoX, tercerPuntoY, segundoPuntoX, segundoPuntoY);
		    g.drawLine(segundoPuntoX, segundoPuntoY, cuartoPuntoX, cuartoPuntoY);
		    g.drawLine(cuartoPuntoX, cuartoPuntoY, primerPuntoX, primerPuntoY);

		}
        
        imagenLabel.setIcon(new ImageIcon(bmp));

	}
	
	private void calcularPromedioEscalaDeGrises(){
	       int grisAcumulado = 0 ;
	       int contadorCantPixel = 0;
	       int promedio = 0;
	       
	       int desdeX = 0;
	       int hastaX = 0;
	       int desdeY = 0;
	       int hastaY = 0;
	       
	       if(primerPuntoX <= segundoPuntoX){
	    	   desdeX = primerPuntoX;
	    	   hastaX = segundoPuntoX;
	       } else {
	    	   desdeX = segundoPuntoX;
	    	   hastaX = primerPuntoX;
	       }
	       
	       if(primerPuntoY<= segundoPuntoY){
	    	   desdeY = primerPuntoY;
	    	   hastaY = segundoPuntoY;
	       } else {
	    	   desdeY = segundoPuntoY;
	    	   hastaY = primerPuntoY;
	       }
	       
	       int anchoSeleccionado = hastaX - desdeX;
	       int altoSeleccionado = hastaY - desdeY;
	       
	       for(int i = desdeX; i<=hastaX;i++){
	    	   for(int j = desdeY; j <= hastaY; j++){
			       Color color = new Color(bmp.getRGB(i, j));
			       int blue = color.getBlue();
			       int green = color.getGreen();
			       int red = color.getRed();
			       
//			       grisAcumulado += ColorProvider.getIntRgbGrayScale(red, green, blue);
			       grisAcumulado += (blue + green + red) / 3;
			       contadorCantPixel++;
	    	   }
	       }
	       
	       promedio = grisAcumulado / contadorCantPixel;
	       int pixelesSeleccionados = anchoSeleccionado * altoSeleccionado;
	       System.out.println("promedio : " + promedio + " pixelesSeleccionados: " + pixelesSeleccionados);
	}
}