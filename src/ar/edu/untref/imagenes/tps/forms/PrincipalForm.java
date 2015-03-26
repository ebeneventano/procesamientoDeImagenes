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
		
		menuOperations = new JMenu("Operaciones");
		menuBar.add(menuOperations);
		
		menuSumImages = new JMenuItem("Sumar Imagenes");
		menuOperations.add(menuSumImages);
		
		menuRestImages = new JMenuItem("Restar Imagenes");
		menuOperations.add(menuRestImages);
		
		menuMultiplicateImages = new JMenuItem("Multiplicar Imagenes");
		menuOperations.add(menuMultiplicateImages);
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
				sumImages();
			}
		});
		
		menuRestImages.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				restImages();
			}
		});
		
		menuMultiplicateImages.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				multiplicateImages();
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
    	labelPrincipalImage.setIcon(new ImageIcon(io.sumImages()));
    }
    
    private void restImages(){
    	
    	BufferedImage imageRest = getImageFromJFileChooser("Seleccione una imagen para la resta...");
		
    	ImageOperations io = new ImageOperations(originalImage, imageRest);
    	labelPrincipalImage.setIcon(new ImageIcon(io.restImages()));
    }
    
    private void multiplicateImages(){
    	
    	BufferedImage imageMultip = getImageFromJFileChooser("Seleccione una imagen para la multiplicacion...");
		
    	ImageOperations io = new ImageOperations(originalImage, imageMultip);
    	labelPrincipalImage.setIcon(new ImageIcon(io.multiplicateImages()));
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

}
