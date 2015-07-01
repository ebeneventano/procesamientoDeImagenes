package ar.edu.untref.imagenes.tps.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ar.edu.untref.imagenes.susan.Susan;
import ar.edu.untref.imagenes.tps.bordes.Borde;
import ar.edu.untref.imagenes.tps.bordes.DetectorDeBordeCanny;
import ar.edu.untref.imagenes.tps.bordes.DetectorDeHarris;
import ar.edu.untref.imagenes.tps.bordes.ImagenVideoPreProcesada;
import ar.edu.untref.imagenes.tps.bordes.Segmentador;
import ar.edu.untref.imagenes.tps.difusion.Difuminador;
import ar.edu.untref.imagenes.tps.domain.MatrizAcumuladora;
import ar.edu.untref.imagenes.tps.hough.TransformadaDeHough;
import ar.edu.untref.imagenes.tps.noise.FiltroGaussiano;
import ar.edu.untref.imagenes.tps.noise.FiltroPasaAltos;
import ar.edu.untref.imagenes.tps.noise.FiltroPasaBajos;
import ar.edu.untref.imagenes.tps.noise.GeneradorDeRuido;
import ar.edu.untref.imagenes.tps.sift.Sift;
import ar.edu.untref.imagenes.tps.utils.ImageOperations;

@SuppressWarnings("serial")
public class PrincipalForm extends JFrame {

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
	private JMenuItem menuMultiplicateByScalar;
	private JMenuItem menuCompresionRangoDinamico;
	private JMenuItem menuNegativeImage;
	private JMenuItem menuIncreaseContrast;
	private JMenuItem menuUmbralization;
	private JMenuItem menuSift;

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
	private JMenuItem menuGenerarFiltroDeGauss;
	private JMenuItem menuGenerarFiltroDeLaMedia;
	private JMenuItem menuGenerarFiltroDeLaMediana;
	private JMenuItem menuGenerarFiltroPasaBajos;
	private JMenuItem menuGenerarFiltroPasaAltos;

	private JMenu menuDeteccionDeBordes;
	private JMenu menuDetectorPrewitt;
	private JMenuItem menuDetectorBordePrewitt;
	private JMenuItem menuDetectorBordePrewittX;
	private JMenuItem menuDetectorBordePrewittY;
	private JMenuItem menuDetectorBordePrewitt45Grados;
	private JMenuItem menuDetectorBordePrewitt135Grados;
	private JMenuItem menuDetectorBordePrewittColor;
	
	private JMenu menuDetectorSobel;
	private JMenuItem menuDetectorBordeSobel;
	private JMenuItem menuDetectorBordeSobelVertical;
	private JMenuItem menuDetectorBordeSobel45Grados;
	private JMenuItem menuDetectorBordeSobel135Grados;
	private JMenuItem menuDetectorBordeSobelColor;

	private JMenu menuDetectorKirsh;
	private JMenuItem menuDetectorBordeKirshVertical;
	private JMenuItem menuDetectorBordeKirsh45Grados;
	private JMenuItem menuDetectorBordeKirsh135Grados;
	
	private JMenu menuDetectorSinNombre;
	private JMenuItem menuDetectorBordePuntoAVertical;
	private JMenuItem menuDetectorBordePuntoA45Grados;
	private JMenuItem menuDetectorBordePuntoA135Grados;

	private JMenu menuLaplaciano;
	private JMenuItem menuMetodoLaplaciano;
	private JMenuItem menuMetodoLaplacianoConPendiente;
	private JMenuItem menuMetodoLaplacianoDelGausiano;

	private JMenu menuDifusores;
	private JMenuItem menuDifusionIsotropica;
	private JMenuItem menuDifusionAnisotropica;

	private JMenu menuUmbralizaciones;
	private JMenuItem menuUmbralizacionGlobal;
	private JMenuItem menuUmbralizacionOtsu;
	
	private JMenuItem menuUmbralizacionConHisteresis;
	private JMenuItem menuSupresionNoMaximos;
	private JMenuItem menuDetectorDeBordeCanny;
	
	private JMenuItem menuTransformadaDeHough;
	
	private JMenuItem menuHarris;
	
	private JMenu menuSegmentacion;
	private JMenuItem menuSegmentacionSemisupervisada;
	
	private JMenuItem menuMetodoDeSusan;
	
	private JMenuItem menuVideoPrimerImagen;

	private JScrollPane scrollPane;
	private JPanel contentPane;

	private JLabel labelPrincipalImage;

	private JButton buttonSiguienteImagen;
//	private JButton buttonPlay;
	
	private BufferedImage imageInLabel;
	private BufferedImage originalImage;
	
	private int primerPuntoX = -1;
	private int primerPuntoY = -1;
	private int segundoPuntoX = -1;
	private int segundoPuntoY = -1;
	
	private List<Long> tiempos = new ArrayList<>();
	private Long tiempo1 = null;
	private Long tiempo2 = null;
	
	private ImagenVideoPreProcesada procesamientoAnterior = null;

	int contadorImagenes = 0;
	private	File[] files = null;

	private File querySift;

	private File targetSift;

	public PrincipalForm() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		labelPrincipalImage = new JLabel();
		labelPrincipalImage.setHorizontalAlignment(JLabel.CENTER);

		contentPane = new JPanel();
		setContentPane(contentPane);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(labelPrincipalImage);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		this.initializeMenu();
		this.addListenersToComponents();
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

		menuMultiplicateByScalar = new JMenuItem("Multiplicar por Escalar");
		menuOperations.add(menuMultiplicateByScalar);

		menuCompresionRangoDinamico = new JMenuItem(
				"Compresion de rango dinamico");
		menuOperations.add(menuCompresionRangoDinamico);

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

