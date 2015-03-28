package ar.edu.untref.imagenes.tps.utils;

import java.awt.image.BufferedImage;

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

				int pix = 0;
				int rgb = imageToMultiplicate.getRGB(i, j);
				int alpha = 0xff & (rgb >> 24);
				int red = 0xff & (rgb >> 16);
				int green = 0xff & (rgb >> 8);
				int blue = 0xff & rgb;

				red = (int) Math.round(scalar * Math.log10((double) 1 + red));
				green = (int) Math.round(scalar * Math.log10((double) 1 + green));
				blue = (int) Math.round(scalar * Math.log10((double) 1 + blue));

				pix = pix | blue;
				pix = pix | (green << 8);
				pix = pix | (red << 16);
				pix = pix | (alpha << 24);

				imageResult.setRGB(i, j, pix);
				pix = 0;
			}
		}

		return imageResult;
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
}
