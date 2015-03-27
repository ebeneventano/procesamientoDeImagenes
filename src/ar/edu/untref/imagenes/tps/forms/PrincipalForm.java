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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ar.edu.untref.imagenes.tps.utils.ImageOperations;

@SuppressWarnings("serial")
public class PrincipalForm extends JFrame{
	
	private JMenuBar menuBar;
	
	private JMenu menuArchivo;
	private JMenu menuHistograma;
	private JMenu menuOperations;
	
	private JMenuItem menuOpenImage;
	private JMenuItem menuSaveAs;
	private JMenuItem menuRefreshChanges;
	
	private JMenuItem menuSumImages;
	private JMenuItem menuRestImages;
	private JMenuItem menuMultiplicateImages;
	private JMenuItem menuScalarMultiplication;
	
	private JMenuItem menuCreateHistogram;

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
		labelPrincipalImage.setHorizontalAlignment(JLabel.CENTER);
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
		
		menuCreateHistogram = new JMenuItem("Crear Histograma");
		menuHistograma.add(menuCreateHistogram);
		
		menuOperations = new JMenu("Operaciones");
		menuBar.add(menuOperations);
		
		menuSumImages = new JMenuItem("Sumar Imagenes");
		menuOperations.add(menuSumImages);
		
		menuRestImages = new JMenuItem("Restar Imagenes");
		menuOperations.add(menuRestImages);
		
		menuMultiplicateImages = new JMenuItem("Multiplicar Imagenes");
		menuOperations.add(menuMultiplicateImages);
		
		menuScalarMultiplication = new JMenuItem("Multiplicar por Escalar");
		menuOperations.add(menuScalarMultiplication);
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
		
		menuSumImages.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					sumImages();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
			
		});
		
		menuRestImages.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					restImages();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuMultiplicateImages.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					multiplicateImages();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuScalarMultiplication.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					scalarMultiplication();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}

		});
		
		menuCreateHistogram.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
										
				} else {
					
					showAlertOriginalImageNull();
					
				}
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
    
    private void sumImages(){
    	
    	BufferedImage imageSum = getImageFromJFileChooser("Seleccione una imagen para la suma...");
		
    	ImageOperations io = new ImageOperations(originalImage, imageSum);
    	
    	imageInLabel = io.sumImages();
    	
    	labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
    }
    
    private void restImages(){
    	
    	BufferedImage imageRest = getImageFromJFileChooser("Seleccione una imagen para la resta...");
		
    	ImageOperations io = new ImageOperations(originalImage, imageRest);
    	
    	imageInLabel = io.restImages();
    	
    	labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
    }
    
    private void multiplicateImages(){
    	
    	BufferedImage imageMultip = getImageFromJFileChooser("Seleccione una imagen para la multiplicacion...");
		
    	ImageOperations io = new ImageOperations(originalImage, imageMultip);
    	
    	imageInLabel = io.multiplicateImages();
    	
    	labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
    }
    
    private BufferedImage getImageFromJFileChooser(String titleDialog){
    	
    	BufferedImage imageToOperate = null;

		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle(titleDialog);

		int flag = selector.showOpenDialog(null);

		if (flag == JFileChooser.APPROVE_OPTION) {
			try {
				
				File archivoSeleccionado = selector.getSelectedFile();
				imageToOperate = ImageIO.read(archivoSeleccionado);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		
		return imageToOperate;
    }
    
	private void showAlertOriginalImageNull() {
		
		JOptionPane.showMessageDialog(null, "Primero debe abrir una imagen. Para ello, seleccione la opción ABRIR IMAGEN en el menu ARCHIVO.");				
	}
	
	private void scalarMultiplication() {
		
		ImageOperations io = new ImageOperations();
		
		imageInLabel = io.scalarMultiplication(2, imageInLabel);
		
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

}
