package ar.edu.untref.imagenes.tps.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.untref.imagenes.tps.domain.Histograma;
import ar.edu.untref.imagenes.tps.domain.Operations;
import ar.edu.untref.imagenes.tps.transformations.LinealTransformation;
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
		return operateWithImage(Operations.ADD);
	}

	public BufferedImage restImages() {
		return operateWithImage(Operations.SUBTRACT);
	}

	public BufferedImage multiplicateImages() {
		return operateWithImage(Operations.MULTIPLY);
	}

	public BufferedImage compresionRangoDinamico(
			BufferedImage imageToMultiplicate) {

		return aplicarTransformacionLog(imageToMultiplicate);
	}

	private long aplicarTransformacionLog(int scalar, int color) {
		return Math.round(scalar * Math.log10((double) 1 + color));
	}

	public BufferedImage aplicarTransformacionLog(BufferedImage bufferedImage) {

		BufferedImage imgFinal = new BufferedImage(bufferedImage.getWidth(),
				bufferedImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

		Color color = new Color(bufferedImage.getRGB(0, 0));
		float rojoMax = color.getRed();
		float verdeMax = color.getGreen();
		float azulMax = color.getBlue();

		for (int f = 0; f < bufferedImage.getWidth(); f++) {
			for (int g = 0; g < bufferedImage.getHeight(); g++) {

				Color colorActual = new Color(bufferedImage.getRGB(f, g));
				int rojoActual = colorActual.getRed();
				int verdeActual = colorActual.getGreen();
				int azulActual = colorActual.getBlue();

				if (rojoMax < rojoActual) {
					rojoMax = rojoActual;
				}

				if (verdeMax < verdeActual) {
					verdeMax = verdeActual;
				}

				if (azulMax < azulActual) {
					azulMax = azulActual;
				}

			}

		}

		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {

				Color colorActual = new Color(bufferedImage.getRGB(i, j));
				int rojoActual = colorActual.getRed();
				int verdeActual = colorActual.getGreen();
				int azulActual = colorActual.getBlue();

				int rojoTransformado = (int) ((255f / (Math.log(rojoMax))) * Math
						.log(1 + rojoActual));
				int verdeTransformado = (int) ((255f / (Math.log(verdeMax))) * Math
						.log(1 + verdeActual));
				int azulTransformado = (int) ((255f / (Math.log(azulMax))) * Math
						.log(1 + azulActual));

				Color colorModificado = new Color(rojoTransformado,
						verdeTransformado, azulTransformado);
				imgFinal.setRGB(i, j, colorModificado.getRGB());
			}
		}

		return imgFinal;
	}

	public BufferedImage operateWithImage(Operations operator) {
		if (image1.getWidth() == image2.getWidth()
				&& image1.getHeight() == image2.getHeight()) {

			int[][] matrizResultado = new int[image1.getWidth()][image1
					.getHeight()];

			int[][] matrizDeImagen1 = calcularMatrizDeUnaImagenGris(image1);
			int[][] matrizDeImagen2 = calcularMatrizDeUnaImagenGris(image2);

			for (int i = 0; i < image1.getWidth(); i++) {
				for (int j = 0; j < image1.getHeight(); j++) {

					if (Operations.ADD.equals(operator)) {
						matrizResultado[i][j] = Operations.ADD.apply(
								matrizDeImagen1[i][j], matrizDeImagen2[i][j]);
					} else if (Operations.SUBTRACT.equals(operator)) {
						matrizResultado[i][j] = Operations.SUBTRACT.apply(
								matrizDeImagen1[i][j], matrizDeImagen2[i][j]);
					} else if (Operations.MULTIPLY.equals(operator)) {
						matrizResultado[i][j] = Operations.MULTIPLY.apply(
								matrizDeImagen1[i][j], matrizDeImagen2[i][j]);
					}
				}
			}

			BufferedImage transformada = LinealTransformation
					.aplicarTransformacionLineal(getBufferedImageDeMatriz(
							matrizResultado, image1.getWidth(),
							image1.getHeight()));

			return transformada;

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

				red = (int) aplicarTransformacionLog(1, red);
				green = (int) aplicarTransformacionLog(1, green);
				blue = (int) aplicarTransformacionLog(1, blue);

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

	public BufferedImage changeBrightness(BufferedImage inImage,
			int increasingFactor) {

		// size of input image
		int w = inImage.getWidth();
		int h = inImage.getHeight();

		BufferedImage outImage = new BufferedImage(w, h,
				BufferedImage.TYPE_3BYTE_BGR);

		// Pixel by pixel navigation loop
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {

				// get the RGB component of input imge pixel
				Color color = new Color(inImage.getRGB(i, j));

				int r, g, b;

				// change the value of each component
				r = color.getRed() * increasingFactor;
				g = color.getGreen() * increasingFactor;
				b = color.getBlue() * increasingFactor;

				// r,g,b values which are out of the range 0 to 255 should set
				// to 0 or 255
				if (r >= 256) {
					r = 255;
				} else if (r < 0) {
					r = 0;
				}

				if (g >= 256) {
					g = 255;
				} else if (g < 0) {
					g = 0;
				}

				if (b >= 256) {
					b = 255;
				} else if (b < 0) {
					b = 0;
				}

				// set output image pixel component
				outImage.setRGB(i, j, new Color(r, g, b).getRGB());

			}
		}

		return LinealTransformation.aplicarTransformacionLineal(outImage);
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

		ImageComparator.holdImages(histogramEQ);
		ImageComparator.equals();

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
	public static BufferedImage umbralization(BufferedImage originalImage, int umbral) {

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

				int newValue = (greenValue > umbral) ? white.getRGB() : black.getRGB();

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

	public static BufferedImage clonarImagen(BufferedImage image) {
		
		BufferedImage imageResult = new BufferedImage(image.getWidth(),
				image.getHeight(), image.getType());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				imageResult.setRGB(i, j, image.getRGB(i, j));
			}
		}
		
		return imageResult;
	}

	public static BufferedImage generarUmbralizacionGlobal(BufferedImage image, int umbral) {
		
		int cantidadGrupoBlancos = 0;
		int cantidadGrupoNegro = 0;
		
		int sumatoriaGrupoBlancos = 0;
		int sumatoriaGrupoNegros = 0;
		
		int umbralAnterior = 0;
		int nuevoUmbral = umbral;
		
		int umbralUtilizado = umbral;
		int cantidadRepeticiones = 0;
		
		int diferencia = umbralAnterior > nuevoUmbral ? umbralAnterior - nuevoUmbral : nuevoUmbral - umbralAnterior;
		while (diferencia > 2){
			cantidadRepeticiones++;
			umbralAnterior = nuevoUmbral;
			
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					
					int greenValue = new Color(image.getRGB(i, j)).getGreen();
					
					if(greenValue > umbralUtilizado){
						cantidadGrupoBlancos++;
						sumatoriaGrupoBlancos += greenValue;
					} else {
						cantidadGrupoNegro++;
						sumatoriaGrupoNegros += greenValue;
					}
				}
			}
			nuevoUmbral = (int) ((0.5f) * (sumatoriaGrupoBlancos/cantidadGrupoBlancos + sumatoriaGrupoNegros/cantidadGrupoNegro));
			umbralUtilizado = nuevoUmbral;
			diferencia = umbralAnterior > nuevoUmbral ? umbralAnterior - nuevoUmbral : nuevoUmbral - umbralAnterior;
		}
		
		JOptionPane.showMessageDialog(null, "El umbral calculado es: " + String.valueOf(nuevoUmbral) 
				+ " y la cantidad de repeticiones realizadas fueron: " + String.valueOf(cantidadRepeticiones));				

		return umbralization(image, nuevoUmbral);
	}

	public static BufferedImage generarUmbralizacionOtsu(BufferedImage imageInLabel, int umbral) {
		
		int cantidadDePixelesDeLaImagen = imageInLabel.getWidth() * imageInLabel.getHeight();
		
		Histograma histograma = new Histograma();
		int[] histogramaDeGrises = histograma.completarHistograma(imageInLabel)[0];
		
		float[] probabilidadesDeOcurrencia = new float[256];
		
		for(int i = 0 ; i < histogramaDeGrises.length ; i++) {
			probabilidadesDeOcurrencia[i] = (float) histogramaDeGrises[i] / (float) cantidadDePixelesDeLaImagen;
		}
		
		float w1 = 0;
		float w2 = 0;
		
		for (int i = 0 ; i < probabilidadesDeOcurrencia.length ; i++) {
			
			if (i < umbral) {
				w1 += probabilidadesDeOcurrencia[i];
			} else {
				w2 += probabilidadesDeOcurrencia[i];
			}
		}
		
		float u1 = 0;
		float u2 = 0;
		for (int i = 0 ; i < probabilidadesDeOcurrencia.length ; i++) {
			
			if (i < umbral) {
				
				u1 += i * probabilidadesDeOcurrencia[i] / w1;
				
			} else {
				
				u2 += i * probabilidadesDeOcurrencia[i] / w2;
			}
			
		}
		
		int umbralResultante = (int) (w1*u1 + w2*u2); 
		
		JOptionPane.showMessageDialog(null, "El umbral calculado es: " + String.valueOf(umbralResultante));
		
		return umbralization(imageInLabel, umbralResultante);
	}
	
}
