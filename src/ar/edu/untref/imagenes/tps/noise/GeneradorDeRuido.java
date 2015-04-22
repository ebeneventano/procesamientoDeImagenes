package ar.edu.untref.imagenes.tps.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import ar.edu.untref.imagenes.utils.ColorProvider;

public class GeneradorDeRuido {

	public double generarNumeroAleatorioGaussiano(double desviacionEstandar, double valorMedio) {

		double varianza = desviacionEstandar * desviacionEstandar;
		double x = valorMedio + new Random().nextGaussian() * varianza;

		double coeficiente = 1 / (desviacionEstandar * Math.sqrt(2 * Math.PI));

		double numeradorDeLaPotencia = (((-1) * x) - valorMedio);
		numeradorDeLaPotencia = numeradorDeLaPotencia * numeradorDeLaPotencia;
		double denominadorDeLaPotencia = 2 * desviacionEstandar
				* desviacionEstandar;

		return coeficiente
				* Math.exp(numeradorDeLaPotencia / denominadorDeLaPotencia);
	}

	public double generarNumeroAleatorioRayleigh(double phi) {

		double x = 0;
		while (x == 0) {
			x = Math.random();
		}

		double coeficiente = x / (phi * phi);

		double numeradorDeLaPotencia = (-1) * (x * x);
		double denominadorDelaPotencia = 2 * phi * phi;

		return coeficiente
				* Math.exp(numeradorDeLaPotencia / denominadorDelaPotencia);
	}

	public double generarNumeroAleatorioExponencial(double lambda) {

		double x = 0;
		while (x == 0) {
			x = Math.random();
		}

		return lambda * Math.exp((-1) * lambda * x);
	}

	public BufferedImage ruidoGaussianoAditivo(BufferedImage original, double sigma, double mu) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				double nivelDeRojo = new Color(original.getRGB(i, j)).getRed();

				double x1 = Math.random();
				double x2 = Math.random();
				double ruido = (Math.sqrt((-2) * Math.log10(x1)) * Math.cos(2
						* Math.PI * x2))
						* sigma + mu;

				int ruidoAditivo = (int) (nivelDeRojo + ruido);

				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				int nuevoPixel = ColorProvider.colorToRGB(alpha, ruidoAditivo,
													ruidoAditivo, ruidoAditivo);

				nuevaImagen.setRGB(i, j, nuevoPixel);
			}
		}

		return nuevaImagen;
	}

	public BufferedImage ruidoRayleighMultiplicativo(BufferedImage original, double phi) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				double x = Math.random();
				double ruido = phi * Math.sqrt( (-2) * Math.log10(1-x) );
