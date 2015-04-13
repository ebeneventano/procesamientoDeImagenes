package ar.edu.untref.imagenes.tps.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ar.edu.untref.imagenes.tps.domain.Histograma;
import ar.edu.untref.imagenes.utils.ColorProvider;

public class ImageOperations {

	private BufferedImage image1;
	private BufferedImage image2;

	public ImageOperations(BufferedImage image1, BufferedImage image2) {
		this.image1 = image1;
		this.image2 = image2;
	}

	public ImageOperations() {
	}

	public BufferedImage sumImages() {
		return operateWithImage("+");
	}

	public BufferedImage restImages() {
		return operateWithImage("-");
	}

	public BufferedImage multiplicateImages() {
		return operateWithImage("*");
	}

	public BufferedImage compresionRangoDinamico(
			BufferedImage imageToMultiplicate) {

		int width = imageToMultiplicate.getWidth();
		int height = imageToMultiplicate.getHeight();

		BufferedImage imageResult = new BufferedImage(width, height,
				imageToMultiplicate.getType());

		int scalar = (int) (255 / (Math
				.log10(1 + calculateScalar(imageToMultiplicate))));

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				int rgb = imageToMultiplicate.getRGB(i, j);
				int alpha = 0xff & (rgb >> 24);
				int red = 0xff & (rgb >> 16);
				int green = 0xff & (rgb >> 8);
				int blue = 0xff & rgb;

				red = (int) aplicarTransformacionLog(scalar, red);
				green = (int) aplicarTransformacionLog(scalar, green);
				blue = (int) aplicarTransformacionLog(scalar, blue);
				alpha = (int) aplicarTransformacionLog(scalar, alpha);

				int newRgb = ColorProvider.getRGB(blue, green, red, alpha);

				imageResult.setRGB(i, j, newRgb);
				newRgb = 0;
			}
		}

		return imageResult;
	}

	private int calculateScalar(BufferedImage imageResult) {

		int rgb = imageResult.getRGB(0, 0);
		int red = 0xff & (rgb >> 16);

		int max = red;
		for (int i = 0; i < imageResult.getWidth(); i++) {
			for (int j = 0; j < imageResult.getHeight(); j++) {

				int rgb2 = imageResult.getRGB(i, j);
				int red2 = 0xff & (rgb2 >> 16);

				int max2 = red2;

				if (max2 > max) {
					max = max2;
				}
			}
		}
		return max;
	}

	private long aplicarTransformacionLog(int scalar, int color) {
		return Math.round(scalar * Math.log10((double) 1 + color));
	}

	private BufferedImage operateWithImage(String operator) {
		BufferedImage imagenResultado = new BufferedImage(image1.getWidth(),
				image1.getHeight(), image1.getType());

		if (image1.getWidth() == image2.getWidth()
				&& image1.getHeight() == image2.getHeight()) {

			int[][] matrizResultado = new int[image1.getWidth()][image1
					.getHeight()];

			int[][] matrizDeImagen1 = calcularMatrizDeUnaImagenGris(image1);
			int[][] matrizDeImagen2 = calcularMatrizDeUnaImagenGris(image2);

			for (int i = 0; i < image1.getWidth(); i++) {
				for (int j = 0; j < image1.getHeight(); j++) {

					if ("+".equals(operator)) {
						matrizResultado[i][j] = matrizDeImagen1[i][j]
								+ matrizDeImagen2[i][j];
					} else if ("-".equals(operator)) {
						matrizResultado[i][j] = matrizDeImagen1[i][j]
								- matrizDeImagen2[i][j];
					} else if ("*".equals(operator)) {
						matrizResultado[i][j] = matrizDeImagen1[i][j]
								* matrizDeImagen2[i][j];
					}
				}
			}

			// TODO ACA QUEDA LA MATRIZ RESULTADO CON LOS VALORES DE LO PIXELES
			// SIN LA TRANSFORMACION, ES DECIR, CON VALORES QUE SE PASAN DE 255.
			// LUEGO DE APLICAR LA TRANSFORMACION Y OPERAR CON LAS MATRICES, HAY
			// QUE LLAMAR AL METODO QUE TE DEVUELVE UN BUFFERIMAGE A PARTIR DE
			// UNA MATRIZ Y DEVOLVER ESA IMAGEN.
			// FUNCIONA SOLO CON IMAGENES GRISES

			return imagenResultado;

		} else {

			return null;
		}
	}

	public BufferedImage getNegativeImage(BufferedImage image) {

		BufferedImage imageResult = image;

		for (int x = 0; x < imageResult.getWidth(); x++) {
			for (int y = 0; y < imageResult.getHeight(); y++) {
				int rgba = imageResult.getRGB(x, y);
				Color col = new Color(rgba, true);
				col = new Color(255 - col.getRed(), 255 - col.getGreen(),
						255 - col.getBlue());
				imageResult.setRGB(x, y, col.getRGB());
			}
		}

		return imageResult;
	}

	public BufferedImage increaseImageContrast(BufferedImage image,
			int increment) {

		BufferedImage imageResult = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				int rgb = image.getRGB(i, j);
				int alpha = 0xff & (rgb >> 24);
				int red = 0xff & (rgb >> 16);
				int green = 0xff & (rgb >> 8);
				int blue = 0xff & rgb;

				alpha = applyTransformationToIncreaseContrast(increment, alpha);
				red = applyTransformationToIncreaseContrast(increment, red);
				green = applyTransformationToIncreaseContrast(increment, green);
				blue = applyTransformationToIncreaseContrast(increment, blue);

				int newRgb = ColorProvider.getRGB(blue, green, red, alpha);

				imageResult.setRGB(i, j, newRgb);

				newRgb = 0;
			}
		}

		return imageResult;
	}

	private int applyTransformationToIncreaseContrast(int increment,
			int valueRGB) {
		return (increment * (valueRGB - 128)) + 128;
	}

	public BufferedImage histogramEqualization(BufferedImage original) {

		int red;
		int green;
		int blue;
		int alpha;
		int newPixel = 0;

		// Get the Lookup table for histogram equalization
		ArrayList<int[]> histLUT = histogramEqualizationLUT(original);
		BufferedImage histogramEQ = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());

		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {

				// Get pixels by R, G, B
				alpha = new Color(original.getRGB(i, j)).getAlpha();
				red = new Color(original.getRGB(i, j)).getRed();
				green = new Color(original.getRGB(i, j)).getGreen();
				blue = new Color(original.getRGB(i, j)).getBlue();

				// Set new pixel values using the histogram lookup table
				red = histLUT.get(0)[red];
				green = histLUT.get(1)[green];
				blue = histLUT.get(2)[blue];

				// Return back to original format
				newPixel = ColorProvider.colorToRGB(alpha, red, green, blue);

				// Write pixels into image
				histogramEQ.setRGB(i, j, newPixel);
			}
		}
		return histogramEQ;
	}

	// Get the histogram equalization lookup table for separate R, G, B channels
	private static ArrayList<int[]> histogramEqualizationLUT(BufferedImage image) {

		// Get an image histogram - calculated values by R, G, B channels
		ArrayList<int[]> imageHist = Histograma.getImageHistogram(image);

		// Create the lookup table
		ArrayList<int[]> imageLUT = new ArrayList<int[]>();

		// Fill the lookup table
		int[] rhistogram = new int[256];
		int[] ghistogram = new int[256];
		int[] bhistogram = new int[256];

		for (int i = 0; i < rhistogram.length; i++)
			rhistogram[i] = 0;

		for (int i = 0; i < ghistogram.length; i++)
			ghistogram[i] = 0;

		for (int i = 0; i < bhistogram.length; i++)
			bhistogram[i] = 0;

		long sumr = 0;
		long sumg = 0;
		long sumb = 0;

		// Calculate the scale factor
		float scale_factor = (float) (255.0 / (image.getWidth() * image
				.getHeight()));

		for (int i = 0; i < rhistogram.length; i++) {

			sumr += imageHist.get(0)[i];
			int valr = (int) (sumr * scale_factor);

			if (valr > 255) {
				rhistogram[i] = 255;
			} else
				rhistogram[i] = valr;

			sumg += imageHist.get(1)[i];
			int valg = (int) (sumg * scale_factor);

			if (valg > 255) {
				ghistogram[i] = 255;
			} else
				ghistogram[i] = valg;

			sumb += imageHist.get(2)[i];
			int valb = (int) (sumb * scale_factor);

			if (valb > 255) {
				bhistogram[i] = 255;
			} else
				bhistogram[i] = valb;
		}

		imageLUT.add(rhistogram);
		imageLUT.add(ghistogram);
		imageLUT.add(bhistogram);

		return imageLUT;
	}

	/**
	 * 
	 * This method apply the Umbralization operation to the selected image,
	 * comparing against the Green channel. The umbral must be a number between
	 * 0 and 255.
	 * 
	 * @param imageInLabel
	 *            , umbral
	 * @return the umbral aplicated to the image
	 */
	public BufferedImage umbralization(BufferedImage originalImage, int umbral) {

		if (umbral < 0 || umbral > 255) {
			throw new RuntimeException(
					"El umbral debe ser un valor entre 0 y 255");
		}

		BufferedImage newImage = new BufferedImage(originalImage.getWidth(),
				originalImage.getHeight(), originalImage.getType());

		Color black = new Color(0, 0, 0);
		Color white = new Color(255, 255, 255);

		for (int i = 0; i < originalImage.getWidth(); i++) {
			for (int j = 0; j < originalImage.getHeight(); j++) {

				int greenValue = new Color(originalImage.getRGB(i, j))
						.getGreen();

				int newValue = (greenValue > umbral) ? black.getRGB() : white
						.getRGB();

				newImage.setRGB(i, j, newValue);
			}
		}

		return newImage;
	}

	public BufferedImage multiplicateImagesByScalar(int scalar,
			int[][] matrizDeImagen) {

		int[][] matrizResult = new int[matrizDeImagen.length][matrizDeImagen[0].length];

		for (int i = 0; i < matrizDeImagen.length; i++) {
			for (int j = 0; j < matrizDeImagen[0].length; j++) {

				matrizResult[i][j] = matrizDeImagen[i][j] * scalar;

			}
		}

		return getBufferedImageDeMatriz(matrizResult, matrizDeImagen.length,
				matrizDeImagen[0].length);
	}

	public BufferedImage aplicarTransformacionLineal(BufferedImage bufferedImage) {

		float rojoMin;
		float rojoMax;
		float verdeMin;
		float verdeMax;
		float azulMin;
		float azulMax;

		BufferedImage imagenTransformada;
		int nrows = bufferedImage.getWidth();
		int ncols = bufferedImage.getHeight();
		imagenTransformada = new BufferedImage(nrows, ncols,
				BufferedImage.TYPE_3BYTE_BGR);

		Color color = new Color(bufferedImage.getRGB(0, 0));
		rojoMin = color.getRed();
		rojoMax = color.getRed();
		verdeMin = color.getRed();
		verdeMax = color.getGreen();
		azulMin = color.getRed();
		azulMax = color.getBlue();

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

		float[] maximosYMinimos = new float[6];
		maximosYMinimos[0] = rojoMin;
		maximosYMinimos[1] = rojoMax;
		maximosYMinimos[2] = verdeMin;
		maximosYMinimos[3] = verdeMax;
		maximosYMinimos[4] = azulMin;
		maximosYMinimos[5] = azulMax;

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

	public int[][] calcularMatrizDeLaImagen(BufferedImage image) {

		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				int rgb = image.getRGB(i, j);
				int alpha = 0xff & (rgb >> 24);
				int red = 0xff & (rgb >> 16);
				int green = 0xff & (rgb >> 8);
				int blue = 0xff & rgb;

				int newRgb = ColorProvider.getRGB(blue, green, red, alpha);

				matriz[i][j] = newRgb;
			}
		}
		return matriz;
	}

	public int[][] calcularMatrizDeUnaImagenGris(BufferedImage image) {

		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				int rgb = image.getRGB(i, j);
				int red = 0xff & (rgb >> 16);

				matriz[i][j] = red;
			}
		}
		return matriz;
	}

	public BufferedImage getBufferedImageDeMatriz(int[][] matriz, int ancho,
			int alto) {

		BufferedImage bufferedImage = new BufferedImage(ancho, alto,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				int pixel = matriz[i][j];
				bufferedImage.setRGB(i, j, pixel);
			}
		}

		return bufferedImage;
	}

}
