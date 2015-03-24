/*
 * Created by JFormDesigner on Fri Mar 20 21:17:25 GFT 2015
 */

package ar.edu.untref.imagenes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Emmanuel Beneventano
 */
public class FormularioPrincipal extends JFrame {

	private static final long serialVersionUID = -1575796688929412886L;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JLabel label1;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	
	public FormularioPrincipal() {
		initComponents();
	}

	private void button4ActionPerformed(ActionEvent e) {
		new FormularioCuartoPunto().setVisible(true);
	}

	private void initComponents() {
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		label1 = new JLabel();
		button4 = new JButton();
		button5 = new JButton();
		button6 = new JButton();
		
		//======== this ========
		setTitle("TP 0");
		Container contentPane = getContentPane();

		//---- button1 ----
		button1.setText("Ejercicio 1");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1ActionPerformed(e);
			}
		});

		//---- button2 ----
		button2.setText("Ejercicio 2");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button2ActionPerformed(e);
			}
		});

		//---- button3 ----
		button3.setText("Ejercicio 3");
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button3ActionPerformed(e);
			}
		});

		//---- label1 ----
		label1.setText("EJERCICIOS");
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		//---- button4 ----
		button4.setText("Ejercicio 4");
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button4ActionPerformed(e);
			}
		});
		
		button5.setText("Ejercicio 5");
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button5ActionPerformed(e);
			}
		});
		
		button6.setText("Ejercicio 6");
		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button6ActionPerformed(e);
			}
		});

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(22, 22, 22)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(122, 122, 122)
							.addComponent(label1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(176, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(17, 17, 17)
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(button1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(button2)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(button3)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(button4)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(button5)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(button6)
					.addContainerGap(90, Short.MAX_VALUE))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	private void button1ActionPerformed(ActionEvent e) {
		new FormularioPrimerPunto().setVisible(true);
	}
	
	private void button2ActionPerformed(ActionEvent e) {
		new FormularioSegundoPunto().setVisible(true);
	}

	private void button3ActionPerformed(ActionEvent e) {
		new FormularioTercerPunto().setVisible(true);
	}
	
	private void button5ActionPerformed(ActionEvent e) {
		new FormularioQuintoPunto().setVisible(true);
	}
	
	private void button6ActionPerformed(ActionEvent e) {
		new FormularioSextoPunto().setVisible(true);
	}

}