		menuGenerarNumRuidoGaussiano = new JMenuItem(
				"Generar numero de ruido Gaussiano");
		menuGeneradorDeNumeros.add(menuGenerarNumRuidoGaussiano);

		menuGenerarNumRuidoRayleigh = new JMenuItem(
				"Generar numero de ruido Rayleigh");
		menuGeneradorDeNumeros.add(menuGenerarNumRuidoRayleigh);

		menuGenerarNumRuidoExponencial = new JMenuItem(
				"Generar numero de ruido Exponencial");
		menuGeneradorDeNumeros.add(menuGenerarNumRuidoExponencial);

		menuGeneradorDeRuidos = new JMenu("Generador De Ruidos");
		menuRuido.add(menuGeneradorDeRuidos);

		menuGenerarRuidoGaussianoAditivo = new JMenuItem(
				"Generar ruido Gaussiano Aditivo");
		menuGeneradorDeRuidos.add(menuGenerarRuidoGaussianoAditivo);

		menuGenerarRuidoRayleighMultiplicativo = new JMenuItem(
				"Generar ruido Rayleigh Multiplicativo");
		menuGeneradorDeRuidos.add(menuGenerarRuidoRayleighMultiplicativo);

		menuGenerarRuidoExponencialMultiplicativo = new JMenuItem(
				"Generar ruido Exponencial Multiplicativo");
		menuGeneradorDeRuidos.add(menuGenerarRuidoExponencialMultiplicativo);

		menuGenerarRuidoSalYPimienta = new JMenuItem(
				"Generar ruido Sal y Pimienta");
		menuGeneradorDeRuidos.add(menuGenerarRuidoSalYPimienta);

		menuFiltros = new JMenu("Generador De Filtros");
		menuRuido.add(menuFiltros);

		menuGenerarFiltroDeLaMedia = new JMenuItem("Generar filtro de la media");
		menuFiltros.add(menuGenerarFiltroDeLaMedia);

		menuGenerarFiltroDeLaMediana = new JMenuItem(
				"Generar filtro de la mediana");
		menuFiltros.add(menuGenerarFiltroDeLaMediana);

		menuGenerarFiltroDeGauss = new JMenuItem("Generar filtro de Gauss");
		menuFiltros.add(menuGenerarFiltroDeGauss);

		menuGenerarFiltroPasaAltos = new JMenuItem("Generar filtro Pasa Altos");
		menuFiltros.add(menuGenerarFiltroPasaAltos);

		menuGenerarFiltroPasaBajos = new JMenuItem("Generar filtro Pasa Bajos");
		menuFiltros.add(menuGenerarFiltroPasaBajos);

		menuGenerarImagenSintetica = new JMenuItem(
				"Generar imagen sintetica 100x100");
		menuRuido.add(menuGenerarImagenSintetica);

		menuDeteccionDeBordes = new JMenu("Bordes");
		menuBar.add(menuDeteccionDeBordes);
		
		menuDetectorPrewitt = new JMenu("Prewitt");
		menuDeteccionDeBordes.add(menuDetectorPrewitt);

		menuDetectorBordePrewitt = new JMenuItem("Detector de Bordes Prewitt");
		menuDetectorPrewitt.add(menuDetectorBordePrewitt);

		menuDetectorBordePrewittX = new JMenuItem(
				"Detector de Bordes Prewitt X");
		menuDetectorPrewitt.add(menuDetectorBordePrewittX);

		menuDetectorBordePrewittY = new JMenuItem(
				"Detector de Bordes Prewitt Y");
		menuDetectorPrewitt.add(menuDetectorBordePrewittY);

		menuDetectorBordePrewitt45Grados = new JMenuItem(
				"Detector de Bordes Prewitt 45 Grados");
		menuDetectorPrewitt.add(menuDetectorBordePrewitt45Grados);

		menuDetectorBordePrewitt135Grados = new JMenuItem(
				"Detector de Bordes Prewitt 135 Grados");
		menuDetectorPrewitt.add(menuDetectorBordePrewitt135Grados);

		menuDetectorBordePrewittColor = new JMenuItem(
				"Detector de Bordes Prewitt (Color)");
		menuDetectorPrewitt.add(menuDetectorBordePrewittColor);
		
		menuDetectorSobel = new JMenu("Sobel");
		menuDeteccionDeBordes.add(menuDetectorSobel);

		menuDetectorBordeSobel = new JMenuItem("Detector de Bordes Sobel");
		menuDetectorSobel.add(menuDetectorBordeSobel);

		menuDetectorBordeSobelVertical = new JMenuItem(
				"Detector de Bordes Sobel Vertical");
		menuDetectorSobel.add(menuDetectorBordeSobelVertical);

		menuDetectorBordeSobel45Grados = new JMenuItem(
				"Detector de Bordes Sobel 45 Grados");
		menuDetectorSobel.add(menuDetectorBordeSobel45Grados);

		menuDetectorBordeSobel135Grados = new JMenuItem(
				"Detector de Bordes Sobel 135 Grados");
		menuDetectorSobel.add(menuDetectorBordeSobel135Grados);

		menuDetectorBordeSobelColor = new JMenuItem(
				"Detector de Bordes Sobel (Color)");
		menuDetectorSobel.add(menuDetectorBordeSobelColor);
		
		menuDetectorKirsh = new JMenu("Kirsh");
		menuDeteccionDeBordes.add(menuDetectorKirsh);

		menuDetectorBordeKirshVertical = new JMenuItem(
				"Detector de Bordes Kirsh Vertical");
		menuDetectorKirsh.add(menuDetectorBordeKirshVertical);

