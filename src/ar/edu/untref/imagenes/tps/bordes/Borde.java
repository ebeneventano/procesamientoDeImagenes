package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.image.BufferedImage;

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

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth()
				- (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight()
					- (altoMascara / 2); j++) {

				int sumatoriaX = 0;
				int sumatoriaY = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						// Opero si no es el punto central de la máscara
						// if (!(iAnchoMascara == (anchoMascara / 2) &&
						// iAltoMascara == (altoMascara / 2))) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoriaX += nivelDeRojo
								* mascaraX[iAnchoMascara][iAltoMascara];
						sumatoriaY += nivelDeRojo
								* mascaraY[iAnchoMascara][iAltoMascara];
						// }

					}
				}

				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				sumatoriaX = sumatoriaX / (anchoMascara * altoMascara);
				sumatoriaY = sumatoriaY / (anchoMascara * altoMascara);

				int magnitud = (int) Math
						.sqrt(((sumatoriaX * sumatoriaX) + (sumatoriaY * sumatoriaY)));

				int nuevoPixel = ColorProvider.colorToRGB(alpha, magnitud,
						magnitud, magnitud);

				imagenResultado.setRGB(i, j, nuevoPixel);
			}
		}
		return imagenResultado;
	}

	public static BufferedImage detectarBordeX(BufferedImage image,
			int mascaraX[][]) {
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
						sumatoriaX += nivelDeRojo * mascaraX[iAnchoMascara][iAltoMascara];;
					}
				}
				
				matriz[i][j] = sumatoriaX / 9;
			}
		}

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				int colorPixel = matriz[i][j];
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha,  colorPixel,  colorPixel,  colorPixel));
			}

		}
		return imagenResultado;
	}
	

	public static BufferedImage detectarBordeY(BufferedImage image,
			int mascaraY[][]) {
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

				int sumatoriaY = 0;
				// Iterar la máscara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho
								+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(
								indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoriaY += nivelDeRojo * mascaraY[iAnchoMascara][iAltoMascara];;
					}
				}
				
				matriz[i][j] = sumatoriaY / 9;
			}
		}

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				int colorPixel = matriz[i][j];
				imagenResultado.setRGB(i, j, ColorProvider.colorToRGB(alpha, colorPixel, colorPixel, colorPixel));
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

						// Opero si no es el punto central de la máscara
						// if (!(iAnchoMascara == (anchoMascara / 2) &&
						// iAltoMascara == (altoMascara / 2))) {

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
						// }

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
}
