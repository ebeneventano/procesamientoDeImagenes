package ar.edu.untref.imagenes.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ConversorImagenes {

	public static int[][] convertirImagenEnMatriz(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] matriz = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				matriz[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				matriz[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return matriz;
	}
	
	public static BufferedImage convertirMatrizEnImagen(int[][] matriz, int ancho, int alto){
		
		BufferedImage bufferedImage = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
	    for (int i = 0; i < matriz.length; i++) {
	        for (int j = 0; j < matriz[0].length; j++) {
	            int pixel=matriz[i][j];
	            bufferedImage.setRGB(i, j, pixel);
	        }
	    }
	    
	    return bufferedImage;
	}
	
}