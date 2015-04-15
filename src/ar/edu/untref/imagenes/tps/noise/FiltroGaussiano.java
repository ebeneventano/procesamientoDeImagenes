package ar.edu.untref.imagenes.tps.noise;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class FiltroGaussiano {

	private static int[] dimensionesDeMatricesPosibles = { 3, 5, 9, 13, 15, 19 };

	public static BufferedImage aplicarFiltroGaussiano(BufferedImage imagenOriginal, int sigma) {

		float[][] mascara = generarMascaraGaussiana(sigma);
		
		BufferedImage imagenFiltrada = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), imagenOriginal.getType());
		
		
		int width = mascara.length;
        int height = mascara[0].length;
        int tam = width * height;
        float filtroK[] = new float[tam];

        //Creamos el filtro - Se pasa de una matriz cuadrada (vector de 2 dimensiones) a un vector lineal
        for(int i=0; i < width; i++){
            for(int j=0; j < height; j++){
                filtroK[i*width + j] = mascara[i][j];
            }
        }

        Kernel kernel = new Kernel(width, height, filtroK);
        Filtro filtro = new Filtro(kernel);

        //Aplicamos el filtro
        filtro.filter(imagenOriginal, imagenFiltrada);

		return imagenFiltrada;
	}

	private static float[][] generarMascaraGaussiana(int sigma) {

		// Siempre son matrices cuadradas
		int dimension = dimensionesDeMatricesPosibles[sigma-1];
		float[][] mascara = new float[dimension][dimension];

		for (int j = 0; j < dimension; ++j) {
			for (int i = 0; i < dimension; ++i) {
				mascara[i][j] = calcularValorGaussiano(sigma, i - (dimension/2), j - (dimension/2));
			}
		}

		return mascara;
	}

	private static float calcularValorGaussiano(int sigma, int x, int y) {
		float valor = (float) ((1 / (2 * Math.PI * sigma * sigma)) 
					* 
					Math.pow(Math.E,-(x * x + y * y) / (2 * sigma * sigma)));
		
		return valor;
	}

}