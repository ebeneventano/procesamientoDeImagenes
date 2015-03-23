/*
 * Created by JFormDesigner on Fri Mar 20 20:14:54 GFT 2015
 */

package ar.edu.untref.imagenes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class FormularioQuintoPunto extends JFrame {

	private static final long serialVersionUID = -5624241489547478016L;

	private BufferedImage bmp;
	private JButton buttonCrearImagen;
	private JScrollPane scrollPane1;
	private JLabel imagenLabel;

	public FormularioQuintoPunto() {
		initComponents();
	}

	private void initComponents() {
		buttonCrearImagen = new JButton();
		scrollPane1 = new JScrollPane();
		imagenLabel = new JLabel();

		// ======== this ========
		setTitle("Punto 5 - Escala de Colores");
		Container contentPane = getContentPane();

		// ---- buttonCrearImagen ----
		buttonCrearImagen.setText("Crear Imagen");
		buttonCrearImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCrearImagenConCirculoCentrado(evt);
			}
		});

		// ======== scrollPane1 ========
		{
			imagenLabel.setSize(200, 200);
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
																				buttonCrearImagen))
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
										.addContainerGap(47, Short.MAX_VALUE)));
		pack();
		setLocationRelativeTo(getOwner());
	}

	private void jButtonCrearImagenConCirculoCentrado(ActionEvent evt) {
		imagenLabel.setIcon(new ImageIcon(crearImagen()));

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

}