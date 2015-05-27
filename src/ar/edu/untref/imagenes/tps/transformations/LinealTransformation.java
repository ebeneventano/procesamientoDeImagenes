package ar.edu.untref.imagenes.tps.transformations;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LinealTransformation {

	public LinealTransformation() {

	}

	public static BufferedImage aplicarTransformacionLineal(BufferedImage bufferedImage) {

		BufferedImage imagenTransformada;
		int nrows = bufferedImage.getWidth();
		int ncols = bufferedImage.getHeight();
		imagenTransformada = new BufferedImage(nrows, ncols,
				BufferedImage.TYPE_3BYTE_BGR);

		Color color = new Color(bufferedImage.getRGB(0, 0));
		float rojoMin = color.getRed();
		float rojoMax = color.getRed();
		float verdeMin = color.getGreen();
		float verdeMax = color.getGreen();
		float azulMin = color.getBlue();
		float azulMax = color.getBlue();

		for (int f = 0; f < nrows; f++) {
			for (int g = 0; g < ncols; g++) {

				Color colorActual = new Color(bufferedImage.getRGB(f, g));
				int rojoActual = colorActual.getRed();
				int verdeActual = colorActual.getGreen();
				int azulActual = colorActual.getBlue();

				if (rojoMin > rojoActual) {
					rojoMin = rojoActual;
				}

				if (rojoMax < rojoActual) {
					rojoMax = rojoActual;
				}

				if (verdeMin > verdeActual) {
					verdeMin = verdeActual;
				}

				if (verdeMax < verdeActual) {
					verdeMax = verdeActual;
				}

				if (azulMin > azulActual) {
					azulMin = azulActual;
				}

				if (azulMax < azulActual) {
					azulMax = azulActual;
				}

			}

		}

		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {

				Color colorActual = new Color(bufferedImage.getRGB(i, j));
				int rojoActual = colorActual.getRed();
				int verdeActual = colorActual.getGreen();
				int azulActual = colorActual.getBlue();

				int rojoTransformado = (int) ((((255f) / (rojoMax - rojoMin)) * rojoActual) - ((rojoMin * 255f) / (rojoMax - rojoMin)));
				int verdeTransformado = (int) (((255f / (verdeMax - verdeMin)) * verdeActual) - ((verdeMin * 255f) / (verdeMax - verdeMin)));
				int azulTransformado = (int) (((255f / (azulMax - azulMin)) * azulActual) - ((azulMin * 255f) / (azulMax - azulMin)));

				Color colorModificado = new Color(rojoTransformado,
						verdeTransformado, azulTransformado);
				imagenTransformada.setRGB(i, j, colorModificado.getRGB());
			}
		}

		return imagenTransformada;
	}
	
	public static int[][] aplicarTransformacionLineal(int[][] matriz) {

		int nrows = matriz.length;
		int ncols = matriz[0].length;
		
		int[][] matrizTransformada = new int[nrows][ncols];

		float rojoMin = matrizTransformada[0][0];
		float rojoMax = matrizTransformada[0][0];

		for (int f = 0; f < nrows; f++) {
			for (int g = 0; g < ncols; g++) {

				int rojoActual = matriz[f][g];

				if (rojoMin > rojoActual) {
					rojoMin = rojoActual;
				}

				if (rojoMax < rojoActual) {
					rojoMax = rojoActual;
				}
			}

		}

		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {

				int rojoActual = matriz[i][j];
				int rojoTransformado = (int) ((((255f) / (rojoMax - rojoMin)) * rojoActual) - ((rojoMin * 255f) / (rojoMax - rojoMin)));

				matrizTransformada[i][j] = rojoTransformado;
			}
		}

		return matrizTransformada;
	}
}
