package ar.edu.untref.imagenes.tps.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import ar.edu.untref.imagenes.tps.bordes.Borde;

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

	public static BufferedImage aplicarFiltroLoG(BufferedImage imagenOriginal, int sigma, int umbral, int tamanioMascara) {
		if (tamanioMascara%2==0){
			
			tamanioMascara = tamanioMascara-1;
		}
		
		float[][] mascara = generarMascaraLoG(tamanioMascara, sigma);
		
		BufferedImage imagenFiltrada = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), imagenOriginal.getType());

		int[][] mascaraAplicada = new int[imagenOriginal.getWidth()][imagenOriginal.getHeight()];

		int sumarEnAncho = (-1) * (tamanioMascara / 2);
		int sumarEnAlto = (-1) * (tamanioMascara / 2);
		/* Aplico la máscara */
		// Itera la imagen, sacando los bordes.
		for (int i = tamanioMascara / 2; i < imagenOriginal.getWidth() - (tamanioMascara / 2); i++) {
			for (int j = tamanioMascara / 2; j < imagenOriginal.getHeight()	- (tamanioMascara / 2); j++) {

				float sumatoria = 0f;
				// Iterar la mascara
				for (int iAnchoMascara = 0; iAnchoMascara < tamanioMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < tamanioMascara; iAltoMascara++) {
						
						int indiceIDeLaImagen = i + sumarEnAncho+ iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						int nivelDeRojo = new Color(imagenOriginal.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoria += nivelDeRojo * mascara[iAnchoMascara][iAltoMascara];
					}
				}
				
				mascaraAplicada[i][j] = (int) sumatoria;
			}
		}

		/* Cruces por cero */
		int[][] matrizCrucePorCero = new int[imagenOriginal.getWidth()][imagenOriginal.getHeight()];
		for (int i = 0; i < imagenOriginal.getWidth(); i++) {
			for (int j = 0; j < imagenOriginal.getHeight(); j++) {

				if (Borde.hayCambioDeSignoPorFilaYUmbral(mascaraAplicada, i, j, umbral)) {
					matrizCrucePorCero[i][j] = 255;
				} else {
					matrizCrucePorCero[i][j] = 0;
				}

				int colorPixel = matrizCrucePorCero[i][j];
				Color color = new Color(colorPixel, colorPixel, colorPixel);
				
				imagenFiltrada.setRGB(i, j, color.getRGB());
			}
		}

		return imagenFiltrada;
	}
	
	private static float[][] generarMascaraLoG(int tamanioMascara, int sigma) {
		
		float[][] mascara = new float[tamanioMascara][tamanioMascara];

		for (int j = 0; j < tamanioMascara; j++) {
			for (int i = 0; i < tamanioMascara; i++) {
				mascara[i][j] = calcularValorLoG(sigma, i - (tamanioMascara/2), j - (tamanioMascara/2));
			}
		}

		return mascara;
	}

	private static float calcularValorLoG(int sigma, int x, int y) {
		
		
		float termino1 = (float) ( (float) ( -1 / ((Math.sqrt(2*Math.PI))*Math.pow(sigma,3)) ) );
		float termino2 = (float) ( 2 - ((float) ( ((float)(Math.pow(x, 2) + Math.pow(y, 2))) / Math.pow(sigma, 2))) );
		float termino3 = (float) Math.pow(Math.E, ( -1 * ( (float) (Math.pow(x, 2) + Math.pow(y, 2)) / (2*Math.pow(sigma, 2))) ) );
		float valor = termino1 * termino2 * termino3;
		
		return valor;
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