		menuDetectorBordeKirsh45Grados = new JMenuItem(
				"Detector de Bordes Kirsh 45 Grados");
		menuDetectorKirsh.add(menuDetectorBordeKirsh45Grados);

		menuDetectorBordeKirsh135Grados = new JMenuItem(
				"Detector de Bordes Kirsh 135 Grados");
		menuDetectorKirsh.add(menuDetectorBordeKirsh135Grados);
		
		menuDetectorSinNombre = new JMenu("Mascara Sin Nombre");
		menuDeteccionDeBordes.add(menuDetectorSinNombre);
		
		menuDetectorBordePuntoAVertical = new JMenuItem(
				"Detector de Bordes Punto A");
		menuDetectorSinNombre.add(menuDetectorBordePuntoAVertical);

		menuDetectorBordePuntoA45Grados = new JMenuItem(
				"Detector de Bordes Punto A 45 Grados");
		menuDetectorSinNombre.add(menuDetectorBordePuntoA45Grados);

		menuDetectorBordePuntoA135Grados = new JMenuItem(
				"Detector de Bordes Punto A 135 Grados");
		menuDetectorSinNombre.add(menuDetectorBordePuntoA135Grados);
		
		menuLaplaciano = new JMenu("Metodos del Laplaciano");
		menuDeteccionDeBordes.add(menuLaplaciano);

		menuMetodoLaplaciano = new JMenuItem("Metodo del Laplaciano");
		menuLaplaciano.add(menuMetodoLaplaciano);

		menuMetodoLaplacianoConPendiente = new JMenuItem(
				"Metodo del Laplaciano con Pendiente");
		menuLaplaciano.add(menuMetodoLaplacianoConPendiente);

		menuMetodoLaplacianoDelGausiano = new JMenuItem(
				"Metodo del Laplaciano del Gausiano");
		menuLaplaciano.add(menuMetodoLaplacianoDelGausiano);
		
		menuDifusores = new JMenu("Metodos de Difusion");
		menuDeteccionDeBordes.add(menuDifusores);

		menuDifusionIsotropica = new JMenuItem("Difusion Isotropica");
		menuDifusores.add(menuDifusionIsotropica);

		menuDifusionAnisotropica = new JMenuItem("Difusion Anisotropica");
		menuDifusores.add(menuDifusionAnisotropica);
		
		menuUmbralizaciones= new JMenu("Metodos de Umbralizacion");
		menuDeteccionDeBordes.add(menuUmbralizaciones);

		menuUmbralizacionGlobal = new JMenuItem("Metodo Umbralizacion Global");
		menuUmbralizaciones.add(menuUmbralizacionGlobal);

		menuUmbralizacionOtsu = new JMenuItem("Metodo Umbralizacion Otsu");
		menuUmbralizaciones.add(menuUmbralizacionOtsu);
		
		menuSupresionNoMaximos = new JMenuItem("Metodo Supresion No Maximos");
		menuDeteccionDeBordes.add(menuSupresionNoMaximos);
		
		menuUmbralizacionConHisteresis = new JMenuItem("Metodo Umbralizacion con Histeresis");
		menuDeteccionDeBordes.add(menuUmbralizacionConHisteresis);
		
		menuDetectorDeBordeCanny = new JMenuItem("Metodo Detector de Borde Canny");
		menuDeteccionDeBordes.add(menuDetectorDeBordeCanny);
		
		menuVideoPrimerImagen = new JMenuItem("Seleccionar Primera Imagen de Video");
		menuDeteccionDeBordes.add(menuVideoPrimerImagen);
		
		menuMetodoDeSusan = new JMenuItem("Metodo de SUSAN");
		menuDeteccionDeBordes.add(menuMetodoDeSusan);
		
		menuTransformadaDeHough = new JMenuItem("Transformada de Hough");
		menuDeteccionDeBordes.add(menuTransformadaDeHough);
		
		menuHarris = new JMenuItem("Harris");
		menuDeteccionDeBordes.add(menuHarris);
		
		menuSift = new JMenuItem("SIFT");
		menuDeteccionDeBordes.add(menuSift);
		
		menuSegmentacion= new JMenu("Metodos de Segmentacion");
		menuDeteccionDeBordes.add(menuSegmentacion);

		menuSegmentacionSemisupervisada = new JMenuItem("Segmentar Imagen");
		menuSegmentacion.add(menuSegmentacionSemisupervisada);
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

