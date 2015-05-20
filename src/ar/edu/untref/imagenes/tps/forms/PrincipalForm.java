package ar.edu.untref.imagenes.tps.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

import ar.edu.untref.imagenes.tps.bordes.Borde;
import ar.edu.untref.imagenes.tps.difusion.Difuminador;
import ar.edu.untref.imagenes.tps.noise.FiltroGaussiano;
import ar.edu.untref.imagenes.tps.noise.FiltroPasaAltos;
import ar.edu.untref.imagenes.tps.noise.FiltroPasaBajos;
import ar.edu.untref.imagenes.tps.noise.GeneradorDeRuido;
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
	private JMenuItem menuDetectorBordePrewitt;
	private JMenuItem menuDetectorBordePrewittX;
	private JMenuItem menuDetectorBordePrewittY;
	private JMenuItem menuDetectorBordePrewitt45Grados;
	private JMenuItem menuDetectorBordePrewitt135Grados;
	private JMenuItem menuDetectorBordePrewittColor;
	private JMenuItem menuDetectorBordeSobel;
	private JMenuItem menuDetectorBordeSobelVertical;
	private JMenuItem menuDetectorBordeSobel45Grados;
	private JMenuItem menuDetectorBordeSobel135Grados;
	private JMenuItem menuDetectorBordeSobelColor;

	private JMenuItem menuDetectorBordeKirshVertical;
	private JMenuItem menuDetectorBordeKirsh45Grados;
	private JMenuItem menuDetectorBordeKirsh135Grados;
	
	private JMenuItem menuDetectorBordePuntoAVertical;
	private JMenuItem menuDetectorBordePuntoA45Grados;
	private JMenuItem menuDetectorBordePuntoA135Grados;

	private JMenuItem menuMetodoLaplaciano;
	private JMenuItem menuMetodoLaplacianoConPendiente;
	private JMenuItem menuMetodoLaplacianoDelGausiano;

	private JMenuItem menuDifusionIsotropica;
	private JMenuItem menuDifusionAnisotropica;

	private JMenuItem menuUmbralizacionGlobal;
	private JMenuItem menuUmbralizacionOtsu;

	private JScrollPane scrollPane;
	private JPanel contentPane;

	private JLabel labelPrincipalImage;

	private BufferedImage imageInLabel;
	private BufferedImage originalImage;

	public PrincipalForm() {

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

		menuDetectorBordePrewitt = new JMenuItem("Detector de Bordes Prewitt");
		menuDeteccionDeBordes.add(menuDetectorBordePrewitt);

		menuDetectorBordePrewittX = new JMenuItem(
				"Detector de Bordes Prewitt X");
		menuDeteccionDeBordes.add(menuDetectorBordePrewittX);

		menuDetectorBordePrewittY = new JMenuItem(
				"Detector de Bordes Prewitt Y");
		menuDeteccionDeBordes.add(menuDetectorBordePrewittY);

		menuDetectorBordePrewitt45Grados = new JMenuItem(
				"Detector de Bordes Prewitt 45 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordePrewitt45Grados);

		menuDetectorBordePrewitt135Grados = new JMenuItem(
				"Detector de Bordes Prewitt 135 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordePrewitt135Grados);

		menuDetectorBordePrewittColor = new JMenuItem(
				"Detector de Bordes Prewitt (Color)");
		menuDeteccionDeBordes.add(menuDetectorBordePrewittColor);

		menuDetectorBordeSobel = new JMenuItem("Detector de Bordes Sobel");
		menuDeteccionDeBordes.add(menuDetectorBordeSobel);

		menuDetectorBordeSobelVertical = new JMenuItem(
				"Detector de Bordes Sobel Vertical");
		menuDeteccionDeBordes.add(menuDetectorBordeSobelVertical);

		menuDetectorBordeSobel45Grados = new JMenuItem(
				"Detector de Bordes Sobel 45 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordeSobel45Grados);

		menuDetectorBordeSobel135Grados = new JMenuItem(
				"Detector de Bordes Sobel 135 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordeSobel135Grados);

		menuDetectorBordeSobelColor = new JMenuItem(
				"Detector de Bordes Sobel (Color)");
		menuDeteccionDeBordes.add(menuDetectorBordeSobelColor);

		menuDetectorBordeKirshVertical = new JMenuItem(
				"Detector de Bordes Kirsh Vertical");
		menuDeteccionDeBordes.add(menuDetectorBordeKirshVertical);

		menuDetectorBordeKirsh45Grados = new JMenuItem(
				"Detector de Bordes Kirsh 45 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordeKirsh45Grados);

		menuDetectorBordeKirsh135Grados = new JMenuItem(
				"Detector de Bordes Kirsh 135 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordeKirsh135Grados);
		
		menuDetectorBordePuntoAVertical = new JMenuItem(
				"Detector de Bordes Punto A");
		menuDeteccionDeBordes.add(menuDetectorBordePuntoAVertical);

		menuDetectorBordePuntoA45Grados = new JMenuItem(
				"Detector de Bordes Punto A 45 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordePuntoA45Grados);

		menuDetectorBordePuntoA135Grados = new JMenuItem(
				"Detector de Bordes Punto A 135 Grados");
		menuDeteccionDeBordes.add(menuDetectorBordePuntoA135Grados);

		menuMetodoLaplaciano = new JMenuItem("Metodo del Laplaciano");
		menuDeteccionDeBordes.add(menuMetodoLaplaciano);

		menuMetodoLaplacianoConPendiente = new JMenuItem(
				"Metodo del Laplaciano con Pendiente");
		menuDeteccionDeBordes.add(menuMetodoLaplacianoConPendiente);

		menuMetodoLaplacianoDelGausiano = new JMenuItem(
				"Metodo del Laplaciano del Gausiano");
		menuDeteccionDeBordes.add(menuMetodoLaplacianoDelGausiano);

		menuDifusionIsotropica = new JMenuItem("Difusion Isotropica");
		menuDeteccionDeBordes.add(menuDifusionIsotropica);

		menuDifusionAnisotropica = new JMenuItem("Difusion Anisotropica");
		menuDeteccionDeBordes.add(menuDifusionAnisotropica);

		menuUmbralizacionGlobal = new JMenuItem("Metodo Umbralizacion Global");
		menuDeteccionDeBordes.add(menuUmbralizacionGlobal);

		menuUmbralizacionOtsu = new JMenuItem("Metodo Umbralizacion Otsu");
		menuDeteccionDeBordes.add(menuUmbralizacionOtsu);
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

		ImageOperations io = new ImageOperations();
		imageInLabel = io.getNegativeImage(imageInLabel);

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
		BufferedImage bmp = new BufferedImage(100, 100,
				BufferedImage.TYPE_BYTE_BINARY);

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
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

	private void aplicarDetectorDeBordePrewitt() {

		int mascaraX[][] = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };

		int mascaraY[][] = { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };

		imageInLabel = Borde.detectarBorde(imageInLabel, mascaraX, mascaraY);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
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

	private void aplicarDetectorDeBordeSobel() {
		int mascaraX[][] = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };

		int mascaraY[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		imageInLabel = Borde.detectarBorde(imageInLabel, mascaraX, mascaraY);
		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
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

		int umbral = Integer.valueOf(JOptionPane.showInputDialog(null,
				"Umbral t = ", "Umbralizacion Otzu.",
				JOptionPane.DEFAULT_OPTION));

		imageInLabel = ImageOperations.generarUmbralizacionOtsu(imageInLabel,
				umbral);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));
	}

	private void aplicarMetodoLaplacianoDelGausiano() {

		int sigma = Integer
				.valueOf(JOptionPane.showInputDialog(null, "Sigma",
						"Filtro LoG",
						JOptionPane.DEFAULT_OPTION));
		
		imageInLabel = FiltroGaussiano.aplicarFiltroLoG(imageInLabel,
				sigma);

		labelPrincipalImage.setIcon(new ImageIcon(imageInLabel));

	}
}
