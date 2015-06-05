package ar.edu.untref.imagenes.tps.noise;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class FiltroPasaBajos {

	public static BufferedImage aplicarFiltroPasaBajos(BufferedImage imagenOriginal, int longitudMascara) {

		float[][] mascara = generarMascaraPasaBajos(longitudMascara);

		BufferedImage imagenFiltrada = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), imagenOriginal.getType());

		int width = mascara.length;
		int height = mascara[0].length;
		int tam = width * height;
		float filtroK[] = new float[tam];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				filtroK[i * width + j] = mascara[i][j];
			}
		}

		Kernel kernel = new Kernel(width, height, filtroK);
		Filtro filtro = new Filtro(kernel);

		// Aplicamos el filtro
		filtro.filter(imagenOriginal, imagenFiltrada);

		return imagenFiltrada;
	}

	private static float[][] generarMascaraPasaBajos(int longMascara) {

		float[][] mascara = new float[longMascara][longMascara];

		for (int j = 0; j < longMascara; ++j) {
			for (int i = 0; i < longMascara; ++i) {
				
				if (j == longMascara/2 && i == longMascara/2){
					
					mascara[i][j] = (longMascara * longMascara) - 1;
				} else {
					
					mascara[i][j] = -1;
				}
			}
		}

		return mascara;
	}
}