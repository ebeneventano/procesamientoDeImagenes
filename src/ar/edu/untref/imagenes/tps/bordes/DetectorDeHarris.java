package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.edu.untref.imagenes.tps.noise.FiltroGaussiano;
import ar.edu.untref.imagenes.tps.utils.ImageOperations;
import ar.edu.untref.imagenes.utils.ConversorImagenes;

public class DetectorDeHarris {

	public static BufferedImage detectarEsquinas(BufferedImage imagenOriginal){
		
		int mascaraX[][] = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };
		int mascaraY[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		int[][] matrizImagenSobelX = aplicarMascara(imagenOriginal, mascaraX);
		int[][] matrizImagenSobelY  = aplicarMascara(imagenOriginal, mascaraY);
		
        //Paso2: Calcular Ix2 elevando Ix^2 elemento a elemento y aplicar un filtro gaussiano 
        //para suavizar, por ejemplo, de 7x7 con sigma=2. Lo mismo Iy^2.
        int[][] matrizRojoEnXCuadrado = aplicarTransformacionLineal(elevarAlCuadrado(matrizImagenSobelX));
        int[][] matrizRojoEnYCuadrado = aplicarTransformacionLineal(elevarAlCuadrado(matrizImagenSobelY));
        
        BufferedImage imagenConFiltroGaussEnX = FiltroGaussiano.aplicarFiltroGaussiano(ConversorImagenes.convertirMatrizEnImagen(
        		matrizRojoEnXCuadrado,imagenOriginal.getWidth(), imagenOriginal.getHeight()), 2);
        BufferedImage imagenConFiltroGaussEnY = FiltroGaussiano.aplicarFiltroGaussiano(ConversorImagenes.convertirMatrizEnImagen(
        		matrizRojoEnYCuadrado,imagenOriginal.getWidth(), imagenOriginal.getHeight()), 2);
        
        //Paso3: Calcular Ixy multiplicando elemento a elemento también suavizar con el mismo filtro gaussiano.
        
        int[][] matrizRojoXY = aplicarTransformacionLineal(multiplicarValores(matrizImagenSobelX, matrizImagenSobelY));

        BufferedImage imagenXY = ConversorImagenes.convertirMatrizEnImagen(matrizRojoXY, imagenOriginal.getWidth(), imagenOriginal.getHeight());
        BufferedImage imagenXYConFiltroGauss = FiltroGaussiano.aplicarFiltroGaussiano(imagenXY, 2);
        
        //Paso 4: con k=0.04 Calcular: cim1 = (Ix2*Iy2 - Ixy^2) - k*(Ix2 + Iy2)^2  
        int[][] cimRojos = aplicarTransformacionLineal(calcularCim(ConversorImagenes.convertirImagenEnMatriz(
        		imagenConFiltroGaussEnX), ConversorImagenes.convertirImagenEnMatriz(
        		imagenConFiltroGaussEnY), ConversorImagenes.convertirImagenEnMatriz(imagenXYConFiltroGauss)));
        
        //Aplicamos filtros en X y en Y
        int[][] transpuestaRojo = new int[cimRojos[0].length][cimRojos.length];
    	
    	for(int j = 0; j < cimRojos.length; j++){
           for(int i = 0; i < cimRojos[0].length; i++){
        	   transpuestaRojo[i][j] = cimRojos[j][i];
           }
        }
    	
    	BufferedImage imagenUmbralizada = ImageOperations.generarUmbralizacionOtsu(ConversorImagenes.convertirMatrizEnImagen(
    			transpuestaRojo, imagenOriginal.getWidth(), imagenOriginal.getHeight()));
    	
        
		return superponerAImagenOriginal(imagenUmbralizada, imagenOriginal);
	}

	private static BufferedImage superponerAImagenOriginal(BufferedImage umbralizada, BufferedImage original) {

		BufferedImage imagenFinal = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());		
		for (int i=0; i< umbralizada.getWidth(); i++){
			for (int j=0; j< umbralizada.getHeight(); j++){
				
				Color colorEnUmbralizada = new Color(umbralizada.getRGB(i, j));
				if (colorEnUmbralizada.getRed()==255){
					
					imagenFinal.setRGB(i, j, Color.YELLOW.getRGB());
				} else {
					
					imagenFinal.setRGB(i, j, original.getRGB(i, j));
				}
			}
		}
		
