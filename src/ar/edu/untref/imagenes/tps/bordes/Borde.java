package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.edu.untref.imagenes.tps.utils.ImageOperations;
import ar.edu.untref.imagenes.utils.ColorProvider;

public class Borde {

	public static BufferedImage detectarBorde(BufferedImage image,
			int mascaraX[][], int mascaraY[][]) {
		BufferedImage imagenResultado = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);

		int[][] matrixConMagnitud = new int [image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				int sumatoriaX = 0;
				int sumatoriaY = 0;
				
				// Iterar la mascara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						
						sumatoriaX += nivelDeRojo * mascaraX[iAnchoMascara][iAltoMascara];
						sumatoriaY += nivelDeRojo * mascaraY[iAnchoMascara][iAltoMascara];
					}
				}

				int magnitud = (int) Math.hypot(sumatoriaX,sumatoriaY);
				matrixConMagnitud[i][j] = magnitud;
			}
		}
		
		int min = min(matrixConMagnitud);
		int max = max(matrixConMagnitud);
		
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {
				int nuevoPixel = getPixelTransformado(max, min, matrixConMagnitud[i][j]);
				Color color = new Color(nuevoPixel, nuevoPixel, nuevoPixel);
				imagenResultado.setRGB(i, j, color.getRGB());
			}
		}
		return imagenResultado;
	}

	public static BufferedImage aplicarMascara(BufferedImage image,
			int mascara[][]) {

		BufferedImage imagenResultado = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				int sumatoriaX = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoriaX += nivelDeRojo
								* mascara[iAnchoMascara][iAltoMascara];
						;
					}
				}

				matriz[i][j] = sumatoriaX;
			}
		}

		int rojoMax = max(matriz);
		int rojoMin = min(matriz);

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				int colorPixel = matriz[i][j];
				colorPixel = getPixelTransformado(rojoMax, rojoMin, colorPixel);
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha,
						colorPixel, colorPixel, colorPixel));
			}

		}
		return imagenResultado;
	}

	public static BufferedImage aplicarMascara(BufferedImage image,
			double mascara[][]) {

		BufferedImage imagenResultado = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int anchoMascara = 7;
		int altoMascara = 7;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		double[][] matriz = new double[image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				double sumatoriaX = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoriaX += nivelDeRojo
								* mascara[iAnchoMascara][iAltoMascara];
						;
					}
				}

				matriz[i][j] = sumatoriaX;
			}
		}

		double rojoMax = max(matriz);
		double rojoMin = min(matriz);

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				int colorPixel = (int) matriz[i][j];
				colorPixel = getPixelTransformado((int)rojoMax, (int)rojoMin, colorPixel);
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha,
						colorPixel, colorPixel, colorPixel));
			}

		}
		return imagenResultado;
	}

	
	public static BufferedImage detectarBordeColor(BufferedImage image,
			int mascaraX[][], int mascaraY[][]) {
		BufferedImage imagenResultado = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				int sumatoriaXRed = 0;
				int sumatoriaYRed = 0;

				int sumatoriaXGreen = 0;
				int sumatoriaYGreen = 0;

				int sumatoriaXBlue = 0;
				int sumatoriaYBlue = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();

						double nivelDeVerde = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen))
								.getGreen();

						double nivelDeAzul = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen))
								.getBlue();

						sumatoriaXRed += nivelDeRojo
								* mascaraX[iAnchoMascara][iAltoMascara];
						sumatoriaYRed += nivelDeRojo
								* mascaraY[iAnchoMascara][iAltoMascara];
						sumatoriaXGreen += nivelDeVerde
								* mascaraX[iAnchoMascara][iAltoMascara];
						sumatoriaYGreen += nivelDeVerde
								* mascaraY[iAnchoMascara][iAltoMascara];
						sumatoriaXBlue += nivelDeAzul
								* mascaraX[iAnchoMascara][iAltoMascara];
						sumatoriaYBlue += nivelDeAzul
								* mascaraY[iAnchoMascara][iAltoMascara];
					}
				}

				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				sumatoriaXRed = sumatoriaXRed / (anchoMascara * altoMascara);
				sumatoriaYRed = sumatoriaYRed / (anchoMascara * altoMascara);

				sumatoriaXGreen = sumatoriaXGreen
						/ (anchoMascara * altoMascara);
				sumatoriaYGreen = sumatoriaYGreen
						/ (anchoMascara * altoMascara);

				sumatoriaXBlue = sumatoriaXBlue / (anchoMascara * altoMascara);
				sumatoriaYBlue = sumatoriaYBlue / (anchoMascara * altoMascara);

				int magnitudRojo = (int) Math
						.sqrt(((sumatoriaXRed * sumatoriaXRed) + (sumatoriaYRed * sumatoriaYRed)));
				int magnitudVerde = (int) Math
						.sqrt(((sumatoriaXGreen * sumatoriaXGreen) + (sumatoriaYGreen * sumatoriaYGreen)));
				int magnitudAzul = (int) Math
						.sqrt(((sumatoriaXBlue * sumatoriaXBlue) + (sumatoriaYBlue * sumatoriaYBlue)));

				int nuevoPixel = ColorProvider.colorToRGB(alpha, magnitudRojo,
						magnitudVerde, magnitudAzul);

				imagenResultado.setRGB(i, j, nuevoPixel);
			}
		}
		return imagenResultado;
	}

	private static int min(int[][] matrix) {
		int min = matrix[0][0];
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix[col].length; row++) {
				if (min > matrix[col][row]) {
					min = matrix[col][row];
				}
			}
		}
		return min;
	}
	
	private static double min(double[][] matrix) {
		double min = matrix[0][0];
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix[col].length; row++) {
				if (min > matrix[col][row]) {
					min = matrix[col][row];
				}
			}
		}
		return min;
	}

	private static int max(int[][] matrix) {
		int max = matrix[0][0];
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix[col].length; row++) {
				if (max < matrix[col][row]) {
					max = matrix[col][row];
				}
			}
		}
		return max;
	}
	
	private static double max(double[][] matrix) {
		double max = matrix[0][0];
		for (int col = 0; col < matrix.length; col++) {
			for (int row = 0; row < matrix[col].length; row++) {
				if (max < matrix[col][row]) {
					max = matrix[col][row];
				}
			}
		}
		return max;
	}

	public static BufferedImage detectarBordeLaplaciano(BufferedImage image,
			int mascara[][]) {
		BufferedImage imagenResultado = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				int sumatoria = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoria += nivelDeRojo
								* mascara[iAnchoMascara][iAltoMascara];
						;
					}
				}

				matriz[i][j] = sumatoria;
			}
		}

		int[][] matrizCrucePorCero = new int[image.getWidth()][image
				.getHeight()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				if (hayCambioDeSignoPorFila(matriz, i, j)) {
					matrizCrucePorCero[i][j] = 255;
				} else {
					matrizCrucePorCero[i][j] = 0;
				}

				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				int colorPixel = matrizCrucePorCero[i][j];
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha,
						colorPixel, colorPixel, colorPixel));
			}

		}
		return imagenResultado;
	}

	public static BufferedImage detectarBordeLaplacianoConDerivadaDireccional(
			BufferedImage image, int mascara[][], int umbral) {
		BufferedImage imagenResultado = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				int sumatoria = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoria += nivelDeRojo
								* mascara[iAnchoMascara][iAltoMascara];
						;
					}
				}

				matriz[i][j] = sumatoria;
			}
		}

		int[][] matrizCrucePorCero = new int[image.getWidth()][image
				.getHeight()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				if (hayCambioDeSignoPorFilaYUmbral(matriz, i, j, umbral)) {
					matrizCrucePorCero[i][j] = 255;
				} else {
					matrizCrucePorCero[i][j] = 0;
				}

				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				int colorPixel = matrizCrucePorCero[i][j];
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha,
						colorPixel, colorPixel, colorPixel));
			}

		}
		return imagenResultado;
	}

	public static boolean hayCambioDeSignoPorFila(int[][] matriz, int i, int j) {

		if (j - 1 >= 0) {

			int valorActual = matriz[i][j];

			int valorAnterior = matriz[i][j - 1];
			if (valorAnterior == 0 && j - 2 >= 0) {
				valorAnterior = matriz[i][j - 2];
			}

			if ((valorAnterior < 0 && valorActual > 0)
					|| (valorAnterior > 0 && valorActual < 0)) {
				return true;
			}

		}

		return false;

	}

	public static boolean hayCambioDeSignoPorFila(double[][] matriz, int i, int j) {

		if (j - 1 >= 0) {

			double valorActual = matriz[i][j];

			double valorAnterior = matriz[i][j - 1];
			if (valorAnterior == 0 && j - 2 >= 0) {
				valorAnterior = matriz[i][j - 2];
			}

			if ((valorAnterior < 0 && valorActual > 0)
					|| (valorAnterior > 0 && valorActual < 0)) {
				return true;
			}

		}

		return false;

	}
	
	public static boolean hayCambioDeSignoPorFilaYUmbral(int[][] matriz,
			int i, int j, int umbral) {

		if (j - 1 >= 0) {

			int valorActual = matriz[i][j];

			int valorAnterior = matriz[i][j - 1];
			if (valorAnterior == 0 && j - 2 >= 0) {
				valorAnterior = matriz[i][j - 2];
			}

			if ((valorAnterior < 0 && valorActual > 0)
					|| (valorAnterior > 0 && valorActual < 0)) {
				if ((valorActual + valorAnterior) > umbral) {
					return true;
				}
			}

		}

		return false;

	}

	public static int getPixelTransformado(int rojoMax, int rojoMin,
			int rojoActual) {

		int rojoTransformado = (int) ((((255f) / (rojoMax - rojoMin)) * rojoActual) - ((rojoMin * 255f) / (rojoMax - rojoMin)));

		return rojoTransformado;

	}

	public static BufferedImage laplacianoDelGaussiano(BufferedImage imageInLabel, int sigma, int umbral) {

		double[][] mascara = generarMascaraLaplacianoDelGaussiano(sigma);

		int[][] log = aplicarMascaraLog(imageInLabel, mascara);
		
		BufferedImage imagenResultado = new BufferedImage(imageInLabel.getWidth(),
				imageInLabel.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		int[][] matrizCrucePorCero = new int[imageInLabel.getWidth()][imageInLabel.getHeight()];
		for (int i = 0; i < imageInLabel.getWidth(); i++) {
			for (int j = 0; j < imageInLabel.getHeight(); j++) {

				if (hayCambioDeSignoPorFila(log, i, j)) {
					matrizCrucePorCero[i][j] = 255;
				} else {
					matrizCrucePorCero[i][j] = 0;
				}

				int alpha = new Color(imageInLabel.getRGB(i, j)).getAlpha();
				int colorPixel = matrizCrucePorCero[i][j];
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha,
						colorPixel, colorPixel, colorPixel));
			}
		}

		return imagenResultado;
	}

	private static double[][] generarMascaraLaplacianoDelGaussiano(int sigma) {
		
		double[][] matrizDeLaplacianoDelGaussiano = new double[7][7];
		
		double primerTermino = -1.0 * (Math.sqrt(2 * Math.PI) * Math.pow(sigma, 3.0));
		
		double segundoTermino = 0;
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {

				segundoTermino = (Math.pow(i, 2.0) + Math.pow(j, 2.0)) / Math.pow(sigma, 2.0);
				
				matrizDeLaplacianoDelGaussiano[i][j] = primerTermino * (2 - segundoTermino) * Math.exp((-0.5) * segundoTermino);
			}

		}
		
		return matrizDeLaplacianoDelGaussiano;
	}
	
	public static int [][] aplicarMascaraLog(BufferedImage image,
			double mascara[][]) {

		int anchoMascara = 7;
		int altoMascara = 7;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				double sumatoriaX = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoriaX += nivelDeRojo
								* mascara[iAnchoMascara][iAltoMascara];
					}
				}

				matriz[i][j] = (int) sumatoriaX;
			}
		}

		return matriz;
	}

	public static BufferedImage generarDifusionIsotropica(BufferedImage image, int sigma, int cantidadRepeticiones) {
		
		BufferedImage imagenResultado = ImageOperations.clonarImagen(image);
		
		for(int i = 0; i < cantidadRepeticiones; i ++){
			int [][] matriz = new int [image.getWidth()][image.getHeight()];
			
			for(int j = 1; j < image.getWidth() - 1 ; j++){
				for (int k = 1 ; k < image.getHeight() - 1 ; k++){
					int derivadaNorte = calcularDerivadaNorte(imagenResultado, j, k);
					int derivadaSur = calcularDerivadaSur(imagenResultado, j, k);
					int derivadaOeste = calcularDerivadaOeste(imagenResultado, j, k);
					int derivadaEste = calcularDerivadaEste(imagenResultado, j, k);
					
					int pixel = (int) (new Color(imagenResultado.getRGB(j, k)).getRed() + (((float) (0.25f * ((derivadaNorte) + (derivadaSur) 
							+ (derivadaOeste) + (derivadaEste))))));
					
					matriz[j][k] = pixel;
				}
			}

			int max = max(matriz);
			int min = min(matriz);
			for(int j = 1; j < image.getWidth() - 1 ; j++){
				for (int k = 1 ; k < image.getHeight() - 1 ; k++){
					int pixel = getPixelTransformado(max, min, matriz[j][k]);
					
					Color color = new Color(pixel, pixel, pixel);
					imagenResultado.setRGB(j, k, color.getRGB());
				}
			}
		}
		
		return imagenResultado;
	}
	
	public static int[][] calcularMatrizDeUnaImagenGris(BufferedImage image) {

		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				int red = new Color(image.getRGB(i, j)).getRed();

				matriz[i][j] = red;
			}
		}
		return matriz;
	}
	
	public static BufferedImage generarDifusionAnisotropica(
			BufferedImage image, int sigma, int cantidadRepeticiones) {
		
		BufferedImage imagenResultado = ImageOperations.clonarImagen(image);
		
		for(int i = 0; i < cantidadRepeticiones; i ++){
			
			int[][] matriz = new int[image.getWidth()][image.getHeight()];
			
			for(int j = 1; j < image.getWidth() - 1 ; j++){
				for (int k = 1 ; k < image.getHeight() - 1 ; k++){
					int derivadaNorte = calcularDerivadaNorte(imagenResultado, j, k);
					float cnNorte = calcularCn(derivadaNorte, sigma);
					int derivadaSur = calcularDerivadaSur(imagenResultado, j, k);
					float cnSur = calcularCn(derivadaSur, sigma);
					int derivadaOeste = calcularDerivadaOeste(imagenResultado, j, k);
					float cnOeste = calcularCn(derivadaOeste, sigma);
					int derivadaEste = calcularDerivadaEste(imagenResultado, j, k);
					float cnEste = calcularCn(derivadaEste, sigma);
					
					float pixel = new Color(imagenResultado.getRGB(j, k)).getRed() + (((float) (0.25f * ((cnNorte*derivadaNorte) + (cnSur * derivadaSur) 
							+ (cnOeste * derivadaOeste) + (cnEste * derivadaEste)))));
					
					matriz[j][k] = (int)pixel;
				}
			}

			int max = max(matriz);
			int min = min(matriz);
			for(int j = 1; j < image.getWidth() - 1 ; j++){
				for (int k = 1 ; k < image.getHeight() - 1 ; k++){
					int pixel = getPixelTransformado(max, min, matriz[j][k]);
					
					Color color = new Color(pixel, pixel, pixel);
					imagenResultado.setRGB(j, k, color.getRGB());
				}
			}
		}

		return imagenResultado;
	}

	private static float calcularCn(int derivada, int sigma) {
		return ((float) 1 / (1 + (derivada * derivada / sigma)));
	}

	private static int calcularDerivadaEste(BufferedImage image, int j, int k) {
		
		int nivelDeRojo = new Color(image.getRGB(
				j - 1, k)).getRed();
		
		return nivelDeRojo;
	}

	private static int calcularDerivadaOeste(BufferedImage image, int j, int k) {
		
		int nivelDeRojo = new Color(image.getRGB(
				j + 1, k)).getRed();
		
		return nivelDeRojo;
	}

	private static int calcularDerivadaSur(BufferedImage image, int j, int k) {
	
		int nivelDeRojo = new Color(image.getRGB(
				j, k + 1)).getRed();
		
		return nivelDeRojo;
	}

	private static int calcularDerivadaNorte(BufferedImage image, int j, int k) {
		int nivelDeRojo = new Color(image.getRGB(
				j, k - 1)).getRed();
		
		return nivelDeRojo;
	}
}