				if (originalImage != null) {

					sumImages();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});

		menuRestImages.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					restImages();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuMultiplicateImages.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					multiplicateImages();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuMultiplicateByScalar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					multiplicarPorEscalar();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuCompresionRangoDinamico.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					compresionRangoDinamico();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});

		menuNegativeImage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					getNegative();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});

		menuIncreaseContrast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					increaseContrast();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});

		menuCreateHistogram.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					new Formulario(imageInLabel);

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuEqualizeHistogram.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					equalizeImage();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuUmbralization.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					umbralizate();

				} else {

					showAlertOriginalImageNull();
				}
			}
		});

		menuGenerarNumRuidoGaussiano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String desviacionEstandar = JOptionPane.showInputDialog(null,
						"Desviacion Estandar",
						"Generador de numero de ruido aleatorio Gaussiano",
						JOptionPane.DEFAULT_OPTION);
				String varianza = JOptionPane.showInputDialog(null, "Varianza",
						"Generador de numero de ruido aleatorio Gaussiano",
						JOptionPane.DEFAULT_OPTION);

				GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
				double ruidoGenerado = generadorDeRuido
						.generarNumeroAleatorioGaussiano(
								Double.valueOf(desviacionEstandar),
								Double.valueOf(varianza));

				JOptionPane.showMessageDialog(null,
						String.valueOf(ruidoGenerado));

			}
		});

		menuGenerarNumRuidoRayleigh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String phi = JOptionPane.showInputDialog(null, "Phi",
						"Generador de numero de ruido aleatorio Rayleigh",
						JOptionPane.DEFAULT_OPTION);

				GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
				double ruidoGenerado = generadorDeRuido
						.generarNumeroAleatorioRayleigh(Double.valueOf(phi));

				JOptionPane.showMessageDialog(null,
						String.valueOf(ruidoGenerado));
			}
		});

		menuGenerarNumRuidoExponencial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String lambda = JOptionPane.showInputDialog(null, "Lambda",
						"Generador de numero de ruido aleatorio Exponencial",
						JOptionPane.DEFAULT_OPTION);

				GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
				double ruidoGenerado = generadorDeRuido
						.generarNumeroAleatorioExponencial(Double
								.valueOf(lambda));

				JOptionPane.showMessageDialog(null,
						String.valueOf(ruidoGenerado));
			}
		});

		menuGenerarRuidoGaussianoAditivo
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (originalImage != null) {

							generarRuidoBlacoGaussiano();

						} else {

							showAlertOriginalImageNull();

						}
					}
				});

		menuGenerarRuidoRayleighMultiplicativo
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (originalImage != null) {

							generarRuidoRayleighMultiplicativo();

						} else {

							showAlertOriginalImageNull();

						}
					}
				});

		menuGenerarRuidoExponencialMultiplicativo
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (originalImage != null) {

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
				if (originalImage != null) {

					generarRuidoSalYPimienta();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuGenerarFiltroDeLaMedia.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarFiltroDeLaMedia();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuGenerarFiltroDeLaMediana.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarFiltroDeLaMediana();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuGenerarFiltroDeGauss.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarFiltroDeGauss();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuGenerarFiltroPasaAltos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarFiltroPasaAltos();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});

		menuGenerarFiltroPasaBajos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarFiltroPasaBajos();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});

		menuDetectorBordePrewitt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePrewitt();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordePrewittX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePrewittHorizontal();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordePrewittY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePrewittVertical();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeSobelVertical.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeSobelVertical();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeKirshVertical.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeKirshVertical();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeKirsh45Grados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeKirsh45Grados();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeKirsh135Grados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeKirsh135Grados();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});
		
		menuDetectorBordePuntoAVertical.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePuntoAVertical();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordePuntoA45Grados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePuntoA45Grados();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordePuntoA135Grados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePuntoA135Grados();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordePrewitt45Grados
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (originalImage != null) {

							aplicarDetectorDeBordePrewitt45Grados();

						} else {

							showAlertOriginalImageNull();

						}
					}
				});

		menuDetectorBordePrewitt135Grados
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (originalImage != null) {

							aplicarDetectorDeBordePrewitt135Grados();

						} else {

							showAlertOriginalImageNull();

						}
					}
				});

		menuDetectorBordeSobel45Grados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeSobel45Grados();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeSobel135Grados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeSobel135Grados();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordePrewittColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordePrewittColor();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeSobel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeSobel();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDetectorBordeSobelColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarDetectorDeBordeSobelColor();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuMetodoLaplaciano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarMetodoLaplaciano();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuMetodoLaplacianoConPendiente
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (originalImage != null) {

							aplicarMetodoLaplacianoConPendiente();

						} else {

							showAlertOriginalImageNull();

						}
					}
				});

		menuMetodoLaplacianoDelGausiano.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarMetodoLaplacianoDelGausiano();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDifusionIsotropica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarDifusionIsotropica();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuDifusionAnisotropica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarDifusionAnisotropica();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuUmbralizacionGlobal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarUmbralizacionGlobal();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});

		menuUmbralizacionOtsu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					generarUmbralizacionOtsu();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});
		
		menuSupresionNoMaximos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarSupresionNoMaximos();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});
		
		menuUmbralizacionConHisteresis.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarUmbralizacionConHisteresis();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});
		
		menuDetectorDeBordeCanny.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarCanny();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});
		
		menuTransformadaDeHough.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarTransformadaDeHough();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});
		
		menuHarris.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarMetodoHarris();

				} else {

					showAlertOriginalImageNull();

				}
			}

		});
		
		menuSift.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aplicarMetodoSift();
			}

		});
		
		menuMetodoDeSusan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {

					aplicarMetodoSusanBorde();

				} else {

					showAlertOriginalImageNull();

				}
			}
		});
		
		menuVideoPrimerImagen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				instanciarPrimerImagenVideo();
			}
			
		});
		
		labelPrincipalImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label1MouseClicked(e);
			}
		});
		
		menuSegmentacionSemisupervisada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (originalImage != null) {
					segmentarImagen();

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

				String extension = getFileExtension(archivoSeleccionado);

				if (extension.equalsIgnoreCase("raw")) {
					int width = Integer.valueOf(JOptionPane.showInputDialog(
							null, "Width", "Abrir imagen RAW",
							JOptionPane.DEFAULT_OPTION));

					int height = Integer.valueOf(JOptionPane.showInputDialog(
							null, "Height", "Abrir imagen RAW",
							JOptionPane.DEFAULT_OPTION));

					imageInLabel = abrirImagenRaw(archivoSeleccionado, width,
							height);
					originalImage = abrirImagenRaw(archivoSeleccionado, width,
							height);

				} else {

					imageInLabel = ImageIO.read(archivoSeleccionado);
					originalImage = ImageIO.read(archivoSeleccionado);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		
		this.getContentPane().setPreferredSize(new Dimension(imageInLabel.getWidth(), imageInLabel.getHeight()));
		this.pack();
		this.setVisible(true);
		
		return imageInLabel;
	}

	private String getFileExtension(File archivoSeleccionado) {
		String extension = "";

		int i = archivoSeleccionado.getName().lastIndexOf('.');
		if (i > 0) {
			extension = archivoSeleccionado.getName().substring(i + 1);
		}
		return extension;
	}

	private void refreshChanges() {

		imageInLabel = originalImage;

		labelPrincipalImage.setIcon(new ImageIcon(originalImage));
	}

	private void sumImages() {

		BufferedImage imageSum = getImageFromJFileChooser("Seleccione una imagen para la suma...");

		ImageOperations io = new ImageOperations(originalImage, imageSum);

		imageInLabel = io.aplicarTransformacionLineal(io.sumImages());

		if (imageInLabel != null) {

			labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		} else {

			showAlertByDifferentSizeOrType();
		}
	}

	private void restImages() {

		BufferedImage imageRest = getImageFromJFileChooser("Seleccione una imagen para la resta...");

		ImageOperations io = new ImageOperations(originalImage, imageRest);

		imageInLabel = io.restImages();

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void multiplicateImages() {

		BufferedImage imageMultip = getImageFromJFileChooser("Seleccione una imagen para la multiplicacion...");

		ImageOperations io = new ImageOperations(originalImage, imageMultip);

		imageInLabel = io.multiplicateImages();

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private BufferedImage getImageFromJFileChooser(String titleDialog) {

		BufferedImage imageToOperate = null;

		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle(titleDialog);

		int flag = selector.showOpenDialog(null);

		if (flag == JFileChooser.APPROVE_OPTION) {
			try {

				File archivoSeleccionado = selector.getSelectedFile();
				String extension = getFileExtension(archivoSeleccionado);

				if (extension.equalsIgnoreCase("raw")) {
					int width = Integer.valueOf(JOptionPane.showInputDialog(
							null, "Width", "Abrir imagen RAW",
							JOptionPane.DEFAULT_OPTION));

					int height = Integer.valueOf(JOptionPane.showInputDialog(
							null, "Height", "Abrir imagen RAW",
							JOptionPane.DEFAULT_OPTION));

					imageToOperate = abrirImagenRaw(archivoSeleccionado, width,
							height);

				} else {

					imageToOperate = ImageIO.read(archivoSeleccionado);
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return imageToOperate;
	}

	private void showAlertOriginalImageNull() {

		JOptionPane
				.showMessageDialog(
						null,
						"Primero debe abrir una imagen. Para ello, seleccione la opcion ABRIR IMAGEN en el menu ARCHIVO.");
	}

	private void showAlertByDifferentSizeOrType() {

		JOptionPane
				.showMessageDialog(
						null,
						"Seleccione una imagen con el mismo tipo y tama�o de la que se encuentra en la pantalla principal.");
	}

	private void multiplicarPorEscalar() {

		int scalar = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Escalar", "Multiplicar por escalar",
				JOptionPane.DEFAULT_OPTION));

		ImageOperations io = new ImageOperations();
		int[][] matrixOfImage = io.calcularMatrizDeLaImagen(imageInLabel);

		imageInLabel = io.multiplicateImagesByScalar(scalar, matrixOfImage);

		imageInLabel = io.compresionRangoDinamico(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void compresionRangoDinamico() {

		ImageOperations io = new ImageOperations();

		imageInLabel = io.compresionRangoDinamico(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void getNegative() {

		imageInLabel = ImageOperations.getNegativeImage(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void increaseContrast() {

		int increment = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Increment", "Contraste", JOptionPane.DEFAULT_OPTION));

		ImageOperations io = new ImageOperations();

		imageInLabel = io.changeBrightness(imageInLabel, increment);
		// imageInLabel = io.increaseImageContrast(imageInLabel, increment);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private void equalizeImage() {

		ImageOperations io = new ImageOperations();
		imageInLabel = io.histogramEqualization(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private void umbralizate() {

		int umbral = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Umbral (entre 0 y 255)", "Umbralizacion",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = ImageOperations.umbralization(imageInLabel, umbral);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarRuidoBlacoGaussiano() {

		int sigma = Integer.valueOf(JOptionPane.showInputDialog(null, "Sigma",
				"Generador de ruido blanco gaussiano",
				JOptionPane.DEFAULT_OPTION));

		int mu = Integer.valueOf(JOptionPane.showInputDialog(null, "Mu",
				"Generador de ruido blanco gaussiano",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = GeneradorDeRuido.generarRuidoGauss(imageInLabel, sigma,
				mu);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarRuidoRayleighMultiplicativo() {

		double phi = Double.valueOf(JOptionPane.showInputDialog(null, "phi",
				"Generador de ruido Rayleigh Multiplicativo",
				JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoRayleighMultiplicativo(
				imageInLabel, phi);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private void generarRuidoExponencialMultiplicativo() {

		double lambda = Double.valueOf(JOptionPane.showInputDialog(null,
				"Lambda", "Generador de ruido Exponencial Multiplicativo",
				JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoExponencialMultiplicativo(
				imageInLabel, lambda);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private void crearImagenSintetica() {
		BufferedImage bmp = new BufferedImage(500, 500,
				BufferedImage.TYPE_BYTE_BINARY);

		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				bmp.setRGB(i, j, Color.BLACK.getRGB());
			}
		}

		Graphics2D g = bmp.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(220, 220, 50, 50);

		originalImage = bmp;
		imageInLabel = bmp;
		imageInLabel = ImageOperations.getNegativeImage(imageInLabel);
		imageInLabel = aplicarDetectorDeBordeSobel();
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarRuidoSalYPimienta() {

		int densidadContaminacion = Integer.valueOf(JOptionPane
				.showInputDialog(null, "Densidad de Contaminacion",
						"Generador de ruido Sal y Pimienta",
						JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();
		imageInLabel = generadorDeRuido.ruidoImpulsivo(imageInLabel,
				densidadContaminacion);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarFiltroDeLaMedia() {

		int ancho = Integer.valueOf(JOptionPane.showInputDialog(null, "Ancho",
				"Generador de Filtro de la Media", JOptionPane.DEFAULT_OPTION));

		int alto = Integer.valueOf(JOptionPane.showInputDialog(null, "Alto",
				"Generador de Filtro de la Media", JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();

		imageInLabel = generadorDeRuido.suavizadoConFiltroDeLaMedia(
				imageInLabel, ancho, alto);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private void generarFiltroDeLaMediana() {

		int ancho = Integer.valueOf(JOptionPane.showInputDialog(null, "Ancho",
				"Generador de Filtro de la Media", JOptionPane.DEFAULT_OPTION));

		int alto = Integer.valueOf(JOptionPane.showInputDialog(null, "Alto",
				"Generador de Filtro de la Media", JOptionPane.DEFAULT_OPTION));

		GeneradorDeRuido generadorDeRuido = new GeneradorDeRuido();

		imageInLabel = generadorDeRuido.suavizadoConFiltroDeLaMediana(
				imageInLabel, ancho, alto);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private BufferedImage abrirImagenRaw(File archivoActual, int width,
			int height) {

		BufferedImage imagen = null;
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(archivoActual.toPath());

			imagen = new BufferedImage(width, height,
					BufferedImage.TYPE_3BYTE_BGR);
			int contador = 0;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					int alpha = -16777216;
					int red = ((int) bytes[contador] & 0xff) << 16;
					int green = ((int) bytes[contador] & 0xff) << 8;
					int blue = ((int) bytes[contador] & 0xff);

					int color = alpha + red + green + blue;

					imagen.setRGB(j, i, color);

					contador++;
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return imagen;
	}

	private void generarFiltroDeGauss() {

		int sigma = Integer.valueOf(JOptionPane.showInputDialog(null, "Sigma",
				"Aplicar filtro de Gauss", JOptionPane.DEFAULT_OPTION));

		imageInLabel = FiltroGaussiano.aplicarFiltroGaussiano(imageInLabel,
				sigma);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarFiltroPasaAltos() {

		int longitudMascara = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Longitud de Mascara", "Aplicar filtro Pasa Altos",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = FiltroPasaAltos.aplicarFiltroPasaAltos(imageInLabel,
				longitudMascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private void generarFiltroPasaBajos() {

		int longitudMascara = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Longitud de Mascara", "Aplicar filtro Pasa Bajos",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = FiltroPasaBajos.aplicarFiltroPasaBajos(imageInLabel,
				longitudMascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}

	private BufferedImage aplicarDetectorDeBordePrewitt() {

		int mascaraX[][] = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };

		int mascaraY[][] = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

		imageInLabel = Borde.detectarBorde(imageInLabel, mascaraX, mascaraY);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
		return imageInLabel;
	}

	private void aplicarDetectorDeBordePrewittHorizontal() {

		int mascaraHorizontal[][] = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraHorizontal);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordePrewitt45Grados() {

		int mascara[][] = { { 0, -1, -1 }, { 1, 0, -1 }, { 1, 1, 0 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel, mascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordePrewitt135Grados() {

		int mascara[][] = { { -1, -1, 0 }, { -1, 0, 1 }, { 0, 1, 1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel, mascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordePrewittVertical() {

		int mascaraVertical[][] = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordePrewittColor() {

		int mascaraX[][] = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };

		int mascaraY[][] = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

		imageInLabel = Borde.detectarBordeColor(imageInLabel, mascaraX,
				mascaraY);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private BufferedImage aplicarDetectorDeBordeSobel() {
		int mascaraX[][] = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };

		int mascaraY[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		imageInLabel = Borde.detectarBorde(imageInLabel, mascaraX, mascaraY);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
		return imageInLabel;
	}

	private void aplicarDetectorDeBordeSobelColor() {
		int mascaraX[][] = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };

		int mascaraY[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		imageInLabel = Borde.detectarBordeColor(imageInLabel, mascaraX,
				mascaraY);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarMetodoLaplaciano() {
		int mascara[][] = { { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };

		imageInLabel = Borde.detectarBordeLaplaciano(imageInLabel, mascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarMetodoLaplacianoConPendiente() {

		int umbral = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Umbral", "Borde Laplaciano con Derivada Direccional",
				JOptionPane.DEFAULT_OPTION));

		int mascara[][] = { { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };
		imageInLabel = Borde.detectarBordeLaplacianoConDerivadaDireccional(
				imageInLabel, mascara, umbral);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordeSobelVertical() {

		int mascaraVertical[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordeSobel45Grados() {

		int mascara[][] = { { 0, -1, -2 }, { 1, 0, -1 }, { 2, 1, 0 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel, mascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordeSobel135Grados() {

		int mascara[][] = { { -2, -1, 0 }, { -1, 0, 1 }, { 0, 1, 2 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel, mascara);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordeKirshVertical() {

		int mascaraVertical[][] = { { 5, 5, 5 }, { -3, 0, -3 }, { -3, -3, -3 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordeKirsh45Grados() {

		int mascaraVertical[][] = { { -3, -3, -3 }, { 5, 0, -3 }, { 5, 5, -3 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordeKirsh135Grados() {

		int mascaraVertical[][] = { { -3, -3, -3 }, { -3, 0, 5 }, { -3, 5, 5 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	private void aplicarDetectorDeBordePuntoAVertical() {

		int mascaraVertical[][] = { { 1, 1, 1 }, { 1, -2, 1 }, { -1, -1, -1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordePuntoA45Grados() {

		int mascaraVertical[][] = { { 1, -1, -1 }, { 1, -2, -1 }, { 1, 1, 1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarDetectorDeBordePuntoA135Grados() {

		int mascaraVertical[][] = { { -1, -1, 1 }, { -1, -2, 1 }, { 1, 1, 1 } };

		imageInLabel = Borde.aplicarMascara(imageInLabel,
				mascaraVertical);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarDifusionIsotropica() {

		int sigma = Integer.valueOf(JOptionPane.showInputDialog(null, "Sigma",
						"Generador de Difusion Isotropica",
						JOptionPane.DEFAULT_OPTION));

		int cantidadRepeticiones = Integer.valueOf(JOptionPane.showInputDialog(null, "Cantidad de Pasos: ",
				"Generador de Difusion Isotropica",
				JOptionPane.DEFAULT_OPTION));
		
		imageInLabel = Difuminador.aplicarDifusion(imageInLabel, cantidadRepeticiones, true, sigma);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarDifusionAnisotropica() {

		int sigma = Integer.valueOf(JOptionPane.showInputDialog(null, "Sigma",
				"Generador de Difusion Anisotropica",
				JOptionPane.DEFAULT_OPTION));

		int cantidadRepeticiones = Integer.valueOf(JOptionPane.showInputDialog(
				null, "Cantidad de Pasos",
				"Generador de Difusion Anisotropica",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = Difuminador.aplicarDifusion(imageInLabel, cantidadRepeticiones, false, sigma);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarUmbralizacionGlobal() {

		int umbral = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Umbral", "Umbralizacion Automatica",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = ImageOperations.generarUmbralizacionGlobal(imageInLabel,
				umbral);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void generarUmbralizacionOtsu() {

		imageInLabel = ImageOperations.generarUmbralizacionOtsu(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarMetodoLaplacianoDelGausiano() {

		int sigma = Integer
				.valueOf(JOptionPane.showInputDialog(null, "Sigma",
						"Filtro LoG",
						JOptionPane.DEFAULT_OPTION));
		
		int umbral = Integer
				.valueOf(JOptionPane.showInputDialog(null, "Umbral",
						"Filtro LoG",
						JOptionPane.DEFAULT_OPTION));
		
		int dimensionMascara = Integer
				.valueOf(JOptionPane.showInputDialog(null, "Dimension Mascara",
						"Filtro LoG",
						JOptionPane.DEFAULT_OPTION));
		
		imageInLabel = FiltroGaussiano.aplicarFiltroLoG(imageInLabel,
				sigma, umbral, dimensionMascara);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}
	
	private void aplicarSupresionNoMaximos() {

		imageInLabel = DetectorDeBordeCanny.aplicarSupresionNoMaximos(imageInLabel);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}
	

	private void aplicarUmbralizacionConHisteresis() {
		int umbral1 = Integer.valueOf(JOptionPane.showInputDialog(null, "Umbral 1: ", "Umbralizacion con Histeresis", JOptionPane.DEFAULT_OPTION));
		int umbral2 = Integer.valueOf(JOptionPane.showInputDialog(null, "Umbral 2: ", "Umbralizacion con Histeresis", JOptionPane.DEFAULT_OPTION));
		
		imageInLabel = DetectorDeBordeCanny.aplicarUmbralizacionConHisteresis(imageInLabel, umbral1, umbral2);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));		
	}
	
	private void aplicarCanny() {
		int umbral1 = Integer.valueOf(JOptionPane.showInputDialog(null, "Umbral 1: ", "Canny", JOptionPane.DEFAULT_OPTION));
		int umbral2 = Integer.valueOf(JOptionPane.showInputDialog(null, "Umbral 2: ", "Canny", JOptionPane.DEFAULT_OPTION));
		
		int sigma1 = Integer.valueOf(JOptionPane.showInputDialog(null, "Sigma 1: ", "Canny", JOptionPane.DEFAULT_OPTION));
		int sigma2 = Integer.valueOf(JOptionPane.showInputDialog(null, "Sigma 2: ", "Canny", JOptionPane.DEFAULT_OPTION));

		imageInLabel = DetectorDeBordeCanny.detectorDeBordeCanny(imageInLabel, sigma1, sigma2, umbral1, umbral2);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	private void aplicarTransformadaDeHough() {
		
		Integer saltoEntreDiscretizacionesRo = Integer.valueOf(JOptionPane.showInputDialog(null, "Discretizaciones Rho: ", 
				"Transformada de Hough", JOptionPane.DEFAULT_OPTION));
		Integer tethaMin = Integer.valueOf(JOptionPane.showInputDialog(null, "Tetha mínimo: ", 
				"Transformada de Hough", JOptionPane.DEFAULT_OPTION));
		Integer tethaMax = Integer.valueOf(JOptionPane.showInputDialog(null, "Tetha máximo: ", 
				"Transformada de Hough", JOptionPane.DEFAULT_OPTION));
		Integer discretizacionesTetha = Integer.valueOf(JOptionPane.showInputDialog(null, "Discretizaciones Tetha: ", 
				"Transformada de Hough", JOptionPane.DEFAULT_OPTION));
		
		MatrizAcumuladora matriz = new MatrizAcumuladora(0, 200, tethaMin, tethaMax, saltoEntreDiscretizacionesRo, discretizacionesTetha);
		
		imageInLabel = TransformadaDeHough.aplicarTransformadaDeHough(imageInLabel, matriz);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
	}
	
	private void segmentarImagen(){
		Point punto1 = new Point();
		punto1.setLocation(primerPuntoX, primerPuntoY);
		
		Point punto2 = new Point();
		punto2.setLocation(segundoPuntoX, segundoPuntoY);
		
		Segmentador seg = new Segmentador();
		imageInLabel = seg.segmentarImagen(originalImage, punto1, punto2).getImage();

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));	
		
	}
	
	private void label1MouseClicked(MouseEvent e) {
		if(primerPuntoX == -1 || segundoPuntoX == -1){
			if(primerPuntoX == -1 && primerPuntoY == -1){
				primerPuntoX =(int)e.getPoint().getX();
				primerPuntoY = (int) e.getPoint().getY();
	
			} else {
				segundoPuntoX =(int)e.getPoint().getX();
				segundoPuntoY = (int) e.getPoint().getY();
			}
		}
		
		if(primerPuntoX != -1 && segundoPuntoX != -1){
//			int tercerPuntoX = primerPuntoX;
//			int tercerPuntoY = segundoPuntoY;
//			int cuartoPuntoX = segundoPuntoX;
//			int cuartoPuntoY = primerPuntoY;
//			
//			Graphics2D g = imageInLabel.createGraphics();
//		    g.setColor(Color.BLACK);
//		    g.drawLine(primerPuntoX, primerPuntoY, tercerPuntoX, tercerPuntoY);
//		    g.drawLine(tercerPuntoX, tercerPuntoY, segundoPuntoX, segundoPuntoY);
//		    g.drawLine(segundoPuntoX, segundoPuntoY, cuartoPuntoX, cuartoPuntoY);
//		    g.drawLine(cuartoPuntoX, cuartoPuntoY, primerPuntoX, primerPuntoY);
		    
		}
        
        labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}


	private void aplicarMetodoSusanBorde() {
		
		String flagDetector = String.valueOf(JOptionPane.showInputDialog(null, "Opcion borde/esquina", "Filtro LoG", 
				JOptionPane.DEFAULT_OPTION));

		Susan susan = new Susan();
		imageInLabel = susan.aplicarSusanBorde(imageInLabel, flagDetector);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	
	public void instanciarPrimerImagenVideo() {
		
		Integer rutaVideo = Integer.valueOf(JOptionPane.showInputDialog(null, "Numero de Video", "Segmentador", 
				JOptionPane.DEFAULT_OPTION));

		File folder = new File("video/Movie" + String.valueOf(rutaVideo));
		
		files = folder.listFiles();
		Arrays.sort(files);

		try {
			imageInLabel = ImageIO.read(files[0]);
			originalImage = ImageIO.read(files[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.getContentPane().setPreferredSize(new Dimension(imageInLabel.getWidth(), imageInLabel.getHeight()));
		this.pack();
		this.setVisible(true);
		
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		
		buttonSiguienteImagen = new JButton("Procesar Imagen");
//		buttonPlay = new JButton("Play");
		this.menuBar.add(buttonSiguienteImagen);
//		this.add(buttonPlay);
		
		agregarListenerAlBoton();
		
	}

	private void agregarListenerAlBoton() {
		buttonSiguienteImagen.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				procesarVideo();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
	}

	private void procesarVideo() {
		
		Point punto1 = new Point();
		punto1.setLocation(primerPuntoX, primerPuntoY);

		Point punto2 = new Point();
		punto2.setLocation(segundoPuntoX, segundoPuntoY);

		Arrays.sort(files);
		
		Segmentador segmentador = new Segmentador();
		
		if (this.contadorImagenes == 0) {
			procesamientoAnterior = segmentador.segmentarImagen(imageInLabel, punto1, punto2);
		} else {
			try {
				procesamientoAnterior = segmentador.segmentarImagen(ImageIO.read(files[contadorImagenes]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		contadorImagenes++;

		imageInLabel = procesamientoAnterior.getImage();
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
		mostrarTiempoPromedio();
	}

	private void mostrarTiempoPromedio() {
			if(tiempo1 == null){
				tiempo1 = System.currentTimeMillis();
			} else {
				tiempo2 = System.currentTimeMillis();
			}
			
			if(tiempo2 != null){
				tiempos.add(tiempo2 - tiempo1);
				tiempo1 = tiempo2;
			}
		if(contadorImagenes == 40){
			long contadorTiempos = 0;
			for(Long unTiempo : tiempos){
				contadorTiempos+=unTiempo;
			}
			
			JOptionPane.showMessageDialog(null, "El promedio de tiempo procesado en 40 imagenes es de: " + String.valueOf(contadorTiempos / (long) tiempos.size()) + " milisegundos");		
		}
	}
	

	private void aplicarMetodoHarris() {
		DetectorDeHarris dt = new DetectorDeHarris(imageInLabel);
		imageInLabel = dt.detectarEsquinas();
		
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}
	
	private void aplicarMetodoSift() {
		this.seleccionarQuerySift();
		this.seleccionarTargetSift();
		
		try {
			Sift.aplicar(querySift, targetSift);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void seleccionarQuerySift() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccione la primer imagen a comparar");

		int flag = selector.showOpenDialog(null);

		if (flag == JFileChooser.APPROVE_OPTION) {
			this.querySift = selector.getSelectedFile();
		}
	}
	
	public void seleccionarTargetSift() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccione la segunda imagen a comparar");

		int flag = selector.showOpenDialog(null);

		if (flag == JFileChooser.APPROVE_OPTION) {
			this.targetSift = selector.getSelectedFile();
		}
	}
}