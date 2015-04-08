package ar.edu.untref.imagenes.tps.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
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

import ar.edu.untref.imagenes.tps.noise.GeneradorDeRuido;
import ar.edu.untref.imagenes.tps.utils.ImageOperations;

@SuppressWarnings("serial")
public class PrincipalForm extends JFrame{
	
	private static final int UMBRAL_PARA_UMBRALIZACION = 150;

	private JMenuBar menuBar;
	
	private JMenu menuArchivo;
	private JMenuItem menuOpenImage;
	private JMenuItem menuSaveAs;
	private JMenuItem menuRefreshChanges;
	
	private JMenu menuHistograma;
	private JMenuItem menuCreateHistogram;
	private JMenuItem menuEqualizeHistogram;

	private JMenu menuOperations;
	private JMenuItem menuSumImages;
	private JMenuItem menuRestImages;
	private JMenuItem menuMultiplicateImages;
	private JMenuItem menuScalarMultiplication;
	private JMenuItem menuNegativeImage;
	private JMenuItem menuIncreaseContrast;
	private JMenuItem menuUmbralization;
	
	private JMenu menuRuido;
	
	private JMenu menuGeneradorDeNumeros;
	private JMenuItem menuGenerarNumRuidoGaussiano;
	private JMenuItem menuGenerarNumRuidoRayleigh;
	private JMenuItem menuGenerarNumRuidoExponencial;

	private JMenu menuGeneradorDeRuidos;
	private JMenuItem menuGenerarRuidoGaussianoAditivo;
	private JMenuItem menuGenerarRuidoRayleighMultiplicativo;
	private JMenuItem menuGenerarRuidoExponencialMultiplicativo;
	private JMenuItem menuGenerarRuidoSalYPimienta;

	private JMenuItem menuGenerarImagenSintetica;
	
	private JMenu menuFiltros;
	private JMenuItem menuGenerarFiltroDeLaMedia;

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
		
		menuEqualizeHistogram = new JMenuItem("Equalizar Histograma");
		menuHistograma.add(menuEqualizeHistogram);
		
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
		
		menuNegativeImage = new JMenuItem("Obtener negativo");
		menuOperations.add(menuNegativeImage);
		
		menuIncreaseContrast = new JMenuItem("Aumentar contraste");
		menuOperations.add(menuIncreaseContrast);
		
		menuUmbralization = new JMenuItem("Umbralizacion");
		menuOperations.add(menuUmbralization);
		
		menuRuido = new JMenu("Ruidos");
		menuBar.add(menuRuido);
		
		menuGeneradorDeNumeros = new JMenu("Generador De Numeros");
		menuRuido.add(menuGeneradorDeNumeros);
	
		menuGenerarNumRuidoGaussiano = new JMenuItem("Generar numero de ruido Gaussiano");
		menuGeneradorDeNumeros.add(menuGenerarNumRuidoGaussiano);
		
		menuGenerarNumRuidoRayleigh = new JMenuItem("Generar numero de ruido Rayleigh");
		menuGeneradorDeNumeros.add(menuGenerarNumRuidoRayleigh);
		
		menuGenerarNumRuidoExponencial = new JMenuItem("Generar numero de ruido Exponencial");
		menuGeneradorDeNumeros.add(menuGenerarNumRuidoExponencial);
		
		menuGeneradorDeRuidos = new JMenu("Generador De Ruidos");
		menuRuido.add(menuGeneradorDeRuidos);
		
		menuGenerarRuidoGaussianoAditivo = new JMenuItem("Generar ruido Gaussiano Aditivo");
		menuGeneradorDeRuidos.add(menuGenerarRuidoGaussianoAditivo);
		
		menuGenerarRuidoRayleighMultiplicativo = new JMenuItem("Generar ruido Rayleigh Multiplicativo");
		menuGeneradorDeRuidos.add(menuGenerarRuidoRayleighMultiplicativo);
		
