/*
 * Created by JFormDesigner on Tue Mar 24 21:29:01 GFT 2015
 */

package ar.edu.untref.imagenes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioPuntoSeis extends JFrame {
	
    private BufferedImage bmp ;
	private File archivoSeleccionado;
	private int primerPuntoX = -1;
	private int primerPuntoY = -1;
	private int segundoPuntoX = -1;
	private int segundoPuntoY = -1;

	public FormularioPuntoSeis() {
		initComponents();
	}

	private void label1MouseClicked(MouseEvent e) {
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
        
        label1.setIcon(new ImageIcon(bmp));
	}

	private void button1ActionPerformed(ActionEvent e) {
		buttonCargarImagenActionPerformed(e);
	}
	
	private void buttonCargarImagenActionPerformed(ActionEvent e) {
		label1.setIcon(new ImageIcon(abrirImagen()));
	}
	
	public BufferedImage abrirImagen() {

		bmp = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccione una imagen");

		int flag = selector.showOpenDialog(null);

		// Comprobamos que pulse en aceptar
		if (flag == JFileChooser.APPROVE_OPTION) {
			// Devuelve el fichero seleccionado
			archivoSeleccionado = selector.getSelectedFile();
			try {
				bmp = ImageIO.read(archivoSeleccionado);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Asignamos la imagen cargada a la propiedad imageActual
		return bmp;
	}

	private void button2ActionPerformed(ActionEvent e) {
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
	       
	       for(int i = desdeX ; i < hastaX ; i++){
	    	   for(int j = desdeY ; j < hastaY ; j++){
			       Color color = new Color(bmp.getRGB(i, j));
			       int blue = color.getBlue();
			       int green = color.getGreen();
			       int red = color.getRed();
			       
			       grisAcumulado += (blue + green + red) / 3;
			       contadorCantPixel++;
	    	   }
	       }
	       
	       promedio = grisAcumulado / contadorCantPixel;
	       int pixelesSeleccionados = anchoSeleccionado * altoSeleccionado;
	       labelPromedio.setText("Promedio : " + promedio + " Pixeles seleccionados : " + pixelesSeleccionados);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
		button1 = new JButton();
		button2 = new JButton();
		labelPromedio = new JLabel();
		scrollPane1 = new JScrollPane();
		label1 = new JLabel();

		//======== this ========
		setTitle("Punto 6");
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
		button2.setText("Calcular Promedio");
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
							.addGap(81, 81, 81)
							.addComponent(button1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(labelPromedio)
								.addComponent(button2)))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(93, 93, 93)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap(15, Short.MAX_VALUE)
					.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button1)
						.addComponent(button2))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(labelPromedio)
					.addGap(27, 27, 27))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
	private JButton button1;
	private JButton button2;
	private JLabel labelPromedio;
	private JScrollPane scrollPane1;
	private JLabel label1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
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
			       
			       grisAcumulado += (blue + green + red) / 3;
			       contadorCantPixel++;
	    	   }
	       }
	       
	       promedio = grisAcumulado / contadorCantPixel;
	       int pixelesSeleccionados = anchoSeleccionado * altoSeleccionado;
	       System.out.println("promedio : " + promedio + " pixelesSeleccionados: " + pixelesSeleccionados);
	}
	
	
}
