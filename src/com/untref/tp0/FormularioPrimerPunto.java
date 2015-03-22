/*
 * Created by JFormDesigner on Fri Mar 20 18:12:54 GFT 2015
 */

package com.untref.tp0;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioPrimerPunto extends JFrame {
    private File archivoSeleccionado;
    private String nombreDelArchivo;
    private BufferedImage imagenActual;
    private double[][] matrizDeImagen;
    private int width;
    private int lenght;
    private BufferedImage bmp ;
    
	public FormularioPrimerPunto() {
		initComponents();
	}

	private void crearImagenNuevaButtonActionPerformed(ActionEvent e) {
		crearNuevaImagen();
	}

	private void buttonObtenerPixelActionPerformed(ActionEvent e) {
		obtenerPixelPorCoordenadas();
	}

	private void buttonCambiarPixelActionPerformed(ActionEvent e) {
		cambiarPixelPorCoordenadas();
	}

	private void buttonCargarImagenActionPerformed(ActionEvent e) {
		labelImagen.setIcon(new ImageIcon(abrirImagen()));
	}

	private void buttonGuardarImagenActionPerformed(ActionEvent e) {
		guardarImagen();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
		buttonObtenerPixel = new JButton();
		buttonCambiarPixel = new JButton();
		posicionXobtenerPixel = new JTextField();
		posicionYobtenerPixel = new JTextField();
		coordenadasObtenerPixel = new JLabel();
		coordenadasCambiarPixel = new JLabel();
		posicionXcambiarPixel = new JTextField();
		posicionYcambiarPixel = new JTextField();
		buttonCargarImagen = new JButton();
		resultado = new JLabel();
		valorResultado = new JLabel();
		buttonGuardarImagen = new JButton();
		scrollPane1 = new JScrollPane();
		labelImagen = new JLabel();
		label4 = new JLabel();
		lenghtLabel = new JLabel();
		widthTextField = new JTextField();
		lenghtTextField = new JTextField();
		cambiarPixelLabel = new JLabel();
		cambiarPixelTextField = new JTextField();
		labelDesde = new JLabel();
		desdeTextFieldX = new JTextField();
		desdeTextFieldY = new JTextField();
		labelHasta = new JLabel();
		hastaTextFieldX = new JTextField();
		hastaTextFieldY = new JTextField();
		scrollPane2 = new JScrollPane();
		labelImagenNueva = new JLabel();
		crearImagenNuevaButton = new JButton();

		//======== this ========
		Container contentPane = getContentPane();

		//---- buttonObtenerPixel ----
		buttonObtenerPixel.setText("Obtener pixel");
		buttonObtenerPixel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonObtenerPixelActionPerformed(e);
			}
		});

		//---- buttonCambiarPixel ----
		buttonCambiarPixel.setText("Cambiar pixel");
		buttonCambiarPixel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCambiarPixelActionPerformed(e);
			}
		});

		//---- coordenadasObtenerPixel ----
		coordenadasObtenerPixel.setText("(x,y)");

		//---- coordenadasCambiarPixel ----
		coordenadasCambiarPixel.setText("(x,y)");

		//---- buttonCargarImagen ----
		buttonCargarImagen.setText("Cargar imagen");
		buttonCargarImagen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCargarImagenActionPerformed(e);
			}
		});

		//---- resultado ----
		resultado.setText("Resultado:");

		//---- buttonGuardarImagen ----
		buttonGuardarImagen.setText("Guardar imagen");
		buttonGuardarImagen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonGuardarImagenActionPerformed(e);
			}
		});

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(labelImagen);
		}

		//---- label4 ----
		label4.setText("Width:");

		//---- lenghtLabel ----
		lenghtLabel.setText("Lenght:");

		//---- cambiarPixelLabel ----
		cambiarPixelLabel.setText("Valor nuevo pixel:");

		//---- labelDesde ----
		labelDesde.setText("Desde");

		//---- labelHasta ----
		labelHasta.setText("Hasta");

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(labelImagenNueva);
		}

		//---- crearImagenNuevaButton ----
		crearImagenNuevaButton.setText("Crear Imagen Nueva");
		crearImagenNuevaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearImagenNuevaButtonActionPerformed(e);
			}
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(16, 16, 16)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(buttonObtenerPixel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(coordenadasObtenerPixel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(buttonCambiarPixel)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(coordenadasCambiarPixel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(posicionXobtenerPixel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(posicionYobtenerPixel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(posicionXcambiarPixel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(posicionYcambiarPixel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)))
							.addGap(29, 29, 29)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(resultado)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(valorResultado))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(cambiarPixelLabel)
									.addGap(18, 18, 18)
									.addComponent(cambiarPixelTextField, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(buttonGuardarImagen)
								.addComponent(buttonCargarImagen, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addComponent(lenghtLabel)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(lenghtTextField))
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addComponent(label4)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(widthTextField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)))
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(40, 40, 40)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(labelDesde, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
								.addComponent(labelHasta, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
							.addGap(37, 37, 37)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(desdeTextFieldX, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
								.addComponent(hastaTextFieldX, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(desdeTextFieldY, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
								.addComponent(hastaTextFieldY, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(crearImagenNuevaButton))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(18, 18, 18)
							.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(20, 20, 20)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(buttonObtenerPixel)
								.addComponent(coordenadasObtenerPixel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(posicionXobtenerPixel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(posicionYobtenerPixel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(resultado, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(valorResultado)
								.addComponent(labelDesde)
								.addComponent(desdeTextFieldX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(desdeTextFieldY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(buttonCambiarPixel)
									.addComponent(coordenadasCambiarPixel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addComponent(posicionXcambiarPixel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(posicionYcambiarPixel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cambiarPixelLabel)
									.addComponent(cambiarPixelTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(labelHasta, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addComponent(hastaTextFieldX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(hastaTextFieldY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(crearImagenNuevaButton)))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(0, 110, Short.MAX_VALUE)
							.addComponent(buttonCargarImagen)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label4)
								.addComponent(widthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6, 6, 6)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lenghtLabel)
								.addComponent(lenghtTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(30, 30, 30)
							.addComponent(buttonGuardarImagen))
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Emmanuel Beneventano
	private JButton buttonObtenerPixel;
	private JButton buttonCambiarPixel;
	private JTextField posicionXobtenerPixel;
	private JTextField posicionYobtenerPixel;
	private JLabel coordenadasObtenerPixel;
	private JLabel coordenadasCambiarPixel;
	private JTextField posicionXcambiarPixel;
	private JTextField posicionYcambiarPixel;
	private JButton buttonCargarImagen;
	private JLabel resultado;
	private JLabel valorResultado;
	private JButton buttonGuardarImagen;
	private JScrollPane scrollPane1;
	private JLabel labelImagen;
	private JLabel label4;
	private JLabel lenghtLabel;
	private JTextField widthTextField;
	private JTextField lenghtTextField;
	private JLabel cambiarPixelLabel;
	private JTextField cambiarPixelTextField;
	private JLabel labelDesde;
	private JTextField desdeTextFieldX;
	private JTextField desdeTextFieldY;
	private JLabel labelHasta;
	private JTextField hastaTextFieldX;
	private JTextField hastaTextFieldY;
	private JScrollPane scrollPane2;
	private JLabel labelImagenNueva;
	private JButton crearImagenNuevaButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
    public BufferedImage abrirImagen(){
    	
    	this.width = Integer.valueOf(widthTextField.getText());
    	this.lenght = Integer.valueOf(lenghtTextField.getText());
    	
    	bmp = new BufferedImage(width, lenght, BufferedImage.TYPE_3BYTE_BGR);

        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector=new JFileChooser();
        selector.setDialogTitle("Seleccione una imagen");
        
        int flag=selector.showOpenDialog(null);
        
        //Comprobamos que pulse en aceptar
        if(flag==JFileChooser.APPROVE_OPTION){
            try {
                //Devuelve el fichero seleccionado
                archivoSeleccionado=selector.getSelectedFile();
                byte[] bytes = Files.readAllBytes(archivoSeleccionado.toPath());
                matrizDeImagen = new double [width][lenght];
                int contador = 0;
                for(int i = 0; i < lenght ; i++){
                	for(int j = 0; j < width; j++){
                		matrizDeImagen[j][i] = bytes[contador];
                		bmp.setRGB(j, i, bytes[contador]);
                		contador++;
                	}
                }
            } catch (Exception e) {
            }
                 
        }
        
        //Asignamos la imagen cargada a la propiedad imageActual
        imagenActual=bmp;
        nombreDelArchivo = getNombreImagen(null);
        //Retornamos el valor
        return bmp;
    }
    
	private String getNombreImagen(String filtro) {
		
		nombreDelArchivo = archivoSeleccionado.getName().split("\\.")[0];
		
		if(filtro != null && !filtro.isEmpty()){
			
			nombreDelArchivo += "_" + filtro;
		}
		
		return nombreDelArchivo;
	}
	
    private void jButtonCargarImagen(ActionEvent evt) {
        labelImagen.setIcon(new ImageIcon(abrirImagen()));

    }
    
    private void jButtonObtenerrPixel(ActionEvent evt) {
    	obtenerPixelPorCoordenadas();
    }
    
    private void jButtonCambiarPixel(ActionEvent evt) {
    	cambiarPixelPorCoordenadas();
    }
    
    private void jButtonGuardarImagen(ActionEvent evt) {
    	guardarImagen();
    }
    
    public void obtenerPixelPorCoordenadas(){
    	int x = Integer.valueOf(posicionXobtenerPixel.getText());
    	int y = Integer.valueOf(posicionYobtenerPixel.getText());
    	valorResultado.setText(String.valueOf(matrizDeImagen[x][y]));
    }
    
    public void cambiarPixelPorCoordenadas(){
    	int x = Integer.valueOf(posicionXcambiarPixel.getText());
    	int y = Integer.valueOf(posicionYcambiarPixel.getText());
    	matrizDeImagen[y][x] = (double) Integer.valueOf(cambiarPixelTextField.getText()).intValue();
    	
    	bmp.setRGB(y, x, Integer.valueOf(cambiarPixelTextField.getText()));
    	imagenActual=bmp;
    	
        labelImagen.setIcon(new ImageIcon(imagenActual));

    }
    
    public void guardarImagen(){
    	File outputfile = new File("C:/imagenes/"+getNombreImagen(null)+".jpg");
    	try {
			ImageIO.write(imagenActual, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	private void crearNuevaImagen() {
    	int xDesde = Integer.valueOf(desdeTextFieldX.getText());
    	int yDesde = Integer.valueOf(desdeTextFieldY.getText());
    	int xHasta = Integer.valueOf(hastaTextFieldX.getText());
    	int yHasta = Integer.valueOf(hastaTextFieldY.getText());
    	
    	BufferedImage imagenNueva = bmp.getSubimage(xDesde, yDesde, xHasta, yHasta);
    	
        labelImagenNueva.setIcon(new ImageIcon(imagenNueva));

    	
	}

}