		return imagenFinal;
	}

	/**
	 * cim1 = (Ix2*Iy2 - Ixy^2) - k*(Ix2 + Iy2)^2  con k=0.04
	 * @param imagenConFiltroGaussEnX
	 * @param imagenConFiltroGaussEnY
	 * @param imagenXYConFiltroGauss
	 * @return
	 */
	private static int[][] calcularCim(int[][] Ix2,
			int[][] Iy2, int[][] Ixy2) {
		
		int filas = Ix2.length;
		int columnas = Ix2[0].length;
		int[][] matrizCim = new int[filas][columnas];
		
		for (int f = 0; f < filas; f++) {
			for (int g = 0; g < columnas; g++) {

				matrizCim[f][g] = (int) (((Ix2[f][g]*Iy2[f][g]) - Ixy2[f][g]) - (0.04 * Math.pow((Ix2[f][g] + Iy2[f][g]),2)));
			}
		}
		
		return matrizCim;
	}
	
	public static int[][] aplicarMascara(BufferedImage image,
			int mascara[][]) {

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		int[][] matriz = new int[image.getWidth()][image.getHeight()];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < image.getWidth() - (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < image.getHeight() - (altoMascara / 2); j++) {

				int sumatoriaX = 0;
				// Iterar la mÃ¡scara
				for (int iAnchoMascara = 0; iAnchoMascara < anchoMascara; iAnchoMascara++) {
					for (int iAltoMascara = 0; iAltoMascara < altoMascara; iAltoMascara++) {

						int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double nivelDeRojo = new Color(image.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						sumatoriaX += nivelDeRojo * mascara[iAnchoMascara][iAltoMascara];
					}
				}

				matriz[i][j] = sumatoriaX;
			}
		}
		return matriz;
	}
	
	public static int[][] elevarAlCuadrado(int[][] matriz) {
		
		int filas = matriz.length;
		int columnas = matriz[0].length;

		for (int f = 0; f < filas; f++) {
			for (int g = 0; g < columnas; g++) {

				matriz[f][g] = (int) Math.pow(matriz[f][g],2);
			}
		}
		
		return matriz;
	}
	
	public static int[][] aplicarTransformacionLineal(int[][] matrizDesfasada) {

		float minimo;
		float maximo;

		int[][] matrizTransformada;
		
		int filas = matrizDesfasada.length;
		int columnas = matrizDesfasada[0].length;
		
		matrizTransformada = new int[filas][columnas];
		
		minimo = 0;
		maximo = 255;

		for (int f = 0; f < filas; f++) {
			for (int g = 0; g < columnas; g++) {
		
				int valorActual = matrizDesfasada[f][g];
				
				if (minimo > valorActual) {
					minimo = valorActual;
				}

				if (maximo < valorActual) {
					maximo = valorActual;
				}

			}

		}

		float[] maximosYMinimos = new float[2];
		maximosYMinimos[0] = minimo;
		maximosYMinimos[1] = maximo;
		
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {

				int valorActual = matrizDesfasada[i][j];
				int valorTransformado = (int) ((((255f) / (maximo - minimo)) * valorActual) - ((minimo * 255f) / (maximo - minimo)));

				matrizTransformada[i][j] = valorTransformado;
			}
		}
		
		return matrizTransformada;
	}
	
	public static int[][] multiplicarValores(int[][] matrizRojoEnX,
			int[][] matrizRojoEnY) {

		int filas = matrizRojoEnX.length;
		int columnas = matrizRojoEnX[0].length;
		int[][] matrizResultado = new int[filas][columnas];

		for (int f = 0; f < filas; f++) {
			for (int g = 0; g < columnas; g++) {

				matrizResultado[f][g] = matrizRojoEnX[f][g]*matrizRojoEnY[f][g];
			}
		}
		
		return matrizResultado;
	}
}