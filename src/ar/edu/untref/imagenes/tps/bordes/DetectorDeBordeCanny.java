package ar.edu.untref.imagenes.tps.bordes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.edu.untref.imagenes.tps.transformations.LinealTransformation;
import ar.edu.untref.imagenes.utils.ConversorImagenes;

public class DetectorDeBordeCanny {
	
	public static BufferedImage supresionNoMaximos(BufferedImage imagenOriginal){
		
		int mascaraX[][] = { { 1, 0, -1 }, { 2, 0, -2 }, { 1, 0, -1 } };
		int mascaraY[][] = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };
		
		int[][] matrizImagenSobelX = aplicarMascara(imagenOriginal, mascaraX);
		int[][] matrizImagenSobelY  = aplicarMascara(imagenOriginal, mascaraY);
		
		int[][] matrizMagnitudDeBorde = sintetizar(matrizImagenSobelX, matrizImagenSobelY);
		int[][] matrizDeAngulos = obtenerAnguloDelGradiente(matrizImagenSobelX, matrizImagenSobelY);
		
		int[][] matrizSupresionNoMaximosSinTransformar = aplicarSupresionNoMaximos(matrizMagnitudDeBorde, matrizDeAngulos);
		
		int[][] matrizSupresionNoMaximosConTransformacionLineal = LinealTransformation.aplicarTransformacionLineal(matrizSupresionNoMaximosSinTransformar);
		
		return ConversorImagenes.convertirMatrizEnImagen(matrizSupresionNoMaximosConTransformacionLineal, imagenOriginal.getWidth(), imagenOriginal.getHeight());
	}
	
	private static int[][] aplicarSupresionNoMaximos(int[][] matrizMagnitudDeBorde, int[][] matrizDeAngulos) {

		for (int i = 1; i < matrizMagnitudDeBorde.length - 1; i++) {
			for (int j = 1; j < matrizMagnitudDeBorde[0].length - 1; j++) {
				if(matrizMagnitudDeBorde[i][j] != 0){
					
					int[] pixelesDireccionales = new int[2];
					int magnitud = matrizMagnitudDeBorde[i][j]; 
					
					if(matrizDeAngulos[i][j] == 0){
						
						pixelesDireccionales = obtenerPixelesDeMagnitud0Grados(matrizMagnitudDeBorde,i,j);
						
					}else if(matrizDeAngulos[i][j] == 45){
						
						pixelesDireccionales = obtenerPixelesDeMagnitud45Grados(matrizMagnitudDeBorde,i,j);
						
					}else if (matrizDeAngulos[i][j] == 90){
						
						pixelesDireccionales = obtenerPixelesDeMagnitud90Grados(matrizMagnitudDeBorde,i,j);
						
					}else if(matrizDeAngulos[i][j] == 135){
						
						pixelesDireccionales = obtenerPixelesDeMagnitud135Grados(matrizMagnitudDeBorde,i,j);
						
					}
					
					if(pixelesDireccionales[0] > magnitud || pixelesDireccionales[1] > magnitud){
						
						matrizMagnitudDeBorde[i][j] = 0;
						
					}
				}
			}
		}
			
		return matrizMagnitudDeBorde;
	}

	private static int[] obtenerPixelesDeMagnitud135Grados(int[][] matrizMagnitudDeBorde, int i, int j) {
		int[] pixeles = new int[2];
		
		pixeles[0] = matrizMagnitudDeBorde[i-1][j+1];
		pixeles[1] = matrizMagnitudDeBorde[i+1][j-1];

		return pixeles;
	}

	private static int[] obtenerPixelesDeMagnitud90Grados(int[][] matrizMagnitudDeBorde, int i, int j) {
		
		int[] pixeles = new int[2];
		
		pixeles[0] = matrizMagnitudDeBorde[i][j-1];
		pixeles[1] = matrizMagnitudDeBorde[i][j+1];

		return pixeles;
	}

	private static int[] obtenerPixelesDeMagnitud45Grados(int[][] matrizMagnitudDeBorde, int i, int j) {
		
		int[] pixeles = new int[2];
		
		pixeles[0] = matrizMagnitudDeBorde[i+1][j-1];
		pixeles[1] = matrizMagnitudDeBorde[i-1][j+1];

		return pixeles;
	}

	private static int[] obtenerPixelesDeMagnitud0Grados(int[][] matrizMagnitudDeBorde, int i, int j) {
		
		int[] pixeles = new int[2];
		
		pixeles[0] = matrizMagnitudDeBorde[i-1][j];
		pixeles[1] = matrizMagnitudDeBorde[i+1][j];

		return pixeles;
	}

	public static int[][] sintetizar(int[][] matrizEnX, int[][] matrizEnY) {
		
		int[][] matrizSitetizada  = new int[matrizEnX.length][matrizEnX[0].length]; 
		
		for (int i=0; i<matrizEnX.length ;i++){
			for (int j=0; j<matrizEnX[0].length ;j++){
				matrizSitetizada[i][j] = (int) Math.hypot(matrizEnX[i][j], matrizEnY[i][j]);
			}
		}
		return matrizSitetizada;
	}
	
	private static int[][] obtenerAnguloDelGradiente(int[][] matrizSobelX,int[][] matrizSobelY){

		int[][] matrizDeAngulos  = new int[matrizSobelX.length][matrizSobelX[0].length];
		
		for (int i=0; i<matrizSobelX.length ;i++){
			for (int j=0; j<matrizSobelX[0].length ;j++){
				
				float angulo = (float) Math.atan((float) matrizSobelX[i][j] / matrizSobelY[i][j]);
				double grados = Math.toDegrees(angulo);
                
                if ( grados < 0){
                	grados = 180 + grados;
                }

                matrizDeAngulos[i][j] = acomodarGrados(grados);
			}
		}

		return matrizDeAngulos;
	}
	
	private static int acomodarGrados(double grados) {

		int anguloFinal = 0;
		
		if ( grados >= 22.5 && grados <= 67.5 ){
			
			anguloFinal = 45;
			
		} else if ( grados >= 67.5 && grados <= 112.5 ){
			
			anguloFinal = 90;
			
		} else if ( grados >= 112.5 && grados <= 157.5 ){
			
			anguloFinal = 135;
			
		}

		return anguloFinal;
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
				// Iterar la máscara
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


}
