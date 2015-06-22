package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.edu.untref.imagenes.tps.noise.FiltroGaussiano;
import ar.edu.untref.imagenes.tps.utils.ImageOperations;
import ar.edu.untref.imagenes.utils.ConversorImagenes;

public class DetectorDeHarris {
	
	private BufferedImage imagenOriginal;
	
	private int ancho;
	private int alto;
	
	public DetectorDeHarris(BufferedImage imagen){
		this.imagenOriginal = imagen;
		this.ancho = imagenOriginal.getWidth();
		this.alto = imagenOriginal.getHeight();
	}

	public BufferedImage detectarEsquinas(){
		
		int mascaraX[][] = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };
		int mascaraY[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };

		int[][] matrizImagenSobelX = aplicarMascara(imagenOriginal, mascaraX);
		int[][] matrizImagenSobelY  = aplicarMascara(imagenOriginal, mascaraY);
		
        //Paso2: Calcular Ix2 elevando Ix^2 elemento a elemento y aplicar un filtro gaussiano 
        //para suavizar, por ejemplo, de 7x7 con sigma=2. Lo mismo Iy^2.
        int[][] matrizRojoEnXCuadrado = aplicarTransformacionLineal(elevarAlCuadrado(matrizImagenSobelX));
        int[][] matrizRojoEnYCuadrado = aplicarTransformacionLineal(elevarAlCuadrado(matrizImagenSobelY));
        
		
        BufferedImage imagenConFiltroGaussEnX = FiltroGaussiano.aplicarFiltroGaussiano(ConversorImagenes.convertirMatrizEnImagen(
        		matrizRojoEnXCuadrado,ancho, alto), 2);
        BufferedImage imagenConFiltroGaussEnY = FiltroGaussiano.aplicarFiltroGaussiano(ConversorImagenes.convertirMatrizEnImagen(
        		matrizRojoEnYCuadrado, ancho, alto), 2);
         
        int [][] matrizImagenFiltroGaussEnX = ConversorImagenes.convertirImagenEnMatriz(imagenConFiltroGaussEnX);
        int [][] matrizImagenFiltroGaussEnY = ConversorImagenes.convertirImagenEnMatriz(imagenConFiltroGaussEnY);

        //Paso3: Calcular Ixy multiplicando elemento a elemento también suavizar con el mismo filtro gaussiano.
        int[][] resultadoMultiplicacion = multiplicarValores(matrizImagenFiltroGaussEnX, matrizImagenFiltroGaussEnY);
        int[][] matrizRojoXY = aplicarTransformacionLineal(resultadoMultiplicacion);

        BufferedImage imagenXY = ConversorImagenes.convertirMatrizEnImagen(matrizRojoXY, ancho, alto);
        BufferedImage imagenXYConFiltroGauss = FiltroGaussiano.aplicarFiltroGaussiano(imagenXY, 2);
        
        //Paso 4: con k=0.04 Calcular: cim1 = (Ix2*Iy2 - Ixy^2) - k*(Ix2 + Iy2)^2  
        int[][] cimRojos = aplicarTransformacionLineal(calcularCim(matrizImagenFiltroGaussEnX, matrizImagenFiltroGaussEnY, ConversorImagenes.convertirImagenEnMatriz(imagenXYConFiltroGauss)));
    	
    	BufferedImage imagenUmbralizada = ImageOperations.generarUmbralizacionOtsu(ConversorImagenes.convertirMatrizEnImagen(cimRojos, ancho, alto));
        
		return superponerAImagenOriginal(imagenUmbralizada, imagenOriginal);
	}

	private BufferedImage superponerAImagenOriginal(BufferedImage umbralizada, BufferedImage original) {
		
		int greenRGB = Color.GREEN.getRGB();
		BufferedImage imagenFinal = new BufferedImage(ancho, alto, original.getType());		
		
		for (int i=0; i< ancho; i++){
			for (int j=0; j< alto; j++){
				
				Color colorEnUmbralizada = new Color(umbralizada.getRGB(i, j));
				if (colorEnUmbralizada.getRed()==255){
					
					imagenFinal.setRGB(i, j, greenRGB);
				} else {
					
					imagenFinal.setRGB(i, j, original.getRGB(i, j));
				}
			}
		}
		
		return imagenFinal;
	}

	/**
	 * cim1 = (Ix2*Iy2 - Ixy^2) - k*(Ix2 + Iy2)^2  con k=0.04
	 */
	private int[][] calcularCim(int[][] Ix2, int[][] Iy2, int[][] Ixy2) {
		
		int ancho = this.ancho;
		int alto = this.alto;
		int[][] matrizCim = new int[ancho][alto];
		
		for (int f = 0; f < ancho; f++) {
			for (int g = 0; g < alto; g++) {

				matrizCim[f][g] = (int) (((Ix2[f][g]*Iy2[f][g]) - Ixy2[f][g]) - (0.04 * Math.pow((Ix2[f][g] + Iy2[f][g]),2)));
			}
		}
		
		return matrizCim;
	}
	
	public int[][] aplicarMascara(BufferedImage image, int mascara[][]) {

		int anchoMascara = 3;
		int altoMascara = 3;
		int sumarEnAncho = (-1) * (anchoMascara / 2);
		int sumarEnAlto = (-1) * (altoMascara / 2);
		int[][] matriz = new int[ancho][alto];

		// Iterar la imagen, sacando los bordes.
		for (int i = anchoMascara / 2; i < ancho - (anchoMascara / 2); i++) {
			for (int j = altoMascara / 2; j < alto - (altoMascara / 2); j++) {

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
	
	public int[][] elevarAlCuadrado(int[][] matriz) {
		
		for (int f = 0; f < ancho; f++) {
			for (int g = 0; g < alto; g++) {

				matriz[f][g] = (int) Math.pow(matriz[f][g],2);
			}
		}
		
		return matriz;
	}
	
	public int[][] aplicarTransformacionLineal(int[][] matrizDesfasada) {

		float minimo;
		float maximo;

		int[][] matrizTransformada;
		
		matrizTransformada = new int[ancho][alto];
		
		minimo = 0;
		maximo = 255;

		for (int f = 0; f < ancho; f++) {
			for (int g = 0; g < alto; g++) {
		
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
		
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {

				int valorActual = matrizDesfasada[i][j];
				int valorTransformado = (int) ((((255f) / (maximo - minimo)) * valorActual) - ((minimo * 255f) / (maximo - minimo)));

				matrizTransformada[i][j] = valorTransformado;
			}
		}
		
		return matrizTransformada;
	}
	
	public int[][] multiplicarValores(int[][] matrizRojoEnX, int[][] matrizRojoEnY) {
 
		int[][] matrizResultado = new int[ancho][alto];

		for (int f = 0; f < ancho; f++) {
			for (int g = 0; g < alto; g++) {

				matrizResultado[f][g] = matrizRojoEnX[f][g]*matrizRojoEnY[f][g];
			}
		}
		
		return matrizResultado;
	}

	public BufferedImage getImagenOriginal() {
		return imagenOriginal;
	}

	public void setImagenOriginal(BufferedImage imagenOriginal) {
		this.imagenOriginal = imagenOriginal;
	}
}