//				double ruido = 1 - (Math.exp(-((x*x)/(2*phi*phi))));

				double nivelDeRojo = new Color(original.getRGB(i, j)).getRed();
				
				int ruidoMultiplicativo = (int) (nivelDeRojo * ruido);

				int alpha = (int) (new Color(original.getRGB(i, j)).getAlpha() * ruido);
				int nuevoPixel = ColorProvider.colorToRGB(alpha, ruidoMultiplicativo,
														ruidoMultiplicativo, ruidoMultiplicativo);

				nuevaImagen.setRGB(i, j, nuevoPixel);
				
			}
		}


		return nuevaImagen;
	}

	public BufferedImage ruidoExponencialMultiplicativo(BufferedImage original, double lambda) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				double x = Math.random();
				while (x == 0) {
					x = Math.random();
				}

				double ruido = ((-1) / lambda) * Math.log10(x);

				double nivelDeRojo = new Color(original.getRGB(i, j)).getRed();
				
				int ruidoMultiplicativo = (int) (nivelDeRojo * ruido);

				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				int nuevoPixel = ColorProvider.colorToRGB(alpha, ruidoMultiplicativo,
														ruidoMultiplicativo, ruidoMultiplicativo);

				nuevaImagen.setRGB(i, j, nuevoPixel);
				
			}
		}


		return nuevaImagen;
	}
	
	public BufferedImage ruidoImpulsivo(BufferedImage original, int densidadDeContaminacion) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		
		int cantidadDePixelesDeLaImagen = original.getWidth() * original.getHeight();
		int cantidadDePixelesAContaminar = densidadDeContaminacion * cantidadDePixelesDeLaImagen / 100;

		// Copiar imagen antes de contaminarla
		for(int ancho = 0; ancho < original.getWidth() ; ancho++) {
			for(int alto = 0; alto < original.getHeight() ; alto++) {
				nuevaImagen.setRGB(ancho, alto, original.getRGB(ancho, alto));
			}
		}
		
		while (cantidadDePixelesAContaminar > 0) {

			System.out.println("cantidadDePixelesAContaminar: " + cantidadDePixelesAContaminar);
			
			int i = (int) (Math.random() * original.getWidth()) ;
			int j = (int) (Math.random() * original.getHeight());

			// Contaminación
			double x = Math.random();

			double p0 = Math.random();
			
			double p1 = Math.random();
			while (p1 <= p0) {
				p1 = Math.random();
			}
			
			int pixelBlanco = ColorProvider.colorToRGB(255, 255, 255, 255);
			int pixelNegro = ColorProvider.colorToRGB(255, 0, 0, 0);
			
			if (x <= p0) {
				nuevaImagen.setRGB(i, j, pixelNegro);

			/* Consultar: ¿Qué hacemos con los valores intermedios? */
//			} else if (x >= p1) {
			} else {

				nuevaImagen.setRGB(i, j, pixelBlanco);
			}
			// Fin contaminación
			
			cantidadDePixelesAContaminar--;
		}
		
		return nuevaImagen;
	}
	
	public BufferedImage suavizadoConFiltroDeLaMedia(BufferedImage original, int anchoMascara, int altoMascara) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		
		// Copiar imagen antes de filtrarla
		for(int ancho = 0; ancho < original.getWidth() ; ancho++) {
			for(int alto = 0; alto < original.getHeight() ; alto++) {
				nuevaImagen.setRGB(ancho, alto, original.getRGB(ancho, alto));
			}
		}

		// Crear la máscara de la mediana
		int[][] mascara = new int[anchoMascara][altoMascara];
		for (int i = 0; i < anchoMascara; i++) {
			for(int j = 0; j < altoMascara; j++) {
				mascara[i][j] = 1;
			}
		}
		
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		
		// Agregar borde zero-padding
		int pixelNegro = ColorProvider.colorToRGB(255, 0, 0, 0);
		for (int i = 0; i < anchoMascara / 2; i++) {
			for (int j = 0 ; j < nuevaImagen.getHeight() ; j++) {
				
				nuevaImagen.setRGB(i, j, pixelNegro);
				nuevaImagen.setRGB(nuevaImagen.getWidth() - 1 - i, nuevaImagen.getHeight() - 1 - j, pixelNegro);
			}
		}
		
		for (int i = 0; i < nuevaImagen.getWidth(); i++) {
			for (int j = 0 ; j < altoMascara / 2 ; j++) {
				
				nuevaImagen.setRGB(i, j, pixelNegro);
				nuevaImagen.setRGB(nuevaImagen.getWidth() - 1 - i, nuevaImagen.getHeight() - 1 - j, pixelNegro);
			}
		}
		
		// Iterar la imagen, sacando los bordes.
		for(int i = anchoMascara / 2; i < original.getWidth() - (anchoMascara / 2); i++) {
			for(int j = altoMascara / 2; j < original.getHeight() - (altoMascara / 2); j++) {
				
				int sumatoria = 0;
				// Iterar la máscara
				for(int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for(int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {
						
						// Opero si no es el punto central de la máscara
						if(!(iAnchoMascara == (anchoMascara / 2) && iAltoMascara == (altoMascara / 2))) {
							
							int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
							int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;
				
							double nivelDeRojo = new Color(original.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
							sumatoria += nivelDeRojo * mascara[iAnchoMascara][iAltoMascara];
							
						}
						
					}
				}
				
				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				sumatoria = sumatoria / (anchoMascara * altoMascara);
				int nuevoPixel = ColorProvider.colorToRGB(alpha, sumatoria,
															sumatoria, sumatoria);

				nuevaImagen.setRGB(i, j, nuevoPixel);
			}
		}
		
		return nuevaImagen;
	}

	public BufferedImage suavizadoConFiltroDeLaMediana(BufferedImage original, int anchoMascara, int altoMascara) {

		BufferedImage nuevaImagen = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		
		// Copiar imagen antes de filtrarla
		for(int ancho = 0; ancho < original.getWidth() ; ancho++) {
			for(int alto = 0; alto < original.getHeight() ; alto++) {
				nuevaImagen.setRGB(ancho, alto, original.getRGB(ancho, alto));
			}
		}

		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		
		// Agregar borde zero-padding
		int pixelNegro = ColorProvider.colorToRGB(255, 0, 0, 0);
		for (int i = 0; i < anchoMascara / 2; i++) {
			for (int j = 0 ; j < nuevaImagen.getHeight() ; j++) {
				
				nuevaImagen.setRGB(i, j, pixelNegro);
				nuevaImagen.setRGB(nuevaImagen.getWidth() - 1 - i, nuevaImagen.getHeight() - 1 - j, pixelNegro);
			}
		}
		
		for (int i = 0; i < nuevaImagen.getWidth(); i++) {
			for (int j = 0 ; j < altoMascara / 2 ; j++) {
				
				nuevaImagen.setRGB(i, j, pixelNegro);
				nuevaImagen.setRGB(nuevaImagen.getWidth() - 1 - i, nuevaImagen.getHeight() - 1 - j, pixelNegro);
			}
		}
		
		// Iterar la imagen, sacando los bordes.
		for(int i = anchoMascara / 2; i < original.getWidth() - (anchoMascara / 2); i++) {
			for(int j = altoMascara / 2; j < original.getHeight() - (altoMascara / 2); j++) {
				
				Integer idxVal = 0;
				float[] valores = new float[anchoMascara*altoMascara];
				
				// Generaro mascara
				for(int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for(int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {
						
						int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;
				
						float nivelDeRojo = new Color(original.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						valores[idxVal] = nivelDeRojo;	
						idxVal++;
						
					}
				}
				
				int alpha = new Color(original.getRGB(i, j)).getAlpha();
				float mediana = obtenerValorMedio(valores);
				int nuevoPixel = ColorProvider.colorToRGB(alpha, (int)mediana, (int)mediana, (int)mediana);

				nuevaImagen.setRGB(i, j, nuevoPixel);
			}
		}
		
		return nuevaImagen;
	}
	
	public float obtenerValorMedio(float[] valores) {

		Arrays.sort(valores);
		float mediana;
		if (valores.length % 2 == 0)
		    mediana = ((float)valores[valores.length/2] + (float)valores[valores.length/2 - 1])/2;
		else
		    mediana = (float) valores[valores.length/2];
		
		return mediana;
	}
	
	static float[][] matrizRojos;
	static float[][] matrizVerdes;
	static float[][] matrizAzules;

	// 8 - A - Generador de Aleatorios para Ruido de Gauss
	private static double[] generadorFuncionesAleatoriasDeGauss() {
		double x1, x2, y1, y2;
		Random numero1 = new Random();
		Random numero2 = new Random();

		x1 = 0;
		x2 = 0;

		do
			x1 = numero1.nextGaussian();
		while (x1 <= 0 | x1 > 1); // x1 no puede ser cero ni mayor a 1

		do
			x2 = numero2.nextGaussian();
		while (x2 <= 0 | x2 > 1); // x2 no puede ser cero ni mayor a 1

		y1 = Math.sqrt(-2 * Math.log(x1)) * Math.cos(2 * Math.PI * x2);
		y2 = Math.sqrt(-2 * Math.log(x1)) * Math.sin(2 * Math.PI * x2);

		double[] resultados = new double[2];
		resultados[0] = y1;
		resultados[1] = y2;

		return resultados;
	}
	// Aplica alguna de las 2 funciones aleatorias y a su vez calcula los M�ximos y m�nimos para poder generar el ruido Gaussiano
	private static float[] aplicarFuncionAleatoriaYObtenerMaximosYMinimos(BufferedImage bufferedImage, int sigma, int mu) {

		float rojoMin;
		float rojoMax;
		float verdeMin;
		float verdeMax;
		float azulMin;
		float azulMax;

		int rojo = 0;
		int verde = 0;
		int azul = 0;

		Random random = new Random();
		int nrows = bufferedImage.getWidth();
		int ncols = bufferedImage.getHeight();
		matrizRojos = new float[nrows][ncols];
		matrizVerdes = new float[nrows][ncols];
		matrizAzules = new float[nrows][ncols];

		rojoMin = 0; 
		rojoMax = 255;
		verdeMin = 0; 
		verdeMax = 255;
		azulMin = 0;
		azulMax = 255;

		for (int f = 0; f < nrows; f++) {
			for (int g = 0; g < ncols; g++) {

				double[] funcionesAleatorias = generadorFuncionesAleatoriasDeGauss();
				Color colorActual = new Color(bufferedImage.getRGB(f, g));

				boolean elegirFormula1 = random.nextBoolean();
				rojo = (colorActual.getRed() + (int) (((elegirFormula1 ? funcionesAleatorias[0]: funcionesAleatorias[1]) * sigma) + mu));
				verde = (colorActual.getGreen() + (int) (((elegirFormula1 ? funcionesAleatorias[0]: funcionesAleatorias[1]) * sigma) + mu));
				azul = (colorActual.getBlue() + (int) (((elegirFormula1 ? funcionesAleatorias[0]: funcionesAleatorias[1]) * sigma) + mu));

				matrizRojos[f][g] = rojo;
				matrizVerdes[f][g] = verde;
				matrizAzules[f][g] = azul;

				if (rojoMin > rojo) {
					rojoMin = rojo;
				}

				if (rojoMax < rojo) {
					rojoMax = rojo;
				}

				if (verdeMin > verde) {
					verdeMin = verde;
				}

				if (verdeMax < verde) {
					verdeMax = verde;
				}

				if (azulMin > azul) {
					azulMin = azul;
				}

				if (azulMax < azul) {
					azulMax = azul;
				}

			}

		}

		float[] maximosYMinimos = new float[6];
		maximosYMinimos[0] = rojoMin;
		maximosYMinimos[1] = rojoMax;
		maximosYMinimos[2] = verdeMin;
		maximosYMinimos[3] = verdeMax;
		maximosYMinimos[4] = azulMin;
		maximosYMinimos[5] = azulMax;

		return maximosYMinimos;
	}

	// 10 - A - Generador de ruido de Gauss
	public static BufferedImage generarRuidoGauss(BufferedImage bufferedImage,
			int sigma, int mu) {

		int nrows, ncols;

		float rojoMin;
		float rojoMax;
		float verdeMin;
		float verdeMax;
		float azulMin;
		float azulMax;

		BufferedImage imagenConRuido;

		nrows = bufferedImage.getWidth();
		ncols = bufferedImage.getHeight();
		imagenConRuido = new BufferedImage(nrows, ncols, BufferedImage.TYPE_3BYTE_BGR);

		float[] maximosYMinimos = aplicarFuncionAleatoriaYObtenerMaximosYMinimos(bufferedImage, sigma, mu);
		rojoMin = maximosYMinimos[0];
		rojoMax = maximosYMinimos[1];
		verdeMin = maximosYMinimos[2];
		verdeMax = maximosYMinimos[3];
		azulMin = maximosYMinimos[4];
		azulMax = maximosYMinimos[5];

		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {


				int rojoTransformado = (int) ((((255f) / (rojoMax - rojoMin)) * matrizRojos[i][j]) - ((rojoMin * 255f) / (rojoMax - rojoMin)));
				int verdeTransformado = (int) (((255f / (verdeMax - verdeMin)) * matrizVerdes[i][j]) - ((verdeMin * 255f) / (verdeMax - verdeMin)));
				int azulTransformado = (int) (((255f / (azulMax - azulMin)) * matrizAzules[i][j]) - ((azulMin * 255f) / (azulMax - azulMin)));

				Color colorModificado = new Color(rojoTransformado, verdeTransformado, azulTransformado);
				imagenConRuido.setRGB(i, j, colorModificado.getRGB());
			}
		}
		
		return imagenConRuido;
	}
}