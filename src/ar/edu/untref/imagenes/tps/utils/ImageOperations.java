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

	public BufferedImage scalarMultiplication(int scalar,
			BufferedImage imageToMultiplicate) {

		int width = imageToMultiplicate.getWidth();
		int height = imageToMultiplicate.getHeight();

		BufferedImage imageResult = new BufferedImage(width, height,
				imageToMultiplicate.getType());

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

				int newRgb = ColorProvider.getRGB(blue, green, red, alpha);

				imageResult.setRGB(i, j, newRgb);
				newRgb = 0;
			}
		}

		return imageResult;
	}

	private long aplicarTransformacionLog(int scalar, int color) {
		return Math.round(scalar * Math.log10((double) 1 + color));
	}

	private BufferedImage operateWithImage(String operator) {

		if (image1.getWidth() == image2.getWidth()
				&& image1.getHeight() == image2.getHeight()) {

			BufferedImage imageSum = new BufferedImage(image1.getWidth(),
					image1.getHeight(), image1.getType());

			for (int i = 0; i < image1.getWidth(); i++) {
				for (int j = 0; j < image1.getHeight(); j++) {

					int rgbImage1 = image1.getRGB(i, j);
					int rgbImage2 = image2.getRGB(i, j);

					if ("+".equals(operator)) {
						imageSum.setRGB(i, j, rgbImage1 + rgbImage2);
					} else if ("-".equals(operator)) {
						imageSum.setRGB(i, j, rgbImage1 - rgbImage2);
					} else if ("*".equals(operator)) {
						imageSum.setRGB(i, j, rgbImage1 * rgbImage2);
					}
				}
			}

			return imageSum;

		} else {

			return null;
		}
	}
	
	public BufferedImage getNegativeImage (BufferedImage image) {
        
		BufferedImage imageResult = image;

        for (int x = 0; x < imageResult.getWidth(); x++) {
            for (int y = 0; y < imageResult.getHeight(); y++) {
                int rgba = imageResult.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                imageResult.setRGB(x, y, col.getRGB());
            }
        }
        
        return imageResult;
    }
	
	public BufferedImage increaseImageContrast(BufferedImage image, float increment){
		
		BufferedImage imageResult = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				
				int rgb = image.getRGB(i, j);
				float alpha = 0xff & (rgb >> 24);
				float red = 0xff & (rgb >> 16);
				float green = 0xff & (rgb >> 8);
				float blue = 0xff & rgb;
				
				red = (int) applyTransformationToIncreaseContrast(increment, red);
				green = (int) applyTransformationToIncreaseContrast(increment, green);
				blue = (int) applyTransformationToIncreaseContrast(increment, blue);

				int newRgb = ColorProvider.getRGB(blue, green, red, alpha);

				imageResult.setRGB(i, j, newRgb);
				
				newRgb = 0;
			}
		}
		
		return imageResult;
	}
	
	private float applyTransformationToIncreaseContrast(float increment, float valueRGB){
		return (increment*(valueRGB - 128)) + 128;
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
		float scale_factor = (float) (255.0 / (image.getWidth() * image.getHeight()));
		
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

}
