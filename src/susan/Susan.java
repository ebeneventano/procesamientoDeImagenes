package susan;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Smallest Univaluate Segment Assimilating Nucleus
 * 
 * Detector de bordes y esquinas
 * 
 * @author frodriguez
 * 
 */
public class Susan {

	private static final int TAMANIO_MASCARA = 7;
	
	// Se cuentan sólo los píxeles de la imágen circular
	private static final int CANTIDAD_PIXELES_MASCARA = 37;
	
	private int[][] mascara;
	private double umbralT = 27.0;
	
	private int pixelNegro = new Color(0, 0, 0).getRGB();
	private int pixelBlanco = new Color(255, 255, 255).getRGB();
	private int pixelRojo = new Color(255, 0, 0).getRGB();
	
	/**
	 * Si el resultado es aprox 0, no corresponde a borde ni esquina.
	 * Si el resultado es aprox 0.5, es un borde.
	 * Si el resultado es aprox 0.75 es una esquina.
	 * 
	 * Por lo tanto, se tomó como criterio que cualquier resultado mayor a 0.4, será considerado borde/esquina.
	 */
	private double criterioDeBordeBordeOEsquina = 0.4;
	
	/**
	 * Aplica una máscara circular de 7x7 con el método de Susan.
	 */
	public Susan() {
		
		inicializarMascaraCircular();
	}

	private void inicializarMascaraCircular() {

		this.mascara = new int[TAMANIO_MASCARA][TAMANIO_MASCARA];
		
		int[] primerFila = {0, 0, 1, 1, 1, 0, 0};
		int[] segundaFila = {0, 1, 1, 1, 1, 1, 0};
		
		int[] filaDeSiete1 = new int[TAMANIO_MASCARA];
		int[] filaDeSiete2 = new int[TAMANIO_MASCARA];
		int[] filaDeSiete3 = new int[TAMANIO_MASCARA];
		
		for (int i = 0 ; i < TAMANIO_MASCARA ; i++) {
			filaDeSiete1[i] = 1;
			filaDeSiete2[i] = 1;
			filaDeSiete3[i] = 1;
		}
		
		int[] anteUltimaFila = {0, 1, 1, 1, 1, 1, 0};
		int[] ultimaFila = {0, 0, 1, 1, 1, 0, 0};
		
		this.mascara[0] = primerFila;
		this.mascara[1] = segundaFila;
		this.mascara[2] = filaDeSiete1;
		this.mascara[3] = filaDeSiete2;
		this.mascara[4] = filaDeSiete3;
		this.mascara[5] = anteUltimaFila;
		this.mascara[6] = ultimaFila;
	}
	
	/**
	 * @return imagen binaria que detecta los bordes y esquinas de la imagen original
	 */
	public BufferedImage aplicar(BufferedImage imagenOriginal) {
		
		BufferedImage resultado = new BufferedImage(imagenOriginal.getWidth(),
													imagenOriginal.getHeight(),
													imagenOriginal.getType());
		
		for (int i = 0; i < imagenOriginal.getWidth() ; i++) {
			for (int j = 0; j < imagenOriginal.getHeight() ; j++) {
				resultado.setRGB(i, j, pixelNegro);
			}
		}
		
		int sumarEnAncho = (-1) * (TAMANIO_MASCARA / 2);
		int sumarEnAlto = (-1) * (TAMANIO_MASCARA / 2);
		
		// Iterar la imagen, sacando los bordes.
		for (int i = TAMANIO_MASCARA / 2; i < imagenOriginal.getWidth() - (TAMANIO_MASCARA / 2); i++) {
			for (int j = TAMANIO_MASCARA / 2; j < imagenOriginal.getHeight() - (TAMANIO_MASCARA / 2); j++) {

				
				
				// Tomo el valor del píxel central de la máscara (el (3,3) de la máscara)
				int indiceICentralDeLaImagen = i + sumarEnAncho + (TAMANIO_MASCARA / 2);
				int indiceJCentralDeLaImagen = j + sumarEnAlto + (TAMANIO_MASCARA / 2);
				double valorCentral = new Color(imagenOriginal.getRGB(indiceICentralDeLaImagen, indiceJCentralDeLaImagen)).getRed();
				
				// Iterar la máscara
				int cantidadDePixelesSimilaresAlCentral = 0;
				for(int iAnchoMascara = 0; iAnchoMascara < TAMANIO_MASCARA; iAnchoMascara++) {
					for(int iAltoMascara = 0; iAltoMascara < TAMANIO_MASCARA; iAltoMascara++) {
						
							
						int indiceIDeLaImagen = i + sumarEnAncho + iAnchoMascara;
						int indiceJDeLaImagen = j + sumarEnAlto + iAltoMascara;

						double valor = new Color(imagenOriginal.getRGB(indiceIDeLaImagen, indiceJDeLaImagen)).getRed();
						// Se multiplica el valor leído por la máscara, para sacar los que no pertenezcan a la parte circular.
						valor = valor * this.mascara[iAnchoMascara][iAltoMascara];
						
						if (Math.abs(valor - valorCentral) < umbralT) {
							cantidadDePixelesSimilaresAlCentral++;
						}
						
					}
				}
				// Fin iteración máscara
				
				double s = 1.0 - ((double)cantidadDePixelesSimilaresAlCentral / (double)CANTIDAD_PIXELES_MASCARA);
				
				if (s > criterioDeBordeBordeOEsquina) {
					resultado.setRGB(i, j, pixelBlanco);
					
				} else {
					resultado.setRGB(i, j, pixelNegro);
				}
				
//				// Bordes blancos
//				if (s > 0.4 && s < 0.6) {
//					resultado.setRGB(i, j, pixelBlanco);
//				
//				// Esquinas rojas
//				} else if (s > 0.6 && s < 1.0) {
//					resultado.setRGB(i, j, pixelRojo);
//				
//				// Ni borde, ni esquina
//				} else {
//					resultado.setRGB(i, j, pixelNegro);
//				}
				
				
			}
		}
		
		return resultado;
	}
	
}