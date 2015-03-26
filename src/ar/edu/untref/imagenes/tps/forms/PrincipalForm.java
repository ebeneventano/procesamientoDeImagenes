package ar.edu.untref.imagenes.tps.forms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PrincipalForm extends JFrame{
	
	private JMenuBar menuBar;
	private JMenu menuArchivo;
	private JMenu menuHistograma;
	private JMenuItem menuOpenImage;
	private JMenuItem menuSaveAs;
	private JMenuItem menuRefreshChanges;

	private JScrollPane scrollPane;
	private JPanel contentPane;
	
	private JLabel labelPrincipalImage;
	
	private BufferedImage imageInLabel;
	private BufferedImage originalImage;

	
	public PrincipalForm (){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		this.initializeMenu();
		this.addListenersToComponents();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		labelPrincipalImage = new JLabel();
		scrollPane.setViewportView(labelPrincipalImage);
	}

	private void initializeMenu() {
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuArchivo = new JMenu("Archivo");
		menuBar.add(menuArchivo);
		
		menuOpenImage = new JMenuItem("Abrir Imagen");
		menuArchivo.add(menuOpenImage);
		
		menuSaveAs = new JMenuItem("Guardar Como");
		menuArchivo.add(menuSaveAs);
		
		menuRefreshChanges = new JMenuItem("Deshacer Cambios");
		menuArchivo.add(menuRefreshChanges);
		
		menuHistograma = new JMenu("Histograma");
		menuBar.add(menuHistograma);
	}
	
	private void addListenersToComponents() {
		
		menuOpenImage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				labelPrincipalImage.setIcon(new ImageIcon(abrirImagen()));
			}
		});
		
		menuRefreshChanges.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				refreshChanges();
			}
		});
		
	}
	
	public BufferedImage abrirImagen() {

		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccione una imagen");

		int flag = selector.showOpenDialog(null);

		if (flag == JFileChooser.APPROVE_OPTION) {
			try {

				File archivoSeleccionado = selector.getSelectedFile();
				imageInLabel = ImageIO.read(archivoSeleccionado);
				originalImage = ImageIO.read(archivoSeleccionado);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return imageInLabel;
	}
    
    private void refreshChanges(){
		labelPrincipalImage.setIcon(new ImageIcon(originalImage));
    }

	

}
