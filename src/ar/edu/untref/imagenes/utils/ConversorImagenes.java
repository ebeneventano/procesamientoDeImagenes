package ar.edu.untref.imagenes.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ConversorImagenes {

//	public static int[][] convertirImagenEnMatriz(BufferedImage image) {
//
//		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//		final int width = image.getWidth();
//		final int height = image.getHeight();
//		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
//
//		int[][] matriz = new int[height][width];
//		if (hasAlphaChannel) {
//			final int pixelLength = 4;
//			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
//				int argb = 0;
//				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
//				argb += ((int) pixels[pixel + 1] & 0xff); // blue
//				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
//				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
//				matriz[row][col] = argb;
//				col++;
//				if (col == width) {
//					col = 0;
//					row++;
//				}
//			}
//		} else {
//			final int pixelLength = 3;
//			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
//				int argb = 0;
//				argb += -16777216; // 255 alpha
//				argb += ((int) pixels[pixel] & 0xff); // blue
//				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
//				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
//				matriz[row][col] = argb;
//				col++;
//				if (col == width) {
//					col = 0;
//					row++;
//				}
//			}
//		}
//
//		return matriz;
//	}
	
	public static BufferedImage convertirMatrizEnImagen(int[][] matriz, int ancho, int alto){
		
		BufferedImage bufferedImage = new BufferedImage(ancho, alto, BufferedImage.TYPE_3BYTE_BGR);
	    for (int i = 0; i < ancho; i++) {
	        for (int j = 0; j < alto; j++) {
	            int pixel=matriz[i][j];
	            ColorProvider.colorToRGB(0, pixel, pixel, pixel);
	            bufferedImage.setRGB(i, j, ColorProvider.colorToRGB(0, pixel, pixel, pixel));
	        }
	    }
	    
	    return bufferedImage;
	}
	
	public static int[][] convertirImagenEnMatriz(BufferedImage image) {

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
	
}