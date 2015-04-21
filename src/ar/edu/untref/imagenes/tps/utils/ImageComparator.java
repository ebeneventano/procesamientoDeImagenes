package ar.edu.untref.imagenes.tps.utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import ar.edu.untref.imagenes.tps.domain.Operations;

public class ImageComparator {

	private static List<BufferedImage> images = new ArrayList<>();

	public static void holdImages(BufferedImage image) {
		images.add(image);
	}

	public static Boolean equals() {
		if (images.size() >= 2) {
			
			BufferedImage last = images.get(images.size() - 1);
			BufferedImage atLast = images.get(images.size() - 2);
			
			ImageOperations imageOperations = new ImageOperations(last, atLast);

			BufferedImage result = imageOperations.operateWithImage(Operations.SUBTRACT);

			int[][] matrizDeUnaImagenGris = imageOperations
					.calcularMatrizDeUnaImagenGris(result);

			return checkEqual(result, matrizDeUnaImagenGris);
		
		} else {
			return false;
		}
	}

	private static Boolean checkEqual(BufferedImage result,
			int[][] matrizDeUnaImagenGris) {
		
		for (int i = 0; i < result.getWidth(); i++) {
			for (int j = 0; j < result.getHeight(); j++) {
				if (matrizDeUnaImagenGris[i][j] != 0) {
					return false;
				}
			}
		}

		return true;
	}

}