		menuGenerarRuidoExponencialMultiplicativo = new JMenuItem("Generar ruido Exponencial Multiplicativo");
		menuGeneradorDeRuidos.add(menuGenerarRuidoExponencialMultiplicativo);
		
		menuGenerarRuidoSalYPimienta = new JMenuItem("Generar ruido Sal y Pimienta");
		menuGeneradorDeRuidos.add(menuGenerarRuidoSalYPimienta);
		
		menuFiltros = new JMenu("Generador De Filtros");
		menuRuido.add(menuFiltros);
		
		menuGenerarFiltroDeLaMedia = new JMenuItem("Generar filtro de la media");
		menuFiltros.add(menuGenerarFiltroDeLaMedia);
		
		menuGenerarImagenSintetica = new JMenuItem("Generar imagen sintetica 100x100");
		menuRuido.add(menuGenerarImagenSintetica);		
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
		
		menuNegativeImage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					getNegative();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}

		});
		
		menuIncreaseContrast.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					increaseContrast();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}

		});
		
		menuCreateHistogram.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					new Formulario(imageInLabel);
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuEqualizeHistogram.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					equalizeImage();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuUmbralization.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(originalImage != null) {
					
					umbralizate();
					
				} else {
					
					showAlertOriginalImageNull();
				}
			}
		});

		menuGenerarNumRuidoGaussiano.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				    
				String desviacionEstandar = JOptionPane.showInputDialog(
				    		null, "Desviacion Estandar", "Generador de numero de ruido aleatorio Gaussiano", JOptionPane.DEFAULT_OPTION);
				String varianza = JOptionPane.showInputDialog(
				    		null, "Varianza", "Generador de numero de ruido aleatorio Gaussiano", JOptionPane.DEFAULT_OPTION);
				    
				    GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
				    double ruidoGenerado = generadorDeRuido.generarNumeroAleatorioGaussiano(Double.valueOf(desviacionEstandar), Double.valueOf(varianza));
				    
				    JOptionPane.showMessageDialog(null,String.valueOf(ruidoGenerado));
				    
				}
		});
		
		menuGenerarNumRuidoRayleigh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				    
				String phi = JOptionPane.showInputDialog(
				    		null, "Phi", "Generador de numero de ruido aleatorio Rayleigh", JOptionPane.DEFAULT_OPTION);
				    
				    GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
				    double ruidoGenerado = generadorDeRuido.generarNumeroAleatorioRayleigh(Double.valueOf(phi));
				  
				    JOptionPane.showMessageDialog(null,String.valueOf(ruidoGenerado));
				}
		});
		
		menuGenerarNumRuidoExponencial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				    
				String lambda = JOptionPane.showInputDialog(
				    		null, "Lambda", "Generador de numero de ruido aleatorio Exponencial", JOptionPane.DEFAULT_OPTION);
				    
				    GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
				    double ruidoGenerado = generadorDeRuido.generarNumeroAleatorioExponencial(Double.valueOf(lambda));
				  
				    JOptionPane.showMessageDialog(null,String.valueOf(ruidoGenerado));
				}
		});
		
		menuGenerarRuidoGaussianoAditivo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(originalImage != null) {
					
					generarRuidoBlacoGaussiano();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuGenerarRuidoRayleighMultiplicativo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(originalImage != null) {
					
					generarRuidoRayleighMultiplicativo();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuGenerarRuidoExponencialMultiplicativo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(originalImage != null) {
					
					generarRuidoExponencialMultiplicativo();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}

		});
		
		menuGenerarImagenSintetica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				    
				crearImagenSintetica();
			}
		});
		
		menuGenerarRuidoSalYPimienta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(originalImage != null) {
					
					generarRuidoSalYPimienta();
					
				} else {
					
					showAlertOriginalImageNull();
					
				}
			}
		});
		
		menuGenerarFiltroDeLaMedia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(originalImage != null) {
					
					generarFiltroDeLaMedia();
					
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
    	
    	imageInLabel = originalImage;
    	
		labelPrincipalImage.setIcon(new ImageIcon(originalImage));
    }
    
    private void sumImages(){
    	
    	BufferedImage imageSum = getImageFromJFileChooser("Seleccione una imagen para la suma...");
		
    	ImageOperations io = new ImageOperations(originalImage, imageSum);
    	
    	imageInLabel = io.sumImages();
    	
    	if(imageInLabel != null){
    	
    		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
    	}else{
    		
    		showAlertByDifferentSizeOrType();
    	}
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
		
		JOptionPane.showMessageDialog(null, "Primero debe abrir una imagen. Para ello, seleccione la opcion ABRIR IMAGEN en el menu ARCHIVO.");				
	}
	
	private void showAlertByDifferentSizeOrType() {
		
		JOptionPane.showMessageDialog(null, "Seleccione una imagen con el mismo tipo y tama�o de la que se encuentra en la pantalla principal.");				
	}
	
	private void scalarMultiplication() {
		
		ImageOperations io = new ImageOperations();
		
		imageInLabel = io.scalarMultiplication(100, imageInLabel);
		
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	private void getNegative() {
		
		ImageOperations io = new ImageOperations();
		imageInLabel = io.getNegativeImage(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	

	private void increaseContrast() {
		
		ImageOperations io = new ImageOperations();
		imageInLabel = io.increaseImageContrast(imageInLabel, 1.2f);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
	}

	private void equalizeImage() {
		
		ImageOperations io = new ImageOperations();
		imageInLabel = io.histogramEqualization(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
	}
	
	private void umbralizate() {
		
		ImageOperations io = new ImageOperations();
		imageInLabel = io.umbralization(imageInLabel, UMBRAL_PARA_UMBRALIZACION);
		
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	private void generarRuidoBlacoGaussiano() {
		
		double sigma = Double.valueOf(JOptionPane.showInputDialog(
				null, "Sigma", "Generador de ruido blanco gaussiano", JOptionPane.DEFAULT_OPTION));
		
		double mu = Double.valueOf(JOptionPane.showInputDialog(
				null, "Mu", "Generador de ruido blanco gaussiano", JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoGaussianoAditivo(imageInLabel, sigma, mu);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	

	private void generarRuidoRayleighMultiplicativo() {
		
		double phi = Double.valueOf(JOptionPane.showInputDialog(
				null, "phi", "Generador de ruido Rayleigh Multiplicativo", JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoRayleighMultiplicativo(imageInLabel, phi);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
	}
	
	private void generarRuidoExponencialMultiplicativo() {
		
		double lambda = Double.valueOf(JOptionPane.showInputDialog(
				null, "Lambda", "Generador de ruido Rayleigh Multiplicativo", JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoExponencialMultiplicativo(imageInLabel, lambda);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
	}
	
	private void crearImagenSintetica() {
    	BufferedImage bmp = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_BINARY);

        for(int i = 0; i < 100 ; i++){
        	for(int j = 0; j < 100; j++){
        		bmp.setRGB(i, j, 0);
        	}
        }
        
        Graphics2D g = bmp.createGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(50, 50, 15, 15);
        
        originalImage = bmp;
		imageInLabel = bmp;
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarRuidoSalYPimienta() {
		
		int densidadContaminacion = Integer.valueOf(JOptionPane.showInputDialog(
				null, "Densidad de Contaminacion", "Generador de ruido Sal y Pimienta", JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoImpulsivo(imageInLabel, densidadContaminacion);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	private void generarFiltroDeLaMedia() {
		
		int ancho = Integer.valueOf(JOptionPane.showInputDialog(
				null, "Ancho", "Generador de Filtro de la Media", JOptionPane.DEFAULT_OPTION));
		
		int alto = Integer.valueOf(JOptionPane.showInputDialog(
				null, "Alto", "Generador de Filtro de la Media", JOptionPane.DEFAULT_OPTION));
		
		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		
		imageInLabel = generadorDeRuido.suavizadoConFiltroDeLaMedia(imageInLabel, ancho, alto);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
	}

}
