package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.edu.untref.imagenes.utils.ColorProvider;

public class Borde {


	public static BufferedImage detectarBorde(BufferedImage image, int mascaraX[][], int mascaraY[][]) {
		BufferedImage imagenResultado = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
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
//						if (!(iAnchoMascara == (anchoMascara / 2) && iAltoMascara == (altoMascara / 2))) {

							int indiceIDeLaImagen = i + sumarEnAncho
									+ iAnchoMascara;
							int indiceJDeLaImagen = j + sumarEnAlto
									+ iAltoMascara;

							double nivelDeRojo = new Color(image.getRGB(
									indiceIDeLaImagen, indiceJDeLaImagen))
									.getRed();
							sumatoriaX += nivelDeRojo * mascaraX[iAnchoMascara][iAltoMascara];
							sumatoriaY += nivelDeRojo * mascaraY[iAnchoMascara][iAltoMascara];
//						}

					}
				}

				int alpha = new Color(image.getRGB(i, j)).getAlpha();
				sumatoriaX = sumatoriaX / (anchoMascara * altoMascara);
				sumatoriaY = sumatoriaY / (anchoMascara * altoMascara);
				
				int magnitud = (int) Math.sqrt(((sumatoriaX*sumatoriaX) + (sumatoriaY*sumatoriaY)));

				int nuevoPixel = ColorProvider.colorToRGB(alpha, magnitud,
						magnitud, magnitud);
				
				imagenResultado.setRGB(i, j, nuevoPixel);
			}
		}
		return imagenResultado;
	}

}
